<%@page contentType="text/html; charset=UTF-8"%>

<jsp:useBean id="messageService"
	class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="e3ps.common.util.KETObjectUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base target="_self">
<title>Project Template Register Page</title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<!-- jQuery -->
<!-- <script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script> -->
<!-- 자동완성 스크립트 -->
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/extcore/js/shared/commonUtil.js"></SCRIPT>

<style type="text/css">
<!--
/* body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 5px;
} */
-->
</style>
<style type="text/css">

/* jquery autocomplete ui */
.ui-autocomplete { height: 200px;width:160px; overflow-y: scroll; overflow-x: hidden; font-size: 11px;}

</style>

<%
 String divide = request.getParameter("divide");
 String division_ = KETObjectUtil.getCurrentUserGroup();

%>
<%@include file="/jsp/common/processing.html"%>
<script language="JavaScript">
function onMessage(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02779") %><%--처리 완료 했습니다\nWBS 편집 화면으로 이동 합니다--%>');

    showElements = xmlDoc.selectNodes("//tempid");
    var newTmpId = showElements[0].getElementsByTagName("l_tempid")[0].text;
    onMoveEditPage(newTmpId);
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
  }
}

function onMoveEditPage(oid) {
  //parent.document.location.href = "/plm/jsp/project/template/TemplateProjectViewFrm.jsp?oid="+oid;

  var urlA = "/plm/jsp/project/template/TemplateProjectViewLeftFrm.jsp?oid="+oid;
  var urlB = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+oid;
  parent.viewContentPage(urlA, urlB);
}
/*
function onMoveEditPage(tmpoid) {
  //document.location.href = "/plm/jsp/project/template/ProjectTempEditFrame.jsp?oid=" + tmpoid;
  var url = "/plm/jsp/project/template/ProjectTempEditFrame.jsp?oid=" + tmpoid;
  newPopUpPage(url, 'EditPage','1220','800','yes', 'yes');
}
*/
var win = null;
function newPopUpPage(pageUrl,pageName,pageWidth,pageHeight,scroll, resize) {
  leftPosition = (screen.width) ? (screen.width - pageWidth)/2 : 0;
  topPosition = (screen.height) ? (screen.height - pageHeight)/2 : 0;
  pageOption = 'toolbar=no,menubar=no,status=no,titlebar=no';
  pageOption += ',height=' + pageHeight;
  pageOption += ',width=' + pageWidth;
  pageOption += ',top=' + topPosition;
  pageOption += ',left=' + leftPosition;
  pageOption += ',scrollbars=' + scroll;
  pageOption += ',resizable=' + resize;
  win = window.open(pageUrl,pageName,pageOption);
  win.focus();
}

function checkValidate() {
  if(checkField(document.forms[0].name, "Template 명")) {
    return false;
  }

  return true;
}

function tmpCopy() {
  form  = document.forms[0];
  //alert(form.oemTypeRoot.value);
//  if(form.oemTypeRoot.value==''){
    //alert("OEM 타입을 선택해 주시기 바랍니다.");
    //form.name.focus();
    //return;
//  }else{

//    if(form.oemType.value==''){
//      alert("OEM 개발 단계를 선택해 주시기 바랍니다.");
//      form.name.focus();
//      return;
//    }
//  }

  tmplist.style.display = "";
  form = document.forms[0];

  var wname = form.tmpName.value;
  var wcode = "";//form.tmpCode.value;
  var pttype = "";
  if(form.ptType) pttype = form.ptType.value;
  else {
    var divisionValue = "";
    for(i=0; i<form.division.length; i++) {
      if(form.division[i].selected) divisionValue = form.division[i].value;
    }
    if(divisionValue == "자동차사업부") {
      pttype = "4";
    }else {
      pttype = "5";
    }
  }

  form.method = "post";
  form.action= "/plm/jsp/project/template/ProjectTempListTable.jsp?name=" + wname + "&code=" + wcode + "&ptType=" + pttype;
  form.target = "list";
  form.submit();
}

<!-- 2014.07.14 WBS COPY 검색팝업 추가-->
function wbsCopySearchPopup(frm){
	
	var divide = $("#divide").val();
	
	if($("#type").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07111")%>"); //유형을 선택하세요
        return;
    }
    
   <%--  if($("#division").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07110")%>"); //사업부를 선택하세요
        return;
    }
    
    if($("#clientCompany").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07112")%>"); //고객처를 선택하세요
        return;
     }
    
    if($("#devType").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07113")%>"); //개발구분을 선택하세요
        return;
    }
    
    if($("#devStep").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07114")%>"); //개발단계를 선택하세요
        return;
    }
    
    if( divide == "mold"){
        if($("#makeOffice").val() == ''){
            alert("<%=messageService.getString("e3ps.message.ket_message", "07117")%>"); //제작처를 선택하세요
            return;
        } 
        
        if($("#moldType").val() == ''){
            alert("<%=messageService.getString("e3ps.message.ket_message", "07115")%>"); //금형구분을 선택하세요
            return;
        } 
        
        if($("#making").val() == ''){
            alert("<%=messageService.getString("e3ps.message.ket_message", "07116")%>"); //제작구분을 선택하세요
            return;
        }
    }  --%>
   
    if($("#name").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07118")%>"); //표준 WBS Name을 입력하세요
        return;
    }
	
	
    var url = "/plm/servlet/e3ps/SearchProjectTemplateServlet?divide="+divide+"&popup=yes";
    var title = "wbsCopySearchPopup";
    window.open(url, title, "1100", "900", "status=no,scrollbars=yes,resizable=no");
    
}

function tmpRegister(tid) {
	var divide = $("#divide").val();
	var division = $("#division").val();
	var type = $("#type").val();
	<!-- 등록 Validation Check -->

    if(type == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07111")%>");
        return;
    }
    
    /* if($("#division").val() == ''){
        alert("사업부를 선택하세요");
        return;
    }
    
    if($("#clientCompany").val() == ''){
        alert("고객처를 선택하세요");
        return;
     }
    
    if($("#devType").val() == ''){
        alert("개발구분을 선택하세요");
        return;
    }
    
    if($("#devStep").val() == ''){
        alert("개발단계를 선택하세요");
        return;
    }
    
    if(divide == "mold"){
         if($("#makeOffice").val() == ''){
            alert("제작처를 선택하세요");
            return;
        } 
         
        if($("#moldType").val() == ''){
            alert("금형구분을 선택하세요");
            return;
        } 
        
        if($("#making").val() == ''){
            alert("제작구분을 선택하세요");
            return;
        }
    } */
   
    if($("#name").val() == ''){
        alert("<%=messageService.getString("e3ps.message.ket_message", "07118")%>");
        return;
    }
    
    if(type == "검토" && division == "자동차사업부"){
        param = "ptType=4";
    }else if(type == "검토" && division == "전자사업부"){
        param = "ptType=5";
    }else if(type == "제품" && division == "자동차사업부"){
        param = "ptType=1";
    }else if(type == "제품" && division == "전자사업부"){
        param = "ptType=3";
    }else if(type == "제품" && division == "KETS"){
        param = "ptType=7";
    }else if(type == "금형" && division == "자동차사업부"){
        param = "ptType=2";
    }else if(type == "금형" && division == "전자사업부"){
        param = "ptType=6";
    }else if(type == "금형" && division == "KETS"){
        param = "ptType=8";
    }else if(type == "구매품" && division == "자동차사업부"){
        param = "ptType=10";
    }else if(type == "구매품" && division == "전자사업부"){
        param = "ptType=11";
    }else if(type == "구매품" && division == "KETS"){
        param = "ptType=12";
    }else if(type == "업무" && division == "자동차사업부"){
        param = "ptType=13";
    }else if(type == "업무" && division == "전자사업부"){
        param = "ptType=14";
    }
 
    if(tid != null && tid.length > 0) {
        $("#tempid").val(tid);
    }
    showProcessing();
	form.method = "post";
	form.action = "/plm/jsp/project/template/ProjectTempAction.jsp?"+param;
	form.submit(); 
}

<!-- 2014.07.14 초기화 함수 추가 -->
function init(type){
	if(type == 0){
    $("#type option:eq(0)").attr("selected","selected"); //유형
	}
    $("#division option:eq(0)").attr("selected","selected"); //사업부
    $("#clientCompany").val(""); //고객처
    $("#name").val(""); //표준WBS Name
    $("#devType option:eq(0)").attr("selected","selected"); //개발구분
    $("#devStep option:eq(0)").attr("selected","selected"); //개발단계
    $("#makeOffice").val(""); //제작처
    $("#moldType option:eq(0)").attr("selected","selected"); //금형구분
    $("#making option:eq(0)").attr("selected","selected"); //제작구분
}


<!-- 2014.07.14 휴지통 함수 추가 -->
function delValue(id){
	if(id != ""){
	$("#"+id).val("");
	}
	$("#clientCompany").val(""); //고객처
	$("#makeOffice").val(""); //제작처
}

function insertLastUsingBuyer(arrObj){
	$('[name=clientCompany]').val(arrObj[0][2]);
}

<!-- 2014.07.15 기존 최종사용처(고객처) 추가-->
 function addValue(type, depth, viewType) {
	 SearchUtil.selectOneLastUsingBuyerPopup('insertLastUsingBuyer');
	 
/*     var form = document.forms[0];
    var mode = "multi";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode="+mode+"&viewType="+viewType;

    if(viewType == 'noTree')
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=680px; dialogHeight:500px; center:yes");
    else
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=860px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
        return;
    }
    if(type == "SUBCONTRACTOR"){
        acceptProcessM2(returnValue, type);
    }else{
        acceptProcessM(returnValue, type);
    } */
}
 
 <!-- 2014.07.21 협력업체 추가 -->
function selectPartner(){
	    var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
	    openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
    if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
            return;
        }

        //document.getElementById("proteamNo").value = arr[0][0];
        $("#makeOffice").val(arr[0][1]);
}

function acceptProcessM(arrObj, type){
    var clientCompany = arrObj[0];
    $("#clientCompany").val(clientCompany[2]);
} 

  function displayChangeOEM(){
    form = document.forms[0];
    form.method = "post";
    form.submit();

  }

  function displayChange3(){
    if(wbsTable.rows.length > 3) {
      for(i=wbsTable.rows.length; i>3; i--)
        wbsTable.deleteRow(i-1);
    }

    form = document.forms[0];
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
      onecell2.innerHTML = "<input type=hidden name=ptType value=3> <input type=\"radio\" value=\"1\" name=\"productType\" checked/><%=messageService.getString("e3ps.message.ket_message", "01801") %><%--생활가전--%> <input type=\"radio\" value=\"2\" name=\"productType\" /><%=messageService.getString("e3ps.message.ket_message", "03220") %><%--협피치--%>";
    }else{
      ;
    }
  }

  function displayChange2(){
    if(wbsTable.rows.length > 3) {
      for(i=wbsTable.rows.length; i>3; i--)
        wbsTable.deleteRow(i-1);
    }

    form = document.forms[0];
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
      onecell2.innerHTML = "<input type=hidden name=ptType value=1> <input type=\"radio\" value=\"true\" name=\"planType\" checked/><%=messageService.getString("e3ps.message.ket_message", "01842") %><%--설계 有--%> &nbsp;&nbsp;<input type=\"radio\" value=\"false\" name=\"planType\" /><%=messageService.getString("e3ps.message.ket_message", "01841") %><%--설계 無--%>";

      onecell3 = row1.insertCell();
      onecell3.className = "tdblueL";
      onecell3.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "02640") %><%--조립구분--%>";

      onecell4 = row1.insertCell();
      onecell4.className = "tdwhiteL0";
      onecell4.innerHTML = "<input type=\"radio\" value=\"true\" name=\"assembling\" checked/><%=messageService.getString("e3ps.message.ket_message", "02639") %><%--조립 有--%> <input type=\"radio\" value=\"false\" name=\"assembling\" /><%=messageService.getString("e3ps.message.ket_message", "02638") %><%--조립 無--%>";

      var row2 = wbsTable.insertRow();

      onecell1 = row2.insertCell();
      onecell1.className = "tdblueL";
      onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%>";

      onecell2 = row2.insertCell();
      onecell2.className = "tdwhiteL0";
      onecell2.colSpan = "3";
      onecell2.innerHTML = "<input type=\"radio\" value=\"1\" name=\"developType\" checked/><%=messageService.getString("e3ps.message.ket_message", "02476") %><%--전략개발--%> <input type=\"radio\" value=\"2\" name=\"developType\" /><%=messageService.getString("e3ps.message.ket_message", "01963") %><%--수주개발--%> <input type=\"radio\" value=\"3\" name=\"developType\" /><%=messageService.getString("e3ps.message.ket_message", "02128") %><%--연구개발--%> <input type=\"radio\" value=\"4\" name=\"developType\" /><%=messageService.getString("e3ps.message.ket_message", "02865") %><%--추가금형--%>";
    }else if(wbstypeValue == "금형 Project") {
      var row1 = wbsTable.insertRow();

      onecell1 = row1.insertCell();
      onecell1.className = "tdblueL";
      onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01099") %><%--금형제작--%>";

      onecell2 = row1.insertCell();
      onecell2.className = "tdwhiteL0";
      onecell2.colSpan = "3";
      onecell2.innerHTML = "<input type=hidden name=ptType value=2> <input type=\"radio\" value=\"1\" name=\"makeType\" checked/><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%> <input type=\"radio\" value=\"2\" name=\"makeType\" /><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%> <input type=\"radio\" value=\"3\" name=\"makeType\" />OEM";
    }
  }

  function displayChange(){
    if(wbsTable.rows.length > 3) {
      for(i=wbsTable.rows.length; i>3; i--)
        wbsTable.deleteRow(i-1);
    }

    form = document.forms[0];
    var divisionValue = "";
    for(i=0; i<form.division.length; i++) {
      if(form.division[i].selected) divisionValue = form.division[i].value;
    }

    if(divisionValue == "") {
      wbsTypeTd.innerHTML = "&nbsp;";
    }else if(divisionValue == "자동차사업부") {
      wbsTypeTd.innerHTML = divisionValue + " / <select name=\"wbsType\" onchange=\"javascript:displayChange2();\" class=\"fm_jmp\" style=\"width:125\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option  value=\"검토 Project\"><%=messageService.getString("e3ps.message.ket_message", "00717") %><%--검토 Project--%></option><option  value=\"제품 Project\"><%=messageService.getString("e3ps.message.ket_message", "02548") %><%--제품 프로젝트--%></option><option  value=\"금형 Project\"><%=messageService.getString("e3ps.message.ket_message", "01009") %><%--금형 Project--%></option></select>";
    }else {
      wbsTypeTd.innerHTML = divisionValue + " / <select name=\"wbsType\" onchange=\"javascript:displayChange3();\" class=\"fm_jmp\" style=\"width:125\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option  value=\"검토 Project\"><%=messageService.getString("e3ps.message.ket_message", "00717") %><%--검토 Project--%></option><option  value=\"제품 Project\"><%=messageService.getString("e3ps.message.ket_message", "02548") %><%--제품 프로젝트--%></option></select>";
    }
  }

  function exceldown(){
    ;
  }
  function tableChange(){
	  init(1);
      var selectVal =  $("#type option:selected").val();
      $("#stadardWbsName").attr("colspan","5");
      
      if("<%=division_%>" == "KETS"){
          if(selectVal == "제품"){
              $("#division").val("<%=division_%>").attr("selected","selected");
              
              $("#divide").val("product");
              $("#type1").html("<%=messageService.getString("e3ps.message.ket_message", "00583")%>");
              $("#typeAttr1").html('<select id="devType" name="devType" class="fm_jmp" style="width: 160px">'
                      +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                      +'<option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%></option>'
                      +'<option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%></option>'
                      +'<option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%></option>'
                      +'<option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%></option>'
                 +'</select>');
               if($("#tr1").length == 0){
                   $("#tr0").append( '<tr id="tr1">'
                                    +'<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%></td>'
                                    +'<td  class="tdwhiteL"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value="" />'
                                    +'<a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a>'
                                    +'<a href="javascript:delValue();"><img src="/plm/portal/images/icon_delete.gif"></a></td>'
                                    +'<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%></td>'
                                    +'<td  class="tdwhiteL"><select name="devStep" id="devStep"  class="fm_jmp" style="width: 160">'
                                    +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                                    +'<option value="pilot">Pilot</option>'
                                    +'<option value="proto">Proto</option></select></td>'
                                    +'<td  class="tdblueL non-back"></td>'
                                    +'<td  class="tdwhiteL0"></td>'
                                    +'</tr>');
               }else{
                   $("#tr1").html( 
                           '<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><span class="red">*<span></td>'
                           +'<td class="tdwhiteL"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value="" />'
                           +'<a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a>'
                           +'<a href="javascript:delValue("clientCompany");"><img src="/plm/portal/images/icon_delete.gif"></a></td>'
                           +'<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%><span class="red">*</span></td>'
                           +'<td class="tdwhiteL"><select name="devStep" id="devStep"  class="fm_jmp" style="width: 160">'
                           +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                           +'<option value="pilot">Pilot</option>'
                           +'<option value="proto">Proto</option></select></td>'
                           +'<td class="tdblueL non-back"></td>'
                           +'<td class="tdwhiteL0"></td>'
                           );  
               }
               $("#type2").remove();
               $("#type2").removeClass("tdblueL");
               $("#typeAttr2").remove();
               $("#stadardWbsName").attr("class","tdwhiteL0");
          } else{
              $("#division").val("<%=division_%>").attr("selected","selected");
              $("#type1").html("<%=messageService.getString("e3ps.message.ket_message", "02533")%>");
              $("#typeAttr1").html('<td class="tdwhiteL"><input name="makeOffice" class="txt_field" id="makeOffice" value="" /> <a href="javascript:selectPartner();">'
                                  +'<img src="/plm/portal/images/icon_5.png"></a>' 
                                  +'<a href="javascript:delValue();">'
                                  +'<img src="/plm/portal/images/icon_delete.gif"></a></td>');
              
              $("#tr1").remove();
              if(selectVal != ""){
                  var attrHtml = '<tr id="tr1">';
                  if(selectVal == "금형"){
                      $("#divide").val("mold");
                      attrHtml +='<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01019")%></td>'
                          +'<td  class="tdwhiteL"><select name="moldType" id="moldType"  class="fm_jmp" style="width: 160">'
                          +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                          +'<option value="mold">mold</option>'
                          +'<option value="press">press</option>'
                          +'</select></td>';
                  }
                  attrHtml +='<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532")%></td>'
                      +'<td  class="tdwhiteL"><select name="making" id="making"  class="fm_jmp" style="width: 160">'
                      +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                      +'<option value="사내"><%=messageService.getString("e3ps.message.ket_message", "01655")%></option>'
                      +'<option value="외주"><%=messageService.getString("e3ps.message.ket_message", "02184")%></option>'
                      +'</select></td>'
                      +'<td class="tdblueL non-back"></td>';
                  if(selectVal == "구매품"){
                      $("#divide").val("purchase");
                      attrHtml += '<td class="tdwhiteL0"></td><td class="tdwhiteL0"></td>';
                  }
                  attrHtml += '<td class="tdwhiteL0"></td></tr>';
                  $("#tr0").append(attrHtml);
              }
              
              $("#type2").remove();
              $("#type2").removeClass("tdblueL");
              $("#typeAttr2").remove();
              $("#stadardWbsName").attr("class","tdwhiteL0");
          }
      }else{
	      if(selectVal == "검토"){
	          $("#division").val("<%=division_%>").attr("selected","selected");
	          
	          $("#stadardWbsName").attr("colspan","3");
	          $("#divide").val("review");
	          $("#tr1").remove();
	          $("#type1").html("<%=messageService.getString("e3ps.message.ket_message", "00859") %><span class='red'>*</span>");
	          $("#typeAttr1").html('<td  class="tdwhiteL"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value="" />'
	                  +'<a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a>'
	                  +'<a href="javascript:delValue();"><img src="/plm/portal/images/icon_delete.gif"></a></td>');
	          $("#type1").show();
	          $("#typeAttr1").show();
	          $("#stadardWbsName").after('<td  class="tdblueL" id="type2"><%=messageService.getString("e3ps.message.ket_message", "00583") %></td><td width="240" class="tdwhiteL0" id="typeAttr2">'
	                                      +'<select name="devType" id="devType" class="fm_jmp" style="width: 160">'
	                                      +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
	                                      +'<option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%></option>'
	                                      +'<option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%></option>'
	                                      +'<option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%></option>'
	                                      +'<option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%></option>'
	                                      +'</select>'
	                                      +'</td>');
	          /* $("#type2").addClass("tdblueL");
	          $("#typeAttr2").html('<select id="devType" name="devType" class="fm_jmp" style="width: 160px">'
	                              +'<option value="">[선택]</option>'
	                              +'<option value="전략개발">전략개발</option>'
	                              +'<option value="수주개발">수주개발</option>'
	                              +'<option value="연구개발">연구개발</option>'
	                              +'<option value="추가금형">추가금형</option>'
	                              +'</select>'); */
	      }else if(selectVal == "제품"){
	          
	          $("#division").val("<%=division_%>").attr("selected","selected");
	          $("#divide").val("product");
	          $("#type1").html("<%=messageService.getString("e3ps.message.ket_message", "00583")%>");
	          $("#typeAttr1").html('<select id="devType" name="devType" class="fm_jmp" style="width: 160px">'
	                  +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
	                  +'<option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%></option>'
	                  +'<option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%></option>'
	                  +'<option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%></option>'
	                  +'<option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%></option>'
	             +'</select>');
	           if($("#tr1").length == 0){
	               $("#tr0").after( '<tr id="tr1">'
	                                +'<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%></td>'
	                                +'<td  class="tdwhiteL"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value="" />'
	                                +'<a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a>'
	                                +'<a href="javascript:delValue();"><img src="/plm/portal/images/icon_delete.gif"></a></td>'
	                                +'<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%></td>'
	                                +'<td  class="tdwhiteL"><select name="devStep" id="devStep"  class="fm_jmp" style="width: 160">'
	                                +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
	                                +'<option value="pilot">Pilot</option>'
	                                +'<option value="proto">Proto</option></select></td>'
	                                +'<td  class="tdblueL non-back"></td>'
	                                +'<td  class="tdwhiteL0"></td>'
	                                +'</tr>');
	           }else{
	               $("#tr1").html( 
	                       '<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859")%><span class="red">*<span></td>'
	                       +'<td class="tdwhiteL"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value="" />'
	                       +'<a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a>'
	                       +'<a href="javascript:delValue("clientCompany");"><img src="/plm/portal/images/icon_delete.gif"></a></td>'
	                       +'<td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00629")%><span class="red">*</span></td>'
	                       +'<td class="tdwhiteL"><select name="devStep" id="devStep"  class="fm_jmp" style="width: 160">'
	                       +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
	                       +'<option value="pilot">Pilot</option>'
	                       +'<option value="proto">Proto</option></select></td>'
	                       +'<td class="tdblueL non-back"></td>'
	                       +'<td class="tdwhiteL0"></td>'
	                       );  
	           }
	           $("#type2").remove();
	           $("#type2").removeClass("tdblueL");
	           $("#typeAttr2").remove();
	           $("#stadardWbsName").attr("class","tdwhiteL0");
	           
<%-- 	           $("#type1").html("<%=messageService.getString("e3ps.message.ket_message", "00859") %><span class='red'>*</span>");
	           $("#typeAttr1").html('<td  class="tdwhiteL"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value="" />'
	                      +'<a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a>'
	                      +'<a href="javascript:delValue();"><img src="/plm/portal/images/icon_delete.gif"></a></td>'); --%>
	           $("#type1").show();
               $("#typeAttr1").show();
	      } else{
	          $("#division").val("<%=division_%>").attr("selected","selected");
	          $("#type1").html("<%=messageService.getString("e3ps.message.ket_message", "02533")%>");
	          $("#typeAttr1").html('<td class="tdwhiteL"><input name="makeOffice" class="txt_field" id="makeOffice" value="" /> <a href="javascript:selectPartner();">'
	                              +'<img src="/plm/portal/images/icon_5.png"></a>' 
	                              +'<a href="javascript:delValue();">'
	                              +'<img src="/plm/portal/images/icon_delete.gif"></a></td>');
	          
	          $("#tr1").remove();
	          
	          if(selectVal != "" && selectVal != "업무"){
                  var attrHtml = '<tr id="tr1">';
                  if(selectVal == "금형"){
                      $("#divide").val("mold");
                      attrHtml +='<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01019")%></td>'
                          +'<td  class="tdwhiteL"><select name="moldType" id="moldType"  class="fm_jmp" style="width: 160">'
                          +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                          +'<option value="mold">mold</option>'
                          +'<option value="press">press</option>'
                          +'</select></td>';
                  }
                  attrHtml +='<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532")%></td>'
                      +'<td  class="tdwhiteL"><select name="making" id="making"  class="fm_jmp" style="width: 160">'
                      +'<option value=""><%=messageService.getString("e3ps.message.ket_message", "01802")%></option>'
                      +'<option value="사내"><%=messageService.getString("e3ps.message.ket_message", "01655")%></option>'
                      +'<option value="외주"><%=messageService.getString("e3ps.message.ket_message", "02184")%></option>'
                      +'</select></td>'
                      +'<td class="tdblueL non-back"></td>';
                  if(selectVal == "구매품"){
                      $("#divide").val("purchase");
                      attrHtml += '<td class="tdwhiteL0"></td><td class="tdwhiteL0"></td>';
                  }
                  attrHtml += '<td class="tdwhiteL0"></td></tr>';
                  $("#tr0").after(attrHtml);
              }
	          
	          $("#type2").remove();
	          $("#type2").removeClass("tdblueL");
	          $("#typeAttr2").remove();
	          $("#stadardWbsName").attr("class","tdwhiteL0");
	          
	          if(selectVal == "업무"){
	        	  $("#divide").val("work");  
	        	  $("#type1").hide();
	        	  $("#typeAttr1").hide();
	        	  /* $("#type1").remove();
                  $("#type1").removeClass("tdblueL");
                  $("#typeAttr1").remove(); */
	          }
	          
	      }
      }
  }
  
$(document).ready(function(){
	  
	  /* CommonUtil.singleSelect('type',100);
	  CommonUtil.singleSelect('division',100);
	  CommonUtil.singleSelect('devType',100); */
	var divide = "<%=divide%>";
	     
	$("#division").val("<%=division_%>").attr("selected","selected");
	if(divide == "product"){
        $("#type").val("제품");
    }else if(divide == "mold"){
        $("#type").val("금형");
    }else if(divide == "purchase"){
        $("#type").val("구매품");
    }else if(divide == "work"){
        $("#type").val("업무");
    }else{
        $("#type").val("검토");
    }
    tableChange();
	SuggestUtil.bind('CUSTOMEREVENT', 'clientCompany', '');  

	$("#type").change(function(){
		tableChange();
		SuggestUtil.bind('CUSTOMEREVENT', 'clientCompany', ''); 
    });
});
</script>
<style type="text/css">
<!--
/* body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 5px;
} */
-->
</style>
</head>
<body class="popup-background02 popup-space">
	<%@include file="ajaxProgress.html"%>
	<form method="post" enctype="multipart/form-data" name="form">
		<!-- Hidden Value -->
		<input type="hidden" name="command" value="init"> <input
			type="hidden" id="tempid" name="tempid" value=""></input>
	       <input type="hidden" name="popup" value="yes" />
	       <input type="hidden" id="divide" name="divide" value="<%=divide%>" />
		<!-- //Hidden Value -->
		<!-- title제목 시작 //-->
		<table width="100%"  border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td valign="top"><table width="100%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td><table width="100%" height="28" border="0"
									cellspacing="0" cellpadding="0">
									<tr>
										<td width="20"><img src="/plm/portal/images/icon_3.png"></td>
										<td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00554")%><%--WBS 등록--%></td>
										
									</tr>
								</table></td>
						</tr>
						<tr>
							<td class="space10"></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>&nbsp;</td>
							<td align="right"><table border="0" cellspacing="0"
									cellpadding="0">
									<tr>
									    <td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
													<td class="btn_blue"
														background="/plm/portal/images/btn_bg1.gif"><a
														href="javascript:wbsCopySearchPopup(this.form);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07109") %><%--기존 WBS Copy --%></a></td>
													<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
												</tr>
											</table></td>
										<td width="5">&nbsp;</td>
										<td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
													<td class="btn_blue"
														background="/plm/portal/images/btn_bg1.gif"><a
														href="javascript:init(0);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message","02819")%><%--초기화--%></a></td>
													<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
												</tr>
											</table></td>
										<td width="5">&nbsp;</td> 
										<td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
													<td class="btn_blue"
														background="/plm/portal/images/btn_bg1.gif"><a
														href="javascript:tmpRegister();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message","02453")%><%--저장--%></a></td>
													<td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
												</tr>
											</table></td>
										<td width="5">&nbsp;</td>
										<td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue"
                                                        background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
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
					<table border="0" cellspacing="0" cellpadding="0" width="100%"
						id="wbsTable">
						 <tr id="tr0">
						 	<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%>
								<span class="red">*</span></td>
							<td  class="tdwhiteL">
									<select name="type" id="type"  class="fm_jmp" style="width: 150">
										<option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
										<%if(!CommonUtil.isKETSUser()) {%>
										<option value="검토"><%=messageService.getString("e3ps.message.ket_message", "00716")%><%--검토--%></option>
										<%} %>
										<option value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%></option>
										<option value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></option>
										<%-- <option value="구매품">구매품구매품</option> --%>
										<option value="업무">업무</option>
									</select>
						    </td>
							<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
							<td  class="tdwhiteL"><select name="division" id="division"  class="fm_jmp" style="width: 120">
									<option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>]</option>
									<%if(!CommonUtil.isKETSUser()) {%>
                                    <option value="자동차사업부"><%=messageService.getString("e3ps.message.ket_message", "02401")%><%--자동차사업부--%></option>
                                    <option value="전자사업부"><%=messageService.getString("e3ps.message.ket_message", "02483")%><%--전자사업부--%></option>
									<%}else{ %>
									<option value="KETS">KETS</option>
									<%} %>
							</select>
							</td>
						    <td  class="tdblueL" id="type1"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
						    <td  class="tdwhiteL0" id="typeAttr1">
							    <input type="text" class="txt_field" name="clientCompany" id="clientCompany" class="txt_field" value=""  /> 
							    <a href="javascript:addValue();"><img src="/plm/portal/images/icon_5.png"></a> 
							    <a href="javascript:delValue('clientCompany');"><img src="/plm/portal/images/icon_delete.gif"></a>
						    </td>
						</tr>
						<tr>
							<td width=130px; class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표준 WBS Name --%><span class="red">*</span></td>
							<td colspan="5" class="tdwhiteL" id="stadardWbsName"><input name="name" id="name" class="txt_field" style="width: 98%"></td>
						</tr>
						<tr>
							<td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866")%><%--설명--%></td>
							<td colspan="5" class="tdwhiteL0">
								<!-- <textarea name="description" cols="110" rows="3" class="fm_area" onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)" style="width:99%;height:97%;"></textarea></td> -->
								<input type="text" maxlength="100" name="description"
								id="description" value="" class="txt_field" style="width: 98%" />
							</td>
						</tr>
						<tr>
                            <!-- <td width="87" class="tdblueL0">&nbsp;</td> -->
                            
                            <td width="70" class="tdblueL">Excel</td>
                            <td class="tdwhiteL0" colspan="5"><input type="file"
                                name="wbsUpFile"  />&nbsp;&nbsp;<a
                                  href="/plm/jsp/project/template/WBS_Form.xls"><img
                                      src="/plm/portal/images/iocn_excel.png" alt="excel down"
                                      name="leftbtn_02" border="0"></a></td>
                        </tr>
					</table>
					
					<!-- <table border="0" cellspacing="0" cellpadding="0" width="780">
						<tr>
							<td class="space15"></td>
						</tr>
					</table> -->
					<%-- <div id="tmplist" style="display: none">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<!--<td class="titleP">Template 목록</td>-->
								<td align="right" width='70%'>
									<table border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td><strong><img
																src="/plm/portal/images/icon_3.gif" width="10"
																height="10" align="absmiddle">Name :</strong> <input
															name="tmpName" class="txt_field" width="50px" value=''></td>
													</tr>
												</table>
											</td>
											<td>&nbsp;</td>
											<!--td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><b>Code</b> : <input name="tmpCode" class="txt_field" size=10 maxlength='10'  value=''></td>
                    </tr>
                  </table>
                </td-->
											<td>
												<table border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="10"><img
															src="/plm/portal/images/btn_1.gif"></td>
														<td class="btn_blue"
															background="/plm/portal/images/btn_bg1.gif"><a
															href="javascript:tmpCopy();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%>검색</a></td>
														<td width="10"><img
															src="/plm/portal/images/btn_2.gif"></td>
													</tr>
												</table> <!--<a href="javascript:tmpCopy();">
                  <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="검색" width="52" height="20" border="0">
                  </a>-->
											</td>
										</tr>
										<tr>
											<td class="space5"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td><iframe src="" id="list" name="list" frameborder="0"
										width="100%" height="400" leftmargin="0" topmargin="0"
										scrolling="no"></iframe></td>
							</tr>
						</table>
					</div></td> --%>
			</tr>
			<!-- <tr>
				<td height="30" valign="bottom"><iframe
						src="/plm/portal/common/copyright.html" name="copyright"
						width="780" height="24" frameborder="0" marginwidth="0"
						marginheight="0" scrolling="no"></iframe></td>
			</tr> -->
		</table>
	</form>
</body>
</html>
