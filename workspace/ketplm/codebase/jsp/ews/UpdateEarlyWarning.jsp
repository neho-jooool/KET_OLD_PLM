<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%@page import = "e3ps.common.util.CommonUtil,
                  e3ps.common.util.DateUtil,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.content.*,
                  e3ps.common.code.NumberCode,
                  e3ps.ews.entity.KETEarlyWarning,
                  e3ps.ews.dao.PartnerDao,
                  e3ps.ews.dao.EarlyWarningDao,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.ews.beans.EWSHelper,
                  wt.vc.VersionControlHelper,
                  wt.org.WTUser,
                  wt.content.*,
                  java.util.Hashtable,
                  java.util.ArrayList,
                                    java.util.Vector"%>

<%
    String cmd = ParamUtil.getParameter(request, "cmd");

    String oid = ParamUtil.getParameter(request, "oid");
    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning)CommonUtil.getObject(oid);

    EarlyWarningDao earlyWarningDao = new EarlyWarningDao();
    ArrayList ingPartList = earlyWarningDao.ViewPartList(oid);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/multicombo.jsp"%>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<script language=JavaScript src='../common/content/content.js'></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>

<style type="text/css">
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 0px;
}
.ui-datepicker-trigger {
    vertical-align: 0px;
}

.ui-autocomplete { height: 200px; width:250px;  overflow-y: scroll; overflow-x: hidden; font-size: 11px;}
</style>
<script language='javascript'>
$(document).ready(function(){
    CalendarUtil.dateInputFormat('startDate','endDate');
    CalendarUtil.dateInputFormat('planDate');
    
    SuggestUtil.bind('USER', 'fstChargeName','fstCharge');
    SuggestUtil.bind('USER', 'sndChargeName','sndCharge');
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo','pjtOid','pjtName');
});
<!--

  //프로젝트 검색팝업 open
    function popupProject() {
        var url = "/plm/jsp/project/SearchPjtPop.jsp?status=C&type=P&modal=y";
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        setProject(returnValue);
    }

    //프로젝트 정보 setting
    function setProject(objArr) {

        if(objArr.length == 0) {
            return;
        }

        var trArr;
        var str = "";
        for(var i = 0; i < objArr.length; i++)
        {
            trArr = objArr[i];

            document.forms[0].pjtNo.value = trArr[1];
            document.forms[0].pjtName.value= trArr[2];
        }

        deletePartAll();
    }

    //제품/부품 전체삭제
    function deletePartAll() {
        var partTable = document.getElementById("partTable");
        var targetTable = document.getElementById("targetTable");
        if (partTable.rows.length == 2) return;
        var partCheck = document.forms[0].partCheck;

        for (var inx = partTable.rows.length; inx > 2; inx--) {
            partTable.deleteRow(inx-1);
            targetTable.deleteRow(inx);
        }
    }

    //부품체크박스
    function allcheck() {
        var isCheck;
        if (partTable.rows.length == 2){
            return;
        }
        if (partTable.rows.length > 2){
            isCheck = document.forms[0].partCheck[0].checked;
        }

        var body = document.getElementById("partTable");
        var partCheck = document.forms[0].partCheck;

        if (isCheck) {
            for (var inx = 2; inx < partCheck.length; inx++) {
                partCheck[inx].checked = true;
            }
        } else {
            for (var inx = 2; inx < partCheck.length; inx++) {
                partCheck[inx].checked = false;
            }
        }
    }

    //제품/부품 검색 팝업 Open
    function selectPart(){
        if (isNullData(document.forms[0].pjtNo.value)){
          alert('<%=messageService.getString("e3ps.message.ket_message", "03112") %><%--프로젝트를 입력해 주십시오--%>');
          return;
      }

        var pjtNo = document.forms[0].pjtNo.value;
        var pjtName = document.forms[0].pjtName.value;

        var url="/plm/servlet/e3ps/EarlyWarningServlet?cmd=searchPart&pjtNo="+pjtNo+"&pjtName="+encodeURIComponent(pjtName);
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=820px; dialogHeight:500px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        insertPart(returnValue);
  }

    //새부품 입력
  function insertPart(arr){
      if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01581") %><%--부품/제품을 선택하시기 바랍니다--%>');
            return;
        }

        var flag = '0';
      var partTable = document.getElementById("partTable");
      var targetTable = document.getElementById("targetTable");
      var newTr;
      var newTd;
      var tNewTr;
      var tNewTd;

      var inout = document.getElementById("selInOut").value;
      if ( inout == 'i'){
        inout = 'txt_fieldR';
      }else{
          inout = 'txt_fieldRRO';
      }

      for (var inx = 0 ; inx < arr.length ; inx++ ){
          flag = '0';

            for(var jnx = 0; jnx < document.forms[0].poid.length; jnx++){
                if(arr[inx][6] == document.forms[0].poid[jnx].value){
                    flag = '1';
                }
            }

          <% for(int knx = 0; knx < ingPartList.size() ; knx++){ %>
              if(arr[inx][6] == '<%=(String)ingPartList.get(knx)%>'){
                     flag = '2';
                 }
        <% } %>

            if(flag == '0'){
              newTr = partTable.insertRow();

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteM";
              newTd.innerHTML = "<input type='checkbox' name='partCheck'>";

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteM";
              newTd.innerHTML =  arr[inx][0]
                                 + "<input name='partNo' type='hidden' value='" + arr[inx][0] + "' >"
                               + "<input name='poid' type='hidden' value='" + arr[inx][6] + "' >";

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteL";
              newTd.innerHTML = arr[inx][1]
                                + "<input name='partName' type='hidden' value='" + arr[inx][1] + "' >";

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteL";
              newTd.innerHTML = arr[inx][2]
                                + "<input name='dieNo' type='hidden' value='" + arr[inx][2] + "' >";

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteL";
              newTd.innerHTML = arr[inx][3]
                                + "<input name='partType' type='hidden' value='" + arr[inx][3] + "' >";

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteL";
              newTd.innerHTML = arr[inx][7]
                                + "<input name='partProTeam' type='hidden' value='" + arr[inx][4] + "' >";

              newTd = newTr.insertCell();
              newTd.align = "center";
              newTd.className = "tdwhiteL";
              newTd.innerHTML = arr[inx][5]
                                + "<input name='dieProTeam' type='hidden' value='" + arr[inx][5] + "' >";

              tNewTr = targetTable.insertRow();

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = arr[inx][0];

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteL";
              tNewTd.innerHTML = arr[inx][1];

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = "<input type='text' name='targetcusError' class='txt_fieldR' style='width:98%' onKeyUp='javascript:handlerNum(this);'>";

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = "<input type='text' name='targetWorkError' class='txt_fieldR' style='width:98%' onKeyUp='javascript:handlerNum(this);'>";

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = "<input type='text' name='targetFacility' class='" + inout + "' style='width:98%' onKeyUp='javascript:getTargetTotal(this);' onchange='javascript:getTargetTotal(this);' >";

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = "<input type='text' name='targetPerform' class='" + inout + "' style='width:98%' onKeyUp='javascript:getTargetTotal(this);' onchange='javascript:getTargetTotal(this);' >";

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = "<input type='text' name='targetGood' class='" + inout + "' style='width:98%' onKeyUp='javascript:getTargetTotal(this);' onchange='javascript:getTargetTotal(this);' >";

              tNewTd = tNewTr.insertCell();
              tNewTd.align = "center";
              tNewTd.className = "tdwhiteM";
              tNewTd.innerHTML = "<input type='text' name='targetTotal' class='txt_fieldRRO'  style='width:98%' readonly>";
          }else if (flag == '1'){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02289") %><%--이미 추가되었습니다--%>');
            }else if (flag == '2'){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02288") %><%--이미 진행중입니다--%>');
            }
      }

      changeTarget(document.getElementById("selInOut"));
  }

  //부품 삭제
  function deletePart() {
        var partTable = document.getElementById("partTable");
        var targetTable = document.getElementById("targetTable");
        if (partTable.rows.length == 2) return;
        var partCheck = document.forms[0].partCheck;

        for (var inx = partTable.rows.length; inx > 1; inx--) {
            if (partCheck[inx-1].checked) {
                partTable.deleteRow(inx-1);
                targetTable.deleteRow(inx);
            }
        }

        var  isCheck = document.forms[0].partCheck[0].checked;
        var partCheck = document.forms[0].partCheck;

        if (isCheck) {
            partCheck[0].checked = false;
        }
    }

  //생산처 변경 시 타생산처 비활성화 및 삭제
  function changeProteam(){
      deleteValue("proteamName");
      deleteValue("proteamNo");
  }

  //생산처 변경 시 목표 테이블 변경
  function changeTarget(param){
      var targetFacility = document.forms[0].targetFacility;
        var targetPerform = document.forms[0].targetPerform;
        var targetGood = document.forms[0].targetGood;
        var targetTotal = document.forms[0].targetTotal;
        var processError = document.getElementById("processError");

        if(param.value == 'i'){
            for (var inx = 1 ; inx < targetFacility.length ; inx++){
                targetFacility[inx].className = "txt_fieldR";
                targetFacility[inx].readOnly = false;

                targetPerform[inx].className = "txt_fieldR";
                targetPerform[inx].readOnly = false;

                targetGood[inx].className = "txt_fieldR";
                targetGood[inx].readOnly = false;

                processError.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%>";
            }
        }else if(param.value == 'o'){
            for (var inx = 1 ; inx < targetFacility.length ; inx++){
                targetFacility[inx].value = "";
                targetFacility[inx].className = "txt_fieldRRO";
                targetFacility[inx].readOnly = true;

                targetPerform[inx].value = "";
                targetPerform[inx].className = "txt_fieldRRO";
                targetPerform[inx].readOnly = true;

                targetGood[inx].value = "";
                targetGood[inx].className = "txt_fieldRRO";
                targetGood[inx].readOnly = true;

                targetTotal[inx].value = "";
                processError.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%>";
            }
        }
  }

    //사내 or 협력사 검색팝업 Open
    function selectProduction(){
        var sel = document.getElementById("selInOut").value;
        if (sel == 'i'){
            selectDepartment();
        }else {
            selectPartner();
        }
    }

    //사내생산처 검색 팝업  Open
    function selectDepartment() {
        var mode = "single";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        linkDept(returnValue);
    }

    //사내생산처 검색결과 셋팅
  function linkDept(arr){
      if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01553") %><%--부서를 선택하시기 바랍니다--%>');
            return;
        }

        document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("proteamName").value = arr[0][2];
  }

  //협력사검색 팝업 Open
    function selectPartner() {
        var url = "../../jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner&modal=Y";
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=740px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        linkPartner(returnValue);
    }

  //협력사 검색결과 셋팅
  function linkPartner(arr){
      if(arr.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
            return;
        }

        document.getElementById("proteamNo").value = arr[0][0];
        document.getElementById("proteamName").value = arr[0][1];
  }

    //활동기간 선택(StartDate 선택 후 개월 선택)
    function selectMM(mm){
        if (mm.value != 0){
          document.getElementById("endDateBtn").disabled = true;
        }else{
          document.getElementById("endDateBtn").disabled = false;
        }

        var strStartDate = document.forms[0].startDate.value;

        if (strStartDate != "" && mm.value != 0){
            var arrStartDate = strStartDate.split("-");
            var endDate = new Date(arrStartDate[0],arrStartDate[1]-1+parseInt(mm.value),arrStartDate[2]);

            var preEndDate = endDate.getTime() - (24*60*60*1000);
            endDate.setTime(preEndDate);

            var month = endDate.getMonth()+1;
            var day = endDate.getDate();

            if (month < 10){
                month = '0' + month;
            }
            if (day < 10){
                day = '0' + day;
            }

            var strEndDate = endDate.getYear() + '-' + month + '-' + day;
            document.getElementById("endDate").value = strEndDate;
        }
    }

    //활동기간 선택(개월 선택 후 StartDate 선택)
    function selectStartDate(){
        var mm = document.getElementById("workingMM");
        selectMM(mm);
  }

    //사용자 셋팅
    function acceptMember(rname, objArr) {
        if(objArr.length == 0) {
            return;
        }

        var displayName = document.getElementById(rname+'name');
        var keyName = document.getElementById(rname);

        var nonUserArr = new Array();
        for(var i = 0; i < objArr.length; i++) {
            infoArr = objArr[i];
            displayName.value = infoArr[4];
            keyName.value = infoArr[0];
        }
    }

    //숫자 입력
    function handlerNum(obj){
        var sValue = obj.value;
        if(sValue == '') {
            return;
        }

        if(isNaN(sValue)) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01984") %><%--숫자만 입력하십시오--%>');
            obj.value = '';
            return;
        }

        if(sValue.length > 1 && sValue.substring(0, 1) == '0' && sValue.substring(1, 2) != '.'){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02244") %><%--유효한 숫자만 입력하십시오--%>');
            obj.value = '';
            return;
        }

        var splitData = sValue.split(".");

        if(splitData.length == 2 && splitData[1].length > 1){
            alert("<%=messageService.getString("e3ps.message.ket_message", "01917") %><%--소수 1자리까지 입력가능합니다--%>");
            obj.value = '';
            return;
        }
    }

        //숫자 입력
    function handlerNum2(obj){
        var sValue = obj.value;

        if(sValue > 100) {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00043", 100) %><%--{0}이하로 입력하세요--%>");
            obj.value = '';
            return;
        }
    }

    //종합효율 계산
    function getTargetTotal(obj){

        handlerNum(obj);

        handlerNum2(obj);

        var targetFacility = document.forms[0].targetFacility;
        var targetPerform = document.forms[0].targetPerform;
        var targetGood = document.forms[0].targetGood;
        var targetTotal = document.forms[0].targetTotal;
        var total = 1;

        for (var inx = 1 ; inx < targetTotal.length ; inx++){
            total = 1;
            if(targetFacility[inx].value != "" && parseFloat(targetFacility[inx].value) != 0){
                total = total*(parseFloat(targetFacility[inx].value)/100);
            }
            if(targetPerform[inx].value != "" && parseFloat(targetPerform[inx].value) != 0){
                total = total*(parseFloat(targetPerform[inx].value)/100);
          }
            if(targetGood[inx].value != "" && parseFloat(targetGood[inx].value) != 0){
                total = total*(parseFloat(targetGood[inx].value)/100);
            }
            if((targetFacility[inx].value != "" && parseFloat(targetFacility[inx].value) != 0)
               || (targetPerform[inx].value != "" && parseFloat(targetPerform[inx].value) != 0)
               || (targetGood[inx].value != "" && parseFloat(targetGood[inx].value) != 0) ){
                targetTotal[inx].value = Math.round(total*1000)/10;
            }else {
                targetTotal[inx].value = "";
            }
        }
    }

  //저장 후 상세화면 이동
  function save(){
      if (isNullData(document.forms[0].pjtNo.value)){
          alert('<%=messageService.getString("e3ps.message.ket_message", "03112") %><%--프로젝트를 입력해 주십시오--%>');
          return;
      }

      if (document.getElementById("partTable").rows.length == 2){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02559") %><%--제품/부품을 입력하시기 바랍니다.--%>');
            return;
        }

      if (isNullData(document.forms[0].proteamName.value)){
          alert('<%=messageService.getString("e3ps.message.ket_message", "01794") %><%--생산처를 입력하시기 바랍니다.--%>');
          return;
      }

      if (isNullData(document.forms[0].fstChargeName.value)){
          //alert("수행담당자(정)를 입력하시기 바랍니다.");
          alert('<%=messageService.getString("e3ps.message.ket_message", "01977") %><%--수행담당자(정)를 입력하시기 바랍니다.--%>');
          return;
      }

      if (document.forms[0].fstCharge.value == document.forms[0].sndCharge.value){
          alert("<%=messageService.getString("e3ps.message.ket_message", "01976") %><%--수행담당자(정)과 수행담당자(부)가 동일인입니다--%>");
          return;
      }

      if (document.forms[0].startDate.value == "" || document.forms[0].endDate.value == ""){
          alert('<%=messageService.getString("e3ps.message.ket_message", "03241") %><%--활동기간을 입력하시기 바랍니다.--%>');
          return;
      }

      var strPlanStartDate = document.forms[0].startDate.value;
      var arrPlanStartDate = strPlanStartDate.split("-");
        var planStartDate = new Date(arrPlanStartDate[0],arrPlanStartDate[1]-1,arrPlanStartDate[2]);

      var planEndDate = new Date();
      var plus14 = planStartDate.getTime() + (13*24*60*60*1000);
      planEndDate.setTime(plus14);

      var month = planEndDate.getMonth()+1;
        var day = planEndDate.getDate();

        if (month < 10){
            month = '0' + month;
        }
        if (day < 10){
            day = '0' + day;
        }

        var strEndDate = document.forms[0].endDate.value;
      var arrEndDate = strEndDate.split("-");
        var endDate = new Date(arrEndDate[0],arrEndDate[1]-1,arrEndDate[2]);

        if (planStartDate>endDate){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03240") %><%--활동기간 종료일은 시작일 이후로 입력하십시오--%>');
          return;
        }

        var strplanEndDat = planEndDate.getYear() + '-' + month + '-' + day;

      var strPlanDate = document.forms[0].planDate.value;
      var arrPlanDate = strPlanDate.split("-");
      var planDate = new Date(arrPlanDate[0],arrPlanDate[1]-1,arrPlanDate[2]);

      if (planStartDate>planDate || planDate>planEndDate){
          alert("계획서 제출기한은 "+strPlanStartDate + "~ " + strplanEndDat + "입니다." );
          return;
      }
      if (endDate<planEndDate){
          alert("활동기간 종료일은 " + strplanEndDat + " 이후로 입력하십시오." );
          return;
      }

      if (document.getElementById("partTable").rows.length > 2){
          document.getElementById("isPart").value = "Y";
      }

      showProcessing();
      disabledAllBtn();

      var cmd = document.forms[0].cmd.value;
      var oid = document.forms[0].oid.value;

      document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningServlet?cmd='+cmd +'&oid='+oid;
      document.forms[0].encoding = 'multipart/form-data';
      document.forms[0].submit();
  }

  //초기유동관리 지정 상세화면 이동
  function goView(){
      if(confirm('<%=messageService.getString("e3ps.message.ket_message", "01950") %><%--수정을 취소하시겠습니까?--%>')){
          var oid = document.forms[0].oid.value;

            showProcessing();
          disabledAllBtn();

          window.location= '/plm/jsp/ews/ViewEarlyWarning.jsp?oid='+oid;
      }
    }

//-->
</script>
</head>
<body class="popup-background02">
<form method="post">
<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td class="space10"></td>
    </tr>
</table>
<!-- hidden begin -->
<input type='hidden' name='isPart' value='N'>
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='cmd' value=<%=cmd%> >
<!-- hidden end -->

<%  String title = (cmd.equals("update"))?messageService.getString("e3ps.message.ket_message", "01936")/*수정*/:messageService.getString("e3ps.message.ket_message", "00684")/*개정*/;  %>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02814") %><%--초기유동관리 지정--%> <%=title%></td>
                
              </tr>
            </table>
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
          <td>&nbsp;</td>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:goView();" class="btn_blue" ><%=messageService.getString("e3ps.message.ket_message", "02887") %><%--취소--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--목록--%></a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
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
          <col width="15%">
          <col width="35%">
          <col width="15%">
          <col width="35%">
          <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
          <td class="tdwhiteL"><%=ketEarlyWarning.getNumber()%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
          <td class="tdwhiteL0">
                <%
                    int version = 0;
                    if (cmd.equals("update")){
                        version = Integer.parseInt(VersionControlHelper.getVersionIdentifier(ketEarlyWarning).getSeries().getValue());
                    } else {
                        version = Integer.parseInt(VersionControlHelper.getVersionIdentifier(ketEarlyWarning).getSeries().getValue()) + 1;
                    }
              %>
              <a href="javascript:viewVersionHistory('<%=oid%>');"><%=version%></a></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%><span class="red">*</span></td>
          <td class="tdwhiteL"><input type="text" name="pjtNo" class="txt_field"  style="width:50%" value="<%=ketEarlyWarning.getPjtNo()%>" >
          <input type="hidden" name="pjtOid">
            &nbsp;<a href="javascript:popupProject();"><img src="../../portal/images/icon_5.png" style="vertical-align: 0px;" border="0"></a>
            &nbsp;<a href="javascript:deleteValue('pjtNo');deleteValue('pjtName');deletePartAll();"><img src="../../portal/images/icon_delete.gif" style="vertical-align: 0px;" border="0"></a></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
          <td class="tdwhiteL0"><input type="text" name="pjtName" class="txt_fieldRO" style="width:90%" readonly value="<%=EWSUtil.ViewPjtName(ketEarlyWarning.getPjtNo())%>" ></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02557") %><%--제품/부품--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhitel0"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
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
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:selectPart();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                      <td>
                          <table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deletePart();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                            <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                          </tr>
                        </table>
                      </td>
                      <td width="5">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table id="partTable" width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="30" class="tdgrayM"><input type="checkbox" name="partCheck" onClick="javascript:allcheck();"></td>
                <td width="90" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="180" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td class="tdgrayM">Die No</td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
                <td Class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
              </tr>
              <tr style="display:none">
                  <td><input type='checkbox' name='partCheck'></td>
                  <td>
                      <input name='partNo' value="0">
                      <input name='poid' value="0">
                  </td>
                  <td><input name='partName' value="0"></td>
                  <td><input name='dieNo' value="0"></td>
                  <td><input name='partType' value="0"></td>
                  <td><input name='partProTeam' value="0"></td>
                  <td><input name='dieProTeam' value="0"></td>
              </tr>
              <%
                ArrayList partList = EWSHelper.manager.getPartTarget(ketEarlyWarning);
                Hashtable<String, String> part = new Hashtable<String, String>();

                                for ( int inx = 0; inx < partList.size() ; inx++ ) {
                                    part = (Hashtable)partList.get(inx);
              %>
                        <tr>
                            <td class="tdwhiteM"><input type="checkbox" name="partCheck" ><input name='poid' type="hidden" value="<%=(String)part.get("poid")%>"></td>
                        <td class="tdwhiteM"><%=(String)part.get("partNo")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("partName")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("dieNo")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("type")%></td>
                                <td class="tdwhiteM"><%=(String)part.get("proTeamName")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("dieProTeam")%></td>
                      </tr>
              <%}%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01975") %><%--수행담당자(정)--%><span class="red">*</span></td>
          <td class="tdwhiteL">
              <input type="text" name="fstChargeName" class="txt_field"  style="width:80" value="<%=((WTUser)CommonUtil.getObject(ketEarlyWarning.getFstCharge())).getFullName()%>" >
              <input type="hidden" name="fstCharge" value="<%=ketEarlyWarning.getFstCharge()%>" >
            &nbsp;<a href="javascript:selectUser('fstCharge');"><img src="../../portal/images/icon_user.gif" style="vertical-align: 0px;" border="0"></a>
            &nbsp;<a href="javascript:deleteValue('fstChargeName');deleteValue('fstCharge');"><img src="../../portal/images/icon_delete.gif" style="vertical-align: 0px;" border="0"></a>
          </td>
          <td class="tdblueL" rowspan="2"><%=messageService.getString("e3ps.message.ket_message", "03238") %><%--활동기간--%><span class="red">*</span></td>
          <td class="tdwhiteL0" rowspan="2">
              <select name="workingMM" class="fm_jmp" style="width:85px;margin-bottom: 2px;" onChange="javascript:selectMM(this);">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
              <%  for (int inx = 2 ; inx < 7 ; inx++){  %>
                  <option value=<%=inx%> <%if(inx == ketEarlyWarning.getWorkingMM())out.print("selected");%> > <%=messageService.getString("e3ps.message.ket_message", "00012", inx) %><%--{0}개월--%> </option>
                  <%  }  %>
            </select>
            <br>
            <input type="text" name="startDate" class="txt_field"  style="width:85px;padding-bottom: " value=<%=DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(),"yyyy-MM-dd")%> >
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startDate');" style="cursor: hand;vertical-align: 0px;">
            ~
            <input type="text" name="endDate" class="txt_field"  style="width:85px;" value=<%=DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(),"yyyy-MM-dd")%>>
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endDate');" style="cursor: hand;vertical-align: 0px;">
          </td>

        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01974") %><%--수행담당자(부)--%></td>
          <td class="tdwhiteL">
              <% if (ketEarlyWarning.getSndCharge() != null) { %>
                  <input type="text" name="sndChargeName" class="txt_field"  style="width:80" value="<%=((WTUser)CommonUtil.getObject(ketEarlyWarning.getSndCharge())).getFullName()%>" >
              <% } else {%>
                  <input type="text" name="sndChargeName" class="txt_field"  style="width:80" value='' >
              <% }%>
              <input type="hidden" name="sndCharge" value="<%=StringUtil.checkNull(ketEarlyWarning.getSndCharge())%>" >
            &nbsp;<a href="javascript:selectUser('sndCharge');"><img src="../../portal/images/icon_user.gif" style="vertical-align: 0px;" border="0"></a>
            &nbsp;<a href="javascript:deleteValue('sndChargeName');deleteValue('sndCharge');"><img src="../../portal/images/icon_delete.gif" style="vertical-align: 0px;" border="0"></a>
          </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%><span class="red">*</span></td>
          <td class="tdwhiteL">
              <select name="selInOut" class="fm_jmp" style="width:55;margin-bottom: 2px;" onChange="javascript:changeProteam();changeTarget(this);" >
              <%
                   String inout = ketEarlyWarning.getInOut();
                   String inoutNo = null;
                   String inoutName = null;
                   PartnerDao partnerDao = null;

                   if (inout != null && inout.equals("i")){
                     inoutNo = ketEarlyWarning.getProteamNo();
                     inoutName = ((NumberCode)CommonUtil.getObject(ketEarlyWarning.getProteamNo())).getName();
                   }else {
                                 inoutNo = ketEarlyWarning.getPartnerNo();
                     partnerDao = new PartnerDao();
                                 inoutName = partnerDao.ViewPartnerName(ketEarlyWarning.getPartnerNo());
                             }
            %>
              <option value="i" <%if(inout != null && inout.equals("i"))out.print("selected");%> ><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></option>
                            <option value="o" <%if(inout != null && inout.equals("o"))out.print("selected");%> ><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></option>
            </select>
            &nbsp;<input type="text" name="proteamName" id="proteamName" class="txt_fieldRO"  style="width:70;" value="<%=StringUtil.checkNull(inoutName)%>" readonly >
            <input type="hidden" name="proteamNo" id="proteamNo" value=<%=inoutNo%> >
            &nbsp;<img id="proteamBtn1" src="../../portal/images/icon_5.png" border="0" onClick="javascript:selectProduction();" style="cursor:hand;" style="vertical-align: 0px;">&nbsp;
            <img id="proteamBtn2" src="../../portal/images/icon_delete.gif" border="0" onclick="javascript:deleteValue('proteamName');deleteValue('proteamNo');" style="cursor:hand;" style="vertical-align: 0px;">
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00811") %><%--계획서 제출기한--%></td>
          <td class="tdwhiteL0">
              <%
              if(ketEarlyWarning.getPlanDate() != null){ %>
                  <input type="text" name="planDate" class="txt_field" style="width:85px" value=<%=DateUtil.getTimeFormat(ketEarlyWarning.getPlanDate(),"yyyy-MM-dd")%> >
              <% }else {%>
                  <input type="text" name="planDate" class="txt_field"  style="width:85px">
              <% } %>
              <img src="../../portal/images/icon_delete.gif" border="0" onclick="javascript:deleteValue('planDate');" style="cursor:hand;vertical-align: 0px;"></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01381") %><%--목표--%></td>
          <td colspan="3" class="tdwhitel0">
              <table width="98%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table id="targetTable" width="98%" cellpadding="0" cellspacing="0" class="table_border">
              <tr>
                <td width="80" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                <td width="165" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                <td width="80" rowspan="2" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00833") %><%--고객불량--%><br>(PPM)</td>
                <td rowspan="2" class="tdgrayM">
                    <% if (ketEarlyWarning.getInOut().equals("i")) { %>
                         <span id="processError"><%=messageService.getString("e3ps.message.ket_message", "00883") %><%--공정불량--%></span>
                          <% }else { %>
                               <span id="processError"><%=messageService.getString("e3ps.message.ket_message", "01934") %><%--수입불량--%></span>
                          <% } %>
                    <br>(PPM)</td>
                <td colspan="4" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01785") %><%--생산성(%)--%></td>
              </tr>
              <tr>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02002") %><%--시간가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01905") %><%--성능가동--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02103") %><%--양품율--%></td>
                <td class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02665") %><%--종합효율--%></td>
              </tr>
              <tr style="display:none">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td><input name='targetcusError' ></td>
                  <td><input name='targetWorkError' ></td>
                  <td><input name='targetFacility' ></td>
                  <td><input name='targetPerform' ></td>
                  <td><input name='targetGood' ></td>
                  <td><input name='targetTotal' ></td>
              </tr>
              <%
                                String strClass = null;
                                String strReadonly = "";
                                if (ketEarlyWarning.getInOut().equals("i")) {
                                  strClass = "txt_fieldR";
                                }else {
                                    strClass = "txt_fieldRRO";
                                    strReadonly = "readonly";
                                }
                                for ( int inx = 0; inx < partList.size() ; inx++ ) {
                                    part = (Hashtable)partList.get(inx);
              %>
                      <tr>
                        <td class="tdwhiteM"><%=(String)part.get("partNo")%></td>
                        <td class="tdwhiteM"><%=(String)part.get("partName")%></td>
                        <td class="tdwhiteM"><input type='text' name='targetcusError' class='txt_fieldR'  style='width:78' onKeyUp='javascript:handlerNum(this);'
                            value="<%=StringUtil.getNumber((String)part.get("cusError"))%>" ></td>
                        <td class="tdwhiteM"><input type='text' name='targetWorkError' class='txt_fieldR'  style='width:78' onKeyUp='javascript:handlerNum(this);'
                            value="<%=StringUtil.getNumber((String)part.get("workError"))%>" ></td>
                        <td class="tdwhiteM"><input type='text' name='targetFacility' class='<%=strClass%>'  style='width:98%' onKeyUp='javascript:getTargetTotal(this);' onchange='javascript:getTargetTotal(this);'
                            value="<%=(String)part.get("facility")%>" <%=strReadonly%> ></td>
                        <td class="tdwhiteM"><input type='text' name='targetPerform' class='<%=strClass%>'  style='width:98%' onKeyUp='javascript:getTargetTotal(this);' onchange='javascript:getTargetTotal(this);'
                            value="<%=(String)part.get("perform")%>" <%=strReadonly%> ></td>
                        <td class="tdwhiteM"><input type='text' name='targetGood' class='<%=strClass%>'  style='width:98%' onKeyUp='javascript:getTargetTotal(this);' onchange='javascript:getTargetTotal(this);'
                            value="<%=(String)part.get("good")%>" <%=strReadonly%> ></td>
                      <td class="tdwhiteM"><input type='text' name='targetTotal' class='txt_fieldRRO'  style='width:98%' readonly
                          value="<%=(String)part.get("targetTotal")%>" ></td>
                      </tr>
              <%}%>
            </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01439") %><%--문제점 List--%></td>
          <td colspan="3" class="tdwhitel0">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0"  id="fileTable">
                            <tr>
                                <td></td>
                                <td align="right">
                                    <table>
                                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:insertSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:deleteSecondaryFile();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                  <td width="3">&nbsp;</td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                                </td>
                            </tr>
                            <tr align="center" id="fileTableRow" style="display:none">
                                <td align="center" valign="middle" width="24" height="22">
                                    <input type="checkbox" name="fileDelete">
                                </td>
                                <td align="left">
                                    <input type="file" name="filePath" id=input onChange='isValidSecondarySize(this)' onkeyDown="this.blur()" style="ime-mode:disabled" class="txt_fieldRO" size="100">
                                </td>
                            </tr>
                            <%
                                ContentHolder holder = ContentHelper.service.getContents((ContentHolder)CommonUtil.getObject(oid));
                                Vector files = ContentHelper.getContentList(holder);

                                for(int inx=0; files !=null && inx<files.size(); inx++) {
                                    ApplicationData appData = (ApplicationData)files.get(inx);
                                    String appDataOid = appData.getPersistInfo().getObjectIdentifier().getStringValue();
                                    //String url = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid="+CommonUtil.getOIDString(holder)+"&adOid="+appDataOid;
                                    String url = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + CommonUtil.getOIDString(holder) + "&cioids=" + appDataOid + "&role=" + appData.getRole().toString();
                            %>
                                    <tr align="center" bgcolor="#ffffff" >
                                        <td align="center" width="24" height="22"><input type="checkbox" name="fileDelete"></td>
                                        <td align="left"><input type="hidden" name="secondaryDelFile" value="<%=appDataOid%>" style="width:100%">&nbsp;
                                            <%=e3ps.common.content.E3PSContentHelper.service.getIconImgTag(appData)%>&nbsp;<a href="<%=url%>" target="_blank"><%=appData.getFileName()%></a></td>
                                    </tr>
                            <%
                                }
                            %>
                        </table>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
<form method="post"  name="SearchListForm">
<input type="hidden" name="page" value="">
<input type="hidden" name="ewsno" value="">
<input type="hidden" name="pjtno" value="">
<input type="hidden" name="partno" value="">
<input type="hidden" name="partname" value="">
<input type="hidden" name="inout" value="">
<input type="hidden" name="production" value="">
<input type="hidden" name="fstcharge" value="">
<input type="hidden" name="step" value="">
<input type="hidden" name="creator" value="">
<input type="hidden" name="createdate" value="">
</form>
</body>
</html>
