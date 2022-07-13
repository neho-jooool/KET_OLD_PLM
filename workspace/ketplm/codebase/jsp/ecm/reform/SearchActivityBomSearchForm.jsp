<%@page import="java.util.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="org.apache.commons.lang.*"%>

<%@page import="wt.epm.EPMDocument"%>
<%@page import="wt.part.WTPart"%>
<%@page import="wt.org.WTUser" %>
<%@page import="wt.session.SessionHelper" %>
<%@page import="wt.lifecycle.*" %>

<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.web.PageControl"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.VersionUtil"%>
<%@page import="e3ps.common.web.ParamUtil" %>
<%@page import="e3ps.edm.beans.EDMHelper" %>
<%@page import="e3ps.bom.common.iba.IBAUtil" %>

<%@page import="ext.ket.part.util.*"%>
<%@page import="ext.ket.bom.query.KETBOMQueryBean"%>
<%@page import="ext.ket.part.base.service.PartBaseHelper"%>
<%@page import="ext.ket.part.base.service.internal.RelatedEpmHandler"%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>


<%
// 로그인 사용자
WTUser user = (WTUser)SessionHelper.manager.getPrincipal();
String loginUserOid = (String)CommonUtil.getOIDString(user);
String loginUserName = user.getFullName();

String epmBomCls = ParamUtil.getParameter(request, "epmBomCls");
if("".equals(epmBomCls)) {
    epmBomCls = "1";//1:child 2:bom
}

String isChodo = ParamUtil.getParameter(request, "isChodo");
String prodMoldCls = ParamUtil.getParameter(request, "prodMoldCls");
String currentEcoNo = ParamUtil.getParameter(request, "currentEcoNo");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "활동추가") %><%--활동추가--%></title>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>

<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    
	
})

</script>
<script language="javascript">
$(document).ready(function(){
	SuggestUtil.bind('PARTNO', 'partNumber', '');
    SuggestUtil.bind('EPMNO', 'epmNumber', '');
});
// 검색
function doSearch() {
    var form = document.forms[0];
    
    // validation
    if(form.partNumber.value == "" && form.partName.value == "" && form.epmNumber.value == "" && form.epmName.value == ""){
          
    	alert("<%=messageService.getString("e3ps.message.ket_message", "03135") %><%--하나 이상의 검색조건을 입력해 주십시오--%>");
        form.partName.focus();
        
        return;
    }
    
    loadData();
}
function checkEnterKey(cls) {
    var eKey = window.event.keyCode;
    if (eKey == "13") {// Enter Key 처리
        doSearch();
    	
    }
}

function loadData() {
    showProcessing();
    
    var form = document.forms[0];
    
    form.cmd.value = "searchPartPopup";
    form.sortColumn.value = "2 ASC";
    form.page.value = "";
    form.totalCount.value = "0";
    
    $("#searchActivityForm").attr("target", "searchResultIFrame");
    $("#searchActivityForm").attr("action", "/plm/servlet/e3ps/SearchMoldEcoServlet");
    $("#searchActivityForm").submit(function(){
        
         $("#searchResultIFrame").load(function() {
           //alert(this);
           hideProcessing();
        });
        
    }).submit();
    
}

function onSelect() {
 
	// IFrame의 자바스크립트 함수 호출
    var $frame = $('#searchResultIFrame');
    var fn = $frame[0].contentWindow['onSelect']; 

    var arr = fn();
    parent.onSelect(arr);
 
}

function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
        trArr = objArr[i];
        $('[name=partNumber]').val(trArr[1]).trigger('change');
    }
}

function selectOneDrawingCallBackFn(arr){
    var no = "";
    for(var i = 0; i < arr.length ; i++){
        var subArr = arr[i];
        if(i != 0){
            no += ",";
        }
        no += subArr[1];
    }

    $('[name=epmNumber]').val(no);
}
</script>

</head>
<body>
<form name="searchActivityForm" id="searchActivityForm" method="post" action="">
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="fromPage" value="">
<input type="hidden" name="page" value="">
<input type="hidden" name="totalCount" value="0">
<input type="hidden" name="sortColumn" value="2 ASC">
<input type="hidden" name="param" value="parent.">
<input type="hidden" name="oid" value="">

<input type="hidden" name="pNums" value="">
<input type="hidden" name="epmBomCls" value="<%=epmBomCls %>">
<input type="hidden" name="workerId" value="<%=loginUserOid %>">
<input type="hidden" name="workerName" value="<%=loginUserName %>">
<input type="hidden" name="isChodo" value="<%=isChodo %>">
<input type="hidden" name="prodMoldCls" value="<%=prodMoldCls %>">
<input type="hidden" name="currentEcoNo" value="<%=currentEcoNo %>">

<table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20">&nbsp;</td>
    <td align="right">
      <table border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSearch();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
              </table>
          </td>
          <td width="5">&nbsp;</td>
          <td>
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSelect();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
              </table>
          </td>
      </tr>
      </table>    
    </td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td class="space5"></td>
  </tr>
</table>      
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
    <td width="*" class="tdwhiteL">
        <input name="partNumber" type="text" class="txt_field" style="width:70%" value="" onKeyPress="javascript:checkEnterKey(2);">
        <a href="javascript:SearchUtil.selectOnePart('selectPartAfterFunc');showProcessing();">
        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
        <a href="javascript:CommonUtil.deleteValue('partNumber');">
        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
    </td>
    <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
    <td width="*" class="tdwhiteL0"><input type="text" name="partName" class="txt_field" style="width:98%" value="" onKeyPress="javascript:checkEnterKey(2);"></td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%></td>
    <td class="tdwhiteL">
        <input name="epmNumber" type="text" class="txt_field" style="width:70%" value="" onKeyPress="javascript:checkEnterKey(2);">
        <a href="javascript:SearchUtil.selectDrawing('selectOneDrawingCallBackFn');">
        <img src="/plm/portal/images/icon_5.png" border="0"></a> 
        <a href="javascript:CommonUtil.deleteValue('epmNumber');">
        <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%></td>
    <td class="tdwhiteL0"><input type="text" name="epmName" class="txt_field" style="width:98%" value="" onKeyPress="javascript:checkEnterKey(2);"></td>
  </tr>
</table>
</form>
<div id="tabBom" style="position:; display:block; z-index:1; ">

    <iframe src="" id="searchResultIFrame" name="searchResultIFrame" width="100%" height="415px" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
    
</div>

</body>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</html>
