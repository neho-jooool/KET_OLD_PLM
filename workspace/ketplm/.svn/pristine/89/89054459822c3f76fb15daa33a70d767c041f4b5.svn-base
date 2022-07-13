/**
 * @(#)  createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//초기화면세팅
function onLoad() {
  var form = document.forms[0];
  var stat = form.chkChangeType[11].checked;
  var objName = "otherChangeDesc";
  setEtcValueStatus(objName, stat);

  disable( form.requestType.value );
  
  
  var strHTMLCode = document.forms[0].webEditor.value;
  // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
  // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
  // 세번째 매개변수 => 이노디터 번호
  fnSetEditorHTMLCode(strHTMLCode, false, 0);

  var strHTMLCode1 = document.forms[0].webEditor1.value;
  // 첫번째 매개변수 => 이노디터에 넘겨줄 HTMLCode 내용
  // 두번째 매개변수 => true : < & 특수문자를 editor 내부에서 처리,  false : 기본 설정값
  // 세번째 매개변수 => 이노디터 번호
  fnSetEditorHTMLCode(strHTMLCode1, false, 1);

}

//기타입력항목 입력상태 처리
function setEtcValueStatus(objName, stat) {
  var obj = eval("document.forms[0]." + objName);
  if(stat) {
    obj.disabled = false;
  } else {
    obj.disabled = true;
  }
}

//프로젝트상세조회 팝업
function viewProjectPopup(projectNo) {
  alert("프로젝트상세조회:" + projectNo);
}

//프로젝트상세조회 팝업
/*
function popupProject() {
  var form = document.forms[0];
  var devflag;
  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    devflag = form.rdoDevYn[0].value;
  } else {
    devflag = form.rdoDevYn[1].value;
  }
//  var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&dev_flag="+devflag+"&status=P&type=P";
  var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=M";
  openWindow(url,"","725","500","status=0,scrollbars=no,resizable=no");
}
*/
function popupProject()
{
  showProcessing();	
  var url="/plm/jsp/project/SearchPjtPop.jsp?status=P&type=M&modal=Y";
  //openWindow(url,"","725","500","status=0,scrollbars=no,resizable=no");
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=725px; dialogHeight:500px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
      hideProcessing();
  	return;
  }
  setProject(attache);
}

function setProject(objArr) {
  if(objArr.length == 0) {
    return;
  }

  var trArr;
  var str = "";
  var form = document.forms[0];
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    form.projectOid.value = trArr[0];
    form.projectNo.value = trArr[1];
    form.projectName.value= trArr[2];
  }
  if(form.rdoDevYn[0].checked) {
    searchProjectVendor();
  }
  
  hideProcessing();
}


//개발단계인 경우 관련 프로젝트가 선택되면 생산처를 조회한다.
function searchProjectVendor() {
  var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
  var form = document.forms[0];
  form.cmd.value = "searchProjectVendor";
  form.target = "download";
  form.action = url;
  form.method = "post";
  disabledAllBtn();
  showProcessing();
  form.submit();
}

//프로젝트상세조회 팝업
function clearProject() {
  var form = document.forms[0];
  form.projectOid.value = "";
  form.projectNo.value = "";
  form.projectName.value = "";
}


//처리중 화면 전환
/*
function hideProcessing() {
  var div1 = document.getElementById('div1');
  var div2 = document.getElementById('div2');
  div1.style.display = "none";
  div2.style.display = "none";
}
*/

//자료 저장
function doSave(){
  var url = "/plm/servlet/e3ps/MoldEcrServlet";
  var form = document.forms[0];

  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    form.devYn.value = form.rdoDevYn[0].value;
  } else {
    form.devYn.value = form.rdoDevYn[1].value;
  }

  //설계변경 유형
  var cnt = 0;
  form.changeType.value = "";
  for(var i=0; i<form.chkChangeType.length; i++) {
    if(form.chkChangeType[i].checked) {
      cnt++;
      if(cnt < 2) {
        form.changeType.value = form.chkChangeType[i].value;
      } else {
        form.changeType.value += "|" + form.chkChangeType[i].value;
      }
    }
  }
  if(form.changeType.value.indexOf("12") < 0) {
    form.otherChangeDesc.value = "";
  }

  if(checkValidate()){
    if(form.oid.value == "") {
      form.cmd.value = "create";
    } else {
      form.cmd.value = "update";
    }

	  
    // 이노디터 처리
    $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
    $('[name=webEditorText]').val(fnGetEditorText(0));

    // 이노디터 처리
    $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
    $('[name=webEditorText1]').val(fnGetEditorText(1));

    
    form.target = "download";
    form.action = url;
    form.method = "post";
    form.encoding = 'multipart/form-data';

    disabledAllBtn();
    showProcessing();
    form.submit();
  }
}

//연계부품 검색 팝업 호출
function popupRelPart() {
  var url="/plm/servlet/e3ps/PartServlet?cmd=openSearchPopup&mode=multi&pType=D";
  openWindow(url,"","725","515","status=1,scrollbars=no,resizable=no");
}

function isDuplicatePart( partOid )
{
  var isDuplicatePart = false;

  var partList = document.getElementsByName("relPartOid");
  for( var cnt=0; cnt < partList.length; cnt++ )
  {
    if( partOid == partList[cnt].value )
    {
      isDuplicatePart = true;
    }
  }

  return isDuplicatePart;
}

//연계부품 라인추가(projectIssueCreate.jsp 참조)
function selectedPart(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relPartTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    if( !isDuplicatePart( trArr[0]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.height="27";

      //선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "20";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<div style='display:none'><input type='text' name='changeReqComment' value=''></div>"
    	  
	                  + "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><b>-</b></a>"
                      + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
                      ;

      //Die No
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "120";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='relPartLinkOid' value=''>";
      newTd.innerHTML = str;

      //Part Name
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      str = "";
      str +="<font title=\""+trArr[2]+"\">";
      str += "<div class='ellipsis' style='width:217;'><nobr>";
      str += trArr[2] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //Rev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "50";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = trArr[3] + "&nbsp;";

      /*
      //요청내용
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width = "200";
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<input type='text' name='changeReqComment' class='txt_field' style='width:190'>";
      */
    }
  }
}

//(공통)목록전체 선택/해제
function checkAll(formName, checkboxName, allCheckName) {
  var formNameStr = "document." + formName + ".";
  var objChecks = eval(formNameStr + checkboxName);
  var objAllChecks = eval(formNameStr + allCheckName);
  if(objChecks) {
    var chkLen = objChecks.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        objChecks[i].checked = objAllChecks.checked;
      }
    }else{
      objChecks.checked = objAllChecks.checked;
    }
  }
}


//생산처 검색 팝업
function popupVendor(){
  var form = document.forms[0];
  if(form.vendorFlag.value == "i") {//사내
    selectDepartment();
  } else {//외주
    selectPartner();
  }
}

//생산처 초기화
function clearVendor(){
  var form = document.forms[0];
  form.prodVendor.value = '';
  form.prodVendorName.value = '';
}

//생산처 초기화
function clearReqDate(){
  var form = document.forms[0];
  form.completeReqDate.value = '';
}

//협력사검색 팝업 Open
function selectPartner() {
  var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
  openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
}

//협력사 검색결과 셋팅
function linkPartner(arr){
  if(arr.length == 0) {
    alert("협력사를 선택하시기 바랍니다.");
    return;
  }
  var form = document.forms[0];
  form.prodVendor.value = arr[0][0];
  form.prodVendorName.value = arr[0][1];
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
    alert("부서를 선택하시기 바랍니다.");
    return;
  }
  var form = document.forms[0];
  form.prodVendorName.value = arr[0][2];
  form.prodVendor.value = arr[0][1];
}

//부품 상세조회 팝업
function viewRelPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
}

function viewRelIssue(oid){
  var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
  openWindow(url,"","750","400","scrollbars=no,resizable=no,top=200,left=250");
}
