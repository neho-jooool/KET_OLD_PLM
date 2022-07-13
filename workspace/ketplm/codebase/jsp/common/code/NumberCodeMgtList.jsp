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
                  e3ps.common.code.*,
                  e3ps.common.code.NumberCode,
          e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.PropertiesUtil,
                  e3ps.common.util.KETStringUtil,
                  e3ps.common.web.PageControl"%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="searchCondition" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="pnumbercodetype" class="java.lang.String" scope="request"/>

<jsp:useBean id="codetype" class="java.lang.String" scope="request"/>
<jsp:useBean id="parentOid" class="java.lang.String" scope="request"/>
<jsp:useBean id="depth" class="java.lang.String" scope="request"/>
<jsp:useBean id="command" class="java.lang.String" scope="request"/>
<jsp:useBean id="mode" class="java.lang.String" scope="request"/>
<jsp:useBean id="invokeMethod" class="java.lang.String" scope="request"/>
<jsp:useBean id="valueField" class="java.lang.String" scope="request"/>
<jsp:useBean id="displayField" class="java.lang.String" scope="request"/>
<jsp:useBean id="designTeam" class="java.lang.String" scope="request"/>
<jsp:useBean id="disable" class="java.lang.String" scope="request"/>

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

  e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigEx.getInstance("message");

  boolean isType0 = CommonUtil.isMember("전자사업부");
  boolean isType1 = CommonUtil.isMember("자동차사업부");
  String projectType = "";
  if(isType0){
    projectType = "전자";
  }
  if(isType1){
    projectType = "자동차";
  }

%>
<%@page import="e3ps.sap.ProcessInterface"%>
<%@page import="e3ps.sap.StdInfoInterface"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=NumberCodeType.toNumberCodeType(codetype).getDisplay(messageService.getLocale())%></title>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>

<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>

<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>

<script language="javascript">

$(document).ready(function(){
	treeGridResize("#numberCodeMgtListPopupGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});

function Search(){

  document.forms[0].action = '/plm/servlet/e3ps/NumberCodeMgtListServlet';
  document.forms[0].method ="post";
  document.forms[0].target = "_self";
  document.forms[0].submit();
}


function report(url){
  document.location.href=url + "?oid=<%=parentOid%>"+"&codetype=<%=codetype%>";
}


function erpSync(str){
  //alert(str);
  document.forms[0].sessionid.value='0';
  if(str == "WORKCENTER") {
    document.forms[0].command.value = "WORKCENTER";
  } else if (str == "MODELCODE") {
    document.forms[0].command.value = "MODELCODE";
  } else if (str == "CUSTOMERCODE") {
    document.forms[0].command.value = "CUSTOMERCODE";
  }

  //document.forms[0].command.value ="erp";
  document.forms[0].submit();
}

function pageClose() {
  if(opener || parent.opener) {
    parent.pageClose();
  }
  else {
    window.close();
  }
}

function onKeyPress() {
  if (window.event) {
    if (window.event.keyCode == 13) Search();
  } else return;
}

document.onkeypress = onKeyPress;

</script>

<style type="text/css">
<!--
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 10px;
	margin-bottom: 5px;
	overflow-x: auto;
	overflow-y: auto;
	scrollbar-highlight-color: #f4f6fb;
	scrollbar-3dlight-color: #c7d0e6;
	scrollbar-face-color: #f4f6fb;
	scrollbar-shadow-color: #f4f6fb;
	scrollbar-darkshadow-color: #c7d0e6;
	scrollbar-track-color: #f4f6fb;
	scrollbar-arrow-color: #607ddb;
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
-->
</style>
</head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<body>
    <form>
        <!-- Hidden Value -->
        <input type='hidden' name='codetype' value='<%=codetype%>'> <input type='hidden' name='parentOid' value='<%=parentOid%>'>
        <input type='hidden' name='disable' value='<%=disable%>'>
        <input type='hidden' name='depth' value='<%=depth%>'> <input type='hidden' name='command' value='<%=command%>'> <input
            type='hidden' name='sessionid'> <input type='hidden' name='tpage'> <input type='hidden' name='mode'
            value="<%=mode%>"> <input type='hidden' name='invokeMethod' value="<%=invokeMethod%>"> <input type='hidden'
            name='designTeam' value="<%=designTeam%>">
        <!-- 본문외관테두리 시작 //-->
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td valign="top" style="padding: 0px 0px 0px 0px">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png">
                                <table height="28" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=NumberCodeType.toNumberCodeType(codetype).getDisplay(messageService.getLocale())%></td>
                                        <td width="10"></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <%
                                            if ((!"selectView".equals(command)) && !("select".equals(command))) {
                                        %>
                                        <td><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%> :&nbsp;<input
                                            type=text name="sc_value" value="" class="txt_field" /> &nbsp;&nbsp; <%=messageService.getString("e3ps.message.ket_message", "02909")%><%--코드--%>
                                            :&nbsp;<input type=text name="sc_name" value="" class="txt_field" /> &nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:Search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>&nbsp;</td>
                                        <%
                                            }
                                            if ("select".equals(command)) {
                                        %>
                                        <td><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%> :&nbsp;<input
                                            type=text name="sc_value" class="txt_field" /> &nbsp;&nbsp; <%=messageService.getString("e3ps.message.ket_message", "02909")%><%--코드--%>
                                            :&nbsp;<input type=text name="sc_name" class="txt_field" /> &nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:Search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:addSelectCode();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:selfClose();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <%
                                            }
                                        %>
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
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%; min-height:200px">
                        <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/common/code/NumberCodeMgtListPopupGridLayout.jsp"
                            Layout_Param_Mode="<%=mode%>" Layout_Param_NumberCodeType="<%=codetype%>"
                            Layout_Param_ParentOid="<%=parentOid%>" Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST" Data_Param_Result="<%=tgData%>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File=""></bdo>
                    </div>
                </td>
            </tr>
        </table>
    </form>
    <iframe src="" name="hiddenFrame" scrolling=no frameborder=no marginwidth=0 marginheight=0 style="display: none"></iframe>
</body>
</html>

<script language="JavaScript">
<!--
function addSelectCode() {

  var G = Grids[0];
  var arr = new Array();
  var subarr = new Array();//0:code, 1:name
  var idx = 0;
  var codetype = '<%=codetype%>';
  var projectType = '<%=projectType%>';


  // PLM 1차 개선 프로젝트
  // 07-08 남현승
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
            if(codetype == "SUBCONTRACTOR" && projectType == "자동차" && R[i].Name == 'C1017'){
              alert('코드 C1017 ⇒ 해당 사업부는 선택할수 있는 권한이 없습니다.');
              return;
            }
            if(codetype == "SUBCONTRACTOR" && projectType == "전자" && R[i].Name == 'C0055'){
              alert('코드 C0055 ⇒ 해당 사업부는 선택할수 있는 권한이 없습니다.');
              return;
            }

          subarr = new Array();
          subarr[0] = R[i].oid;// oid
          subarr[1] = R[i].Code;// code
          subarr[2] = R[i].Name;//codename
          subarr[3] = R[i].Description;//codedesc
          subarr[4] = R[i].codelong;//codelong
          arr[idx++] = subarr;
          }
    }else{
            // mode 가 싱글일 경우 적용
      subarr = new Array();
      subarr[0] = R[0].oid;// oid
      subarr[1] = R[0].Code;// code
      subarr[2] = R[0].Name;//codename
      subarr[3] = R[0].Description;//codedesc
      subarr[4] = R[0].codelong;//codelong
      arr[idx++] = subarr;
    }
  }

<%  if(invokeMethod.length() == 0) {  %>
  //modal dialog
  selectModalDialog(arr);
<%  } else {  %>
  //open window
  selectOpenWindow(arr);
<%  }  %>

}

function selectModalDialog(arrObj) {
  window.returnValue= arrObj;
  window.close();
}

<%  if(invokeMethod.length() > 0) {  %>

function openerValueSet(infoArr){
	var target = (opener || parent.opener); 
    if(target.document.getElementById('<%=valueField%>')){
    	target.document.getElementById('<%=valueField%>').value = infoArr[0];   
    }
    
    if(target.document.getElementById('<%=displayField%>')){
    	target.document.getElementById('<%=displayField%>').value = infoArr[2]; 
    }
    
    if(target.document.getElementsByName('<%=valueField%>')[0]){
    	target.document.getElementsByName('<%=valueField%>')[0].value = infoArr[0]; 
    }
    
    if(target.document.getElementsByName('<%=displayField%>')[0]){
    	target.document.getElementsByName('<%=displayField%>')[0].value = infoArr[2]; 
    }
    
}


function selectOpenWindow(arrObj) {
  //...이하 원하는 스크립트를 만들어서 작업...
  if(opener) {
    if(opener.<%=invokeMethod%>) {
      opener.<%=invokeMethod%>(arrObj);
    }else{
    	openerValueSet(arrObj[0]);
    }
  }

  if(parent.opener) {
    if(parent.opener.<%=invokeMethod%>) {
      parent.opener.<%=invokeMethod%>(arrObj);
    }else{
        openerValueSet(arrObj[0]);
    }
  }

  if(opener || parent) {
    parent.pageClose();
  }
  else {
    window.close();
  }
}

<%  }  %>

window.selfClose = function(){
	if(opener || parent) {
	    parent.pageClose();
	}else {
		window.close();
	}
}

function checkAll() {
  form = document.forms[0];
  if(form.oid == null) {
    return;
  }

  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      form.oid[i].checked = form.chkAll.checked;
    }
  }
  else {
    form.oid.checked = form.chkAll.checked;
  }
}

function checkedCheck() {
  form = document.forms[0];
  if(form.oid == null) {
    return false;
  }

  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
        return true;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      return true;
    }
  }

  return false;

}

function selectedCode() {
  var arr = new Array();

  form = document.forms[0];
  if(form.oid == null) {
    return arr;
  }

  var idx = 0;
  len = form.oid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.oid[i].checked == true) {
        arr[idx++] = form.oid[i].value;
      }
    }
  }
  else {
    if(form.oid.checked == true) {
      arr[idx++] = form.oid.value;
    }
  }

  return arr;
}



function modify()
{
  if(!checkedCheck()) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
    return;
  }

  var arr = selectedCode();
  var oid = arr[0];


  var codetype = document.forms[0].codetype.value;
  var param = "?command=modify&codetype="+codetype+"&parentOid=<%=parentOid%>&depth=<%=depth%>&oid="+oid;
  //openWindow("/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param, "modify", "600", "250");

  var url = "/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param;
  openEditPage(url);
}

function openEditPage(url) {
  //var url = "/plm/jsp/common/code/NumberCodeMgtRegister.jsp"+param;
  codeReg = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=450px; dialogHeight:350px; center:yes");
  if(typeof codeReg == "undefined" || codeReg == null) {
    return;
  }

  document.forms[0].codetype.value = codeReg[0];
  document.forms[0].parentOid.value = codeReg[1];

}

function onSubmit(){

  treeReload();

  document.forms[0].sessionid.value = '0';
  document.forms[0].action = "/plm/jsp/common/code/NumberCodeMgtList.jsp";
  document.forms[0].method ="post";
  document.forms[0].target = "_self";
  document.forms[0].submit();
}

function treeReload() {

}



function onErpSend() {
  var codetype = document.forms[0].codetype.value;
  var param = "?command=erpSend&codetype=" + codetype;

  onProgressBar();

  var url = "/plm/jsp/common/code/CodeAjaxAction.jsp" + param;
  callServer(url, onMessage);
}

function onMessage(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00220") %><%--ERP 전송 완료 했습니다--%>");
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
  }
}

//-->
</script>
