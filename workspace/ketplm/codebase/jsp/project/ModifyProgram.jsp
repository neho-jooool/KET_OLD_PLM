<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.outputtype.CustomerTheModelPlan"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="wt.fc.ReferenceFactory"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.TaskPlanComparator"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.project.beans.ProgramManagerHelper"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
  String oid = request.getParameter("oid");
  //Kogger.debug("oid = " + oid ) ;

  ModelPlan mp = null;

  if(oid != null){
    mp = (ModelPlan)CommonUtil.getObject(oid);
  }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02399") %><%--자동차일정 수정--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<%@include file="/jsp/common/multicombo.jsp" %>
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

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
}
-->
</style>
<script language="JavaScript">
function ProgramModify(){
  var form  = document.ModifyProgram;
  
  if(form.formtype.value==null || form.formtype.value == "" ){

      alert('형태를 선택하여 주십시오.');
      return;
  }
    if (!checkNumber(form.yield1.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 1) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield1.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 1) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield2.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 2) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield2.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 2) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield3.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 3) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield3.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 3) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield4.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 4) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield4.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 4) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield5.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 5) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield5.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 5) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield6.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 6) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield6.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 6) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield7.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 7) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield7.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 7) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield8.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 8) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield8.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 8) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield9.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 9) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield9.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 9) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber(form.yield10.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00019", 10) %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
      return;
    }

    if (!checkNumber2(form.yield10.value)){
      alert('<%=messageService.getString("e3ps.message.ket_message", "00018", 10) %><%--{0}년차 생산량을 0이상의 값을 입력하여 주십시오--%>');
      return;
    }
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03330") %><%--자동차일정을 수정하시겠습니까?--%>")) {
      return;
    }

    var url = "/plm/jsp/project/ActionProgram.jsp";

    form.action = url;
    form.target ='_self';
    form.submit();
  }



//********************************************************************
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

//*********************************************************************
  function isValidDate(obj, maxLength) {
      var retVal = true;
      var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
     //document.ModifyProgram.duration.value = "";

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
    alert(obj.value + " " + msg);
  }

  function displayModelDesc(mObj){
    setSelectModel(mObj);
  }
  function setSelectModel(mObj) {
    form = document.ModifyProgram;

    if(mObj.value != '') {
      selectSearchModel(mObj.value);
    }
  }

// str은 0~9까지 숫자만 가능하다.
function checkNumber(str) {
    var flag=true;
    if (str.length > 0) {
        for (i = 0; i < str.length; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                flag=false;
            }
        }
    }
    return flag;
}

// 0이상의 값
function checkNumber2(str) {
    var flag=true;
    if (str.length > 0) {
            if (str.charAt(0) < '0') {
                flag=false;
            }
        }
    return flag;
}

function gotoView(oid){
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03386") %><%--자동차일정 수정을 취소 하시겠습니까?--%>")) {
      return;
    }

  document.location.href="/plm/jsp/project/ViewProgram.jsp?oid="+oid;
}

var processType = "";
//멀티 속성 가져오기
function addProcessM(type, depth, viewType) {
	processType = type;
    var form = document.forms[0];
    var mode = "multi";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+type+"&command=select&mode="+mode+"&viewType="+viewType+"&invokeMethod=acceptProcessM";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0, width=680px, height=500px";
    if(viewType == 'noTree'){
        
    }else{
        opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0, width=860px, height=480px";
    }
    
    window.open(url, 'acceptProcessM', opts);
}

//고객처 추가
function acceptProcessM(arrObj){
  var type = processType;
  var subArr;
  var form = document.forms[0];

  var targetTable = document.getElementById(type);
  var subArr;
  //var chkTag = document.all.item("processoid");
  var form = document.forms[0];

  //targetTable.deleteRow(1);

    for(var i = 0; i < arrObj.length; i++) {
      subArr = arrObj[i];
      if(isDuplicateCheckBox('uOid', subArr[0])) {
        continue;
      }
      //alert(subArr[0]);
      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      //checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.width = 20;
      newTd.valign = "top";
      newTd.innerHTML = "<input type=\"checkbox\" name=\"uOid\" value=\"" + subArr[0] + "\"></input><input type='hidden' name='sOid' value='" + subArr[0] + "'>";

      //Code | Name
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.width = 110;
      newTd.height = "100%";
      newTd.valign = "top";
      newTd.innerText = subArr[2];

/*       //납입처 Table
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerHTML = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" id=\"" + subArr[0] + "\"></table>"; */
    }
}

function isDuplicateCheckBox(chk_name, chk_value) {
  var chkTag = document.all.item(chk_name);//eval("document.forms[0]." + membertag);
  if(chkTag == null || chkTag == 'undefined') {
    return false;
  }

  var chkLen = chkTag.length;
  if(chkLen) {
    for(var i = 0; i < chkLen; i++) {
      if(chkTag[i].value == chk_value) {
        return true;
      }
    }
  }else {
    if(chkTag.value == chk_value) {
      return true;
    }
  }

  return false;
}

function onDelete(){
  if(!checkedCheck()){
    alert('<%=messageService.getString("e3ps.message.ket_message", "01698") %><%--삭제 할 고객사를 선택 하십시오--%>');
    return;
  }

  form = document.forms[0];

  len = form.uOid.length;
  //alert(len);
  if(len) {
    for(var i = len - 1; i >= 0; i--) {
      if(form.uOid[i].checked == true) {
        onDeleteTableRow('SUBCONTRACTOR', 'uOid', form.uOid[i].value);
      }
    }
  }else{
    onDeleteTableRow('SUBCONTRACTOR', 'uOid', form.uOid.value);
  }
}

function onDeleteTableRow(tableid, chk_name, chk_value) {
  targetTable = document.getElementById(tableid);
  var chkTag = document.all.item(chk_name);

  var chkLen = chkTag.length;
  if(chkLen) {
    for(var i = 0; i < chkLen; i++) {
      if(chkTag[i].value == chk_value) {
        targetTable.deleteRow(i+1);
        return;
      }
    }
  }else {
    if(chkTag.value == chk_value) {
      targetTable.deleteRow(1);
      return;
    }
  }
}

function checkedCheck() {
  form = document.forms[0];
  if(form.uOid == null) {
    return false;
  }

  len = form.uOid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(form.uOid[i].checked == true) {
        return true;
      }
    }
  }else {
    if(form.uOid.checked == true) {
      return true;
    }
  }

  return false;

}

function checkAll() {
  form = document.forms[0];
  if(form.uOid == null) {
    return;
  }

  len = form.uOid.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      form.uOid[i].checked = form.chkAll.checked;
    }
  }
  else {
    form.uOid.checked = form.chkAll.checked;
  }
}

$(document).ready(function(){
    // Calener field 설정
    CalendarUtil.dateInputFormat('oemEndDate');
});

</script>
</head>
<body>
<form name='ModifyProgram' method="post">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<input type=hidden name=cmd value=Modify>
<input type=hidden name=oid value="<%=oid %>">
<input type=hidden name=creatorName value="%>">
<table style="width: 100%; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02399") %><%--자동차일정 수정--%></td>
                          </tr>
                        </table>
                    </td>
                    <td width="176" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
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
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01118") %><%--기본--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:ProgramModify();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"  onclick="javascript:gotoView('<%=oid%>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
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
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td width="240" class="tdwhiteL0" colspan="3"><%=((OEMProjectType)mp.getCarType()).getMaker().getParent().getName()%></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
          <td width="240" class="tdwhiteL"><%=((OEMProjectType)mp.getCarType()).getMaker().getName()%></td>
          <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
          <td width="240" class="tdwhiteL0"><%=mp.getCarType().getName() %></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01744") %><%--상세 차종명--%></td>
          <td class="tdwhiteL"><input type="text" name="modelname" class="txt_field"  style="width:98%" id="textfield" value="<%=mp.getModelName()==null?"":mp.getModelName()%>"></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03223") %><%--형태--%></td>
          <td class="tdwhiteL0"><select name="formtype" class="fm_jmp" style="width:98%">

          <%
          String mpName = "";
          if(mp.getFormType() != null){
           mpName = mp.getFormType().getName();
          }

          %>
          <option value="" <%if(mpName.equals("")) {%>selected<%}%>>[ <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%> ]</option>
          <%
    String type = "FORMTYPE";
    HashMap map = new HashMap();
    map.put("type", type);
    QuerySpec qs1 = NumberCodeHelper.getCodeQuerySpec(map);

    QueryResult qr1 = PersistenceHelper.manager.find(qs1);

    while(qr1.hasMoreElements()){
      Object[] o = (Object[]) qr1.nextElement();
        NumberCode nCode = (NumberCode) o[0];
        if(nCode.getName().equals("-")){
            continue;
        }
    %>
              <option value="<%=nCode.getPersistInfo().getObjectIdentifier().toString()%>"
              <%if(nCode.getName().equals(mpName)) {%>selected<%}%> ><%=nCode.getName() %></option>
        <%
    }
        %>
        </td>
        </tr>
        <tr>
           <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02400") %><%--자동차사 설계담당--%></td>
           <td class="tdwhiteL" colspan="3"><input type="text" name="person" class="txt_field"  style="width:98%" id="textfield" value="<%=mp.getPerson()==null?"":mp.getPerson()%>">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>

   <table border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addProcessM('SUBCONTRACTOR', 0, 'noTree');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00859") %>  <%=messageService.getString("e3ps.message.ket_message", "02861") %><%--접점고객--%></a></td>
       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
         <td width="5">&nbsp;</td>
       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00859") %>  <%=messageService.getString("e3ps.message.ket_message", "01690") %></a></td>
       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
    </tr>
     </table>

      <div style="width:100%;height:73px;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">
    <table border="0" cellspacing="0" cellpadding="0" width="100%" id="SUBCONTRACTOR">
    <tr>
      <td width="20" class="tdblueM"><input name="chkAll" type="checkbox" onClick="javascript:checkAll();"></td>
      <td width= "760" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%></td>
      </tr>
    <%
        QuerySpec qs2 = new QuerySpec();
        int idx1 = qs2.appendClassList(CustomerTheModelPlan.class, true);

        long longValue = CommonUtil.getOIDLongValue(oid);

        SearchCondition sc1 =  new SearchCondition(CustomerTheModelPlan.class, "roleBObjectRef.key.id", "=", longValue);
        qs2.appendWhere(sc1, new int[]{idx1});
        QueryResult qr2 = PersistenceHelper.manager.find(qs2);
        CustomerTheModelPlan link = null;
        String SUBCONTRACTOR = "";
        String code = "";
    int qsize = qr2.size();
        while(qr2.hasMoreElements())
        {
          Object[] objs = (Object[])qr2.nextElement();
          link = (CustomerTheModelPlan)objs[0];
          SUBCONTRACTOR = link.getCustomer().getName();
          code =CommonUtil.getOIDString(link.getCustomer());
        %>
        <tr>
      <td class="tdwhiteM0" >
      <input type="checkbox" name="uOid" value="<%=code%>" >
      <input type='hidden' name='sOid' value="<%=code%>" >
      </td>
      <td class="tdwhiteL"><%=SUBCONTRACTOR%></td>
    </tr>
        <%
        }
        %>

    </table>
    </div>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01781") %><%--생산량(단위:천대)--%></td>
          <td align="right">&nbsp;</td>
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
          <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016") %><%--{0}년차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 1) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 2) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 3) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 4) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 5) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 6) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 7) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 8) %><%--{0}차--%></td>
          <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 9) %><%--{0}차--%></td>
          <td width="70" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00046", 10) %><%--{0}차--%></td>
        </tr>
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01779") %><%--생산대수--%></td>
          <td class="tdwhiteR">
            <input type="text" name="yield1" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield1()==null?"":mp.getYield1() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield2" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield2()==null?"":mp.getYield2() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield3" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield3()==null?"":mp.getYield3() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield4" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield4()==null?"":mp.getYield4() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield5" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield5()==null?"":mp.getYield5() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield6" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield6()==null?"":mp.getYield6() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield7" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield7()==null?"":mp.getYield7() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield8" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield8()==null?"":mp.getYield8() %>">
          </td>
          <td class="tdwhiteR">
            <input type="text" name="yield9" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield9()==null?"":mp.getYield9() %>">
          </td>
          <td class="tdwhiteR0">
            <input type="text" name="yield10" class="txt_field"  style="width:50px" id="textfield3" align="center" value="<%=mp.getYield10()==null?"":mp.getYield10() %>">
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></td>
          <td align="right">&nbsp;</td>
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
      <%

      QuerySpec qs = new QuerySpec();
    int idx = qs.appendClassList(OEMProjectType.class, true);

    String aa = ((OEMProjectType)mp.getCarType()).getMaker().getPersistInfo().getObjectIdentifier().getStringValue();

    Long makerOid = (Long)CommonUtil.getOIDLongValue(aa);

    ReferenceFactory rf = new ReferenceFactory();
    SearchCondition sc3 = new SearchCondition(OEMProjectType.class,"makerReference.key.id","=", makerOid);

    qs.appendWhere(sc3, new int[]{idx});
    qs.appendAnd();

    SearchCondition sc4 = new SearchCondition(OEMProjectType.class, "isDisabled", SearchCondition.IS_FALSE);
    qs.appendWhere(sc4, new int[] {idx});

    qs.appendAnd();
    SearchCondition sc2 = new SearchCondition(OEMProjectType.class, "oemType", "=", "CUSTOMEREVENT");
    qs.appendWhere(sc2, new int[] {idx});

    ClassAttribute ca = new ClassAttribute(OEMProjectType.class,"code");
    qs.appendOrderBy(new OrderBy(ca, false), new int[] { idx });

    QueryResult qr = PersistenceHelper.manager.find( qs );
    String customName = "";
    String customOid = "";
    int plan = 0 ;
    while(qr.hasMoreElements()){
      Object[] o = (Object[])qr.nextElement();
      OEMProjectType oType = (OEMProjectType) o[0];
      String oTypeOid = oType.getPersistInfo().getObjectIdentifier().getStringValue();
      String endDate = ProgramManagerHelper.getOEMDate(oTypeOid, mp);

    %>
      <td width='150' class='tdblueL'>&nbsp;<%=oType.getName()%>
      <input type='hidden' name=oemtypeOid value="<%=oTypeOid %>"></td>
    <%

      if(qr.size()==plan+1){
        %>
          <td width='240' colspan='3' class='tdwhiteL0'>
          <input name="oemEndDate<%=plan%>" value='<%=endDate%>' class=txt_field style='width:180' maxlength=15  onChange='javascript:isValidDate(this, 8);' />&nbsp;
          </td>
        <%
        }else if(plan%2==1){
        %>

           <td width='240' class='tdwhiteL0'>
           <input name="oemEndDate<%=plan%>" value='<%=endDate%>' class=txt_field style='width:180' maxlength=15  onChange='javascript:isValidDate(this, 8);' />
           </td>
        <%
        }else{
        %>
           <td width='240' class='tdwhiteL'>
           <input name="oemEndDate<%=plan%>" value='<%=endDate%>' class=txt_field style='width:180' maxlength=15  onChange='javascript:isValidDate(this, 8);' />
           </td>
        <%
        }
        if( plan%2==1){
        %>
           </tr>
           <tr>
        <%
        }
        if(o.length==plan){
        %>
           </tr>
          <%
        }
        plan++;
        %>
        <%
    }
        %>
        </table>
    <input type=hidden name=countPlan value="<%=plan%>">
        </td></tr>
  </tr>
  <!-- <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr> -->
</table>
</form>
</body>
</html>
