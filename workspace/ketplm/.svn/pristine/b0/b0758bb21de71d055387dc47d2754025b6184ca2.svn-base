/**
 * @(#)  createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

var projectTargetId = "";
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
//function hideProcessing() {
//    var div1 = document.getElementById('div1');
//    var div2 = document.getElementById('div2');
//    div1.style.display = "none";
//    div2.style.display = "none";
//}

function perPage(v) {
    document.forms[0].perPage.value = v;

    search();
}

//자료 검색
function search(){
    showProcessing();     // 진행중 Bar 표시
    disabledAllBtn();     // 버튼 비활성화

    document.forms[0].cmd.value = "search";
    var url = "/plm/servlet/e3ps/SearchMoldEcrServlet";

    document.forms[0].HdivisionFlag.value  = $("#divisionFlag").val();
    document.forms[0].HprodMoldCls.value  = $("#prodMoldCls").val();
    document.forms[0].HdevYn.value  = $("#devYn").val();
    document.forms[0].HsancStateFlag.value  = $("#sancStateFlag").val();

    document.forms[0].HmoldChange.value  = $("#moldChange").val();
    document.forms[0].HcostChange.value  = $("#costChange").val();
    document.forms[0].HprodChangeReason.value  = $("#prodChangeReason").val();

    
    document.forms[0].target = "_self";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//상세조회
function viewDetail(oid, prodMoldCls) {
    var form = document.forms[0];
    var url = "";
    if(prodMoldCls == "제품") {//제품
        url = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search";
        document.forms[0].cmd.value = "View";
    } else {
        url = "/plm/servlet/e3ps/MoldEcrServlet";
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
    var url = "/plm/servlet/e3ps/SearchMoldEcrServlet";
    document.forms[0].cmd.value = "excel";
    document.forms[0].target = "download";
    document.forms[0].action = url;
    document.forms[0].method = "post";

    //disabledAllBtn();
    //showProcessing();
    document.forms[0].submit();
    //enabledAllBtn();
    //hideProcessing();
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
    case 5://ECR 번호
        form.ecrId.value = "";
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


//연계ECR 검색 팝업 호출
function popupRelEcr() {
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
    url += "&obj=mode^single&obj=statusAll^Y";
    attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=1000px; dialogHeight:670px; center:yes");
    if(typeof attache == "undefined" || attache == null) {
        return;
    }
    setRelEcr(attache);
}

function setRelEcr(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];
    form.ecrOid.value = trArr[0];
    form.ecrId.value = trArr[1];
}

//프로젝트상세조회 팝업
function popupProject(targetId) {
	projectTargetId = targetId;
	SearchUtil.selectOneProjectPopUp('setProject','status=P&type=P&modal=&mode=m');
}


//프로젝트상세조회 팝업
function popupProjectProject(targetId) {
  
  projectTargetId = targetId;
  SearchUtil.selectOneProjectPopUp('setProject','status=P&type=P&modal=&mode=m');

}

//프로젝트상세조회 팝업
function popupMoldProject(targetId) {

  projectTargetId = targetId;
  SearchUtil.selectOneProjectPopUp('setProject','status=M&type=M&modal=&mode=m');
}



function setProject(arrObj) {
	
	var targetId = projectTargetId;
	var isAppend = "Y";
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
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        tmpId = $.merge($("#projectOid").val().split(", "), arrId);
        tmpId = $.unique(tmpNo.sort());

        tmpNo = $.merge($("#"+targetId).val().split(", "), arrNo);
        tmpNo = $.unique(tmpNo.sort());
    }
    else {
        tmpId = arrId.sort();
        tmpNo = arrNo.sort();
    }

    $("#projectOid").val(tmpId.join(", "));
    $("#"+targetId).val(tmpNo.join(", "));
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
        form.projectNo.value = trArr[1];
        form.projectName.value= trArr[2];
    }
}

//프로젝트상세조회 팝업
function clearProject() {
    var form = document.forms[0];
    form.projectOid.value = "";
    form.projectNo.value = "";
    form.projectName.value = "";
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
    openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+'&popup=popup', '',1050,800);
}

//부품번호 팝업
function popupPart(targetId){
   var url = "/plm/servlet/e3ps/PartServlet?cmd=openSearchPopup&mode=m&modal=Y"
   var selectedPartAry = window.showModalDialog(url, window,"help=no; resizable=no; status=no; scroll=yes; dialogWidth=725px; dialogHeight:515px; center:yes");
   if (selectedPartAry != undefined && selectedPartAry.length > 0) {
     var isAppend = "Y";
     acceptPartNo(targetId, selectedPartAry, isAppend);
   }
}

 function acceptPartNo(targetId, arrObj, isAppend) {
     var partData = new Array();
     for ( var i=0; i < arrObj.length; i++ ) {
         // [0] - wtpart oid       // [1] - part number    // [2] - part name
         // [3] - part version     // [4] - part type      // [5] - die number
         // [6] - die name         // [7] - die cnt

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



