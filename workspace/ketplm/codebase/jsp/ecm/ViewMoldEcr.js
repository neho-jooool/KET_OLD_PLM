 /**
 * @(#)	createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 * 
 * @author ..., email 한글.
 */


//프로젝트상세조회 팝업
function init() {
//	var btnUpdate = document.getElementById("btnUpdate");
//	var btnDelete = document.getElementById("btnDelete");
//	var btnCreateEco = document.getElementById("btnCreateEco");
//	var btnApprove = document.getElementById("btnApprove");
//	var form = document.forms[0];
//	var state = form.stateState.value;
//	if(state == "APPROVED") {//승인완료
//		btnUpdate.disabled = true;
//		btnDelete.disabled = true;
//		btnCreateEco.disabled = false;//ECO작성
//		btnApprove.disabled = true;
//	} else if(state == "UNDERREVIEW") {//검토중
//		btnUpdate.disabled = true;
//		btnDelete.disabled = true;
//		btnCreateEco.disabled = true;
//		btnApprove.disabled = false;
//	} else if(state == "INWORK") {//작업중
//		btnUpdate.disabled = false;
//		btnDelete.disabled = false;
//		btnCreateEco.disabled = true;
//		btnApprove.disabled = false;
//	}
	
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당
    fnSetBodyStyleValue(1, document.forms[0].hdnBackgroundColor.value, 0);// 배경색, Value, 이노디터 번호
    fnSetBodyStyleValue(2, document.forms[0].hdnBackgroundImage.value, 0);// 배경이미지, Value, 이노디터 번호
    fnSetBodyStyleValue(3, document.forms[0].hdnBackgroundRepeat.value, 0);// 배경이미지 반복옵션, Value, 이노디터 번호
    ////////////////////////////////////////////////////////////////////////////////////////////////////
	
}


//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
	openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
}

//탭전환
function clickTabBtn(tabId) {
	var tabBasic = document.getElementById("tabBasic");
	var tabActivity = document.getElementById("tabActivity");
	if(tabId == 1) {
		tabBasic.style.visibility = "visible";
		tabActivity.style.visibility = "hidden";
	} else {
		tabBasic.style.visibility = "hidden";
		tabActivity.style.visibility = "visible";
	}

	var imgBasic = document.getElementById("imgBasic");
	var imgActivity = document.getElementById("imgActivity");
	if(tabId == 1) {
		imgBasic.src = "/plm/portal/images/tab7_1.png";
		imgActivity.src = "/plm/portal/images/tab8_2.png";
	} else {
		imgBasic.src = "/plm/portal/images/tab7_2.png";
		imgActivity.src = "/plm/portal/images/tab8_1.png";
	}
}

//처리중 화면 전환
function hideProcessing() {
	var div1 = document.getElementById('div1');
	var div2 = document.getElementById('div2');
	div1.style.display = "none";
	div2.style.display = "none";
}

//금형ECR 등록/수정 화면으로 이동한다. 
function callUpdate(){
	var url = "/plm/servlet/e3ps/MoldEcrServlet";
	document.forms[0].cmd.value = "updateview";
	//document.forms[0].target = "contName";
	document.forms[0].target = "_self";
	document.forms[0].action = url;
	document.forms[0].method = "post";
	document.forms[0].submit();
}

//목록
function callSearch(){

	if( document.forms[0].prePage.value == 'Search' )
	{
		history.back();
	}
	else
	{
		var url = "/plm/jsp/ecm/SearchEcrForm.jsp";
		document.forms[0].cmd.value = "search";
		document.forms[0].target = "contName";
		document.forms[0].action = url;
		document.forms[0].method = "post";
		document.forms[0].submit();
	}
}

//결재요청
function doSanction(){
	var form = document.forms[0];
	goPage(form.oid.value);
}

//자료 취소
function doCancel(){
	document.forms[0].reset();
}

//자료 취소
function doDelete(){
	if(!confirm("삭제 하시겠습니까?")) {
		return;
	}
	disabledAllBtn();
	showProcessing();
	var url = "/plm/servlet/e3ps/MoldEcrServlet";
	document.forms[0].cmd.value = "delete";
	document.forms[0].target = "download";
	document.forms[0].action = url;
	document.forms[0].method = "post";
	document.forms[0].submit();
}


//목록
function callCreateMoldEco(){
	var url = "/plm/servlet/e3ps/MoldEcrServlet";
	document.forms[0].cmd.value = "goMoldEco";
	document.forms[0].target = "contName";
	document.forms[0].action = url;
	document.forms[0].method = "post";
	document.forms[0].submit();
}

//부품 상세조회 팝업
function viewPart(v_poid){
	var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
	openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

function viewIssue(oid){
	var url = '/plm/jsp/project/projectIssueView.jsp?oid='+oid;
	openWindow2(url,"","740","320","scrollbars=no,resizable=no,top=200,left=250");
}
