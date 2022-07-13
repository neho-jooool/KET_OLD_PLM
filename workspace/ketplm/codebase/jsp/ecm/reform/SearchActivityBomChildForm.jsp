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
<%@page import="e3ps.ecm.beans.ProdEcoBeans" %>

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

// ECO의 기본사항탭의 제품 정보
String pNumsParameter = request.getParameter("pNums");
String searchFlag = request.getParameter("searchFlag");
String[] pNums = null; 
if("Y".equals(searchFlag)){
    pNums = StringUtils.splitPreserveAllTokens(pNumsParameter, ",");
}

String epmBomCls = ParamUtil.getParameter(request, "epmBomCls");
if("".equals(epmBomCls)) {
    epmBomCls = "1";//1:child 2:bom
}

String isChodo = ParamUtil.getParameter(request, "isChodo");
String prodMoldCls = ParamUtil.getParameter(request, "prodMoldCls");
String currentEcoNo = ParamUtil.getParameter(request, "currentEcoNo");
currentEcoNo = (currentEcoNo != null && !currentEcoNo.equals("") && currentEcoNo.length() > 4) ? currentEcoNo.substring(4) : "";

boolean isTheFirst = (isChodo != null && isChodo.equalsIgnoreCase("Y")) ? true : false;

ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
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

<script language="javascript">

$(document).on("change", "[name=chkAllSelectObj]", function() {
    var tableId = $(this).val();
    var checked = $(this).prop("checked");
    $("[name=chkSelectObj]").each(function(i) {
        
        if($(this).prop("disabled") == false) {
         $(this).prop("checked", checked);
         $(this).trigger("change");
        }
         
    });
    
});
$(document).on("change", "[name=chkAllSelectObjP]", function() {
    var tableId = $(this).val();
    var checked = $(this).prop("checked");
    $("[name=chkSelectObjP]").each(function(i) {
        
        if($(this).prop("disabled") == false) {
         $(this).prop("checked", checked);
         $(this).trigger("change");
        }
         
    });
    
});
$(document).on("change", "[name=chkAllSelectObjA]", function() {
    var tableId = $(this).val();
    var checked = $(this).prop("checked");
    $("[name=chkSelectObjA]").each(function(i) {
        
        if($(this).prop("disabled") == false) {
         $(this).prop("checked", checked);
         $(this).trigger("change");

        }
         
    });
    
});
$(document).on("change", "[name=chkAllSelectObjC]", function() {
    var tableId = $(this).val();
    var checked = $(this).prop("checked");
    $("[name=chkSelectObjC]").each(function(i) {
        
        if($(this).prop("disabled") == false) {
         $(this).prop("checked", checked);
         $(this).trigger("change");
        }
         
    });
    
});
$(document).on("change", "[name=chkAllSelectObjE]", function() {
    var tableId = $(this).val();
    var checked = $(this).prop("checked");
    $("[name=chkSelectObjE]").each(function(i) {
        
        if($(this).prop("disabled") == false) {
         $(this).prop("checked", checked);
         $(this).trigger("change");
        }
         
    });
    
});


$(document).on("change", "input[name=chkSelectObj]", function() {
    var $OBJ = $(this);
    var pos = -1;
    $("input[name=chkSelectObj]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });

    var checked = $OBJ.prop("checked");
    if(!checked) {  // 부품만 선택할 수 있다는 스펙일 경우
	        
	    if($("input[name=chkSelectObjP]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObjP]:eq("+ pos +")").prop('checked', checked);
	    if($("input[name=chkSelectObjA]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObjA]:eq("+ pos +")").prop('checked', checked);
	    if($("input[name=chkSelectObjC]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObjC]:eq("+ pos +")").prop('checked', checked);
	    if($("input[name=chkSelectObjE]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObjE]:eq("+ pos +")").prop('checked', checked);
	    
    }    
    
});

$(document).on("change", "input[name=chkSelectObjP]", function() {
    var $OBJ = $(this);
    var pos = -1;
    $("input[name=chkSelectObjP]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });

    var checked = $OBJ.prop("checked");
    if(checked) { 
    	if($("input[name=chkSelectObj]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        
    } else {
    	if($("input[name=chkSelectObjA]:eq("+ pos +")").prop('checked') == false 
    		&& $("input[name=chkSelectObjC]:eq("+ pos +")").prop('checked') == false 
    		  && $("input[name=chkSelectObjE]:eq("+ pos +")").prop('checked') == false) 
    	{
    		$("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
    	}
    }    
    
});
$(document).on("change", "input[name=chkSelectObjA]", function() {
    var $OBJ = $(this);
    var pos = -1;
    $("input[name=chkSelectObjA]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });

    var checked = $OBJ.prop("checked");
    if(checked) { 
    	if($("input[name=chkSelectObj]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        
    } else {
        if($("input[name=chkSelectObjP]:eq("+ pos +")").prop('checked') == false 
            && $("input[name=chkSelectObjC]:eq("+ pos +")").prop('checked') == false 
              && $("input[name=chkSelectObjE]:eq("+ pos +")").prop('checked') == false) 
        {
            $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        }
    }    
    
});
$(document).on("change", "input[name=chkSelectObjC]", function() {
    var $OBJ = $(this);
    var pos = -1;
    $("input[name=chkSelectObjC]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });

    var checked = $OBJ.prop("checked");
    if(checked) { 
    	if($("input[name=chkSelectObj]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        
    } else {
        if($("input[name=chkSelectObjP]:eq("+ pos +")").prop('checked') == false 
            && $("input[name=chkSelectObjA]:eq("+ pos +")").prop('checked') == false 
              && $("input[name=chkSelectObjE]:eq("+ pos +")").prop('checked') == false) 
        {
            $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        }
    }    
    
});
$(document).on("change", "input[name=chkSelectObjE]", function() {
    var $OBJ = $(this);
    var pos = -1;
    $("input[name=chkSelectObjE]").each(function(i) {
        if($(this).get(0) == $OBJ.get(0)) {
            pos = i;
            return false;
        }
        
    });

    var checked = $OBJ.prop("checked");
    if(checked) { 
    	if($("input[name=chkSelectObj]:eq("+ pos +")").prop("disabled") == false) $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        
    } else {
        if($("input[name=chkSelectObjP]:eq("+ pos +")").prop('checked') == false 
            && $("input[name=chkSelectObjA]:eq("+ pos +")").prop('checked') == false 
              && $("input[name=chkSelectObjC]:eq("+ pos +")").prop('checked') == false) 
        {
            $("input[name=chkSelectObj]:eq("+ pos +")").prop('checked', checked);
        }
    }    
    
});

//확인
function onSelect() {
	showProcessing(); 
	setTimeout('lfn_onSelect()', 1000);
}

function lfn_onSelect() {
 var form = document.forms[0];
 
 var arr = new Array();
 var idx = 0;
 
 // 부품
 var chkSelectObj = document.getElementsByName("chkSelectObj");
 if(chkSelectObj != null && typeof chkSelectObj != "undefined") {
     var len = chkSelectObj.length;
     
     var epmBomType = document.getElementsByName("epmBomType");
     var codecode = document.getElementsByName("codecode");
     var codename = document.getElementsByName("codename");
     var codedesc = document.getElementsByName("codedesc");
     var codecls = document.getElementsByName("codecls");
     
     var dieNos = document.getElementsByName("dieNo");
     var dieNames = document.getElementsByName("dieName");
     var dieCnts = document.getElementsByName("dieCnt");
     
     var revDis = document.getElementsByName("revDis");
     
     for(var i = 0; i < len; i++) {
         if(chkSelectObj[i].checked) {
             
             var subArr = new Array();
             subArr[0] = epmBomType[i].value;
             subArr[1] = chkSelectObj[i].value;
             
             subArr[2] = codecode[i].value;
             subArr[3] = codename[i].value;
             subArr[4] = codedesc[i].value;
             subArr[5] = form.workerId.value;
             subArr[6] = form.workerName.value;
             subArr[7] = codecls[i].value;
             
             subArr[8] = dieNos[i].value;
             subArr[9] = dieNames[i].value;
             subArr[10] = dieCnts[i].value;
             
             subArr[11] = revDis[i].value;
             subArr[12] = "";
             subArr[13] = "";
             
             arr[idx++] = subArr;
             
             
             // 도면
             var epmArr = onSelectEpm(chkSelectObj[i].value);
             var epmArrLength = (epmArr != null) ? epmArr.length : 0;
             for(var j=0; j < epmArrLength; j++) {
            	 arr[idx++] = epmArr[j];
             }
                          
             
         }
             
     }
     
 }
 
 parent.onSelect(arr);
 
 
 hideProcessing(); 
 
}

function onSelectEpm(chkSelectObj_value) {
	var form = document.forms[0];
	
    var epmArr = new Array();
    var idx = 0;
    
    var chkP = false;
    $("input[name=chkSelectObjP]").each(function(i) {
        if($(this).val() == chkSelectObj_value && $(this).prop("checked")) {
            chkP = true;
        	return false;
        }
    });
    $("input[name=relPartOidP]").each(function(i) {
        if($(this).val() == chkSelectObj_value && chkP) {
        	
            var subArr = new Array();
            subArr[0] = $("input[name=epmBomTypeP]:eq("+ i +")").val();
            subArr[1] = $("input[name=codeoidP]:eq("+ i +")").val();
            subArr[2] = $("input[name=codecodeP]:eq("+ i +")").val();
            subArr[3] = $("input[name=codenameP]:eq("+ i +")").val();
            subArr[4] = $("input[name=codedescP]:eq("+ i +")").val();
            subArr[5] = form.workerId.value;
            subArr[6] = form.workerName.value;
            subArr[7] = $("input[name=codeclsP]:eq("+ i +")").val();
            
            subArr[8] = "";
            subArr[9] = "";
            subArr[10] = "";
            
            subArr[11] = $("input[name=revDisP]:eq("+ i +")").val();
            subArr[12] = $("input[name=relPartOidP]:eq("+ i +")").val();
            subArr[13] = $("input[name=codecategoryP]:eq("+ i +")").val();
            
            
            epmArr[idx++] = subArr;
        
        }
        
    });
    var chkA = false;
    $("input[name=chkSelectObjA]").each(function(i) {
        if($(this).val() == chkSelectObj_value && $(this).prop("checked")) {
        	chkA = true;
            return false;
        }
    });
    $("input[name=relPartOidA]").each(function(i) {
        if($(this).val() == chkSelectObj_value && chkA) {
            
            var subArr = new Array();
            subArr[0] = $("input[name=epmBomTypeA]:eq("+ i +")").val();
            subArr[1] = $("input[name=codeoidA]:eq("+ i +")").val();
            subArr[2] = $("input[name=codecodeA]:eq("+ i +")").val();
            subArr[3] = $("input[name=codenameA]:eq("+ i +")").val();
            subArr[4] = $("input[name=codedescA]:eq("+ i +")").val();
            subArr[5] = form.workerId.value;
            subArr[6] = form.workerName.value;
            subArr[7] = $("input[name=codeclsA]:eq("+ i +")").val();
            
            subArr[8] = "";
            subArr[9] = "";
            subArr[10] = "";
            
            subArr[11] = $("input[name=revDisA]:eq("+ i +")").val();
            subArr[12] = $("input[name=relPartOidA]:eq("+ i +")").val();
            subArr[13] = $("input[name=codecategoryA]:eq("+ i +")").val();
            
            
            epmArr[idx++] = subArr;
        
        }
        
    });    
    var chkC = false;
    $("input[name=chkSelectObjC]").each(function(i) {
        if($(this).val() == chkSelectObj_value && $(this).prop("checked")) {
        	chkC = true;
            return false;
        }
    });
    $("input[name=relPartOidC]").each(function(i) {
        if($(this).val() == chkSelectObj_value && chkC) {
            
            var subArr = new Array();
            subArr[0] = $("input[name=epmBomTypeC]:eq("+ i +")").val();
            subArr[1] = $("input[name=codeoidC]:eq("+ i +")").val();
            subArr[2] = $("input[name=codecodeC]:eq("+ i +")").val();
            subArr[3] = $("input[name=codenameC]:eq("+ i +")").val();
            subArr[4] = $("input[name=codedescC]:eq("+ i +")").val();
            subArr[5] = form.workerId.value;
            subArr[6] = form.workerName.value;
            subArr[7] = $("input[name=codeclsC]:eq("+ i +")").val();
            
            subArr[8] = "";
            subArr[9] = "";
            subArr[10] = "";
            
            subArr[11] = $("input[name=revDisC]:eq("+ i +")").val();
            subArr[12] = $("input[name=relPartOidC]:eq("+ i +")").val();
            subArr[13] = $("input[name=codecategoryC]:eq("+ i +")").val();
            
            
            epmArr[idx++] = subArr;
        
        }
        
    });
    var chkE = false;
    $("input[name=chkSelectObjE]").each(function(i) {
        if($(this).val() == chkSelectObj_value && $(this).prop("checked")) {
        	chkE = true;
            return false;
        }
    });
    $("input[name=relPartOidE]").each(function(i) {
        if($(this).val() == chkSelectObj_value && chkE) {
            
            var subArr = new Array();
            subArr[0] = $("input[name=epmBomTypeE]:eq("+ i +")").val();
            subArr[1] = $("input[name=codeoidE]:eq("+ i +")").val();
            subArr[2] = $("input[name=codecodeE]:eq("+ i +")").val();
            subArr[3] = $("input[name=codenameE]:eq("+ i +")").val();
            subArr[4] = $("input[name=codedescE]:eq("+ i +")").val();
            subArr[5] = form.workerId.value;
            subArr[6] = form.workerName.value;
            subArr[7] = $("input[name=codeclsE]:eq("+ i +")").val();
            
            subArr[8] = "";
            subArr[9] = "";
            subArr[10] = "";
            
            subArr[11] = $("input[name=revDisE]:eq("+ i +")").val();
            subArr[12] = $("input[name=relPartOidE]:eq("+ i +")").val();
            subArr[13] = $("input[name=codecategoryE]:eq("+ i +")").val();
            
            
            epmArr[idx++] = subArr;
        
        }
        
    });
    
    
    return epmArr;
}

//부품 상세조회 팝업
function viewPart(v_poid){
	var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
	openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
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
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:parent.loadData('Y');" onfocus="this.blur();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04430") %>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--확인--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table>
          </td>
          <td width="5">&nbsp;</td>
          <td>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSelect();" onfocus="this.blur();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
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

<div style="position:; width:100%; height:465px; overflow-x:auto; overflow-y:auto;">
            
<table border="0" cellspacing="0" cellpadding="0" width="100%" class="table_border" id="listBomChildTable">
  <tr>
    <td width="25" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00342") %><%--No--%></td>
    <td width="120" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04037") %><%--제품품번--%></td>    
    <td width="120" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
    <td width="90" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01595") %><%--부품유형--%></td>
    <td width="*" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
    <td width="30" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "07120") %><%--Rev.--%></td>
    <td width="60" rowspan="3" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
    <td width="225" colspan="5" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04038") %><%--변경대상--%></td>
    <td width="90"rowspan="3" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "04044") %><%--진행ECO--%></td> 
  </tr>
  
  <%
  if(prodMoldCls == null || prodMoldCls.equals("1")) {
  %>
  <tr>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04039") %><%--부품<BR>(BOM)--%></td>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04040") %><%--제품<BR>도면--%></td>    
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04041") %><%--승인<BR>도면--%></td>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04042") %><%--고객<BR>제출<BR>도면--%></td>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04043") %><%--ECAD<BR>도면--%></td>
  </tr>
  <%
  } else {
  %>
  <tr>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04039") %><%--부품<BR>(BOM)--%></td>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04047") %><%--사출<BR>SET--%></td>    
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04048") %><%--사출<BR>도면--%></td>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04049") %><%--프레스<BR>SET--%></td>
    <td width="45" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "04050") %><%--프레스<BR>도면--%></td>
  </tr>
  <%
  }
  %>
  <tr>
    <td width="45" class="tdblueM"><input type="checkbox" name="chkAllSelectObj" id="chkAllSelectObj"></td>
    <td width="45" class="tdblueM"><input type="checkbox" name="chkAllSelectObjP" id="chkAllSelectObjP"></td>    
    <td width="45" class="tdblueM"><input type="checkbox" name="chkAllSelectObjA" id="chkAllSelectObjA"></td>
    <td width="45" class="tdblueM"><input type="checkbox" name="chkAllSelectObjC" id="chkAllSelectObjC"></td>
    <td width="45" class="tdblueM"><input type="checkbox" name="chkAllSelectObjE" id="chkAllSelectObjE"></td>
  </tr>
      	
	<%
	List<String> productPartNoList = new ArrayList<String>();
	//productPartNoList.add("H320053");  
	 
	int pNumsLength = (pNums != null) ? pNums.length : 0;
	for(int i=0; i < pNumsLength; i++) {    
	    productPartNoList.add(pNums[i]);    
	}
	
	List<Map<String, Object>> bomList = null;
	if(productPartNoList.size() > 0) {
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    
	    // 초도배포일 경우
	    if(isTheFirst) {
		    bomList = ketBOMQueryBean.getWorkingChildPartByProduct(productPartNoList);
	    } 
	    // 설변일 경우
	    else {
		    //bomList = ketBOMQueryBean.getLatestChildPartByProduct2(productPartNoList);
		    bomList = ketBOMQueryBean.getLatestChildPartByProduct(productPartNoList);
	    }
	}
	int bomListSize = (bomList != null) ? bomList.size() : 0;
	if(bomListSize != 0) {
		for(int i=0; i < bomListSize; i++) {
		    Map<String, Object> partMap = (Map<String, Object>) bomList.get(i);
		    
		    // 부품의 최신 리비전을 다시 가져오고 있다.
		    WTPart wtPart = null;
		    
		    // 부품 OID
		    String oid = (String) partMap.get("oid");
		    // 제품 품번
		    String productCode = (String) partMap.get("productCode");
		    // 부품번호
		    String partNo = (String) partMap.get("partNo");
		    // 부품유형
	        String partType1 = (String) partMap.get("partType");
	        // 부품명
		    String partName = (String) partMap.get("partName");
		    
	        // 리비전
	        String rev = (String) partMap.get("rev");
		    // Display 리비전
		    String revDis = (String) partMap.get("revDis");
		    
		    // 상태
		    String state = (String) partMap.get("state");
		    // 모부품번호
		    String parentNo = (String) partMap.get("parentNo");
		    
		    // Die 관련 정보    
		    String die_no = "";
		    String dieCnt = "";
		    String dieName = "";
		    // 제품
		    //if (ext.ket.part.util.PartUtil.isProductType(part.get("typeCode"))) {
		        //die_no = e3ps.common.util.StringUtil.checkNull(e3ps.project.beans.MoldProjectHelper.getDieNo(parentNo));
		        wtPart = (WTPart) CommonUtil.getObject(oid);
		        die_no = PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpRepresentM);
		        
		        dieCnt = e3ps.project.beans.MoldProjectHelper.getDieNoCnt(parentNo);
		    //}
		    // 금형
		    /* 
		    else if ("D".equals(part.get("typeCode"))) {
		        die_no = e3ps.common.util.StringUtil.checkNull(e3ps.ecm.beans.EcmSearchHelper.manager.getRelatedPartNo(parentNo));
		    }
		    */
		    dieName = e3ps.common.util.StringUtil.checkNull(e3ps.ecm.beans.EcmSearchHelper.manager.getRelatedPartName(die_no));
		    
	        // 부품 초도여부
	        String bomFlag = StringUtils.stripToEmpty(PartSpecGetter.getPartSpec(wtPart, PartSpecEnum.SpBOMFlag));
	                
	            
		    // 진행 ECO
		    String ecoIds = "";
	        boolean isAbled = true;
	        
	        ArrayList<wt.change2.WTChangeOrder2> relatedEcoList = e3ps.ecm.beans.EcmUtil.getECOexistEcaBomLink( partNo, rev );
	        int relatedEcoListSize = (relatedEcoList != null) ? relatedEcoList.size() : 0;
	        if(relatedEcoListSize > 0) {
	      
	            RENEW:
	            for(int j=0; j < relatedEcoListSize; j++) {
	                String ecoId = "";
	                State ecoState = null;
	                // SAP I/F의 성공여부
	                boolean isSucessSapInterface = true;
	                    
	                wt.change2.WTChangeOrder2 wtChangeOrder2 = relatedEcoList.get(j);
	                if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETProdChangeOrder) {
	                    e3ps.ecm.entity.KETProdChangeOrder eco = (e3ps.ecm.entity.KETProdChangeOrder) wtChangeOrder2;
	                    ecoState = eco.getLifeCycleState();
	                    ecoId = eco.getEcoId();
	                    isSucessSapInterface = prodEcoBeans.isSucessSapInterface(ecoId);
	                        
	                    ecoId = ecoId.substring(4);    // 'ECO-'를 뺀다.
	                    ecoId = "<a href=\"javascript:openViewEco('"+ ecoId +"');\">"+ ecoId +"</a>";
	                    
	                    if(ecoState.equals(State.toState("APPROVED"))) {
	                        if(isSucessSapInterface) {
	                            continue RENEW; 
	                        } else {
	                            ecoId = "("+ ecoId +")";
	                        }
	                    }
	                    	                    
	                } else if(wtChangeOrder2 instanceof e3ps.ecm.entity.KETMoldChangeOrder) {
	                    e3ps.ecm.entity.KETMoldChangeOrder eco = (e3ps.ecm.entity.KETMoldChangeOrder) wtChangeOrder2;
	                    ecoState = eco.getLifeCycleState();
	                    ecoId = eco.getEcoId();
	                    isSucessSapInterface = prodEcoBeans.isSucessSapInterface(ecoId);
	                    ecoId = ecoId.substring(4);    // 'ECO-'를 뺀다.
	                    ecoId = "<a href=\"javascript:openViewEco('"+ ecoId +"');\">"+ ecoId +"</a>";

                        if(ecoState.equals(State.toState("APPROVED"))) {
                            if(isSucessSapInterface) {
                                continue RENEW; 
                            } else {
                                ecoId = "("+ ecoId +")";
                            }
                        }
                            
	                }
	                
	                ecoIds += ecoId +", ";
	                
	            }   // for(int j=0; j < relatedEcoListSize; j++) {
	            
	        	if(!ecoIds.equals("") && ecoIds.lastIndexOf(", ") > -1) {
	        	    ecoIds = ecoIds.substring(0, ecoIds.lastIndexOf(", "));
	            
	        	}
	        	
	        }   // if(relatedEcoListSize > 0) {
	        
	            
	        // 제품과 금형 페이지와 검색한 부품이 안맞을 경우
	        // 금형에서 호출하고 부품이 일반제품일 경우
	        if((prodMoldCls != null && prodMoldCls.equals("2")) && PartUtil.isProductType(wtPart)) {
	            isAbled = false;
	        } 
	        // 제품에서 호출하고 부품이 금형(일반제품이 아닐)일 경우
	        else if((prodMoldCls == null || prodMoldCls.equals("1")) && !PartUtil.isProductType(wtPart)) {
	            isAbled = false;
	        }
	        
	        if(isAbled) {
		        // 진행 ECO가 보이면 무조건 선택불가, 단 자기것이면 제외
		        if(currentEcoNo == null || currentEcoNo.equals("") || currentEcoNo.equalsIgnoreCase("null")) {
			        if(!ecoIds.equals("")) {
			            isAbled = false;
			        } else {
			            
			            // 초도일 경우 
			            if(isTheFirst) {
			        	    // 작업중이 아니면 선택불가
			        	    if(!rev.equalsIgnoreCase("0") && !state.equals("작업중")) {
			        		    isAbled = false;
			        	    }
			            }
			            // 설변일 경우
			            else {
			            	/* 
			            	if(bomFlag.equals("OLD") && state.equals("작업중")) {
			        		    isAbled = false;
			                            
			        	    }
			        	    */     
			        	    if(isAbled) {
			        		    
			        		    // 부품이든 도면이든 OOTB 리비전이 '0'이면서 작업중인 것은 첨부가능
			        		    if(rev.equalsIgnoreCase("0") && state.equals("작업중")) {
			        			    isAbled = true;
			        		    } else {
			        			    // 승인완료가 아니면 선택불가
			                        if(!state.equals("승인완료")) {
			                            isAbled = false;
			                        }
			                                
			        		    }
			                        
			        	    }
			        	    
			            }
			        }
			        
		        } else {
		            if(!ecoIds.equals("") && ecoIds.lastIndexOf(currentEcoNo) > -1) {
                        isAbled = true;
                    } else {
                	
                	    if(!ecoIds.equals("")) {
                            isAbled = false;
                        } else {
                            
	                        // 초도일 경우 
	                        if(isTheFirst) {
	                            // 작업중이 아니면 선택불가
	                            if(!rev.equalsIgnoreCase("0") && !state.equals("작업중")) {
	                                isAbled = false;
	                            }
	                        }
	                        // 설변일 경우
	                        else {
	                        	/* 
	                        	if(bomFlag.equals("OLD") && state.equals("작업중")) {
				        		    isAbled = false;
				                            
				        	    }
	                        	*/
	                            if(isAbled) {
	                        	
	                                // 부품이든 도면이든 OOTB 리비전이 '0'이면서 작업중인 것은 첨부가능
	                                if(rev.equalsIgnoreCase("0") && state.equals("작업중")) {
	                                    isAbled = true;
	                                } else {
	                                    // 승인완료가 아니면 선택불가
	                                    if(!state.equals("승인완료")) {
	                                        isAbled = false;
	                                    }
	                                            
	                                }
	                                
	                            }
	                        	
	                        }
                        }
                        
                    }
                    		            
		        }
	        }
	        String disabledHtml = (!isAbled) ? "disabled='disabled'" : "";
	        
	        
	        // 도면    
	        Map<String, List<EPMDocument>> epmDocumentMap = PartBaseHelper.service.getRelatedEPMDocByEcoPart(wtPart);
	        List<EPMDocument> epmPList = null;
            List<EPMDocument> epmAList = null;
            List<EPMDocument> epmCList = null;
            List<EPMDocument> epmEList = null;
            
            if( PartUtil.isProductType(wtPart)){ // 일반제품
                epmPList = epmDocumentMap.get(RelatedEpmHandler.EPM_P);
                epmAList = epmDocumentMap.get(RelatedEpmHandler.EPM_A);
                epmCList = epmDocumentMap.get(RelatedEpmHandler.EPM_C);
                epmEList = epmDocumentMap.get(RelatedEpmHandler.EPM_E);
            } else {
                epmPList = epmDocumentMap.get(RelatedEpmHandler.EPM_MS);
                epmAList = epmDocumentMap.get(RelatedEpmHandler.EPM_M);
                epmCList = epmDocumentMap.get(RelatedEpmHandler.EPM_SS);
                epmEList = epmDocumentMap.get(RelatedEpmHandler.EPM_S);
            }
            
	        int epmPListSize = (epmPList != null) ? epmPList.size() : 0;
	        int epmAListSize = (epmAList != null) ? epmAList.size() : 0;
	        int epmCListSize = (epmCList != null) ? epmCList.size() : 0;
	        int epmEListSize = (epmEList != null) ? epmEList.size() : 0;
	        
    %>          
		<tr>

		  <td class="tdwhiteM"><%=(i+1) %></td>
          <td class="tdwhiteM" title="<%=StringEscapeUtils.escapeHtml(productCode) %>"><div class="ellipsis" style="width:110px;"><nobr><%=productCode %></nobr></div></td>
		  <td class="tdwhiteM" title="<%=StringEscapeUtils.escapeHtml(partNo) %>"><div class="ellipsis" style="width:110px;"><nobr><%=partNo %></nobr></div></td>
		  <td class="tdwhiteM"><%=partType1 %></td>
          <td class="tdwhiteL" title="<%=StringEscapeUtils.escapeHtml(partName) %>"><div class="ellipsis" style="width:170px;"><nobr><%=partName %></nobr></div></td>
		  <td class="tdwhiteM"><a href="javascript:viewPart('<%=oid %>');"><%=revDis %></a></td>
		  <td class="tdwhiteM"><%=state %></td>
          
          <td class="tdwhiteM">
          
    <%
        /* 
        String disabledHtml = "";
        String RETURN_MESSAGE = e3ps.ecm.beans.EcmUtil.existEcaBomLink( partNo, rev );
        //if( !EcmUtil.existEcaBomLink( ketSearchEcoDetailVO.getPartNumber(), ketSearchEcoDetailVO.getPartVersion() ) )
        if( RETURN_MESSAGE != null && !RETURN_MESSAGE.equals("") ) {
            disabledHtml = "disabled='disabled'";
        }
        */
    %>      
            <input type="checkbox" name="chkSelectObj" value="<%=oid %>" <%=disabledHtml %>>
          
          
            <input type="hidden" name="epmBomType" value="2">
            <input type="hidden" name="codename" value="<%=partName %>">
            <input type="hidden" name="codecode" value="<%=partNo %>">
            <input type="hidden" name="codedesc" value="<%=rev %>">
            <input type="hidden" name="codecls" value="">
            
            <input type="hidden" name="dieNo" value="<%=die_no %>">
            <input type="hidden" name="dieName" value="<%=dieName %>">
            <input type="hidden" name="dieCnt" value="<%=dieCnt %>">
            
            <input type="hidden" name="revDis" value="<%=revDis %>">
            <input type="hidden" name="relPartOid" value="<%=oid %>">
            
            
          </td>
          <td class="tdwhiteM">
            <input type="checkbox" name="chkSelectObjP" value="<%=oid %>" <% if(!isAbled || epmPListSize == 0) out.print("disabled=\"disabled\""); %>>
            <%
            for(int j=0; j < epmPListSize; j++) {
        	    EPMDocument epm = epmPList.get(j);
        	    
        	    String epmType = "";
        	    if("PLMSYSTEM".equals(epm.getOwnerApplication().toString())) {
                    epmType = "2D";
                } else {
                    epmType = "3D";
                }

        	    // 도면 유형
        	    String cadCategory = IBAUtil.getAttrValue(epm, EDMHelper.IBA_CAD_CATEGORY);
        	    
            %>
            <input type="hidden" name="epmBomTypeP" value="1">
            <input type="hidden" name="codenameP" value="<%=epm.getName() %>">
            <input type="hidden" name="codecodeP" value="<%=epm.getNumber() %>">
            <input type="hidden" name="codedescP" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="codeclsP" value="<%=epmType %>">
            
            <input type="hidden" name="dieNoP" value="">
            <input type="hidden" name="dieNameP" value="">
            <input type="hidden" name="dieCntP" value="">
            
            <input type="hidden" name="revDisP" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="relPartOidP" value="<%=oid %>">
            <input type="hidden" name="codeoidP" value="<%=CommonUtil.getOIDString(epm) %>">
            <input type="hidden" name="codecategoryP" value="<%=cadCategory %>">
            <%
            }
            %>
          </td>
          <td class="tdwhiteM">
            <input type="checkbox" name="chkSelectObjA" value="<%=oid %>" <% if(!isAbled || epmAListSize == 0) out.print("disabled=\"disabled\""); %>>
            <%
            for(int j=0; j < epmAListSize; j++) {
                EPMDocument epm = epmAList.get(j);
                
                String epmType = "";
                if("PLMSYSTEM".equals(epm.getOwnerApplication().toString())) {
                    epmType = "2D";
                } else {
                    epmType = "3D";
                }

                // 도면 유형
                String cadCategory = IBAUtil.getAttrValue(epm, EDMHelper.IBA_CAD_CATEGORY);
                
            %>
            <input type="hidden" name="epmBomTypeA" value="1">
            <input type="hidden" name="codenameA" value="<%=epm.getName() %>">
            <input type="hidden" name="codecodeA" value="<%=epm.getNumber() %>">
            <input type="hidden" name="codedescA" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="codeclsA" value="<%=epmType %>">
            
            <input type="hidden" name="dieNoA" value="">
            <input type="hidden" name="dieNameA" value="">
            <input type="hidden" name="dieCntA" value="">
            
            <input type="hidden" name="revDisA" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="relPartOidA" value="<%=oid %>">
            <input type="hidden" name="codeoidA" value="<%=CommonUtil.getOIDString(epm) %>">
            <input type="hidden" name="codecategoryA" value="<%=cadCategory %>">
            <%
            }
            %>            
          </td>
          <td class="tdwhiteM">
            <input type="checkbox" name="chkSelectObjC" value="<%=oid %>" <% if(!isAbled || epmCListSize == 0) out.print("disabled=\"disabled\""); %>>
            <%
            for(int j=0; j < epmCListSize; j++) {
                EPMDocument epm = epmCList.get(j);
                
                String epmType = "";
                if("PLMSYSTEM".equals(epm.getOwnerApplication().toString())) {
                    epmType = "2D";
                } else {
                    epmType = "3D";
                }

                // 도면 유형
                String cadCategory = IBAUtil.getAttrValue(epm, EDMHelper.IBA_CAD_CATEGORY);
                
            %>
            <input type="hidden" name="epmBomTypeC" value="1">
            <input type="hidden" name="codenameC" value="<%=epm.getName() %>">
            <input type="hidden" name="codecodeC" value="<%=epm.getNumber() %>">
            <input type="hidden" name="codedescC" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="codeclsC" value="<%=epmType %>">
            
            <input type="hidden" name="dieNoC" value="">
            <input type="hidden" name="dieNameC" value="">
            <input type="hidden" name="dieCntC" value="">
            
            <input type="hidden" name="revDisC" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="relPartOidC" value="<%=oid %>">
            <input type="hidden" name="codeoidC" value="<%=CommonUtil.getOIDString(epm) %>">
            <input type="hidden" name="codecategoryC" value="<%=cadCategory %>">
            <%
            }
            %>            
          </td>
          <td class="tdwhiteM">
            <input type="checkbox" name="chkSelectObjE" value="<%=oid %>" <% if(!isAbled || epmEListSize == 0) out.print("disabled=\"disabled\""); %>>
            <%
            for(int j=0; j < epmEListSize; j++) {
                EPMDocument epm = epmEList.get(j);
                
                String epmType = "";
                if("PLMSYSTEM".equals(epm.getOwnerApplication().toString())) {
                    epmType = "2D";
                } else {
                    epmType = "3D";
                }

                // 도면 유형
                String cadCategory = IBAUtil.getAttrValue(epm, EDMHelper.IBA_CAD_CATEGORY);
                
            %>
            <input type="hidden" name="epmBomTypeE" value="1">
            <input type="hidden" name="codenameE" value="<%=epm.getName() %>">
            <input type="hidden" name="codecodeE" value="<%=epm.getNumber() %>">
            <input type="hidden" name="codedescE" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="codeclsE" value="<%=epmType %>">
            
            <input type="hidden" name="dieNoE" value="">
            <input type="hidden" name="dieNameE" value="">
            <input type="hidden" name="dieCntE" value="">
            
            <input type="hidden" name="revDisE" value="<%=VersionUtil.getMajorVersion(epm) %>">
            <input type="hidden" name="relPartOidE" value="<%=oid %>">
            <input type="hidden" name="codeoidE" value="<%=CommonUtil.getOIDString(epm) %>">
            <input type="hidden" name="codecategoryE" value="<%=cadCategory %>">
            <%
            }
            %>            
          </td>
          
          <td class="tdwhiteM0"><%=ecoIds %></td>
		</tr>
	<%   
		}
		
	} else {
	%>
	    <tr><td colspan="13" class="tdwhiteM0"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "04460") %><%--검색된 항목이 없습니다.--%></font></td></tr>
	<%
	}
	%>
  </table>
</div>

</form>
</body>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</html>
