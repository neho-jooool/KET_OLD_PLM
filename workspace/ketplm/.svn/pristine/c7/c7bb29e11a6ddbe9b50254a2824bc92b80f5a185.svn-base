//사용자 검색 팝업  Open
function selectUser(rname) {
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  acceptMember(rname,attacheMembers);
}

//사용자 검색 팝업  Open
function selectUser2() {
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  acceptMember(attacheMembers);
}

//필드 값 삭제
function deleteValue(param){
  document.getElementById(param).value = "";
}

//초기유동관리 목록화면 이동
function goList(param){
  if(param == 'create'){
    if(confirm("등록이 중단됩니다. 목록화면으로 이동하시겠습니까?")){
      showProcessing();
      disabledAllBtn();

      document.SearchListForm.action = '/plm/servlet/e3ps/EarlyWarningServlet?cmd=search';
      document.SearchListForm.submit();
    }
  }else if(param == 'update'){
    if(confirm("수정이 중단됩니다. 목록화면으로 이동하시겠습니까?")){
      showProcessing();
      disabledAllBtn();

      document.SearchListForm.action = '/plm/servlet/e3ps/EarlyWarningServlet?cmd=search';
      document.SearchListForm.submit();
    }
  }else{
    showProcessing();
    disabledAllBtn();

    document.SearchListForm.action = '/plm/servlet/e3ps/EarlyWarningServlet?cmd=search';
    document.SearchListForm.submit();
  }
}

//프로젝트상세조회 팝업
function viewProjectPopup(oid) {
  var url = "/plm/jsp/project/ProjectViewFrm.jsp?oid=" + oid + "&popup=popup";
  openWindow(url, '',1050,800);
}

//초기유동관리 지정 상세화면 이동
function goBack(param){
  if(param == 'create'){
    if(confirm("등록을 취소하시겠습니까?")){
      var oid = document.forms[0].oid.value;

      showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarning.jsp?oid='+oid;
    }
  }else if(param == 'update'){
    if(confirm("수정을 취소하시겠습니까?")){
      var oid = document.forms[0].oid.value;

      showProcessing();
      disabledAllBtn();

      window.location= '/plm/jsp/ews/ViewEarlyWarning.jsp?oid='+oid;
    }
  }else{
    var oid = document.forms[0].oid.value;

    showProcessing();
    disabledAllBtn();

    window.location= '/plm/jsp/ews/ViewEarlyWarning.jsp?oid='+oid;
  }
}

//todo 목록화면 이동
function goTodoList(param){
  if(param == 'create'){
    if(confirm("등록이 중단됩니다. 목록화면으로 이동하시겠습니까?")){
      showProcessing();
      disabledAllBtn();

      window.location= '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr';
    }
  }else if(param == 'update'){
    if(confirm("수정이 중단됩니다. 목록화면으로 이동하시겠습니까?")){
      showProcessing();
      disabledAllBtn();

      window.location= '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr';
    }
  }else{
    showProcessing();
    disabledAllBtn();

    window.location= '/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr';
  }

}
