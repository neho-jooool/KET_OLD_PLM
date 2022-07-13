<%@ page contentType="text/html;charset=UTF-8"%>

<%@include file="/jsp/common/context.jsp"%>

<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "02145") %><%--예산대비 실적 현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {color: #FF0000}

body {
  background-image: url(/plm/portal/images/img_default/background2.gif);
  background-repeat:repeat-x;
  background-color: #ffffff;
  margin-top: 24px;
  margin-left:15px;
  margin-right:5px;

  overflow-x:hidden;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}
-->
</style>
<base target="_self">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language="javascript">
<!--

// 20091224 ADD
function doSearch(){
  var form = document.forms[0];

  if(!checkValidate()) {
    return;
  }

  var url = "/plm/jsp/project/chart/SAPCost.jsp";

  var param = "";

  param += "?tmpYear="+form.tmpYear.value;
  param += "&modelCode="+form.modelCode.value;

  //개발조직
  var devcompanycodetable = document.getElementById("devcompanycodetable");
  var devcompanycodeLen = devcompanycodetable.rows.length;
  if(devcompanycodeLen > 1) {
    //alert("devcompanycodetable OK!!");
    param += "&devcompanycode=";
    for(var i = 0; i < devcompanycodeLen; i++) {
      param += document.forms[0].devcompanycodetableOID[i].value+"$";
    }
  } else if(devcompanycodeLen == 1) {
    param += "&devcompanycode="+document.forms[0].devcompanycodetableOID.value+"$";
  }

  //제품군
  var productcodetable = document.getElementById("productcodetable");
  var productcodeLen = productcodetable.rows.length;
  if(productcodeLen > 1) {
    //alert("productcodetable OK!!");
    param += "&productcode=";
    for(var i = 0; i < productcodeLen; i++) {
      param += document.forms[0].productcodetableOID[i].value+"$";
    }
  } else if(productcodeLen == 1) {
    param += "&productcode="+document.forms[0].productcodetableOID.value+"$";
  }

  //프로젝트번호
  var pjtnocodetable = document.getElementById("pjtnocodetable");
  var pjtnocodeLen = pjtnocodetable.rows.length;
  if(pjtnocodeLen > 1) {
    //alert("pjtnocodetable OK!!");
    param += "&pjtnocode=";
    for(var i = 0; i < pjtnocodeLen; i++) {
      param += document.forms[0].pjtnocodetableOID[i].value+"$";
    }
  } else if(pjtnocodeLen == 1) {
    param += "&pjtnocode="+document.forms[0].pjtnocodetableOID.value+"$";
  }

  //alert("param>>>> " + param);

  document.forms[0].action=url+param;
  document.forms[0].target = "list";
  document.forms[0].submit();
}


function doSearchExcel(){
  var form = document.forms[0];

  if(!checkValidate()) {
    return;
  }

  var url = "/plm/jsp/project/chart/SAPCostExcel.jsp";

  var param = "";

  param += "?tmpYear="+form.tmpYear.value;
  param += "&modelCode="+form.modelCode.value;

  var devcompanycodetable = document.getElementById("devcompanycodetable");
  var devcompanycodeLen = devcompanycodetable.rows.length;
  if(devcompanycodeLen > 1) {
    //alert("devcompanycodetable OK!!");
    param += "&devcompanycode=";
    for(var i = 0; i < devcompanycodeLen; i++) {
      param += document.forms[0].devcompanycodetableOID[i].value+"$";
    }
  } else if(devcompanycodeLen == 1) {
    param += "&devcompanycode="+document.forms[0].devcompanycodetableOID.value+"$";
  }

  var productcodetable = document.getElementById("productcodetable");
  var productcodeLen = productcodetable.rows.length;
  if(productcodeLen > 1) {
    //alert("productcodetable OK!!");
    param += "&productcode=";
    for(var i = 0; i < productcodeLen; i++) {
      param += document.forms[0].productcodetableOID[i].value+"$";
    }
  } else if(productcodeLen == 1) {
    param += "&productcode="+document.forms[0].productcodetableOID.value+"$";
  }

  var pjtnocodetable = document.getElementById("pjtnocodetable");
  var pjtnocodeLen = pjtnocodetable.rows.length;
  if(pjtnocodeLen >= 1) {
    param += "&pjtnocode=";
    for(var i = 0; i < pjtnocodeLen; i++) {
      param += document.forms[0].pjtnocodetableOID[i].value+"$";
    }
  } else if(pjtnocodeLen == 1) {
    param += "&pjtnocode="+document.forms[0].pjtnocodetableOID.value+"$";
  }

  document.forms[0].action=url+param;
  document.forms[0].target = "list";
  document.forms[0].submit();
}




function checkValidate() {
  var form = document.forms[0];

  //년도 체크
  if(form.tmpYear.value == '') {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01177") %><%--년도를 선택하시기 바랍니다--%>');
    form.tmpYear.focus();
    return false;
  }

  //모델(차종) 체크
  if(form.modelCode.value == '') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01368") %><%--모델(차종)을 선택하시기 바랍니다--%>");
    form.modelCode.focus();
    return false;
  }

  return true;
}

function addNumberSelect(codetype, depth, tableObj) {
  var form = document.forms[0];
  var tmpType = codetype;
  var mode = "multi";
  var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
  returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
  if(typeof returnValue == "undefined" || returnValue == null) {
    return;
  }

  addNumberSelectHtml(returnValue, codetype, tableObj);
}

function addNumberSelectHtml(arrObj, codetype, tableObj){

  var tableid_obj = document.getElementById(tableObj);

  var checkAdd = "a";
  for(var i = 0; i < arrObj.length; i++) {

    checkAdd = "a";
    var table_obj = document.getElementById(tableObj);
    var tableLen = table_obj.rows.length;
    var arrValue = arrObj[i][0];


    if(tableLen > 1){
      for(var a = 0; a < tableLen; a++) {
        var checkObj = eval("document.forms[0]."+tableObj+"OID["+a+"]");
        if(checkObj != null){
          var checkObjvalue = checkObj.value;
          if(checkObjvalue == arrValue){
            checkAdd = "b";
          }
        }
        if(checkAdd == "b"){
          break;
        }
      }
    }else if(tableLen == 1){
      var checkObj2 = eval("document.forms[0]."+tableObj+"OID");
      //alert(checkObj2);
      if(checkObj2 != null){
        var checkObjvalue2 = checkObj2.value;
        if(checkObjvalue2 == arrValue){
          checkAdd = "b";
        }
      }
    }


    if(checkAdd == "a" ){
    newTr=tableid_obj.insertRow();

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = ""+arrObj[i][2];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = ""+arrObj[i][1];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    var codetypeOID = tableObj + "OID";
    newTd.innerHTML = "<input type='hidden' name='" + codetypeOID + "' value='"+ arrObj[i][0] +"'><a href='JavaScript:DeleteRow(\"" + arrObj[i][0] + "\", \"" + tableObj + "\");'><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p><\/a>";
    }

  }

}

function onProject() {
  var url = "/plm/jsp/project/ProjectSelectPopUp.jsp?mode=multi&type=";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=840px; dialogHeight:460px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  acceptProject(attache);
}

function acceptProject(objArr) {
  var tableid_obj = document.getElementById("pjtnocodetable");
  var checkAdd = "a";
  for(var i = 0; i < objArr.length; i++) {

    checkAdd = "a";
    var table_obj = document.getElementById("pjtnocodetable");
    var tableLen = table_obj.rows.length;
    var arrValue = objArr[i][0];


    if(tableLen > 1){
      for(var a = 0; a < tableLen; a++) {
        var checkObj = eval("document.forms[0].pjtnocodetableOID["+a+"]");
        if(checkObj != null){
          var checkObjvalue = checkObj.value;
          if(checkObjvalue == arrValue){
            checkAdd = "b";
          }
        }
        if(checkAdd == "b"){
          break;
        }
      }
    }else if(tableLen == 1){
      var checkObj2 = eval("document.forms[0].pjtnocodetableOID");
      if(checkObj2 != null){
        var checkObjvalue2 = checkObj2.value;
        if(checkObjvalue2 == arrValue){
          checkAdd = "b";
        }
      }
    }


    if(checkAdd == "a" ){
    newTr=tableid_obj.insertRow();

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = objArr[i][1];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = objArr[i][2];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = objArr[i][3];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = objArr[i][4];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    newTd.innerHTML = objArr[i][5];

    newTd = newTr.insertCell();
    newTd.className = "tdwhiteM";
    var codetypeOID = "pjtnocodetableOID";
    newTd.innerHTML = "<input type='hidden' name='" + codetypeOID + "' value='"+ objArr[i][0] +"'><a href='JavaScript:DeleteRow(\"" + objArr[i][0] + "\", \"pjtnocodetable\");'><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p><\/a>";
    }
  }
}

function DeleteRow(oid, tableObj) {
  var table_obj = document.getElementById(tableObj);
  var tableLen = table_obj.rows.length;
  if(tableLen > 1) {
    for(var i = 0; i < tableLen; i++) {
      var checkObj = eval("document.forms[0]."+tableObj+"OID["+i+"]");
      var checkObjvalue = checkObj.value;


      if(tableObj == "devcompanycodetable") {
        if(checkObjvalue == oid) {
          table_obj.deleteRow(i);
          return;
        }
      } else if(tableObj == "productcodetable") {
        if(checkObjvalue == oid) {
          table_obj.deleteRow(i);
          return;
        }
      } else if(tableObj == "pjtnocodetable") {
        if(checkObjvalue == oid) {
          table_obj.deleteRow(i);
          return;
        }
      }
    }
  } else if(tableLen == 1) {
    var checkObj = eval("document.forms[0]."+tableObj+"OID[0]");
    if(checkObj != null){
      var checkObjvalue = checkObj.value;
      if(tableObj == "devcompanycodetable") {
        if(checkObjvalue == oid) {
          table_obj.deleteRow(0);
          return;
        }
      } else if(tableObj == "productcodetable") {
        if(checkObjvalue == oid) {
          table_obj.deleteRow(0);
          return;
        }
      } else if(tableObj == "pjtnocodetable") {
        if(checkObjvalue == oid) {
          table_obj.deleteRow(0);
          return;
        }
      }
    }else{
      var checkObj2 = eval("document.forms[0]."+tableObj+"OID");
      if(checkObj2 != null){
        var checkObjvalue = checkObj2.value;
        if(tableObj == "devcompanycodetable") {
          if(checkObjvalue == oid) {
            table_obj.deleteRow(0);
            return;
          }
        } else if(tableObj == "productcodetable") {
          if(checkObjvalue == oid) {
            table_obj.deleteRow(0);
            return;
          }
        } else if(tableObj == "pjtnocodetable") {
          if(checkObjvalue == oid) {
            table_obj.deleteRow(0);
            return;
          }
        }
      }

    }
  }
  return;
}

// 20091224 END

function addProcess(type, depth) {
    var form = document.forms[0];

    var tmpType = "";
    if(type==("divisioncode")) {
      tmpType = "DIVISIONCODE";
    } else if(type==("levelcode")) {
      tmpType = "LEVELCODE";
    } else if(type==("productcode")) {
      tmpType = "PRODUCTCODE";
    } else if(type==("customercode")) {
      tmpType = "CUSTOMERCODE";
    } else if(type==("devcompanycode")) {
      tmpType = "DEVCOMPANYCODE";
    } else if(type==("makecompanycode")) {
      tmpType = "MAKECOMPANYCODE";
    } else if(type==("modelcode")) {
      tmpType = "MODELCODE";
    }

    var mode = "one";
    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth="+depth+"&codetype="+tmpType+"&command=select&mode="+mode;
    returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof returnValue == "undefined" || returnValue == null) {
      return;
    }
    acceptProcess(returnValue, type);
  }

  function acceptProcess(arrObj, type){
    var tmpType = "";
    var tmpType1 = "";

    if(type==("divisioncode")) {
      tmpType = "divisioncodetable";
      tmpType1 = "divisioncodeoid";
    } else if(type==("levelcode")) {
      tmpType = "levelcodetable";
      tmpType1 = "levelcodeoid";
    } else if(type==("productcode")) {
      tmpType = "productcodetable";
      tmpType1 = "productcodeoid";
    } else if(type==("customercode")) {
      tmpType = "customercodetable";
      tmpType1 = "customercodeoid";
    } else if(type==("devcompanycode")) {
      tmpType = "devcompanycodetable";
      tmpType1 = "devcompanycodeoid";
    } else if(type==("makecompanycode")) {
      tmpType = "makecompanycodetable";
      tmpType1 = "makecompanycodeoid";
    } else if(type==("modelcode")) {
      tmpType = "modelcodetable";
      tmpType1 = "modelcodeoid";
    }

    var subArr;
    var form = document.forms[0];

      for(var i = 0; i < arrObj.length; i++) {
        subArr = arrObj[i];

        if(type==("divisioncode")) {
          form.divisioncode.value = subArr[1];
          form.divisioncode.name =subArr[2];
        } else if(type==("levelcode")) {
          form.levelcode.value = subArr[1];
          form.levelcode.name =subArr[2];
        } else if(type==("productcode")) {
          form.productcode.value =subArr[1];
          form.productcode.name =subArr[2];
        } else if(type==("customercode")) {
          form.customercode.value =subArr[1];
          form.customercode.name =subArr[2];
        } else if(type==("devcompanycode")) {
          form.devcompanycode.value =subArr[1];
          form.devcompanycode.name =subArr[2];
        } else if(type==("makecompanycode")) {
          form.makecompanycode.value =subArr[1];
          form.makecompanycode.name =subArr[2];
        } else if(type==("modelcode")) {
          form.modelCode.value =subArr[1];
          form.modelCode.name =subArr[2];
        }
      }
  }



// -->
</script>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form method="post">


<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td valign="top" style="padding:24px 0px 15px 15px">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 10px 0px">
        <tr>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02145") %><%--예산대비 실적 현황--%></td>
        <td align="right" style="padding:8px 0px 0px 0px"></td>
        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>

          <td>&nbsp;</td>
          <td align="right">
            <a class="a52Btn" href="#" onclick="javascript:history.back();">
                        <img src="/plm/portal/images/img_default/button/board_btn_back.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01309") %><%--뒤로--%>' width="62" height="20" border="0">
            </a>
            &nbsp
            <a class="a52Btn" href="#" onclick="javascript:doSearchExcel();">
            <img src="/plm/portal/images/img_default/button/board_btn_excel.gif" alt="Excel" width="62" height="20" border="0">
            </a>
            &nbsp;
            <a class="a52Btn" href="#" onclick="javascript:doSearch();">
            <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
            </a>
            </td>
        </tr>
      </table>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="tab_btm2"> </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="tab_btm1"> </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <col width="15%"><col width="35%"><col width="15%"><col width="35%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01176") %><%--년도--%><span class="style1"> *</span></td>
          <td class="tdwhiteL">
            <select name="tmpYear" size="1" style="width:70">
              <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
              <%
              String tmpCurrentYear = DateUtil.getThisYear();
              String currentYear = "";
              for(double i = -10; i < 11; i++) {
                currentYear = "" + (Integer.parseInt(tmpCurrentYear) + i);

                currentYear = currentYear.substring(0, 4);
              %>
              <option value='<%=currentYear %>' <%=currentYear.equals(tmpCurrentYear)?"selected":""%>><%=currentYear %></option>
              <%
              }
              %>
            </select>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01366") %><%--모델(차종)--%><span class="style1"> *</span></td>
          <td class="tdwhiteL0">
            <select name="modelCode">
              <option style="background-color:#EEE8AA;" value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
              <%
              Vector tMap = NumberCodeHelper.manager.getNumberCodeLevel("MODELCODE", "child");
              for(int i = 0; i < tMap.size(); i++) {
                NumberCode tNum = (NumberCode)tMap.get(i);
              %>
              <option style="background-color:#EEE8AA;" value="<%=tNum.getCode()%>" ><%=tNum.getName()%></option>
              <%
              }
              %>
            </select>
            &nbsp;&nbsp;<a href="javascript:addProcess('modelcode', 1);">
            <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
            </a >
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00665") %><%--개발조직--%></td>
          <td class="tdwhiteL" colspan="3">
            <table width="100%" align="center">
              <tr>
                <td height="25" >
                  <a href="#" onclick="javascript:addNumberSelect('DEVCOMPANYCODE', '1', 'devcompanycodetable');">
                  <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                  </a>
                </td>
              </tr>
            </table>
            <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                                <td class="tdblueM" width="45%"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                <td class="tdblueM" width="45%"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
                                <td class="tdblueM" width="10%"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
              </tr>
              <tbody id="devcompanycodetable"></tbody>
            </table>
            </div>
          </td>
        </tr>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02581") %><%--제품군--%></td>
          <td class="tdwhiteL" colspan="3">
            <table width="100%" align="center">
              <tr>
                <td height="25" >
                  <a href="#" onclick="javascript:addNumberSelect('PRODUCTCODE', '1', 'productcodetable');">
                  <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                  </a>
                </td>
              </tr>
            </table>
            <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                                <td class="tdblueM" width="45%"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                <td class="tdblueM" width="45%"><%=messageService.getString("e3ps.message.ket_message", "02909") %><%--코드--%></td>
                                <td class="tdblueM" width="10%"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
              </tr>
              <tbody id="productcodetable"></tbody>
            </table>
            </div>
          </td>
        </tr>
        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
          <td class="tdwhiteL0" colspan="3">
            <table width="100%" align="center">
              <tr>
                <td height="25" >
                  <a href="#" onclick="javascript:onProject();">
                  <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                  </a>
                </td>
              </tr>
            </table>
            <div style="width=99%;overflow-x:hidden;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                                <td class="tdblueM" width="30%"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                <td class="tdblueM" width="15%"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--번호--%></td>
                <td class="tdblueM" width="10%"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                                <td class="tdblueM" width="20%"><%=messageService.getString("e3ps.message.ket_message", "01335") %><%--등록일--%></td>
                <td class="tdblueM" width="20%"><%=messageService.getString("e3ps.message.ket_message", "01951") %><%--수정일자--%></td>
                                <td class="tdblueM" width="5%"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
              </tr>
              <tbody id="pjtnocodetable"></tbody>
            </table>
            </div>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space10"> </td>
        </tr>
      </table>
      <iframe src="" id="list" name="list" frameborder="0" width="100%" height="450" leftmargin="0" topmargin="0" scrolling="yes">
      </iframe>
    </td>
  </tr>
</table>
</form>
</body>
</html>
