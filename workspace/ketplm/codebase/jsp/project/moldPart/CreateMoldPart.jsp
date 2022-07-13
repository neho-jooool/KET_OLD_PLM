<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01071") %><%--금형부품관리 등록--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/multicombo.jsp"%>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script>
$(document).ready(function(){
    
    SuggestUtil.bind('DIENO', 'dieNumber');
    
    SuggestUtil.bind('USER', 'partProcess', 'temppartProcess');
    
    CalendarUtil.dateInputFormat('completeDate');
    
    CalendarUtil.dateInputFormat('exhibitDate');
    
    
    $('[name=dieNumber]').change(function(){
        search();
    })
});

function selBox(msg){
  partType = document.getElementsByName("partType");
  mType = document.getElementsByName("mType");
  endDate = document.getElementsByName("endDate");
  actionType = document.getElementsByName("actionType");
  transferDate = document.getElementsByName("transferDate");
  etc = document.getElementsByName("etc");

  if(msg == 'on'){
    for( i = 0; i < partType.length; i++){
      partType[i].style.display = '';
      partType[i].value = "사내";
      mType[i].style.display = '';
      mType[i].value = "";
      endDate[i].style.display = '';
      actionType[i].style.display = '';
      actionType[i].value = "";
      transferDate[i].style.display = '';
      etc[i].style.display = '';
    }
  }else{
    for( i = 0; i < partType.length; i++){
      partType[i].style.display = 'none';
      partType[i].value = "";
      mType[i].style.display = 'none';
      mType[i].value = "";
      endDate[i].style.display = 'none';
      endDate[i].value = "";
      actionType[i].style.display = 'none';
      actionType[i].value = "";
      transferDate[i].style.display = 'none';
      transferDate[i].value = "";
      etc[i].style.display = 'none';
      etc[i].value = "";
    }
  }
}

function search(){
  if(document.forms[0].dieNumber.value == ""){
    alert('<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>');
    return;
  }
  var param = "";
  dieNumber = document.forms[0].dieNumber.value;
  param = "partNumber=" + dieNumber;
  $("[name=dieNum]").val("(" + dieNumber + ")");
  $("[name=dieNum2").val("(" + dieNumber + ")");
  var url = "/plm/jsp/project/moldPart/MoldPartListAjax.jsp";

  $.post(url,param, function(xml) {
    setList(xml);
  });

}
var ajax = new sack();

function setList(xmlDoc){
  if(listTable.rows.length > 2) {
    for(i = listTable.rows.length; i > 2; i--)
      listTable.deleteRow(i-1);
  }
  
  
  var elements = $(xmlDoc).find("projectInfo");
  var dieNo = $(elements).eq(0).find("dieNo");
  var projectOid = $(elements).eq(0).find("projectOid");
  var partProcesser = $(elements).eq(0).find("partProcesser");
  var partProcesserOid = $(elements).eq(0).find("partProcesserOid");
  var exDate = $(elements).eq(0).find("exDate");
  var planDate = $(elements).eq(0).find("planDate");

  //document.getElementById("dieNum").value = "(" + unescape(dieNo[0].text) + ")";
  //document.getElementById("dieNum2").value = "(" + unescape(dieNo[0].text) + ")";
  document.getElementById("projectOid").value = unescape(projectOid[0].textContent);
  document.getElementById("partProcesser").value = unescape(partProcesser[0].textContent);
  document.getElementById("partProcesserOid").value = unescape(partProcesserOid[0].textContent);

  document.getElementById("partProcess").value = unescape(partProcesser[0].textContent);
  document.getElementById("temppartProcess").value = unescape(partProcesserOid[0].textContent);

  document.getElementById("exhibitDate").value = unescape(exDate[0].textContent);
  document.getElementById("completeDate").value = unescape(planDate[0].textContent);

  var showElements = $(xmlDoc).find("data_info");
  var l_usageOid = $(showElements).eq(0).find("l_usageOid");
  var l_number = $(showElements).eq(0).find("l_number");
  var l_name = $(showElements).eq(0).find("l_name");
  var l_quantity = $(showElements).eq(0).find("l_quantity");
  var l_material = $(showElements).eq(0).find("l_material");


  if(l_usageOid != null && l_usageOid.length > 0) {
    for(var i = 0; i < l_usageOid.length; i++) {

      r_usageOid = unescape(l_usageOid[i].textContent);
      r_number = unescape(l_number[i].textContent);
      r_name =  unescape(l_name[i].textContent);
      r_quantity = unescape(l_quantity[i].textContent);
      r_material = unescape(l_material[i].textContent);

      var row1 = listTable.insertRow();
      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = "<input type=\"checkbox\" name=\"usageOid\" id=\"usageOid\" value=" + r_usageOid + ">";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteL";
      onecell1.title = r_number;
      onecell1.innerHTML = "<div style=\"width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + r_number + "</nobr></div>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteL";
      onecell1.title = r_name;
      onecell1.innerHTML = "<div style=\"width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + r_name + "</nobr></div>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = r_quantity;

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = r_material + "<br>";

      for(var j = 0; j < 6; j++){
        onecell1 = row1.insertCell();
        onecell1.className = "tdwhiteM";
        onecell1.innerHTML = "&nbsp";
      }

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM0";
      onecell1.innerHTML = "&nbsp";
    }
  }else{
    var row1 = listTable.insertRow();
    onecell1 = row1.insertCell();
    onecell1.className = "tdwhiteM0";
    onecell1.colSpan = "12";
    onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00713") %><%--검색한 결과가 없습니다--%>";
  }
  //offLayer('layer0');

//  onLayerClear("layer0content");

//  var layerContent = document.getElementById("layer0content");
//  layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";

  document.forms[0].ecoNumber.value = "";
  document.forms[0].ecoOid.value = "";

  moldTable.style.display = '';
  selectedTable.style.display = 'none';
  ecoTable.style.display = 'none';

  document.forms[0].rList.value = "stop";

}



function selected(){

  ajax.requestFile = "/plm/jsp/project/moldPart/MoldPartSelAjax.jsp";
  ajax.URLString = getPostData(document.forms[0]);
  ajax.onCompletion = selectedList;
  ajax.runAJAX();

}
function isPartClass(){
  partClass = document.getElementsByName("partClass");
  partType = document.getElementsByName("partType");
  len = partClass.length;
  for(i = 0; i < len; i++){
    if(partClass[i].value == "도면정리"){
      partType.value= "";
      partType.disabled = true;
    }
  }
}

function selectedList(){
    xmlDoc = ajax.responseXML;
  if(selListTable.rows.length > 2) {
    for(i = selListTable.rows.length; i > 2; i--)
      selListTable.deleteRow(i-1);
  }

  moldTable.style.display = 'none';
  selectedTable.style.display = '';

  var showElements = $(xmlDoc).find("data_info");
  var l_usageOid = $(showElements).eq(0).find("l_usageOid");
  var l_number = $(showElements).eq(0).find("l_number");
  var l_name = $(showElements).eq(0).find("l_name");
  var l_quantity = $(showElements).eq(0).find("l_quantity");
  var l_material = $(showElements).eq(0).find("l_material");

  if(l_usageOid != null && l_usageOid.length > 0) {
    for(var i = 0; i < l_usageOid.length; i++) {

      r_usageOid = unescape(l_usageOid[i].text);
      r_number = unescape(l_number[i].text);
      r_name =  unescape(l_name[i].text);
      r_quantity = unescape(l_quantity[i].text);
      r_material = unescape(l_material[i].text);


      var row1 = selListTable.insertRow();
      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = i + 1 + "<input type=\"hidden\" name=\"linkOid\" value=\"" + r_usageOid + "\">";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteL";
      onecell1.title = r_number;
      onecell1.innerHTML = "<div style=\"width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + r_number + "</nobr></div>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteL";
      onecell1.title = r_name;
      onecell1.innerHTML = "<div style=\"width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + r_name + "</nobr></div>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = r_quantity;

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = r_material + "<br>";


      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = "<select name=\"partClass\" class=\"fm_jmp\" style=\"width:100%\" onChange=\"javascript:isPartClass();\" ><option value=\"가공\"><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option><option value=\"수정\"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option><option value=\"도면정리\"><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option><option value=\"가공+수정\"><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option></select>";

      onecell7 = row1.insertCell();
      onecell7.className = "tdwhiteM";
      onecell7.innerHTML = "<select name=\"partType\" class=\"fm_jmp\" style=\"width:100%\"><option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"사외\" selected>사외</option><option value=\"사내+사외\"><%=messageService.getString("e3ps.message.ket_message", "01656") %><%--사내+사외--%></option><option value=\"중국\"><%=messageService.getString("e3ps.message.ket_message", "02687") %><%--중국--%></option></select><br>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = "&nbsp;";

      onecell9 = row1.insertCell();
      onecell9.className = "tdwhiteM";
      onecell9.innerHTML = "&nbsp;";

      onecell10 = row1.insertCell();
      onecell10.className = "tdwhiteM";
      onecell10.innerHTML = "&nbsp;";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = "&nbsp;";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM0";
      onecell1.innerHTML = "&nbsp;";
    }
    document.forms[0].rList.value = "pass";

  }else{

    var row1 = selListTable.insertRow();
    onecell1 = row1.insertCell();
    onecell1.className = "tdwhiteM0";
    onecell1.colSpan = "12";
    onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01818") %><%--선택한 목록이 없습니다--%>";
    document.forms[0].rList.value = "stop";
  }

//  len = document.forms[0].createType.length;
//  for(i = 0; i < len; i++){
//    if(document.forms[0].createType[i].checked && document.forms[0].createType[i].value == '부품공정'){
//      selBox('on');
//    }else{
//      selBox('off');
//    }
//  }

}
function back(){
  document.forms[0].rList.value = "stop";
  moldTable.style.display = '';
  selectedTable.style.display = 'none';
}
function checkAll(obj, selectObj) {
  if(selectObj) {
    var chkLen = selectObj.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        selectObj[i].checked = obj.checked;
      }
    }else{
      selectObj.checked = obj.checked;
    }
  }
}

function dieNoInput(tObj, vstr, ltdlen) {
    //if(!document.forms[0].cnameCheck.checked){
      //return;
    //}

  form = document.forms[0];

  txtObj = tObj;
  //valueObj = document.getElementById(vstr);

  var dpjtcode = txtObj.value;
  //valueObj.value = '';

  if(dpjtcode.length < ltdlen) {
    //offLayer('layer0');
    return;
  }

  if (window.event.keyCode == 13) {
    set_number = true;
  }
  else {
    set_number = false;
  }

  rectObj = tObj.getBoundingClientRect();
  onLayer('layer0', rectObj.left, rectObj.bottom, 250, lheight);

  form = document.forms[0];
  var param = "dieNo=" + txtObj.value;

  var url = "/plm/jsp/project/moldPart/MoldProjectSearchAjax.jsp";


  ajax.requestFile = url;
  ajax.URLString = param;
  ajax.onCompletion = setLayerMember;
  ajax.runAJAX();

  //callServer(url, setLayerMember);
  //postCallServer(url, param, setLayerMember, false);
}

function setLayerMember(req) {
    //alert(ajax.response);
  var xmlDoc = ajax.responseXML;
  var showElements = $(xmlDoc).find("message");
  var msg = $(showElements).eq(0).find("l_message")[0].textContent;
  if(msg == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03094") %><%--프로젝트 정보를 가져오는 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
    return;
  }

  var showElements = $(xmlDoc).find("data_info");
  var l_code = $(showElements).eq(0).find("l_projectOid");
  var l_name = $(showElements).eq(0).find("l_dieNo");

  /*
  if(set_number && l_code.length == 1) {
    valueObj.value = decodeURIComponent(l_oid[0].textContent);
    txtObj.value = decodeURIComponent(l_name[0].textContent);
    offLayer('layer0');
    return;
  }*/

  var htmlStr = "";
  if(l_code != null && l_code.length > 0) {
    for(var i = 0; i < l_code.length; i++) {
      r_code = decodeURIComponent(l_code[i].textContent);
      r_name = decodeURIComponent(l_name[i].textContent);


      s_name = r_name;
      if(s_name.length > 20) {
        s_name = s_name.substring(0, 17) + "...";
      }
      /*
      v_code = r_code;
      if(set_number != null && set_number == 'true') {
        v_code = v_code.substring(2);
      }
      */
      //J-12345678 > 12345678
      htmlStr +="<li title='"+r_name+"'><a href='#' onclick=\"javascript:setLayerData('" + r_name  + "' , '" + r_code + "');\">"+ s_name +"</a></li>";
    }
  }
  else {
    htmlStr += "<li style='text-align:center;'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></li>";
  }

  $('#layer0content').html(htmlStr);
  //var layerContent = document.getElementById("layer0content");
  //layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";
}

function setLayerData(dieNo, projectOid) {
  document.forms[0].dieNumber.value = dieNo;
  search();

}

/* 부품 공정 */
function addMember(rname) {
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }

  //document.getElementById('requester').value = "";
  acceptMember(rname, attacheMembers);
}

function acceptMember(rname, objArr) {
  if(objArr.length == 0) {
    return;
  }

  var keyName = document.getElementById("temp" + rname);
  var displayName = document.getElementById(rname);

  /*
    subArr[0] = chkobj[i].value;//wtuser oid
    subArr[1] = chkobj[i].poid;//people oid
    subArr[2] = chkobj[i].doid;//dept oid
    subArr[3] = chkobj[i].uid;//uid
    subArr[4] = chkobj[i].sname;//name
    subArr[5] = chkobj[i].dname;//dept name
    subArr[6] = chkobj[i].duty;//duty
    subArr[7] = chkobj[i].dutycode;//duty code
    subArr[8] = chkobj[i].email;//email
  */

  var nonUserArr = new Array();
  for(var i = 0; i < objArr.length; i++) {
    infoArr = objArr[i];
    displayName.value = infoArr[4];
    keyName.value = infoArr[0];

  }
}

function ecoSearch(){
  if(document.forms[0].dieNumber.value == ''){
    alert('<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>');
    document.forms[0].dieNumber.focus();
    return;
  }

  var url="/plm/jsp/project/moldPart/ECOSearch.jsp";
  openOtherName(url, "popup", 500, 530, "status=yes,scrollbars=no,resizable=yes");


  var param = "";
  dieNumber = document.forms[0].dieNumber.value;
  ecoOid = document.forms[0].ecoNumber.value;//document.forms[0].ecoOid.value;
  param = "partNumber=" + dieNumber + "&ecoOid=" + ecoOid;

  var url = "/plm/jsp/project/moldPart/MoldPartListAjax.jsp";

  $.post(url,param, function(xml) {
    ecoList(xml);
  });

}


function ecoList(xmlDoc){

  if(ecoListTable.rows.length > 2) {
    for(i = ecoListTable.rows.length; i > 2; i--)
      ecoListTable.deleteRow(i-1);
  }

  var elements = $(xmlDoc).find("projectInfo");
  var dieNo = $(elements).eq(0).find("dieNo");
  var projectOid = $(elements).eq(0).find("projectOid");
  var partProcesser = $(elements).eq(0).find("partProcesser");
  var partProcesserOid = $(elements).eq(0).find("partProcesserOid");

  var ecoOid = $(elements).eq(0).find("ecoOid");


  document.getElementById("dieNum3").value = "(" + unescape($(dieNo)[0].textContent) + ")";
  document.getElementById("projectOid").value = unescape($(projectOid)[0].textContent);
  document.getElementById("partProcesser").value = unescape($(partProcesser)[0].textContent);
  document.getElementById("partProcesserOid").value = unescape($(partProcesserOid)[0].textContent);

  var showElements = $(xmlDoc).find("data_info");
  var l_usageOid = $(showElements).eq(0).find("l_usageOid");
  var l_number = $(showElements).eq(0).find("l_number");
  var l_name = $(showElements).eq(0).find("l_name");
  var l_quantity = $(showElements).eq(0).find("l_quantity");
  var l_material = $(showElements).eq(0).find("l_material");
   var l_partClass = $(showElements).eq(0).find("l_partClass");

  if(l_usageOid != null && l_usageOid.length > 0) {
    list = l_usageOid.length;
    for(var i = 0; i < l_usageOid.length; i++) {

      r_usageOid = unescape(l_usageOid[i].textContent);
      r_number = unescape(l_number[i].textContent);
      r_name =  unescape(l_name[i].textContent);
      r_quantity = unescape(l_quantity[i].textContent);
      r_material = unescape(l_material[i].textContent);
      r_partClass= unescape(l_partClass[i].textContent);

      var row1 = ecoListTable.insertRow();
      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = list + "<input type=\"hidden\" name=\"linkOid\" value=\"" + r_usageOid + "\">";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteL";
      onecell1.title = r_number;
      onecell1.innerHTML = "<div style=\"width:105;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + r_number + "</nobr></div>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteL";
      onecell1.title = r_name;
      onecell1.innerHTML = "<div style=\"width:125;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + r_name + "</nobr></div>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = r_quantity+"&nbsp;";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML = r_material + "<br>";

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";


      if("가공" == r_partClass){
      onecell1.innerHTML =
      "<select name=\"partClass\" class=\"fm_jmp\" style=\"width:100%\">"+
      "<option value=\"가공\" selected ><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>"+
      "<option value=\"수정\"  ><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>"+
      "<option value=\"도면정리\"  ><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option>"+
      "<option value=\"가공+수정\" ><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option></select>";
      }else if("수정" == r_partClass){
      onecell1.innerHTML =
      "<select name=\"partClass\" class=\"fm_jmp\" style=\"width:100%\">"+
      "<option value=\"가공\" ><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>"+
      "<option value=\"수정\" selected><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>"+
      "<option value=\"도면정리\"  ><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option>"+
      "<option value=\"가공+수정\" ><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option></select>";
      }else if("도면정리" == r_partClass){
      onecell1.innerHTML =
      "<select name=\"partClass\" class=\"fm_jmp\" style=\"width:100%\">"+
      "<option value=\"가공\"  ><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>"+
      "<option value=\"수정\"  ><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>"+
      "<option value=\"도면정리\" selected ><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option>"+
      "<option value=\"가공+수정\" ><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option></select>";
      }else if("가공+수정" == r_partClass){
      onecell1.innerHTML =
      "<select name=\"partClass\" class=\"fm_jmp\" style=\"width:100%\">"+
      "<option value=\"가공\"  ><%=messageService.getString("e3ps.message.ket_message", "00571") %><%--가공--%></option>"+
      "<option value=\"수정\"  ><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></option>"+
      "<option value=\"도면정리\"  ><%=messageService.getString("e3ps.message.ket_message", "01292") %><%--도면정리--%></option>"+
      "<option value=\"가공+수정\" selected ><%=messageService.getString("e3ps.message.ket_message", "00572") %><%--가공+수정--%></option></select>";
      }

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM";
      onecell1.innerHTML ="<select name=\"partType\" class=\"fm_jmp\" style=\"width:100%\"><option value=\"사내\"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option><option value=\"사외\" selected>사외</option><option value=\"사내+사외\"><%=messageService.getString("e3ps.message.ket_message", "01656") %><%--사내+사외--%></option></select><br>";


      for(var j = 0; j < 4; j++){
        onecell1 = row1.insertCell();
        onecell1.className = "tdwhiteM";
        onecell1.innerHTML = "&nbsp";
      }

      onecell1 = row1.insertCell();
      onecell1.className = "tdwhiteM0";
      onecell1.innerHTML = "&nbsp";

      list--;
    }
    document.forms[0].rList.value = "pass";
  }else{
    var row1 = ecoListTable.insertRow();
    onecell1 = row1.insertCell();
    onecell1.className = "tdwhiteM0";
    onecell1.colSpan = "12";
    onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00713") %><%--검색한 결과가 없습니다--%>";
    document.forms[0].rList.value = "stop";
  }
  //offLayer('layer0');

//  onLayerClear("layer0content");

//  var layerContent = document.getElementById("layer0content");
//  layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";

  moldTable.style.display = 'none';
  selectedTable.style.display = 'none';
  ecoTable.style.display = '';

}

function onSave(){
  if(!checkList()) {
    return;
  }


  ajax.requestFile = "/plm/jsp/project/moldPart/MoldCheckAjax.jsp?projectOid=" + document.forms[0].projectOid.value;

  ajax.onCompletion = saveConfirm;
  ajax.runAJAX();


}

function saveConfirm(){
  xmlDoc = ajax.responseXML;

  var showElements = $(xmlDoc).find("data_info");

  var message = $(showElements).eq(0).find("l_message");






  text = unescape(message[0].text);

  if(confirm(text + " <%=messageService.getString("e3ps.message.ket_message", "03267") %><%--{0}를 생성하시겠습니까?--%>")){
    document.forms[0].action="/plm/jsp/project/moldPart/MoldPartAction.jsp";
    document.forms[0].submit();
  }
}

function checkList(){
  var form = document.forms[0];

  if(form.dieNumber.value == ''){
    alert('<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>');
    form.dieNumber.focus();
    return false;
  }

  if(form.partProcess.value == ''){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01584") %><%--부품공정을 입력하세요--%>');
    form.partProcess.focus();
    return false;
  }

  if(form.rList.value != 'pass'){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01314") %><%--등록 가능한 목록이 없습니다.--%>');
    return false;
  }

  return true;
}

function delEco(){
  document.getElementById("ecoNumber").value = "";
  document.getElementById("ecoOid").value = "";

  search();

}


/*****  날짜 check 시작  *******/
//문자열 일괄전환 함수
function funcReplaceStrAll(org_str, find_str, replace_str) {
  var pos = org_str.indexOf(find_str);
  while(pos != -1) {
      pre_str  = org_str.substring(0, pos);
      post_str = org_str.substring(pos + find_str.length, org_str.length);
      org_str  = pre_str + replace_str + post_str;
      pos = org_str.indexOf(find_str);
  }
  return org_str;
}

//*******************************************************************
//년월 입력시 마지막 일자
function  getEndOfMonthDay( yy, mm ) {
  var max_days=0;
  if(mm == 1) {
      max_days = 31 ;
  } else if(mm == 2) {
      if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
      else                                                         max_days = 28;
  }
  else if (mm == 3)   max_days = 31;
  else if (mm == 4)   max_days = 30;
  else if (mm == 5)   max_days = 31;
  else if (mm == 6)   max_days = 30;
  else if (mm == 7)   max_days = 31;
  else if (mm == 8)   max_days = 31;
  else if (mm == 9)   max_days = 30;
  else if (mm == 10)  max_days = 31;
  else if (mm == 11)  max_days = 30;
  else if (mm == 12)  max_days = 31;
  else                return '';

  return max_days;
}

function isValidDate(obj, maxLength) {
  var retVal = true;
  var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
  //document.forms[0].duration.value = "";

  if(obj.value == "") {
    return;
  }

  val=obj.value;
  re=/[^0-9]/gi;
  obj.value=val.replace(re,"");

  var inputDate = funcReplaceStrAll(obj.value,  '년', '');
  inputDate     = funcReplaceStrAll(inputDate,  '월', '');
  inputDate     = funcReplaceStrAll(inputDate,  '일', '');

  var yyyy = inputDate.substring(0, 4);
  var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
  var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

  if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
  if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
  if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

  if(inputDate.length == 8) {
    inputDate = inputDate.substring(0, 8); //미봉책
  }else{
        alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
    return;
  }

  obj.value = yyyy+ "-" +mm+ "-" +dd;
  return true;
}

function viewErrMsg(obj,msg) {
  alert(msg);
}
/*****  날짜 check 끝 *******/

//연계ECO 검색 팝업 호출
function popupRelEco() {

  if(document.forms[0].dieNumber.value == ''){
    alert('<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>');
    document.forms[0].dieNumber.focus();
    return;
  }

  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
  url += "&obj=prodMoldCls^1&obj=mode^single";
  attache = window.showModalDialog(url,window,"help=yes; resizable=yes; status=yes; scroll=no; dialogWidth=740px; dialogHeight:550px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  setRelEco(attache);
}

function setRelEco(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];
  form.ecoOid.value = trArr[0];
  form.ecoNumber.value = trArr[1];

  var param = "";
  dieNumber = document.forms[0].dieNumber.value;
  ecoOid = document.forms[0].ecoOid.value;
  param = "partNumber=" + dieNumber + "&ecoOid=" + ecoOid;

  var url = "/plm/jsp/project/moldPart/MoldPartListAjax.jsp";

  $.post(url,param, function(xml) {
    ecoList(xml);
  });
}
//검색 호출 끝


function delDate(rname){
  document.getElementById(rname).value = "";
}



</script>


<style type="text/css">
<!--
* body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;
} */

-->
</style>
</head>
<%@include file="/jsp/common/processing.html"%>
<body class="popup-background popup-space">
<form method="post" id="frm">
<input type="hidden" name="rList" id="rList" value=""></input>
<input type="hidden" name="projectOid" id="projectOid" value=""></input>
<input type="hidden" name="partProcesser" id="partProcesser" value=""></input>
<input type="hidden" name="partProcesserOid" id="partProcesserOid" value=""></input>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01071") %><%--금형부품관리 등록--%></td>
                
              </tr>
            </table></td>
        </tr>
         <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              <td width="10">&nbsp;</td>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="130"  class="tdblueL">Die No<span class="red">*</span></td>
          <td width="260"  class="tdwhiteL">
            <input type="text" name="dieNumber" class="txt_field"  style="width:200" id="dieNumber" value=""></td>
          <td width="130"  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00190") %><%--ECO 연계--%></td><%//ECOSearch %>
          <td width="260"  class="tdwhiteL0"><input type="text" name="ecoNumber" class="txt_field"  style="width:200" id="ecoNumber" readOnly onClick="javascript:popupRelEco();"><input type="hidden" name="ecoOid" value=""></input>
&nbsp;<a href="#" onclick="javascript:popupRelEco();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="#" onclick="javascript:delEco();">&nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        </tr>
        <tr>
          <td width="130"  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01583") %><%--부품공정--%><span class="red">*</span></td>
          <td class="tdwhiteL">
            <input type="text" name="partProcess" class="txt_field"  style="width:200" id="partProcess">
            <input type="hidden" name="temppartProcess" id="temppartProcess" value="">
&nbsp;<a href="#" onclick="javascript:addMember('partProcess');"><img src="/plm/portal/images/icon_user.gif" border="0"></a></td>
      <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01594") %><%--부품완료 예정일--%></td>
          <td width="260" class="tdwhiteL0">
            <input type="text" name="completeDate" class="txt_field"  style="width:200" id="completeDate" onChange="isValidDate(this, 8);">
<!-- &nbsp;<a href="#" onclick="javascript:showCal(completeDate);"><img src="/plm/portal/images/icon_6.png"border="0"></a>&nbsp; --><!-- <a href="#" onclick="javascript:delDate('completeDate');"> </a>--><img src="/plm/portal/images/icon_delete.gif" onclick="javascript:CommonUtil.deleteDateValue('completeDate');" border="0"></td>
        </tr>
        <tr>
          <!-- <td width="130" class="tdblueL">등록구분<span class="red">*</span></td>
          <td width="260" class="tdwhiteL"><input name="createType" type="radio" class="Checkbox" value="금형개발" checked onclick="javascript:selBox('off');">
금형개발
  <input name="createType" type="radio" class="Checkbox" value="부품공정" onclick="javascript:selBox('on');">
  부품공정
</td> -->
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02880") %><%--출도일--%></td>
          <td width="260" class="tdwhiteL0" colspan="3">
            <input type="text" name="exhibitDate" class="txt_field"  style="width:200" id="exhibitDate" onChange="isValidDate(this, 8);">
<!-- &nbsp;<a href="#" onclick="javascript:showCal(exhibitDate);"><img src="/plm/portal/images/icon_6.png"border="0"></a>&nbsp; <a href="#" onclick="javascript:delDate('exhibitDate');"></a>--><img src="/plm/portal/images/icon_delete.gif" onclick="javascript:CommonUtil.deleteDateValue('exhibitDate');" border="0"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="moldTable" style="display:none">
       <tr><td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png" alt=""></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01568") %><%--부품 목록--%><input type="text" name="dieNum" value="" style="border:0;" readOnly></input></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
              <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:selected();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01379") %><%--목록 선택--%></a></td>
              <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="listTable">
        <tr>
          <td width="30" rowspan="2" class="tdblueM"><input type="checkbox" name="chkAll" onclick="javascript:checkAll(this, document.forms[0].usageOid);"></input></td>
          <td width="110" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%><br>
          <%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
          <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01076") %><%--금형부품설명--%></td>
          <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
          <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
          <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01600") %><%--부품측정--%><br><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
          <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%><br><%=messageService.getString("e3ps.message.ket_message", "02650") %><%--조치사항--%></td>
          <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02326") %><%--인계일--%></td>
          <td width="40" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
        </tr>
        <tr>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
          <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
        </tr>
        </table>
        </td></tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="selectedTable" style="display:none">
       <tr><td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png" alt=""></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01568") %><%--부품 목록--%><input type="text" name="dieNum2" value="" style="border:0;" readOnly></input></td>
            <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:back();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02322") %><%--이전--%></a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
            <td  class="tab_btm2"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="selListTable">
          <tr>
            <td width="30" rowspan="2" class="tdblueM">No</td>
            <td width="110" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%><br>
              <%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
            <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01076") %><%--금형부품설명--%></td>
            <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
              <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
            <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
              <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01600") %><%--부품측정--%><br><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
              <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
              <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%><br><%=messageService.getString("e3ps.message.ket_message", "02650") %><%--조치사항--%></td>
              <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02326") %><%--인계일--%></td>
            <td width="40" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
          </tr>
          <tr>
              <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
            <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
          </tr>

        </table>
       </td></tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="100%" id="ecoTable" style="display:none">
       <tr><td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td width="20"><img src="/plm/portal/images/icon_4.png" alt=""></td>
            <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01568") %><%--부품 목록--%></td><input type="text" name="dieNum3" value="" style="border:0;" readOnly></input>
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
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="ecoListTable">
          <tr>
            <td width="30" rowspan="2" class="tdblueM">No</td>
            <td width="110" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01064") %><%--금형부품--%><br>
              <%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
            <td width="130" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01076") %><%--금형부품설명--%></td>
            <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
              <td width="45" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%></td>
            <td colspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
              <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01600") %><%--부품측정--%><br><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
              <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
              <td width="60" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%><br><%=messageService.getString("e3ps.message.ket_message", "02650") %><%--조치사항--%></td>
              <td width="70" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02326") %><%--인계일--%></td>
            <td width="40" rowspan="2" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
          </tr>
          <tr>
              <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242") %><%--유형--%></td>
            <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
          </tr>

        </table>
       </td></tr>
      </table>

      </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
<%@include file="/jsp/common/AutoCompleteLayer.jsp"%>
</html>
