<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,
                  java.util.ArrayList,
                  java.util.Vector,
                  java.util.StringTokenizer,
                  wt.query.QuerySpec,
                  wt.query.SearchCondition,
                  wt.query.ClassAttribute,
                  wt.query.OrderBy,
                  wt.fc.PersistenceHelper,
                  wt.fc.QueryResult,
                  e3ps.project.beans.OEMTypeHelper,
                  e3ps.project.outputtype.OEMProjectType,
                  e3ps.project.outputtype.OEMType,
                  e3ps.common.util.StringUtil,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.dms.beans.DMSUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
  String cLine =  StringUtil.checkNull(request.getParameter("cLine"));
  String arrList =  StringUtil.checkNull(request.getParameter("arr"));
  String divCode =  StringUtil.checkNull(request.getParameter("divCode"));

  Boolean isReadonly = true;
  int carTypeItemCnt = 0;

  Vector carTypeItems = null;
  Vector carTypeItem = null;
  Vector carTypeIt = null;

  String comName = "";

  //년도별예상수량지정시 기 데이터 표현
  if(!arrList.equals("")){
    carTypeItems = new Vector();
    carTypeItem = new Vector();
    StringTokenizer st = new StringTokenizer(arrList, ",");
    int i = 0;
    while (st.hasMoreTokens()) {
      carTypeItem.addElement(st.nextToken());
      i++;

      if(i%12==0){
        i = 0;
        carTypeItemCnt++;
        carTypeItems.addElement(carTypeItem);
        carTypeItem = null;
        carTypeItem = new Vector();
      }
    }

    for(i = 0; i< carTypeItemCnt; i++) {
      carTypeIt = new Vector();
      carTypeIt = (Vector)carTypeItems.get(i);

      if(comName.equals("")){
        comName = DMSUtil.serCustomerNm((String)carTypeIt.get(11));
      }
    }
  }else{
    arrList="0";
  }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "01178") %><%--년도별 예상수량 지정--%></title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language=javascript src="/plm/portal/js/util.js"></script>
<script src="/plm/portal/js/ajax.js"></SCRIPT>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language="JavaScript">
<!--
  window.onload=function(){
  var fm = document.form01;

    //년도별예상수량지정시 전자사업부일경우
    if("<%=divCode%>"=="ER"){
      fm.chkH.checked=true;
      if("<%=arrList%>"=="0"){
        hAddCarType("<%=divCode%>");
      }else{
        fm.domGb1.disabled = true;
        fm.domGb2.disabled = true;
        fm.carCompany.disabled = true;
        fm.carType.disabled = true;

        var abody = document.getElementById("addCarTypeBtn");
        abody.disabled = true;
        var sbody = document.getElementById("subCarTypeBtn");
        sbody.disabled = true;
      }

      fm.chkH.disabled=true;
    }else{
      carCompanyLs("1000");

      //년도별예상수량지정시 데이터가 있다면
      if("<%=arrList%>"!="0"){

        if("<%=comName%>"==""){
          fm.chkH.checked=true;
          fm.domGb1.disabled = true;
          fm.domGb2.disabled = true;
          fm.carCompany.disabled = true;
          fm.carType.disabled = true;

          var abody = document.getElementById("addCarTypeBtn");
          abody.disabled = true;
          var sbody = document.getElementById("subCarTypeBtn");
          sbody.disabled = true;
        }
      }
    }
  }

  function isNull(str) {
    if(str==null||str==""){
      return true;
    }
    return false;
  }

  function isNotDigit(str) {
    var pattern = /^[0-9]+$/;
    str = str.replace(".", "0");

    if((str.substring(0,1)=="0")&&(str.length > 1)) {
      return true;
    }

    if(!pattern.test(str)){
      return true;
    }
    return false;
  }

  //고객사 변경시
/*   function changeCarCompany(){
    var fm = document.form01;
    fm.carType.length = 1;
    fm.carType.selectedIndex = 0;
    var obj = document.getElementById('dummy');

    obj.src = "./CarTypeSelect.jsp?numCodeOid=" + fm.carCompany.options[fm.carCompany.selectedIndex].value;
  } */
  
  function changeCarCompany(fObj) {
      var form = document.form01;
      if(fObj.value != '') {
          selectSearch2(fObj.value);
      }
  }

  function selectSearch2(svalue) {
      //onProgressBar();

      var param = "command=select2&oemtype=CARTYPE&oid=" + svalue+"&mode=1";
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

          var fTD2 = document.all.item("carType");
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
      var aa = document.all.item("carType");

      for(var j = 1 ; j < aa.length ; j++){
          aa.remove(j);
      }

  }

  //고객사 선택시
  function carCompanyLs(str){

    var fm = document.form01;

    if(fm.domCd.value != str){
      if(str=="1000"){
        fm.domGb1.checked = true;
        fm.domGb2.checked = false;
      }else{
        fm.domGb1.checked = false;
        fm.domGb2.checked = true;
      }
      
      
      $("#carCompany").empty().data('options');
      $("#carCompany").append("<option value='' >"+ "<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>" +"</option>");

      if ( str == "1000" ){
          numCodeAjax("CUSTOMEREVENT", "1000", "자동차", "carCompany");
      }
      else{
          if ( str != '' ) {
              numCodeAjax("CUSTOMEREVENT", str, "자동차", "carCompany");
          }
      }
      clearCartype();

      /* fm.domCd.value = str;
      fm.carType.length = 1;
      fm.carType.selectedIndex = 0;
      fm.carCompany.length = 1;
      fm.carCompany.selectedIndex = 0;
      var obj = document.getElementById('dummy');

      obj.src = "./CarCompanySelect.jsp?domCd=" + str; */
    }
  }
  
//Number Code Ajax
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

  //차종변경시
  function changeCarType(){

    var fm = document.form01
    var tStr = fm.carType.options[fm.carType.selectedIndex].value;
    var tStr1 = fm.carType.options[fm.carType.selectedIndex].text;
    var tStr2 = fm.carCompany.options[fm.carCompany.selectedIndex].text;

  }

  //차종추가시
  function addCarType(){

    var fm = document.form01;

    var url = "./CarTypePlanSelect.jsp?carTypeOid=" + fm.carType.options[fm.carType.selectedIndex].value + "&carTypeName=" + fm.carType.options[fm.carType.selectedIndex].text + "&companyName=" + fm.carCompany.options[fm.carCompany.selectedIndex].text;
    
    if(fm.carCompany.disabled == false){
        var head= document.getElementsByTagName('head')[0];
        var script= document.createElement('script');
        script.src= url;
        head.appendChild(script);
    }
  }

  //차종삭제시
  function subCarType(){

    var fm = document.form01
    var tStr = fm.carType.options[fm.carType.selectedIndex].value;

    var body = document.getElementById("carTypePlanTable");

    if(fm.carCompany.disabled == false){

      if (body.rows.length == 2) return;
      var t_checks = fm.iCarTypePlanChk;


      if (body.rows.length == 3) {
        if (t_checks[0]=="[object]"){
          if (t_checks[0].checked){
            body.deleteRow(1);
          }
        }else{
          if (t_checks.checked){
            body.deleteRow(1);
          }
        }
      } else {
        for (var i = body.rows.length-2; i > 0; i--) {
          if (t_checks[i-1].checked) body.deleteRow(i);
        }
      }

      var confBtn = document.getElementById("confBtn");
      confBtn.disabled = true;
      confBtn.onclick = "return false";
    }
  }

  //차종선택 추가시
  function hAddCarType(divCode){

    var fm = document.form01
    var tStr = fm.carType.options[fm.carType.selectedIndex].value;
    var body = document.getElementById("carTypePlanTable");

    if (body.rows.length > 2){
      for (var i = body.rows.length-2; i > 0; i--) {
        body.deleteRow(i);
      }
    }

    var confBtn = document.getElementById("confBtn");
    confBtn.disabled = true;
    confBtn.onclick = "return false";
    //차종선택 추가시 직접입력이라면
    if(fm.chkH.checked==true){
      fm.domGb1.disabled = true;
      fm.domGb2.disabled = true;
      fm.carCompany.disabled = true;
      fm.carType.disabled = true;

      var abody = document.getElementById("addCarTypeBtn");
      abody.disabled = true;
      var sbody = document.getElementById("subCarTypeBtn");
      sbody.disabled = true;


      var innerRow = body.insertRow(1);
      var innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.innerText = " ";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.innerText = " ";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      if(divCode=="CA"){
        innerCell.innerHTML = "<input type='text' name='carTypeCode' class='txt_field' style='width:80px'  onChange='javascript:usgChgVal();' >";
      }else{
        innerCell.innerHTML = "<input type='text' name='carTypeCode' class='txt_field' style='width:80px'  onChange='javascript:usgChgVal();' >";
      }

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='text' name='y1' class='txt_fieldR' style='width:45' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='text' name='y2' class='txt_fieldR' style='width:45' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='text' name='y3' class='txt_fieldR' style='width:45' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='text' name='y4' class='txt_fieldR' style='width:45' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='text' name='y5' class='txt_fieldR' style='width:45' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR"
      innerCell.innerHTML = "<input type='text' name='y6' class='txt_fieldR' style='width:45' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteR";
      innerCell.innerHTML = "<input type='text' name='sum' readonly class='txt_fieldRRO' style='width:55'>";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.innerHTML = "<input type='text' name='usage' class='txt_fieldR' style='width:30' onChange='javascript:usgChgVal();'  >";

      innerCell = innerRow.insertCell();
      innerCell.className = "tdwhiteM";
      innerCell.innerHTML = "<input type='text' name='optRate' class='txt_fieldR' style='width:40' onChange='javascript:usgChgVal();'>";

      innerCell = innerRow.insertCell();
      innerCell.innerHTML = "<input type='hidden' name='carTypeOid' value='0'>";


    }else{
      fm.domGb1.disabled = false;
      fm.domGb2.disabled = false;
      fm.carCompany.disabled = false;
      fm.carType.disabled = false;

      var abody = document.getElementById("addCarTypeBtn");
      abody.disabled = false;
      var sbody = document.getElementById("subCarTypeBtn");
      sbody.disabled = false;

    }

  }

  //차종중복체크
  function carTypeDuplicateCheck(pOid) {

    var tBody = document.getElementById("carTypePlanTable");
    var rowsLen = tBody.rows.length;
    if(rowsLen > 2) {
      var carTypeOid = document.forms[0].carTypeOid;
      var carTypeLen = carTypeOid.length;

      if(carTypeLen) {
        for(var i = 0; i < carTypeLen; i++) {

          if(carTypeOid[i].value == pOid) {
            return true;
          }
        }
      } else {
        if(carTypeOid.value == pOid) {
          return true;
        }
      }
    }
    return false;
  }

  //합계계산 선택시 필수입력체크
  function validateCal(){

    var tBody = document.getElementById("carTypePlanTable");
    var rowsLen = tBody.rows.length;
    if(rowsLen > 2) {
      var carTypeCd = document.forms[0].carTypeCode;
      var carTypeUsage = document.forms[0].usage;
      var carTypeOptRate = document.forms[0].optRate;
      var usgLen = carTypeUsage.length;

      if(usgLen) {
        for(var i = 0; i < usgLen; i++) {

          if( isNull(carTypeCd[i].value) ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02751") %><%--차종을 반드시 입력하셔야 합니다--%>');
            return false;
          }

          if( isNull(carTypeUsage[i].value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00546") %><%--Usage를 반드시 입력하셔야 합니다--%>');
            return false;
          }else{
            if (isNotDigit(carTypeUsage[i].value)) {
              alert('<%=messageService.getString("e3ps.message.ket_message", "00544") %><%--Usage는 반드시 유효한 숫자여야 합니다--%>');
              return false;
            }
          }

          if( isNull(carTypeOptRate[i].value) ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02169") %><%--옵션률을 반드시 입력하셔야 합니다.--%>');
            return false;
          }else{
            if (isNotDigit(carTypeOptRate[i].value)) {
                          alert('<%=messageService.getString("e3ps.message.ket_message", "02167") %><%--옵션률은 반드시 유효한 숫자여야 합니다--%>');
              return false;
            }
          }

          if(( isNull(document.forms[0].y1[i].value) )&&( isNull(document.forms[0].y2[i].value) )&&( isNull(document.forms[0].y3[i].value) )&&( isNull(document.forms[0].y4[i].value) )&&( isNull(document.forms[0].y5[i].value) )&&( isNull(document.forms[0].y6[i].value) )) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00017", 1, 6) %><%--{0}년차 ~ {1}년차 예상수량을 1개이상 입력하셔야 합니다--%>');
            return false;
          }else{
            if(document.form01.chkH.checked==true){
              if ((isNotDigit(document.forms[0].y1[i].value))&&( !isNull(document.forms[0].y1[i].value) )) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 1) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }

              if ((isNotDigit(document.forms[0].y2[i].value))&&( !isNull(document.forms[0].y2[i].value) )) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 2) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }

              if ((isNotDigit(document.forms[0].y3[i].value))&&( !isNull(document.forms[0].y3[i].value) )) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 3) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }

              if ((isNotDigit(document.forms[0].y4[i].value))&&( !isNull(document.forms[0].y4[i].value) )) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 4) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }

              if ((isNotDigit(document.forms[0].y5[i].value))&&( !isNull(document.forms[0].y5[i].value) )) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 5) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }

              if ((isNotDigit(document.forms[0].y6[i].value))&&( !isNull(document.forms[0].y6[i].value) )) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 6) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
            }
          }
        }
      } else {
          if( isNull(carTypeCd.value) ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02751") %><%--차종을 반드시 입력하셔야 합니다--%>');
            return false;
          }

          if( isNull(carTypeUsage.value) ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00546") %><%--Usage를 반드시 입력하셔야 합니다--%>');
            return false;
          }else{
            if (isNotDigit(carTypeUsage.value)) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "00544") %><%--Usage는 반드시 유효한 숫자여야 합니다--%>');
              return false;
            }
          }

          if( isNull(carTypeOptRate.value) ) {
                        alert('<%=messageService.getString("e3ps.message.ket_message", "02169") %><%--옵션률을 반드시 입력하셔야 합니다.--%>');
            return false;
          }else{
            if (isNotDigit(carTypeOptRate.value)) {
                            alert('<%=messageService.getString("e3ps.message.ket_message", "02167") %><%--옵션률은 반드시 유효한 숫자여야 합니다--%>');
              return false;
            }
          }

          if(( isNull(document.forms[0].y1.value) )&&( isNull(document.forms[0].y2.value) )&&( isNull(document.forms[0].y3.value) )&&( isNull(document.forms[0].y4.value) )&&( isNull(document.forms[0].y5.value) )&&( isNull(document.forms[0].y6.value) )) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "00017", 1, 6) %><%--{0}년차 ~ {1}년차 예상수량을 1개이상 입력하셔야 합니다--%>');
            return false;
          }else{
            if(document.form01.chkH.checked==true){
              if ((isNotDigit(document.forms[0].y1.value))&&( !isNull(document.forms[0].y1.value) )) {
                  alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 1) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
              if ((isNotDigit(document.forms[0].y2.value))&&( !isNull(document.forms[0].y2.value) )) {
                  alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 2) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
              if ((isNotDigit(document.forms[0].y3.value))&&( !isNull(document.forms[0].y3.value) )) {
                  alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 3) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
              if ((isNotDigit(document.forms[0].y4.value))&&( !isNull(document.forms[0].y4.value) )) {
                  alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 4) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
              if ((isNotDigit(document.forms[0].y5.value))&&( !isNull(document.forms[0].y5.value) )) {
                  alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 5) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
              if ((isNotDigit(document.forms[0].y6.value))&&( !isNull(document.forms[0].y6.value) )) {
                  alert('<%=messageService.getString("e3ps.message.ket_message", "00020", 6) %><%--{0}년차 예상수량은 반드시 유효한 숫자여야 합니다--%>');
                return false;
              }
            }
         }
      }
    }else{
            alert('<%=messageService.getString("e3ps.message.ket_message", "01360") %><%--먼저 차종을 추가해야 합니다--%>');
      return false;
    }

    return true;
  }

  //합계계산 선택시 계산처리
  function sumCal(){

    if (!validateCal()) return;
    var tBody = document.getElementById("carTypePlanTable");
    var rowstLen = tBody.rows.length;
    var d = document.form01;
    var rowsLen = d.usage.length;
    var carTypes = "";
    var y1 = 0;
    var y2 = 0;
    var y3 = 0;
    var y4 = 0;
    var y5 = 0;
    var y6 = 0;
    var sum = 0;
    var usg = 0;

    if((rowsLen) > 1) {
      for(var i = 0; i < rowsLen; i++) {
        if(d.y1[i].value=="") d.y1[i].value="0";
        if(d.y2[i].value=="") d.y2[i].value="0";
        if(d.y3[i].value=="") d.y3[i].value="0";
        if(d.y4[i].value=="") d.y4[i].value="0";
        if(d.y5[i].value=="") d.y5[i].value="0";
        if(d.y6[i].value=="") d.y6[i].value="0";


        if( i==rowsLen-1){
          carTypes = carTypes + d.carTypeCode[i].value;
        }else{
          carTypes = carTypes + d.carTypeCode[i].value + ",";
        }

        if(d.sum[i].value==0){
          d.sum[i].value = money_format(parseInt(removecomma(d.y1[i].value),10) + parseInt(removecomma(d.y2[i].value),10) + parseInt(removecomma(d.y3[i].value),10) + parseInt(removecomma(d.y4[i].value),10) + parseInt(removecomma(d.y5[i].value),10) + parseInt(removecomma(d.y6[i].value),10)) ;
        }

        y1 = y1 + Math.round(parseInt(removecomma(d.y1[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        y2 = y2 + Math.round(parseInt(removecomma(d.y2[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        y3 = y3 + Math.round(parseInt(removecomma(d.y3[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        y4 = y4 + Math.round(parseInt(removecomma(d.y4[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        y5 = y5 + Math.round(parseInt(removecomma(d.y5[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        y6 = y6 + Math.round(parseInt(removecomma(d.y6[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        sum = sum + Math.round(parseInt(removecomma(d.sum[i].value), 10) * parseInt(d.usage[i].value, 10) * parseInt(d.optRate[i].value, 10) / 100);
        if (usg < d.usage[i].value){
          usg = d.usage[i].value;
        }
      }
    } else {
        if(d.y1.value=="") d.y1.value="0";
        if(d.y2.value=="") d.y2.value="0";
        if(d.y3.value=="") d.y3.value="0";
        if(d.y4.value=="") d.y4.value="0";
        if(d.y5.value=="") d.y5.value="0";
        if(d.y6.value=="") d.y6.value="0";

        carTypes = d.carTypeCode.value;

        d.sum.value = money_format(parseInt(removecomma(d.y1.value),10) + parseInt(removecomma(d.y2.value),10) + parseInt(removecomma(d.y3.value),10) + parseInt(removecomma(d.y4.value),10) + parseInt(removecomma(d.y5.value),10) + parseInt(removecomma(d.y6.value),10));

        y1 = y1 + Math.round(parseInt(removecomma(d.y1.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);
        y2 = y2 + Math.round(parseInt(removecomma(d.y2.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);
        y3 = y3 + Math.round(parseInt(removecomma(d.y3.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);
        y4 = y4 + Math.round(parseInt(removecomma(d.y4.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);
        y5 = y5 + Math.round(parseInt(removecomma(d.y5.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);
        y6 = y6 + Math.round(parseInt(removecomma(d.y6.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);

        sum = sum + Math.round(parseInt(removecomma(d.sum.value), 10) * parseInt(d.usage.value, 10) * parseInt(d.optRate.value, 10) / 100);
        if (usg < d.usage.value){
          usg = d.usage.value;
        }
    }

    tBody.rows[rowstLen-1].cells[2].innerText = carTypes;
    tBody.rows[rowstLen-1].cells[3].innerHTML = "<input type='text' name = 'sy1' class='txt_fieldR' style='width:45' value=" + money_format(y1) + ">";
    tBody.rows[rowstLen-1].cells[4].innerHTML = "<input type='text' name = 'sy2' class='txt_fieldR' style='width:45' value=" + money_format(y2) + ">";
    tBody.rows[rowstLen-1].cells[5].innerHTML = "<input type='text' name = 'sy3' class='txt_fieldR' style='width:45' value=" + money_format(y3) + ">";
    tBody.rows[rowstLen-1].cells[6].innerHTML = "<input type='text' name = 'sy4' class='txt_fieldR' style='width:45' value=" + money_format(y4) + ">";
    tBody.rows[rowstLen-1].cells[7].innerHTML = "<input type='text' name = 'sy5' class='txt_fieldR' style='width:45' value=" + money_format(y5) + ">";
    tBody.rows[rowstLen-1].cells[8].innerHTML = "<input type='text' name = 'sy6' class='txt_fieldR' style='width:45' value=" + money_format(y6) + ">";
    tBody.rows[rowstLen-1].cells[9].innerHTML = "<input type='text' name = 'ssum' class='txt_fieldR' style='width:55' value=" + money_format(sum) + ">";
    tBody.rows[rowstLen-1].cells[10].innerHTML = "<input type='text' name = 'susg' class='txt_fieldR' style='width:30' value=" + money_format(usg) + ">";

    var confBtn = document.getElementById("confBtn");
    confBtn.disabled = false;
    confBtn.onclick = "return true";

  }

  function removecomma(s){
    s = s.replace(/\,/, "");
    return s;
  }

  //데이터변경시 확인버튼 disable
  function usgChgVal(){
    var confBtn = document.getElementById("confBtn");
    confBtn.disabled = true;
    confBtn.onclick = "return false";
  }

  //합게계산시 데이터 생성
  function getItems(tId){
    var tBody = document.getElementById("carTypePlanTable");
    var rowstLen = tBody.rows.length;
    var arr = new Array();

    var d = document.form01;
    var rowsLen = d.usage.length;
    var idx =  0;
    if(rowsLen){
      for(var i = 0; i < rowsLen; i++) {
        var subarr = new Array();
        subarr[0] = tId;
        subarr[1] = d.y1[i].value;
        subarr[2] = d.y2[i].value;
        subarr[3] = d.y3[i].value;
        subarr[4] = d.y4[i].value;
        subarr[5] = d.y5[i].value;
        subarr[6] = d.y6[i].value;
        subarr[7] = d.sum[i].value;
        subarr[8] = d.usage[i].value;
        subarr[9] = d.optRate[i].value;
        subarr[10] = d.carTypeOid[i].value;
        subarr[11] = d.carTypeCode[i].value;
        arr[i]=subarr;

      }
    }else{
      var subarr = new Array();
      subarr[0] = tId;
      subarr[1] = d.y1.value;
      subarr[2] = d.y2.value;
      subarr[3] = d.y3.value;
      subarr[4] = d.y4.value;
      subarr[5] = d.y5.value;
      subarr[6] = d.y6.value;
      subarr[7] = d.sum.value;
      subarr[8] = d.usage.value;
      subarr[9] = d.optRate.value;
      subarr[10] = d.carTypeOid.value;
      subarr[11] = d.carTypeCode.value;

      arr[0]=subarr;
    }

    return arr;
  }

  //합게계산시 합계데이터 생성
  function getSum(){
    var tBody = document.getElementById("carTypePlanTable");
    var rowstLen = tBody.rows.length;
    var d = document.form01;
    var arr = new Array();

    arr[0] = tBody.rows[rowstLen-1].cells[2].innerText;
    arr[1] = d.sy1.value;
    arr[2] = d.sy2.value;
    arr[3] = d.sy3.value;
    arr[4] = d.sy4.value;
    arr[5] = d.sy5.value;
    arr[6] = d.sy6.value;
    arr[7] = d.ssum.value;
    arr[8] = d.susg.value;

    arr[9] = (Math.floor(Math.random()*100000))+1;
    return arr;
  }

  //확인버튼클릭
  function doSelect() {
    if(confBtn.disabled == true){
            alert('<%=messageService.getString("e3ps.message.ket_message", "03153") %><%--합계 계산 버튼을 먼저 클릭해주시기 바립니다--%>');
       return;
    }

    var arr = getSum();
    var arr1 = getItems(arr[9]);
    var currLine = document.form01.cLine.value;
    //var opener = window.dialogArguments;
    if(opener) {
      if(opener.getCarType) {
        opener.getCarType(arr,arr1,currLine);
      }
    }
    self.close();
  }

//-->
</script>
<script id='dummy'></script>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form name=form01 method="post">
        <input type="hidden" name="domCd" value="0"> <input type="hidden" name="cLine" value="<%=cLine%>">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="../../portal/images/logo_popupbg.png">
                                <table height="28" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01178")%><%--년도별 예상수량 지정--%></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="710" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10">&nbsp;</td>
                            <td valign="top" align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0" name="addCarTypeBtn" id="addCarTypeBtn">
                                                <tr>
                                                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif" onClick=""><a
                                                        href="javascript:addCarType();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></td>
                                                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td width="5">&nbsp;</td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                        onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td width="70" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969")%><%--구분--%>
                                            <span class="red">*</span></td>
                                        <td width="160" class="tdwhiteL"><input name="domGb1" type="radio" class="Checkbox" value="1"
                                            onClick="javascript:carCompanyLs('1000');javascript:clearCartype();"><%=messageService.getString("e3ps.message.ket_message", "00983")%><%--국내--%>
                                            <input name="domGb2" type="radio" class="Checkbox" value="2"
                                            onClick="javascript:carCompanyLs('2000');javascript:clearCartype();"><%=messageService.getString("e3ps.message.ket_message", "00985")%><%--국외--%>
                                        </td>
                                        <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%></td>
                                        <td width="150" class="tdwhiteL"><select name="carCompany" id="carCompany" class="fm_jmp" style="width: 130"
                                            onchange="javascript:changeCarCompany(this);">
                                                <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                        </select></td>
                                        <td width="80" class="tdblueM">
                                            <%
                                                if (divCode.equals("CA")) {
                                            %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%>
                                            <%
                                                } else {
                                            %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%> <%
     }
 %>
                                        </td>
                                        <td width="260" class="tdwhiteL0">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td><select name="carType" id="carType" class="fm_jmp" style="width: 130"
                                                                    onchange="javascript:changeCarType();">
                                                                        <option value="0"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></option>
                                                                </select></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="15"></td>
                                                    <td><b><%=messageService.getString("e3ps.message.ket_message", "02720")%><%--직접입력--%></b></td>
                                                    <td width="5"></td>
                                                    <td><input type="checkbox" name="chkH" id="chkH"
                                                        onClick="javascript:hAddCarType('<%=divCode%>')"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space10"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td><b>※ <%=messageService.getString("e3ps.message.ket_message", "01820",
            divCode.equals("CA") ? messageService.getString("e3ps.message.ket_message", "02745") /*차종*/: messageService.getString("e3ps.message.ket_message", "02466") /*적용기기*/)%></b></td>
                                        <td align="right">
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:sumCal();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03152")%><%--합계 계산--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a id="confBtn"
                                                                    href="javascript:doSelect();" disabled onClick="return false" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5">&nbsp;</td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0" name="subCarTypeBtn" id="subCarTypeBtn">
                                                            <tr>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif" onClick=""><a
                                                                    href="javascript:subCarType();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690")%><%--삭제--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="700">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0" width="700">
                                                <tr>
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table>
                                            <DIV name='carTypePlanDiv' id='carTypePlanDiv' style='overflow: auto; width: 700; height: 290;'>
                                                <table name='carTypePlanTable' id='carTypePlanTable' border="0" cellpadding="0"
                                                    cellspacing="0" width="700">
                                                    <tr>
                                                        <td width="35" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%></td>
                                                        <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00837")%><%--고객사--%></td>
                                                        <td width="120" class="tdblueM">
                                                            <%
                                                                if (divCode.equals("CA")) {
                                                            %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%>
                                                            <%
                                                                } else {
                                                            %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>
                                                            <%
                                                                }
                                                            %>
                                                        </td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 1)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 2)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 3)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 4)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 5)%><%--{0}년--%></td>
                                                        <td width="50" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00015", 6)%><%--{0}년--%>~</td>
                                                        <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03151")%><%--합계--%></td>
                                                        <td width="35" class="tdblueM">U/S<span class="red">*</span></td>
                                                        <td width="70" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02166")%><%--옵션률--%>
                                                            <span class="red">*</span></td>
                                                        <td width="0"></td>
                                                    </tr>
                                                    <%
                                                        if (carTypeItemCnt > 0) {
                                                            carTypeIt = null;

                                                            for (int i = 0; i < carTypeItemCnt; i++) {
                                                                carTypeIt = new Vector();
                                                                carTypeIt = (Vector) carTypeItems.get(i);

                                                                String cName = DMSUtil.serCustomerNm((String) carTypeIt.get(11));

                                                                if (divCode.equals("ER")) {
                                                                isReadonly = false;
                                                                } else if (cName.equals("")) {
                                                                isReadonly = false;
                                                                }
                                                    %>
                                                    <tr>
                                                        <td width="45" class="tdwhiteM"><input type='checkbox' name='iCarTypePlanChk'
                                                            id='iCarTypePlanChk' style='width: 50'></td>
                                                        <td width="80" class="tdwhiteM"><%=cName%>&nbsp;</td>
                                                        <td width="120px" class="tdwhiteM"><input type='text' <%if (isReadonly) {%>
                                                            readonly class='txt_fieldRO' <%} else {%> class='txt_field' <%}%>
                                                            name='carTypeCode' style='width: 80px' value='<%=(String) carTypeIt.get(11)%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <%
                                                            if (isReadonly) {
                                                        %>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if (isReadonly) {%> readonly
                                                            class='txt_fieldRRO' <%} else {%> class='txt_fieldR' <%}%> name='y1'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String) carTypeIt.get(1), "", "###,###")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y2'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(2), "", "###,###")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y3'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(3), "", "###,###")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y4'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(4), "", "###,###")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y5'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(5), "", "###,###")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y6'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(6), "", "###,###")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <% } else {  %>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y1'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(1), "######", "######")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y2'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(2), "######", "######")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y3'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(3), "######", "######")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y4'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(4), "######", "######")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y5'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(5), "######", "######")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="50" class="tdwhiteR"><input type='text' <%if(isReadonly){%> readonly
                                                            class='txt_fieldRRO' <%}else{%> class='txt_fieldR' <%}%> name='y6'
                                                            style='width: 45'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(6), "######", "######")%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <% } %>
                                                        <td width="60" class="tdwhiteR"><input type='text' readonly name='sum'
                                                            class='txt_fieldRRO' style='width: 55'
                                                            value='<%=StringUtil.getDouble((String)carTypeIt.get(7), "", "###,###")%>'></td>
                                                        <td width="35" class="tdwhiteR"><input type='text' name='usage'
                                                            class='txt_fieldR' style='width: 30' value='<%=(String)carTypeIt.get(8)%>'
                                                            onChange='javascript:usgChgVal();'></td>
                                                        <td width="60" class="tdwhiteM"><input type='text' name='optRate'
                                                            class='txt_fieldR' style='width: 40' value='<%=(String)carTypeIt.get(9)%>'
                                                            onChange='javascript:usgChgVal();'>%</td>
                                                        <td width="0"><input type='hidden' name='carTypeOid'
                                                            value='<%=(String)carTypeIt.get(10)%>'></td>
                                                    </tr>
                                                    <%
    }
  }
        %>
                                                    <tr>
                                                        <td width="35" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03151") %><%--합계--%></td>
                                                        <td width="80" class="tdgrayM" readonly>&nbsp;</td>
                                                        <td width="120" class="tdgrayL" readonly>&nbsp;</td>
                                                        <td width="50" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="50" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="50" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="50" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="50" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="50" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="60" class="tdgrayR" readonly>0&nbsp;</td>
                                                        <td width="35" class="tdgrayR" readonly>&nbsp;</td>
                                                        <td width="70" class="tdgrayM0" readonly>&nbsp;</td>
                                                        <td width="0"></td>
                                                    </tr>
                                                </table>
                                            </DIV>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>