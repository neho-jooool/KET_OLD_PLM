<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

//<script type="text/javascript"> // eclipse 속이기...^^;

  // ==  [Start] 부품검색 팝업  == //
  function selectPartNo(targetId, mode){ // mode: m | s

	_callBackFuncTargetId = targetId;
    if( typeof mode != 'undefined' && ('m' == mode || 'M' == mode )){
        showProcessing();
        SearchUtil.selectPart("callBackFuncPartPopup");
    }else{
        showProcessing();
        SearchUtil.selectOnePart("callBackFuncPartPopup");
    }
  }
  
  var _callBackFuncTargetId;
  function callBackFuncPartPopup(selectedPartAry){
      if ( typeof selectedPartAry != "undefined" && selectedPartAry.length > 0) {
          var isAppend = "Y";
          acceptPartNo(_callBackFuncTargetId, selectedPartAry, isAppend);
      }
  }
  
  function selectMultiPartNo(targetId){
      selectPartNo(targetId, 'm');
  }
  function selectSinglePartNo(targetId){
      selectPartNo(targetId, 's');
  }
  function acceptPartNo(targetId, arrObj, isAppend) {
    var partData = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtpart oid       // [1] - part number    // [2] - part name
        // [3] - part version     // [4] - part type      // [5] - die number
        // [6] - die name         // [7] - die cnt        // [8] - wtpartmaster oid

        var infoArr = arrObj[i];
        partData[i] = infoArr[1];
    }

    var tmpNo = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
      // ID 중복 제거
      tmpNo = $.merge($("#"+targetId).val().split(", "), partData);
      tmpNo = $.unique(tmpNo.sort());
    }
    else {
      tmpNo = partData.sort();
    }

    $("#"+targetId).val(tmpNo.join(", "));
  }
  function clearPartNo(targetId) {
    $("#" + targetId).val("");
  }
  //==  [End] 부품검색 팝업  == //


  // [START] 부품번호 입력 팝업
  function inputPartNumberPopup() {
    var url = "/plm/jsp/bom/InputPartNumberPopup.jsp";
    var partNumbers = window.showModalDialog(url, window, "help=no; resizable=yes; status=no; scroll=yes; dialogWidth=520px; dialogHeight:550px; center:yes");
    if (partNumbers != undefined && partNumbers.length > 0) {
      $("#inputPartNos").val(partNumbers);
      initPartNumberTextbox();
    }
  }

  function clearPartNumber() {
    $("#inputPartNos").val("");
    $("#number").val("");
    initPartNumberTextbox();
  }

  function initPartNumberTextbox() {
    var numberObj = document.getElementById("number");
    var numbertempObj = document.getElementById("numbertemp");

    var inited = false;
    var inputPartNos = document.getElementById("inputPartNos").value;

    if (inputPartNos != undefined && inputPartNos != "") {
      var inputPartNosAry = inputPartNos.split(",");
      var inputPartNosAryLen = inputPartNosAry.length;
      if (inputPartNosAry != null && inputPartNosAryLen > 0) {
        var val = inputPartNosAry[0]
        if (inputPartNosAryLen > 1) {
          val = val + " 외 " + (inputPartNosAryLen-1) + "건";
        }
        numbertempObj.value = val;
        numberObj.value = "";
        numberObj.style.display = "none";
        numbertempObj.style.display = "inline";
        inited = true;
      }
    }

    if (inited == false) {
      numbertempObj.style.display = "none";
      numberObj.style.display = "inline";
    }
  }
  // [END] 부품번호 입력 팝업


//</script>
