 /**
 * @(#)  createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */


function selectPartAfterFunc( objArr )
{
    var trArr;
    if(objArr.length == 0) {
        return;
    }
    for(var i = 0; i < objArr.length; i++)
    {
    	setPart(objArr[i]);
    }
}

function setPart(objArr){
	trArr = objArr;
	var tempPartOid = $('[name=partOid]').val();
    var tempPartNo = $('[name=partNo]').val();
	
	if(tempPartOid != ""){
		tempPartOid = tempPartOid+",";
	}
	if(tempPartNo != ""){
		tempPartNo = tempPartNo+",";
    }
	
    $('[name=partOid]').val(tempPartOid+trArr[0]);
    $('[name=partNo]').val(tempPartNo+trArr[1]);
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

//페이지 조회
function gotoPage(pageno){
  document.forms[0].cmd.value = "search";
  document.forms[0].page.value = pageno;
  callSearch();
}

//sort 조회
function doSort(sortSQL){
  document.forms[0].cmd.value = "search";
  document.forms[0].sortColumn.value = sortSQL;
  //document.forms[0].sessionid.value = "";
  document.forms[0].page.value = "";
  document.forms[0].totalCount.value = "0";
  callSearch();
}

function getSort()
{
  return  document.forms[0].sortColumn.value;
}

//상세조회
function viewDetail(oid, prodMoldCls) {
  var form = document.forms[0];
  var url = "";
  if(prodMoldCls == "제품") {//제품
    url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S";
    document.forms[0].cmd.value = "View";
  } else {
    url = "/plm/servlet/e3ps/MoldEcoServlet?prePage=Search";
    document.forms[0].cmd.value = "view";
  }
  document.forms[0].oid.value = oid;
  document.forms[0].target = "";
  document.forms[0].action = url;
  document.forms[0].method = "post";
  document.forms[0].submit();
}

//엑셀저장
function excelDown(){
  var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
  if(checkValidate()){
    document.forms[0].cmd.value = "excel";
    document.forms[0].target = "download";
    document.forms[0].action = url;
    document.forms[0].method = "post";

    //disabledAllBtn();
    //showProcessing();
    document.forms[0].submit();
   // enabledAllBtn();
   // hideProcessing();
  }
}


function moldCostexcel() {
	var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
	if (checkValidate()) {
		document.forms[0].cmd.value = "moldCostexcel";
		document.forms[0].target = "download";
		document.forms[0].action = url;
		document.forms[0].method = "post";

		// disabledAllBtn();
		// showProcessing();
		document.forms[0].submit();
		// enabledAllBtn();
		// hideProcessing();
	}
}

// 입력항목 체크
function checkValidate(){
  var form = document.forms[0];
//  if(isNullData(form.ecoName.value)) {
//    alert("제목을 입력하여 주십시오");
//    form.ecoName.focus();
//    return false;
//  }
  return true;
}

//입력항목 체크
function clearCondition(cls){
  var form = document.forms[0];
  switch(cls) {
  case 1://부품번호
    form.partNo.value = "";
    form.partOid.value = "";
    break;
  case 2://프로젝트
    form.projectOid.value = "";
    form.projectNo.value = "";
    break;
  case 3://ECO 작성부서
    form.orgOid.value = "";
    form.orgName.value = "";
    break;
  case 4://ECO 작성자
    form.creatorOid.value = "";
    form.creatorName.value = "";
    break;
  case 5://ECO 번호
    form.ecoId.value = "";
    form.ecoOid.value = "";
    break;
  case 8://ECR  번호 팝업
    form.ecrNumber.value = "";
    form.ecrOid.value = "";
    break;
  case 6://작성시작일자
    form.createStartDate.value = "";
    break;
  case 7://작성종료일자
    form.createEndDate.value = "";
    break;
  default:
    break;
  }
  return true;
}


//ECO 검색 팝업 호출
function popupRelEco() {
  var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
  url += "&obj=prodMoldCls^0&obj=mode^single&obj=statusAll^Y";
  
  attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=735px; dialogHeight:520px; center:yes");
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
  form.ecoId.value = trArr[1];
}

// 부품번호 팝업
function popupPart(targetId){
//      var url = "/plm/servlet/e3ps/PartServlet?cmd=openSearchPopup&mode=m&modal=Y"
//      var selectedPartAry = window.showModalDialog(url, window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=725px; dialogHeight:515px; center:yes");
//      if (selectedPartAry != undefined && selectedPartAry.length > 0) {
//        var isAppend = "Y";
//        acceptPartNo(targetId, selectedPartAry, isAppend);
//      }
      
      SearchUtil.selectPart('acceptPartNo','pType=P');
}

    function acceptPartNo(arrObj) {
    	if(arrObj.length == 0) {
            return;
        }
        var partData = new Array();
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtpart oid       // [1] - part number    // [2] - part name
            // [3] - part version     // [4] - part type      // [5] - die number
            // [6] - die name         // [7] - die cnt

            var infoArr = arrObj[i];
            partData[i] = infoArr[1];
        }

        var tmpNo = new Array();
        if ( $("#partNo").val() != "" ) {
            // ID 중복 제거
            tmpNo = $.merge($("#partNo").val().split(", "), partData);
            tmpNo = $.unique(tmpNo.sort());
        }
        else {
            tmpNo = partData.sort();
        }

        $("#partNo").val(tmpNo.join(", "));
    }


    function clearPartNo(targetId) {
      $("#" + targetId).val("");
    }




//부품 상세조회 팝업
function viewRelEco(oid){
  var url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + oid;
  openWindow(url,"","800","680","scrollbars=no,resizable=no,top=200,left=250,center=yes");
}

//부품 상세조회 팝업
function viewPart(v_poid){
//  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
//  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
  openView(v_poid);
}

//프로젝트검색 팝업
function popupProject(targetId) {
        var url = "/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P&modal=Y&mode=m"
      var selectedPartAry = window.showModalDialog(url, window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=1024px; dialogHeight:768px; center:yes");
      if (selectedPartAry != undefined && selectedPartAry.length > 0) {
        var isAppend = "Y";
        setProject(targetId, selectedPartAry, isAppend);
      }
}


//프로젝트상세조회 팝업
function popupProjectProject(targetId) {

//var url = "/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P&modal=Y&mode=m"
//    var selectedPartAry = window.showModalDialog(url, window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=1024px; dialogHeight:768px; center:yes");
//if (selectedPartAry != undefined && selectedPartAry.length > 0) {
//    var isAppend = "Y";
//    setProject(targetId, selectedPartAry, isAppend);
//}
	
	SearchUtil.selectProjectPopUp('setProject');
}

//프로젝트상세조회 팝업
function popupMoldProject(targetId) {

//var url = "/plm/jsp/project/SearchPjtPop.jsp?status=M&type=M&modal=Y&mode=m"
//    var selectedPartAry = window.showModalDialog(url, window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=1024px; dialogHeight:768px; center:yes");
//if (selectedPartAry != undefined && selectedPartAry.length > 0) {
//    var isAppend = "Y";
//    setProject(targetId, selectedPartAry, isAppend);
//}
	SearchUtil.selectProjectPopUp('setProject','status=M&type=M');
}


function setProject(arrObj) {
	
	if(arrObj.length < 1){
		return;
	}
	
    var arrId = new Array();
    var arrNo = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] -  oid       // [1] - project number
        var infoArr = arrObj[i];
        arrId[i] = infoArr[0];
        arrNo[i] = infoArr[1];
    }

    var tmpId = new Array();
    var tmpNo = new Array();
    if ( $("#projectNo").val() != "" ) {
        tmpId = $.merge($("#projectOid").val().split(", "), arrId);
        tmpId = $.unique(tmpNo.sort());

        tmpNo = $.merge($("#projectNo").val().split(", "), arrNo);
        tmpNo = $.unique(tmpNo.sort());
    }
    else {
        tmpId = arrId.sort();
        tmpNo = arrNo.sort();
    }

    $("#projectOid").val(tmpId.join(", "));
    $("#projectNo").val(tmpNo.join(", "));
}


function setProject1(objArr) {
  if(objArr.length == 0) {
    return;
  }

  var trArr;
  var str = "";
  var form = document.forms[0];
  for(var i = 0; i < objArr.length; i++) {
    trArr = objArr[i];
    form.projectOid.value = trArr[0];
    form.projectNo.value  = trArr[1];
    //form.projectName.value= trArr[2];
  }
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
  openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
}

