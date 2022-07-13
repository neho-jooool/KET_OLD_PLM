<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    String nation = request.getParameter("nation");
    if( nation == null ) nation = "국내";

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "CUSTOMEREVENT");
    List<Map<String, Object>> customerEventNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02407") %><%--자동차일정 등록--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
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
    line-height: 22px;
}
</style>
<script src="/plm/portal/js/script.js"></SCRIPT>
<script src="/plm/portal/js/common.js"></SCRIPT>
<script src="/plm/portal/js/org.js"></SCRIPT>
<script src="/plm/portal/js/select.js"></SCRIPT>
<script src="/plm/portal/js/table.js"></SCRIPT>
<script src="/plm/portal/js/viewObject.js"></SCRIPT>
<script src="/plm/portal/js/ajax.js"></SCRIPT>
<script src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script src="/plm/portal/js/Calendar.js"></script>
<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript">
var initIndex = 0;
var targetSelectTD = "selectTD";

    function ProgramCreate(){

        var form = document.CreateProgram;

        if(form.cartype.value==null || form.cartype.value == "" ){

            alert('<%=messageService.getString("e3ps.message.ket_message", "02752") %><%--차종을 선택하여 주십시오--%>');
            return;
        }
        
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
            alert('<%=messageService.getString("e3ps.message.ket_message", "00019, 4") %><%--{0}년차 생산량을 숫자로 입력하여 주십시오--%>');
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
        if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03328") %><%--자동차일정을 등록하시겠습니까?--%>")) {
            return;
        }

        var kc;

        var schedPlantTableObj = document.getElementById("schedPlanID");

        if(schedPlantTableObj.rows.length==0) {
            return;
        }

        var test2 = eval("form.countPlan");
        //alert(test2.value);

        /*
        for(var sp=0;sp<test2.value;sp++) {
            var test;
            test = eval("form.oemEndDate"+sp);
            //alert(test);
            if(test.value==null ||test.value=="" ){
                alert("자동차일정을 입력하여 주십시오.");
                return;
            }
        }
        */
        //for(var sp=0;sp<schedPlantTableObj.rows.length-1;sp++) {
        //    kc = eval("form.oemEndDate"+sp);
        //}

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
        var msg    = ' yyyy/MM/dd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
         //document.CreateProgram.duration.value = "";

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

    function displayModelDesc(mObj){
        setSelectModel(mObj);
    }
    function setSelectModel(mObj) {
        form = document.CreateProgram;

        if(mObj.value != '') {
            selectSearchModel(mObj.value);
        }
    }

    function clearCustomer(str) {
        var tartxt = document.getElementById(str).value = "";
    }

    // Number Code Ajax
    function numCodeAjax(codeType, code, venderCode, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, code:code, venderCode:venderCode},
            dataType : 'json',
            async : false,
            success: function(data) {
                
                $.each(customerListSortByName(data.numCode), function() {
                    $("#"+targetId).append("<option value='"+this.oid+"' >"+ this.value +"</option>");
                });
            }
        });
    }
    
    function customerListSortByName(list){//고객사 이름으로 내림차순 정렬
    	var nameResult = list.sort(function (a,b) {
    		let x = a.value.toLowerCase();
    		let y = b.value.toLowerCase();
    		if(x < y){
    			return -1;
    		}
    		if(x > y){
    			return 1;
    	    }
    		return 0;
    	});
    	return nameResult;
    }

  //================================================/
  //==================고객 스크립트 시작==================
  //================================================/

    function onClickNation( fObj ) {
        $("#customer").empty().data('options');
        $("#customer").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");

        // 자동차일정 등록 메뉴 클릭시
        if ( fObj == "국내" ){
            numCodeAjax("CUSTOMEREVENT", "1000", "자동차", "customer");
        }
        else{
            if ( fObj.value != '' ) {
                numCodeAjax("CUSTOMEREVENT", fObj.value, "자동차", "customer");
            }
        }
    }

  /*****************************************************
  *****************고객사별 차종 선택 스크립트******************
  *****************************************************/
  function onChangeCustomer(fObj) {
      form = document.CreateProgram;
      if(fObj.value != '') {
          selectSearch2(fObj.value);
      }
  }

  function selectSearch2(svalue) {
      onProgressBar();

      var param = "command=select2&oemtype=CARTYPE&oid=" + svalue;
      var url = "/plm/jsp/project/OEMAjaxAction.jsp?" + param;
      callServer(url, onSelectOption2);
  }
  
  window.getTagText = function(xmlDoc){
	  return xmlDoc.textContent || xmlDoc.text || '';
  }
  
  function onSelectOption2(req) {
      var xmlDoc = req.responseXML;
      //var showElements = xmlDoc.selectNodes("//message");
      //var msg = showElements[0].getElementsByTagName("l_message")[0].text;
      
      var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);

      //showElements = xmlDoc.selectNodes("//data_info");
      var l_oid = xmlDoc.getElementsByTagName("l_oid");
      var l_name = xmlDoc.getElementsByTagName("l_name");

      var arr = new Array();
      for(var i = 0; i < l_oid.length; i++) {
          subArr = new Array();
          subArr[0] = decodeURIComponent(getTagText(l_oid[i]));
          subArr[1] = decodeURIComponent(getTagText(l_name[i]));
          arr[i] = subArr;
      }

      addSelectNode2(arr);
  }

  function addSelectNode2(vArr) {

          var fTD2 = document.all.item("cartype");
          var len2 = fTD2.length;

          for(var j = len2 ; j > 0 ; j--){
              fTD2.remove(j);
          }

          for(var i = 0; i < vArr.length; i++) {
              var newSpan = document.createElement("option");
              newSpan.innerHTML = vArr[i][1];
              newSpan.value= vArr[i][0];
              fTD2.appendChild(newSpan);
          }
  }

  function clearCartype(){
      var aa = document.all.item("cartype");

      for(var j = 1 ; j < aa.length ; j++){
          aa.remove(j);
      }

  }

  /*****************************************************
  *****************차종별 일정 화면 변경 스크립트******************
  *****************************************************/
  function displayChangeOEM(obj){
        if(obj.value == ''){
            return;
        }
        var param = "command=oemLevelSelect&oemtype=CUSTOMEREVENT&oid=" + obj.value;
        var url = "/plm/jsp/project/OEMAjaxAction.jsp?" + param;
        callServer(url, setLayerOEM);
    }

    function setLayerOEM(req) {
        var xmlDoc = req.responseXML;
        //var showElements = xmlDoc.selectNodes("//message");
        var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
        //var msg = showElements[0].getElementsByTagName("l_message")[0].text;

        //showElements = xmlDoc.selectNodes("//data_info");
        //var l_attrHtml = showElements[0].getElementsByTagName("l_attrHtml")[0].text;
        var l_attrHtml = getTagText(xmlDoc.getElementsByTagName("l_attrHtml")[0]);

        var tBody = document.getElementById("oemPlanTable");
        tBody.innerHTML = l_attrHtml;
        
        // Calener field 설정
        CalendarUtil.dateInputFormat('oemEndDate');
    }

    function oemClear(req) {
        var l_attrHtml = "";

        var tBody = document.getElementById("oemPlanTable");
        tBody.innerHTML = l_attrHtml;

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
            if (str.charAt(0) < '1') {
                flag=false;
            }
      }
      return flag;
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
      var form = document.forms[0];

        for(var i = 0; i < arrObj.length; i++) {
            subArr = arrObj[i];
            if(isDuplicateCheckBox('oid', subArr[0])) {
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
            newTd.innerHTML = "<input type=\"checkbox\" name=\"oid\" value=\"" + subArr[0] + "\"></input><input type='hidden' name='sOid' value='" + subArr[0] + "'>";

            //Code | Name
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.className = "tdwhiteL";
            newTd.width = 110;
            newTd.height = "100%";
            newTd.valign = "top";
            newTd.innerText = subArr[2];

            //납입처 Table
            /* newTd = newTr.insertCell(newTr.cells.length);
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

      len = form.oid.length;
      if(len) {
          for(var i = len - 1; i >= 0; i--) {
              if(form.oid[i].checked == true) {
                  onDeleteTableRow('SUBCONTRACTOR', 'oid', form.oid[i].value);
              }
          }
      }else{
          onDeleteTableRow('SUBCONTRACTOR', 'oid', form.oid.value);
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
      }else {
          if(form.oid.checked == true) {
              return true;
          }
      }
      return false;
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
</script>
</head>
<%if( nation.equals("국내")){%>
<body onload="javascript:onClickNation('<%=nation%>');">
<%}else{ %>
<body>
<%} %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form name="CreateProgram" method="post">
<input type="hidden" name="cmd" value="Create">
<table style="width: 100%; height: 100%;" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                          <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02407") %><%--자동차일정 등록--%></td>
                          </tr>
                        </table>
                    </td>
                    <td width="170" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%" style="width: 100%; height: 100%;">
    <tr>
        <td class="space5"></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" style="width: 804px; height: 100%;">
    <tr>
        <td valign="top">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01118") %><%--기본--%></td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                    <a href=# class="btn_blue" onClick="Javascript:ProgramCreate()"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a>
                                </td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                <td width="5">&nbsp;</td>
                                <td align="right">
                                    <table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                    href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                            </tr>
                                            </table></td>
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
                            <td width="150" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%>
                                <span class="red">*</span></td>
                            <td width="220" class="tdwhiteL0" colspan="3">
                        <%
                        for ( int i=0; i<customerEventNumCode.size(); i++ ) {
                        %>
                        <input id="countryType" name="radio" type="radio" class="Checkbox" value="<%=customerEventNumCode.get(i).get("code") %>" onclick="javascript:onClickNation(this);javascript:clearCartype();javascript:oemClear();" <% if ( customerEventNumCode.get(i).get("code").equals("1000") ){ %> checked  <%} %> ><%=customerEventNumCode.get(i).get("value")%>
                        <%
                        }
                        %>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%><span class="red">*</span></td>
                    <td class="tdwhiteL" >
                        <select name='customer' id='customer' class='fm_jmp'  style="width:98%" onchange="javascript:onChangeCustomer(this);javascript:displayChangeOEM(this);" >
                            <option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                        </select>
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%><span class="red">*</span></td>
                    <td class="tdwhiteL0" >
                        <select name='cartype' id='cartype' class='fm_jmp'  style="width:98%" >
                            <option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01744") %><%--상세 차종명--%></td>
                    <td class="tdwhiteL">
                        <input type="text" name="modelname" class="txt_field"  style="width:98%" id="textfield">
                    </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03223") %><%--형태--%><span class="red">*</span></td>
                    <td class="tdwhiteL0">
                        <select name="formtype" class="fm_jmp" style="width:98%">
                          <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                          <%
                      HashMap map = new HashMap();
                      map.put("type", "FORMTYPE");
                      QuerySpec qs1 = NumberCodeHelper.getCodeQuerySpec(map);
                          QueryResult qr1 = PersistenceHelper.manager.find(qs1);
        
                          while(qr1.hasMoreElements()){
                              Object[] o = (Object[]) qr1.nextElement();
                              NumberCode nCode = (NumberCode) o[0];
                              if(nCode.getName().equals("-")){
                        	  continue;
                              }
                          %>
                          <option value="<%=nCode.getPersistInfo().getObjectIdentifier().toString()%>"><%=nCode.getName() %></option>
                          <%
                          }
                          %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02400") %><%--자동차사 설계담당--%></td>
                    <td class="tdwhiteL0"  colspan="3" ><input type="text" name="person" class="txt_field"  style="width:98%" id="textfield"></td>
                </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space10"></td>
                </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addProcessM('SUBCONTRACTOR', 0, 'noTree');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00859") %>  <%=messageService.getString("e3ps.message.ket_message", "02861") %></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  <td width="5">&nbsp;</td>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onDelete();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00859") %>  <%=messageService.getString("e3ps.message.ket_message", "01690") %></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
             </table>
             <div style="height: 73px; overflow-x: hidden; overflow-y: auto; border: 1px; border-style: solid; border-color: #5F9EA0; padding: 0px; margin: 1px;">
                <table border="0" cellspacing="0" cellpadding="0" id="SUBCONTRACTOR">
                    <tr>
                        <td width="20" class="tdblueM"><input name="chkAll" type="checkbox" onClick="javascript:checkAll();"></td>
                        <td width="760" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00859")%></td>
                    </tr>
                </table>
             </div>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space10"></td>
                </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0" >
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01781") %><%--생산량(단위:천대)--%></td>
                    <td align="right">&nbsp;</td>
                </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space5"></td>
                </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
             </table>
             <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00016")%><%--{0}년차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 1)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 2)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 3)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 4)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 5)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 6)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 7)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 8)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00046", 9)%><%--{0}차--%></td>
                    <td width="65" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00046", 10) %><%--{0}차--%></td>
                </tr>
                <tr>
                    <td width="*" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01779")%><!-- 생산대수 --></td>
                    <td class="tdwhiteR"><input type="text" name="yield1" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield2" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield3" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield4" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield5" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield6" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield7" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield8" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield9" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                    <td class="tdwhiteR"><input type="text" name="yield10" class="txt_field" style="width: 60px"
                        id="textfield3" align="center"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space10"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02348")%><%--일정--%></td>
                    <td align="right">&nbsp;</td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <div id="oemPlanTable"></div>
        </td>
     </tr>
</table>
</form>
</body>
</html>
