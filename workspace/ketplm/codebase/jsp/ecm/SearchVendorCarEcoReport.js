 /**
 * @(#)	createMoldEcoBasic.js
 * Copyright (c) .. All rights reserverd
 *
 * @author ..., email 한글.
 */

//처리중 화면 전환
function hideProcessing() {
    var div1 = document.getElementById('div1');
    var div2 = document.getElementById('div2');
    div1.style.display = "none";
    div2.style.display = "none";
}

//자료 취소
function doCancel(){
    document.forms[0].reset();
}

//자료 검색
function doSearch(){
    document.forms[0].cmd.value = "search";
    document.forms[0].sortColumn.value = "1 DESC";
    //document.forms[0].sessionid.value = "";
    document.forms[0].page.value = "";
    document.forms[0].totalCount.value = "0";
    callSearch();
}

//자료 검색
function callSearch(){
    var url = "/plm/servlet/e3ps/SearchEcoReportServlet";
    if(checkValidate()){
        document.forms[0].target = "frmList";
        document.forms[0].action = url;
        document.forms[0].method = "post";

        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
    }
}

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

//상세조회
function viewDetail(oid){
    var url = "/plm/servlet/e3ps/MoldEcoServlet";
    document.forms[0].cmd.value = "view";
    document.forms[0].oid.value = oid;
    document.forms[0].target = "";
    document.forms[0].action = url;
    document.forms[0].method = "post";
    document.forms[0].submit();
}

//엑셀저장
function doExcel(){
    var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
    if(checkValidate()){
        document.forms[0].cmd.value = "excel";
        document.forms[0].target = "download";
        document.forms[0].action = url;
        document.forms[0].method = "post";

        disabledAllBtn();
        showProcessing();
        document.forms[0].submit();
        enabledAllBtn();
        hideProcessing();
    }
}

//입력항목 체크
function checkValidate(){
    var form = document.forms[0];
//	if(isNullData(form.ecoName.value)) {
//		alert("제목을 입력하여 주십시오");
//		form.ecoName.focus();
//		return false;
//	}
    return true;
}

//입력항목 체크
function clearCondition(cls){
    var form = document.forms[0];
    switch(cls) {
    case 1://부품번호
        form.partNo.value = "";
        form.partOid.value = "";
        form.partName.value = "";
        break;
    case 2://프로젝트
        form.projectNo.value = "";
        form.projectName.value = "";
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

//부서 검색 팝업  Open
function selectDepartment() {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=s";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
    var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
    callServer(url, acceptDepartment);
}

function acceptDepartment(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
        alert('다시 시도하시기 바랍니다');
        return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_oid = showElements[0].getElementsByTagName("l_oid");

    document.forms[0].orgName.value = decodeURIComponent(l_name[0].text);
    document.forms[0].orgOid.value = decodeURIComponent(l_oid[0].text);
}

//담당자검색 팝업
function popupUser(){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }
    setUser(attacheMembers);
}

//담당자 검색 팝업 리턴 포맷
function setUser(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var trArr = objArr[0];
    var form = document.forms[0];
    form.creatorOid.value = trArr[0];
    form.creatorName.value = trArr[4];
}

//연계ECR 검색 팝업 호출
function popupRelEco() {
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcoPopupForm.jsp";
    url += "&obj=prodMoldCls^0&obj=mode^single";
    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=740px; dialogHeight:550px; center:yes");
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


