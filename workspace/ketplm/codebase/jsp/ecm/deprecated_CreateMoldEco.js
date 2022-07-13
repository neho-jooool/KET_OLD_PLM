 /**
 * @(#)  createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//초기화면세팅
function onLoad() {
  var form = document.forms[0];

  var stat = form.chkChangeReason[6].checked;
  var objName = "otherReasonDesc";
  setEtcValueStatus(objName, stat);

  stat = form.chkIncreaseProdType[11].checked;
  objName = "otherIncreaseProdType";
  setEtcValueStatus(objName, stat);

  clickTabBtn(1);
  
  
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
  if ( obj != undefined ) {
      if(stat) {
        obj.disabled = false;
      } else {
        obj.disabled = true;
      }
    }
}

//프로젝트상세조회 팝업
function viewProjectPopup(projectNo) {
  alert("프로젝트상세조회:" + projectNo);
}

//프로젝트검색 팝업
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
  openWindow(url,"","1024","768","status=0,scrollbars=no,resizable=no");
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
  //if(form.rdoDevYn[0].checked) {
    searchProjectVendor();
  //}
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
function clickTabBtn(tabId) {
  var tabBasic = document.getElementById("tabBasic");
  var tabActivity = document.getElementById("tabActivity");
  if(tabId == 1) {
    tabBasic.style.visibility = "visible";
    tabActivity.style.visibility = "hidden";

    var infoShow = document.getElementById("infoShow");
    infoShow.style.display = "block";
    var infoHide = document.getElementById("infoHide");
    infoHide.style.display = "none";
  } else {
    tabBasic.style.visibility = "hidden";
    tabActivity.style.visibility = "visible";

    var infoShow = document.getElementById("infoShow");
    infoShow.style.display = "none";
    var infoHide = document.getElementById("infoHide");
    infoHide.style.display = "block";
  }
}

//처리중 화면 전환
function hideProcessing() {
  var div1 = document.getElementById('div1');
  var div2 = document.getElementById('div2');
  div1.style.display = "none";
  div2.style.display = "none";
}

//자료 저장
function doSave( flag ){
  var url = "/plm/servlet/e3ps/MoldEcoServlet";
  var form = document.forms[0];

  //개발/양산구분
  if(form.rdoDevYn[0].checked) {
    form.devYn.value = form.rdoDevYn[0].value;
  } else {
    form.devYn.value = form.rdoDevYn[1].value;
  }

  //설계변경 사유
  var cnt = 0;
  form.changeReason.value = "";
  for(var i=0; i<form.chkChangeReason.length; i++) {
    if(form.chkChangeReason[i].checked) {
      cnt++;
      if(cnt < 2) {
        form.changeReason.value = form.chkChangeReason[i].value;
      } else {
        form.changeReason.value += "|" + form.chkChangeReason[i].value;
      }
    }
  }
  if(form.changeReason.value.indexOf("7") < 0) {
    form.otherReasonDesc.value = "";
  }

  //생산성 향상 유형
  cnt = 0;
  form.increaseProdType.value = "";
  for(var i=0; i<form.chkIncreaseProdType.length; i++) {
    if(form.chkIncreaseProdType[i].checked) {
      cnt++;
      if(cnt < 2) {
        form.increaseProdType.value = form.chkIncreaseProdType[i].value;
      } else {
        form.increaseProdType.value += "|" + form.chkIncreaseProdType[i].value;
      }

    }
  }
  if(form.increaseProdType.value.indexOf("12") < 0) {
    form.otherIncreaseProdType.value = "";
  }

  if(checkValidate(flag)){

  	
      // 이노디터 처리
      $('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
      $('[name=webEditorText]').val(fnGetEditorText(0));

      // 이노디터 처리
      $('[name=webEditor1]').val(fnGetEditorHTMLCode(false, 1));
      $('[name=webEditorText1]').val(fnGetEditorText(1));

      
    if(form.oid.value == "") {
      form.cmd.value = "create";
    } else {
      form.cmd.value = "update";
    }

    if( flag )
    {
      form.isCompleteModify.value ="Y";
      form.cmd.value = "completeModify";
    }
    else
    {
      form.isCompleteModify.value ="N";
    }

    form.target = "download";
    form.action = url;
    form.method = "post";
    form.encoding = 'multipart/form-data';

    disabledAllBtn();
    showProcessing();
    form.submit();
  }
}

//연계ECR 검색 팝업 호출
function popupRelEcr() {
	var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
    url += "&obj=prodMoldCls^2&obj=mode^multi";
	//var url = "/plm/jsp/ecm/SearchEcrPopupForm.jsp?prodMoldCls=2&mode=multi";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=1000px; dialogHeight:670px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  insertRelEcrLine(attache);
}

function isDuplicateEcr2( oid )
{
  var isDuplicate = false;
  var oidList = document.getElementsByName("relEcrOid");

  for( var i=0; i < oidList.length; i++ )
  {
    if( oid == oidList[i].value )
    {
      isDuplicate = true;
    }
  }

  return isDuplicate;

}

/* deprecated */
function isDuplicateEcr( ecr_id ) {
//부품추가시 선택된 부품정보를 중복체크한다.
  var tBody = document.getElementById("relEcrTable");
  var rowsLen = tBody.rows.length;
  if(rowsLen > 0)
  {
    var primarEcr = document.getElementsByName("relEcrOid");
    var ecrLength = primarEcr.length;
    if( ecrLength > 0 )
    {
      for(var i = 0; i < ecrLength; i++)
      {
        if( primarEcr[i].value == ecr_id )
        {
          return true;
        }
      }
    }
    else
    {
      if( primarEcr.value == ecr_id )
      {
        return true;
      }
    }
  }
  return false;
}

//연계ECR 라인추가
function insertRelEcrLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relEcrTable");
    var str = "";
  var trArr;
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    if( !isDuplicateEcr2(trArr[0]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.width='20';
      //newTd.innerHTML = "<input name='chkSelectRelEcr' type='checkbox' value=''>";
      newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><b>-</b></a>"
			          + "<div style=\"display:none;\"><input name='chkSelectRelEcr' type='checkbox' value=''></div>"
			          ; 
			      
      //ECR번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.width='100';
      newTd.innerHTML = "<a href=\"javascript:viewRelEcr('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      newTd.innerHTML += "<input type='hidden' name='relEcrOid' value='" + trArr[0] + "'>";
      newTd.innerHTML += "<input type='hidden' name='relEcrLinkOid' value=''>";

      //ECR 제목
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.width='162';
      newTd.className = "tdwhiteL";
      str ="";
      str +="<font title=\""+trArr[5]+"\">";
      str += "<div class='ellipsis' style='width:162;'><nobr>";
      str += trArr[5] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //작성부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.width='100';
      newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";

      //작성자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.width='90';
      newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

      //승인일자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.width='100';
      newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";
    }
  }
}


//제품ECO 상세조회 팝업
function viewRelProdEco(oid){
  var url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","850","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//금형ECO 상세조회 팝업
function viewRelMoldEco(oid){
  var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","800","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//연계ECO 검색 팝업 호출
function popupRelProdEco() {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
  url += "&obj=prodMoldCls^1&obj=mode^multi";
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=740px; dialogHeight:550px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  insertRelProdEcoLine(attache);
}

function isDuplicateEco( oid )
{
  var isDuplicate = false;
  var oidList = document.getElementsByName("relProdEcoOid");

  for( var i=0; i < oidList.length; i++ )
  {
    if( oid == oidList[i].value )
    {
      isDuplicate = true;
    }
  }

  return isDuplicate;

}

//연계ECO 라인추가
function insertRelProdEcoLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("RelProdEcoTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( !isDuplicateEco(trArr[0]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      //newTd.innerHTML = "<input name='chkSelectRelProdEco' type='checkbox' value=''>";
      newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><b>-</b></a>"
			          + "<div style=\"display:none;\"><input name='chkSelectRelProdEco' type='checkbox' value=''></div>"
			          ; 
			      
      //ECO번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<a href=\"javascript:viewRelProdEco('" + trArr[0] + "');\">" + trArr[1] + "</a>";
      newTd.innerHTML += "<input type='hidden' name='relProdEcoOid' value='" + trArr[0] + "'>";
      newTd.innerHTML += "<input type='hidden' name='relProdEcoLinkOid' value=''>";

      //ECO제목
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      str ="";
      str +="<font title=\""+trArr[6]+"\">";
      str += "<div class='ellipsis' style='width:126;'><nobr>";
      str += trArr[6] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //작성부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[2] + "&nbsp;";

      //작성자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[3] + "&nbsp;";

      //승인일자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[4] + "&nbsp;";

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "&nbsp;" + trArr[5] + "&nbsp;";
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

//문서 초기화
function clearRelDoc(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    form.relEcaDocOid[pos].value = '';
    form.relEcaDocNo[pos].value = '';
    form.relEcaDocPreRev[pos].value = '';
  } else {
    form.relEcaDocOid.value = '';
    form.relEcaDocNo.value = '';
    form.relEcaDocPreRev.value = '';
  }
}


//표준품 라인 추가
function addRelDoc() {
  var arr = new Array();
  var idx = 0;
  rArr = new Array();
  rArr[0] = "";//oid
  rArr[1] = "";//code
  rArr[2] = "";//version
  arr[idx++] = rArr;
  insertRelDocLine(arr);
}

//표준품 라인추가
function insertRelDocLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relDocTable");

  var trArr;
  var str = "";
  //if( targetTable.rows.length < 1)
  //{
    for(var i = 0; i < objArr.length; i++) {
      trArr = objArr[i];
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.onmouseover=function(){relDocTable.clickedRowIndex=this.rowIndex};
      var currRow = targetTable.rows.length - 1;

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "40";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='hidden' name='relEcaDocActivityType' value='3'>";
      str += "<input type='hidden' name='relEcaDocLinkOid' value=''>";
      str += "<input type='hidden' name='moldEcaDocOid' value=''>";
      str += "<input type='checkbox' name='chkSelectRelDoc'>";
      //newTd.innerHTML = str;
      newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><b>-</b></a>"
			          + "<div style=\"display:none;\">"+ str +"</div>"
			          ; 

      //구분
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "50";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "표준품";

      //문서번호
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "140";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='hidden' name='relEcaDocOid' value='" + trArr[0] + "'>";
      str += "<input type='text' name='relEcaDocNo' class='txt_fieldCRO' style='width:90' readonly value='" + trArr[1] + "'>";
      str += "&nbsp;<a href=\"javascript:popupRelDoc('relDocTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
      str += "&nbsp;<a href=\"javascript:clearRelDoc('relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;

      //Rev
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "50";
      newTd.className = "tdwhiteM";
      newTd.innerHTML = "<input type='text' name='relEcaDocPreRev' class='txt_fieldCRO' style='width:40' readonly value='" + trArr[2] + "'>";

      //담당자
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "130";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='hidden' name='relEcaDocWorkerOid'>";
      str += "<input type='text' name='relEcaDocWorkerName' class='txt_fieldRO'  style='width:80' readonly>&nbsp;";
      str += "<a href=\"javascript:popupUser('relDocTable');\"><img src='/plm/portal/images/icon_user.gif' border='0'></a>&nbsp;";
      str += "<a href=\"javascript:clearUser('relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;

      //변경예정일
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "130";
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='text' name='relEcaDocPlanDate' class='txt_field' style='width:80' readonly>";
      str += "&nbsp;<a href=\"#\" onclick=\"javascript:popupCal('relEcaDocPlanDate','relDocTable');\"><img src='/plm/portal/images/icon_6.png' border='0'></a>";
      str += "&nbsp;<a href=\"javascript:clearCal('relEcaDocPlanDate','relDocTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;

      //변경내용
      newTd = newTr.insertCell(newTr.cells.length);
  //    newTd.width = "";
      newTd.className = "tdwhiteL0";
      str = "";
      str += "<input type='text' name='relEcaDocActivityComment' class='txt_field'  style='width:160'>";
      newTd.innerHTML = str;
    }
  //}
  //else
  //{
  //  alert("표준품리스트는 1개만 등록할 수 있습니다.")
  //}
//  document.recalc();
}

//표준품 담당자검색 팝업
function popupUser(tableId){
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  var pos = eval(tableId).clickedRowIndex;
  setRelDocUser(attacheMembers, pos);
}

//표준품 담당자 검색 팝업 리턴 포맷
function setRelDocUser(objArr, pos) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var targetTable = document.getElementById("relDocTable");
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    form.relEcaDocWorkerOid[pos].value = trArr[0];
    form.relEcaDocWorkerName[pos].value = trArr[4];
  } else {
    form.relEcaDocWorkerOid.value = trArr[0];
    form.relEcaDocWorkerName.value = trArr[4];
  }
}

//담당자 초기화
function clearUser(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    form.relEcaDocWorkerOid[pos].value = '';
    form.relEcaDocWorkerName[pos].value = '';
  } else {
    form.relEcaDocWorkerOid.value = '';
    form.relEcaDocWorkerName.value = '';
  }
}

//변경예정일 팝업
function popupCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearCal(objName, tableId){
  var pos = eval(tableId).clickedRowIndex;
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}


//연계도면/BOM 검색 팝업 호출
function popupRelEpm() {
  var form = document.forms[0];
  /*
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;
  var partNumber = "";
  if(tableRows > 0){
    if(tableRows > 1){
      partNumber = form.relPartNumber[0].value;
    } else {
      partNumber = form.relPartNumber.value;
    }
  }
  */
  
  var partNumber = "";
  var oidList = document.getElementsByName("relPartNumber");
  
  for( var i=0; i < oidList.length ; i++)
  {
    partNumber = oidList[i].value;
    break;
  }
  
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchActivityPopupForm.jsp";
  url += "&obj=prodMoldCls^2&obj=mode^multi&obj=partType^D";
  url += "&obj=partNumber^" + partNumber;
  
//  openWindow(url,"","770","600","status=1,scrollbars=no,resizable=no");
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=770px; dialogHeight:600px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
//  var attache = getSampleRelEpmData();
  insertRelEpmLine(attache);
}

function isDuplicateLine( oid )
{
  var isDuplicate = false;

  var oidList = document.getElementsByName("relEcaEpmOid");

  for( var i=0; i < oidList.length ; i++)
  {
    if( oid == oidList[i].value )
    {
      isDuplicate = true;
    }
  }

  return isDuplicate;
}

//도면 라인추가
function insertRelEpmLine(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var targetTable = document.getElementById("relEpmTable");

  var trArr;
  var str = "";
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];

    if( !isDuplicateLine(trArr[1]) )
    {
      var tableRows = targetTable.rows.length;
      var newTr = targetTable.insertRow(tableRows);
      newTr.onmouseover=function(){relEpmTable.clickedRowIndex=this.rowIndex};
      var currRow = targetTable.rows.length - 2;

      //전체선택 checkbox
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='hidden' name='relEcaEpmActivityType' value='" + trArr[0] + "'>";
      str += "<input type='hidden' name='moldEcaEpmOid' value=''>";
      str += "<input type='hidden' name='relEcaEpmLinkOid' value=''>";
      str += "<input type='hidden' name='relEcaEpmOid' value='" + trArr[1] + "'>";
      str += "<input type='hidden' name='beforePartOid' value='" + trArr[1] + "'>";
      str += "<input type='hidden' name='relEcaEpmNo' value='" + trArr[2] + "'>";
      str += "<input type='hidden' name='relEcaEpmName' value='" + trArr[3] + "'>";
      str += "<input type='hidden' name='relEcaEpmPreRev' value='" + trArr[4] + "'>";
      str += "<input type='hidden' name='relEcaEpmWorkerOid' value='" + trArr[5] + "'>";
      str += "<input type='hidden' name='relEcaEpmChangeYn' value='N'>";
      str += "<input type='hidden' name='relEcaEpmDocType' value='" + trArr[7] + "'>";
      str += "<input type='checkbox' name='chkSelectRelEpm'>";
      //newTd.innerHTML = str;
      newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><b>-</b></a>"
                      + "<div style=\"display:none;\">"+ str +"</div>"
                      ;
      
      //구분
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      if(trArr[0] == "1") {
        newTd.innerHTML = "도면";
      } else {
        newTd.innerHTML = "BOM";
      }

      //금형부품번호
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<font title='"+trArr[2]+"'><div class='ellipsis' style='width:100;'><nobr>";
      str += trArr[2] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //금형부품명
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      str = "";
      str += "<font title=\""+trArr[3]+"\"><div class='ellipsis' style='width:330;'><nobr>";
      str += trArr[3] +"</nobr></div></font>";
      newTd.innerHTML = str;

      //Rev
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = trArr[4];

      //담당자
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = trArr[6];

      //변경예정일
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      str += "<input type='text' name='relEcaEpmPlanDate' id='relEcaEpmPlanDate' class='txt_field' style='width:65;text-align:center;' readonly>";
      str += "&nbsp;<a href=\"#\" onclick=\"javascript:popupEpmCal('relEcaEpmPlanDate','relEpmTable');\" onfocus='this.blur();'><img src='/plm/portal/images/icon_6.png' border='0'></a>";
      str += "&nbsp;<a href=\"javascript:clearEpmCal('relEcaEpmPlanDate','relEpmTable');\" onfocus='this.blur();'><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      newTd.innerHTML = str;


      //연계일정
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      str = "";
      if(trArr[0] == "1") {
        str += "<input type='hidden' name='oldMoldChangePlanOid' value=''>";
        str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
        str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
        str += "<input type='hidden' name='dieNo' value=''>";
        str += "<input type='text' name='scheduleId' class='txt_field' style='width:52' readonly>";
        str += "&nbsp;<a href=\"javascript:popupRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_5.png' border='0'></a>";
        str += "&nbsp;<a href=\"javascript:clearRelPlan('relEpmTable');\"><img src='/plm/portal/images/icon_delete.gif' border='0'></a>";
      } else {
        str += "-<input type='hidden' name='oldMoldChangePlanOid' value=''>";
        str += "<input type='hidden' name='newMoldChangePlanOid' value=''>";
        str += "<input type='hidden' name='moldEcoPlanLinkOid' value=''>";
        str += "<input type='hidden' name='dieNo' value=''>";
        str += "<input type='hidden' name='scheduleId' value=''>";
      }
      newTd.innerHTML = str;

      //변경내용
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      str = "";
      str += "<input type='text' name='relEcaEpmActivityComment' class='txt_field'  style='width:130'>";
      newTd.innerHTML = str;
    }
  }
}


//연계일정검색 팝업
function popupRelPlan(tableId){
   var oid = "e3ps.ecm.entity.KETMoldChangeOrder:";
   if( document.forms[0].oid.value != '' )
   {
      oid = document.forms[0].oid.value;
   }
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchMoldPlanPopupForm.jsp?oid="+oid;
  arr = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=770px; dialogHeight:500px; center:yes");
  if(typeof arr == "undefined" || arr == null) {
    return;
  }
  var pos = eval(tableId).clickedRowIndex - 1;
  setRelPlan(arr, pos);
}

//연계일정 검색 팝업 리턴 포맷
function setRelPlan(objArr, pos) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];

  var targetTable = document.getElementById("relEpmTable");
  var tableRows = targetTable.rows.length;
  if(tableRows > 2){//테이블 헤더가 포함되어 있기 때문에.
    for(var cnt = 0; cnt < tableRows-1; cnt++){
      form.newMoldChangePlanOid[pos].value = trArr[0];
      form.scheduleId[cnt].value = trArr[2];
      form.dieNo[pos].value = trArr[1];
      //form.relEcaEpmPlanDate[pos].value = trArr[4];
      form.relEcaEpmPlanDate[cnt].value = trArr[4];
    }
  } else {
    form.newMoldChangePlanOid.value = trArr[0];
    form.scheduleId.value = trArr[2];
    form.dieNo.value = trArr[1];
    form.relEcaEpmPlanDate.value = trArr[4];
  }
}

//연계일정 초기화
function clearRelPlan(tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex - 1;
  if(tableRows > 2){
    form.newMoldChangePlanOid[pos].value = '';
    form.scheduleId[pos].value = '';
    form.dieNo[pos].value = '';
  } else {
    form.newMoldChangePlanOid.value = '';
    form.scheduleId.value = '';
    form.dieNo.value = '';
  }
}

//변경예정일 팝업
function popupEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  showCal(objName);
}

//변경예정일 초기화
function clearEpmCal(objName, tableId){
  var form = document.forms[0];
  var targetTable = document.getElementById(tableId);
  var tableRows = targetTable.rows.length;
  var pos = eval(tableId).clickedRowIndex;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  if(tableRows > 1){
    objName = objName + "[" + pos + "]";
  }
  eval("document.forms[0]." + objName).value = '';
}

//ECO담당자검색 팝업
function popupEcoUser(){
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  setEcoUser(attacheMembers);
}

//표준품 담당자 검색 팝업 리턴 포맷
function setEcoUser(objArr) {
  if(objArr.length == 0) {
    return;
  }
  var trArr = objArr[0];
  var form = document.forms[0];
  form.ecoWorkerId.value = trArr[0];
  form.ecoWorkerName.value = trArr[4];
}

//ECO담당자 초기화
function clearEcoUser(){
  var form = document.forms[0];
  form.ecoWorkerId.value = '';
  form.ecoWorkerName.value = '';
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
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function checkBudget()
{
  var form = document.forms[0];
  var pos = relPartTable.clickedRowIndex;
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  pos -= 1;
  /*
  alert(tableRows);
  alert(pos);
  */
  
  var division  = "";
  var dev_yn = "";
  var dieno = "";
  var expectCost = "";

  if( form.divisionFlag.value == 'C' )
  {
    division = "1";
  }
  else
  {
    division = "2";
  }

  var dev_yn = document.all("rdoDevYn");
  var str_dev_yn = '';
  for( var inx=0; inx < dev_yn.length ; inx++)
  {
    if( document.forms[0].rdoDevYn[inx].checked )
    {
      str_dev_yn  = document.forms[0].rdoDevYn[inx].value;
    }
  }

  if( str_dev_yn == "D" )
  {
    dev_yn = "1";
  }
  else
  {
    dev_yn = "2";
  }

  if( tableRows > 1 )
  {
    dieno = form.relPartNumber[pos].value;
    expectCost = form.expectCost[pos].value;
    expectCost = expectCost.replace(",","");
  }
  else
  {
    dieno = form.relPartNumber.value;
    expectCost =form.expectCost.value;
    expectCost = expectCost.replace(",","");
  }

  if( isNumber(expectCost) )
  {
    document.forms[0].target="download";
    document.forms[0].action="/plm/jsp/ecm/BudgetInterfaceCheck.jsp?devYn="+dev_yn+"&division="+division+"&dieNo="+dieno+"&cost="+expectCost+"&rowInx="+pos;
    document.forms[0].submit();
  }
  else
  {
      alert("비용이 존재하지 않아서 예산을 확인할 수 없습니다.");
      if( tableRows > 1 )
      {
        form.expectCost[pos].focus();
      }
      else
      {
        form.expectCost.focus();
      }

      return;
  }

}

function setBudgetYn( budget_yn, row_inx, msg )
{
  alert('in CreateMoldEco.js, '+  budget_yn +','+ row_inx +','+ msg );
  
  var targetTable = document.getElementById("relPartTable");
  var tableRows = targetTable.rows.length;
  
  tableRows -= 1;
  row_inx -= 1;
  
  alert(tableRows);
  alert(row_inx);
  
  
  if( tableRows > 1 )
  {
    if( budget_yn == 'N' )
    {
       document.forms[0].budgetYnName[row_inx].value = '미확보';
     }
     else
     {
       document.forms[0].budgetYnName[row_inx].value = '확보';
     }

     document.forms[0].secureBudgetYn[row_inx].value = budget_yn;
     if( msg != '' )
     {
       alert(msg);
     }
   }
   else
   {
     if( budget_yn == 'N' )
    {
       document.forms[0].budgetYnName.value = '미확보';
     }
     else
     {
       document.forms[0].budgetYnName.value = '확보';
     }

     document.forms[0].secureBudgetYn.value = budget_yn;

     if( msg != '' )
     {
       alert(msg);
     }
   }
}

//ECR 상세조회 팝업
function viewRelEcr(oid){
  var url = "/plm/servlet/e3ps/MoldEcrServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","800","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}
