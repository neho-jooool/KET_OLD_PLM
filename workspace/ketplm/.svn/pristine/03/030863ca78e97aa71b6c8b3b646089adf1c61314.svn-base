<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>    

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  String date = request.getParameter("tagetDay");

  HashMap map = new HashMap();
  map.put("type", "TRYTYPE");

  QuerySpec qs = NumberCodeHelper.getCodeQuerySpec(map);

  QueryResult result = PersistenceHelper.manager.find(qs);

%>

<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="java.util.HashMap"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="wt.fc.PersistenceHelper"%><html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01014") %><%--금형 Try 일정 등록--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script language=JavaScript src="/plm/portal/js/jquery-1.3.2.min.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<%@include file="/jsp/common/multicombo.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
    
    CalendarUtil.dateInputFormat('planDate', null);
    
    //사용자 suggest
    SuggestUtil.bind('USER', 'requester', 'temprequester');
});
function addMember(rname) {
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    //alert("error");
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

//프로젝트 속성  가져오기 시작
function addProject() {
//  var form = document.forms[0];

  var url = "/plm/jsp/project/trySchdule/TrySearchProject.jsp";

  returnValues = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");

//  if(typeof returnValue == "undefined" || returnValue == null) {
//    alert("..false");
//    return;
//  }
  acceptProject(returnValues);
}



//var $j=jQuery.noConflict();

function acceptProject(arrObj){
  var subArr;
  var mold = document.getElementById('moldProject');
  var tempmold = document.getElementById('tempmold');
  var partNo = document.getElementById('partNo');
  for(var i = 0; i < arrObj.length; i++) {
    subArr = arrObj[i];

    tempmold.value = subArr[0];
    mold.value = subArr[1];

  }
    //partNo.value = "sdadasd";

  var param = "";

  param = "oid=" + tempmold.value;

  var url = "/plm/jsp/project/trySchdule/MoldProjectInfoAjax.jsp";

  $.post(url,param, function(xml) {
    setAttribute(xml);
  });

}

function setAttribute(xmlDoc){

  var partNumber = getTagText(xmlDoc.getElementsByTagName("partNumber")[0]);
  var projectPlanerName = getTagText(xmlDoc.getElementsByTagName("projectPlanerName")[0]);
  var moldPlanerName = getTagText(xmlDoc.getElementsByTagName("moldPlanerName")[0]);

  var moldMakerName = getTagText(xmlDoc.getElementsByTagName("moldMakerName")[0]);
  var projectPlanerName = getTagText(xmlDoc.getElementsByTagName("projectPlanerName")[0]);
  var partName = getTagText(xmlDoc.getElementsByTagName("partName")[0]);
  var materialName = getTagText(xmlDoc.getElementsByTagName("materialName")[0]);
  var materialOid = getTagText(xmlDoc.getElementsByTagName("materialOid")[0]);
  var propertyOid = getTagText(xmlDoc.getElementsByTagName("propertyOid")[0]);
  var thickness = getTagText(xmlDoc.getElementsByTagName("thickness")[0]);
  var width = getTagText(xmlDoc.getElementsByTagName("width")[0]);
  var outsourcingName = getTagText(xmlDoc.getElementsByTagName("outsourcingName")[0]);
  var cavSu = getTagText(xmlDoc.getElementsByTagName("cavSu")[0]);
  var itemType = getTagText(xmlDoc.getElementsByTagName("itemType")[0]);
  var ton = getTagText(xmlDoc.getElementsByTagName("ton")[0]);

    document.forms[0].partNo.value = decodeURIComponent(partNumber);


    document.forms[0].projectPlanerName.value = decodeURIComponent(projectPlanerName);
    document.forms[0].moldPlanerName.value = decodeURIComponent(moldPlanerName);
    document.forms[0].moldMakerName.value = decodeURIComponent(moldMakerName);
    document.forms[0].projectPlanerName.value = decodeURIComponent(projectPlanerName);
    document.forms[0].partName.value = decodeURIComponent(partName);
    document.forms[0].material.value = decodeURIComponent(materialName);
    document.forms[0].tempmaterial.value = decodeURIComponent(materialOid);
    document.forms[0].pOid.value = decodeURIComponent(propertyOid);
    document.forms[0].height.value = decodeURIComponent(thickness);
    document.forms[0].wide.value = decodeURIComponent(width);
    document.forms[0].outsourcingName.value = decodeURIComponent(outsourcingName);
    document.forms[0].cavSu.value = decodeURIComponent(cavSu);
    document.forms[0].itemType.value = decodeURIComponent(itemType);

    document.forms[0].ton.value = decodeURIComponent(ton);
    document.forms[0].moldType.value = decodeURIComponent(itemType);

  if(decodeURIComponent(itemType) == "Mold"){
    document.forms[0].itemType.value = "Cavity";
  }else{
    document.forms[0].itemType.value = "Line*Pcs";
  }

}

function save(){
  if(document.getElementsByName('tempmold')[0].value == ""){
    alert('<%=messageService.getString("e3ps.message.ket_message", "00146") %><%--Die No를 입력하세요--%>');
    return;
  }else if(document.getElementsByName('planDate')[0].value == ""){
    alert("<%=messageService.getString("e3ps.message.ket_message", "00823") %><%--계획일을 입력하세요--%>");
    return;
  }
  document.forms[0].action="/plm/jsp/project/trySchdule/TrySave.jsp";
  document.forms[0].submit();
}

var ajax = new sack();

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
    offLayer('layer0');
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

window.getTagText = function(xmlDoc){
	return xmlDoc.textContent || xmlDoc.text || '';
}


function setLayerMember(req) {
    //alert(ajax.response);
  var xmlDoc = ajax.responseXML;
  //var showElements = xmlDoc.selectNodes("//message");
  //var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
  
  if(msg == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03094") %><%--프로젝트 정보를 가져오는 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
    return;
  }

  /* var showElements = xmlDoc.selectNodes("//data_info");
  var l_code = showElements[0].getElementsByTagName("l_projectOid");
  var l_name = showElements[0].getElementsByTagName("l_dieNo"); */
  
  var l_code = xmlDoc.getElementsByTagName("l_projectOid");
  var l_name = xmlDoc.getElementsByTagName("l_dieNo");

  /*
  if(set_number && l_code.length == 1) {
    valueObj.value = decodeURIComponent(l_oid[0].text);
    txtObj.value = decodeURIComponent(l_name[0].text);
    offLayer('layer0');
    return;
  }*/

  var htmlStr = "";
  if(l_code != null && l_code.length > 0) {
    for(var i = 0; i < l_code.length; i++) {
      r_code = decodeURIComponent(getTagText(l_code[i]));
      r_name = decodeURIComponent(getTagText(l_name[i]));


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
      //J-12345678 -->> 12345678
      htmlStr +="<li title='"+r_name+"'><a href='#' onclick=\"javascript:setLayerData('" + r_name  + "' , '" + r_code + "');\">"+ s_name +"</a></li>";
    }
  }
  else {
    htmlStr += "<li style='text-align:center;'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></li>";
  }

  onLayerClear("layer0content");
  var layerContent = document.getElementById("layer0content");
  layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";
}

function setLayerData(dieNo, projectOid) {
  document.forms[0].moldProject.value = dieNo;
  document.forms[0].tempmold.value = projectOid;
  selectedProject();
  offLayer('layer0');
}

function selectedProject(){
  param = "oid=" + document.forms[0].tempmold.value;

  var url = "/plm/jsp/project/trySchdule/MoldProjectInfoAjax.jsp";

  $.post(url,param, function(xml) {
    setAttribute(xml);
  });

}

function delMember(rname){
  document.getElementById(rname).value = "";
  document.getElementById("temp" + rname).value = "";
}

function delDate(rname){
  document.getElementById(rname).value = "";
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


//원재료 검색 팝업 호출
function selMaterial() {
  var param = "";
  var form = document.forms[0];
  if(form.tempmaterial.value != ""){
    param = "materialOid=" + form.tempmaterial.value + "&pOid=" + form.pOid.value + "&height=" + form.height.value + "&wide=" + form.wide.value + "&moldType=" + form.moldType.value;
  }else{
    param = "moldType=" + form.moldType.value;
  }
  param = param + "&itemTypeCheck=true&isTry=true";
  var url = "/plm/jsp/project/material/SelectMaterialPopup.jsp?" + param;

  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=450px; dialogHeight:250px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  setMaterial(attache);
}

function setMaterial(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  form.tempmaterial.value = trArr[0];
  form.material.value = trArr[1];
  form.pOid.value = trArr[2];
  form.height.value = trArr[3];
  form.wide.value = trArr[4];
  form.shrinkage.value = trArr[5];

//  for(i = 0; i < trArr.length; i++){
//    alert(trArr[i]);
//  }

}
//검색 호출 끝


</script>
</head>
<body>
<form>
<input type="hidden" name="mode" value="save"></input>
<input type="hidden" name="pOid" value=""></input>
<input type="hidden" name="height" value=""></input>
<input type="hidden" name="wide" value=""></input>
<input type="hidden" name="moldType" value=""></input>
<input type="hidden" name="shrinkage" value=""></input>


<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01039") %><%--금형Try 일정 등록--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="15%" class="tdblueL">Die No</td>
                <td width="30%" class="tdwhiteL">
                  <input type="text" name="moldProject" class="txt_field"  style="width:100" id="textfield2" onkeyup="javascript:dieNoInput(this, 'dieNo', 3);">
                  <input type="hidden" name="tempmold" value=""></input>
                </td>
                <td width="15%" class="tdblueL">Part No</td>
                <td width="30%" class="tdwhiteL0"><input type="text" style="width:100%;border:0" name="partNo" value="" readOnly></input></td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00528") %><%--Try 단계--%></td>
                <td width="30%" class="tdwhiteL"><select name="tryType" class="fm_jmp" style="width:100">
                  <%if(result != null && result.size() > 0){
                NumberCode nCode = null;
                String nCodeOid = "";
                String checked = "";
                Object obj[] = null;

                while(result.hasMoreElements()) {
                  obj = (Object[])result.nextElement();
                  nCode = (NumberCode)obj[0];
                  //Kogger.debug("nCode = " + nCode.getName());
              %>
                      <option value="<%=nCode.getName() %>"><%=nCode.getName() %></option>
                      <%
                }
                }
                %>
                </select></td>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02599") %><%--제품설계--%></td>
                <td width="30%" class="tdwhiteL0"><input type="text" style="width:95%;border:0" name="projectPlanerName" value="" readOnly></input></td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01085") %><%--금형설계--%></td>
                <td width="30%" class="tdwhiteL"><input type="text" style="width:95%;border:0" name="moldPlanerName" value="" readOnly></input></td>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01104") %><%--금형조립--%></td>
                <td width="30%" class="tdwhiteL0"><input type="text" style="width:95%;border:0" name="moldMakerName" value="" readOnly></input></td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL">Part Name</td>
                <td width="30%" class="tdwhiteL"><input type="text" style="width:95%;border:0" name="partName" value="" readOnly></input></td>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02207") %><%--원재료--%></td>
                <td width="30%" class="tdwhiteL0">
                  <input type="text" name="material" class="txt_field"  style="width:100" id="textfield" readOnly>
                  <input type="hidden" name="tempmaterial" value=""></input>
                <a href="#" onclick="javascript:selMaterial()"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="#" onclick="javascript:delMember('material');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
                <td width="30%" class="tdwhiteL"><input type="text" style="width:95%;border:0" name="outsourcingName" value="" readOnly></input></td>
                <td width="15%" class="tdblueL"><input type="text" style="width:95%;border:0;text-align:right;text-vlign:center;background-color:#f4f4f4" name="itemType" value="" readOnly></input></td>
                <td width="30%" class="tdwhiteL0"><input type="text" style="width:95%;border:0" name="cavSu" value="" readOnly></input></td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01876") %><%--설비(TON)--%></td>
                <td width="30%" class="tdwhiteL">
                  <input type="text" name="ton" class="txt_field"  style="width:100" id="textfield4">
                </td>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%></td>
                <td width="30%" class="tdwhiteL0">
                  <input type="text" name="quantity" class="txt_field"  style="width:100" id="textfield5">
                </td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02258") %><%--의뢰자--%></td>
                <td width="30%" class="tdwhiteL">
                  <input type="text" name="requester" class="txt_field"  style="width:100" id="textfield6">
                  <input type="hidden" name="temprequester" value=""></input>
                <a href="#" onclick="javascript:addMember('requester');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>&nbsp;<a href="#" onclick="javascript:delMember('requester');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%><span class="red">*</span></td>
                <td width="30%" class="tdwhiteL0">
                  <input type="text" name="planDate" class="txt_field"  style="width: 80px" value="">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('planDate');" style="cursor: hand;">
                  
                </td>
                  
              </tr>
              <tr>
                <td width="15%" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00532") %><%--Try 장소--%></td>
                <td width="85%" colspan="3" class="tdwhiteL0"><input type="text" name="tryPlace" class="txt_field"  style="width:100" id="textfield6"></td>
              </tr>
              <tr>
                <td width="15%" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
                <td width="85%" colspan="3" class="tdwhiteL0" style="height:100"><textarea name="des" class="txt_field" id="textfield3" style="width:100%; height:96%"></textarea></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    <td width="10">&nbsp;</td>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:;" onclick="javascript:self.close()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
<%@include file="/jsp/common/AutoCompleteLayer.jsp"%>
</html>
