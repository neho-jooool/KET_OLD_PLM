<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.project.*,
        e3ps.project.beans.*,
        e3ps.common.util.*,
        java.util.*,
        wt.query.*,
        wt.fc.*"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
  String oid = StringUtil.checkNull(request.getParameter("oid"));
  Object obj = CommonUtil.getObject(oid);
  String deptOid = StringUtil.checkNull(request.getParameter("deptOid"));

  E3PSProject project = null;
  E3PSTask task = null;
  E3PSTaskData data = null;
  String title = "";
  boolean isLevelOne = false;
  boolean isErp = false;
  boolean isChild = false;

  boolean isDebug = false;
  String making = "";
  String taskNames = "";

  StringTokenizer st = null;

  TemplateTask tempTask = null;
  String readonly = "";
  String readonlyTask = "";
  String readonlyChecked = "";
  String color = "bgcolor=ffffff";
  String optiontype = "Y"; //필수여부
  String milestone = "N";  //Milestone 여부
  String tasktype = "";    //Task 종류
  String drvalue = "";    //DR 목표 값
  String projectType = "";

  String taskTitle = "";
  String startDate = "";
  String endDate = "";
  String duration = "";

  ProjectViewButtonAuth auth = null;
  boolean isProject = false;
  if (obj instanceof E3PSProject) {
    project = (E3PSProject) obj;
        title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + project.getPjtName();
    isLevelOne = true;
    //readonly = "READONLY";
    color = "bgcolor=D8E0E7";

    auth = new ProjectViewButtonAuth(project);
    isProject = true;
    if(project instanceof ProductProject)
      projectType = "product";
    else if(project instanceof MoldProject)
      projectType = "mold";

  } else if (obj instanceof E3PSTask) {
    task = (E3PSTask) obj;
    tempTask = (TemplateTask) obj;
    isErp = false;
        title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + task.getProject().getPjtName();
    data = new E3PSTaskData(task);
    optiontype=data.optiontype;
    milestone=data.milestone;
    tasktype=data.tasktype;
    drvalue=data.drvalue;
    isChild = ProjectTaskHelper.isChild(task);
    project = (E3PSProject)task.getProject();


    taskTitle = task.getTaskName();
    startDate = DateUtil.getDateString(data.taskPlanStartDate, "D");
    endDate = DateUtil.getDateString(data.taskPlanEndDate, "D");
    int dura = DateUtil.getDuration(data.taskPlanStartDate, data.taskPlanEndDate) + 1;
    duration = "" + dura;

    auth = new ProjectViewButtonAuth(project);

    if(project instanceof ProductProject){
      projectType = "product";
    }else if(project instanceof MoldProject){
      projectType = "mold";

      isDebug = task.isDebug_n();

      MoldProject mold = (MoldProject)project;
      MoldItemInfo info = mold.getMoldInfo();
      making = info.getMaking();

      if(making.equals("사내")){
        taskNames = ConfigImpl.getInstance().getString("debgingName_in");

        //taskNames = new String[]{"제품도", "금형설계", "금형부품", "금형조립","금형 Try", "검사 의뢰", "제품 측정", "제품 검토 협의"};
      }else{

        taskNames = ConfigImpl.getInstance().getString("debgingName_out");

        //taskNames = new String[]{"제품도", "외주 금형 수정", "금형 Try", "검사 의뢰", "제품 측정", "제품 검토 협의"};
      }

      st = new StringTokenizer(taskNames, ";");

    }

  }

  if(isChild){
    readonlyTask = "READONLY";
    readonlyChecked = "READONLY";
  }




%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp"%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/checkbox2.js"></SCRIPT>
<script language="JavaScript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript">

  var ajax = new sack();

  function checkfirst() {
    var objBody = document.getElementById("taskTable");
    if(objBody.rows.length == 0){
      return;
    }
    var cbox=document.forms[0].check;

    if (cbox != null) {
        // 2이상 일때
          if (cbox.length) {
           // 체크된 리스트를 삭제한다.
          cbox[0].checked = true;
          }
          // 1명 일때
          else if (cbox)  {
          cbox.checked = true;
           }
      }
  }

  function checkClick(oid) {
    var cbox=document.forms[0].check;

    if (cbox != null) {
        // 2이상 일때
          if (cbox.length) {
              // 체크된 리스트를 삭제한다.
              for (var i = cbox.length; i > 0 ; i--)  {
                  if (cbox[i - 1].value == oid) {
            cbox[i - 1].checked = true;
                  }
              }
          }
          // 1명 일때
          else if (cbox)  {
              // 체크된 리스트를 삭제한다.

              if (cbox.value == oid) {

          cbox.checked = true;
              }
           }
      }
  }

  function mainTask() {
      var oid = document.getElementById("oid");
      if(oid.value.length>0){
      ajax.requestFile = 'ManageProjectTaskAjax.jsp?oid='+oid.value;
      ajax.onCompletion = mainList;
      ajax.runAJAX();
      }
  }

  function deleteRow() {
    var objBody = document.getElementById("taskTable");
    //alert(objBody.rows.length);

    var intseq = objBody.rows.length;
    for(var i = 0 ; i < intseq ; i++){
      objBody.deleteRow(0);
    }
  }

  function upDownTask(type) {
    var oid = document.getElementById("oid");
    //var check = chk=document.forms[0].check;
    var check = document.forms[0].check;
    var len;
    if(check == null){
      //if(check.value == ""){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00495") %><%--Task를 선택 해주세요--%>");
        return;
      //}
    }else{
      len = check.length;
    }

    var selectedList;
    if(check != null) {
      if(len > 1) {
        for(var i=0 ; i<len ; i++) {
          if (check.check[i].checked==true)
            selectedList = check.check[i].value;
        }
      } else {
        if (check.checked==true) selectedList = check.value;
      }
    }


    if(len>0) {
      disabledAllBtn();
      document.forms[0].command.value = type;
      ajax.requestFile = 'ManageProjectTaskAjax.jsp';
      ajax.URLString = getPostData(document.forms[0]);
      ajax.onCompletion = Complated;
      ajax.runAJAX();
    }
  }

  function ComplatedModify() {
    if(ajax.response != ""){
        eval(ajax.response);
      }
  }

  function Complated() {
    if(ajax.response == "undifine") {
      alert(ajax.response);
      return;
    }

    if(ajax.response != "") {
        eval(ajax.response);
        if(document.forms[0].command.value == "create") {
          enabledAllBtn();
          return;
        }
     }

      ajax.requestFile = "ManageTaskListAjax.jsp";
    ajax.URLString = getPostData(document.forms[0]);
    ajax.onCompletion = reload;
    ajax.runAJAX();
  }

  function reload() {
    var objBody = document.getElementById("taskTable");
    deleteRow();
    //alert(ajax.response);
    eval(ajax.response);
    enabledAllBtn();
    if(document.forms[0].command.value != "" ){
      parent.tree.reload2(document.forms[0].oid.value);
    }
    //parent.tree.reloadTree();
  }

  function mainList() {
    var objBody = document.getElementById("taskTable");
    eval(ajax.response);
  }

  function openCal2(param) {
    var str="/plm/jsp/common/calendar.jsp?chageObj=true&form=manageProjectTaskList&obj="+param;
    newWin = window.open(str,"cel","scrollbars=no,status=yes,menubar=no,toolbar=no,location=no,directories=no,width=220,height=220,resizable=no,mebar=no,left=250,top=65");
    newWin.focus();
  }

  function settingProject() {
    if(ajax.response != "") {
      //alert(unescape(ajax.response));
        eval(ajax.response);
    }
  }

  function selectTask(oid) {
    var taskoid = oid;
    ajax.requestFile = "ManageProjectTaskSelectAjax.jsp?taskoid=" + taskoid;
    ajax.onCompletion = settingProject;
    ajax.runAJAX();
  }

  function createTask(obj) {
    if( checkField(document.forms[0].cname, "TASK 명") ) {
      return;
    }

    if( checkField(document.forms[0].cstartdate, "시작일자") ) {
      return;
    }

    if( checkField(document.forms[0].cenddate, "종료일자") ) {
      return;
    }

    if(checkDate()){
      return;
    }
    //if(document.forms[0].stdWork.value == ''){
    //  document.forms[0].stdWork.value = '0';
    //}

    //if( document.forms[0].cnameCheck.checked){
      //if(document.forms[0].wbsItem.value == ""){
        //alert("ERP 연계 테스크를 선택하십시오");
        //return;
      //}
    //}

    <% //if(!isLevelOne) { %>
    //if(document.forms[0].istargetItem.checked){
      //if(checkField(document.forms[0].target, "Target 값")) {
        //return;
      //}
    //}
    <% //} %>

    disabledAllBtn();

    document.forms[0].command.value = "create";
    ajax.requestFile = "/plm/servlet/e3ps/ManageProjectTaskServlet";
    ajax.URLString = getPostData(document.forms[0]);
    ajax.onCompletion = Complated;
    ajax.runAJAX();
  }

  function thisUpdate(obj) {
    if( document.forms[0].tcname.value == "" ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01955") %><%--수정할 TASK가 선택되지 않았습니다 \n수정할 TASK를 선택 후 내용을 변경해 주십시요--%>");
      return;
    } else {
      if( checkField(document.forms[0].tcname, "TASK 명") ){
        return;
      }
      if( checkField(document.forms[0].tcstartdate, "시작일자") ){
        return;
      }
      if( checkField(document.forms[0].tcenddate, "종료일자") ){
        return;
      }

    //disabledAllBtn();
    document.forms[0].command.value = "taskUpdate";
    ajax.requestFile = "/plm/servlet/e3ps/ManageProjectTaskServlet";

    ajax.URLString = getPostData(document.forms[0]);

    ajax.onCompletion = ComplatedModify;
    ajax.runAJAX();
    }
  }

  function updateTask(obj) {
    if( document.forms[0].coid.value == "" ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01955") %><%--수정할 TASK가 선택되지 않았습니다 \n수정할 TASK를 선택 후 내용을 변경해 주십시요--%>");
      return;
    } else {
      if( checkField(document.forms[0].cname, "TASK 명") ){
        return;
      }
      if( checkField(document.forms[0].cstartdate, "시작일자") ){
        return;
      }
      if( checkField(document.forms[0].cenddate, "종료일자") ){
        return;
      }

      if(checkDate()){
        return;
      }
      //if(document.forms[0].stdWork.value == ''){
      //  document.forms[0].stdWork.value ='0';
      //}

      //if( document.forms[0].cnameCheck.checked){
        //if(document.forms[0].wbsItem.value == ""){
          //alert("ERP 연계 테스크를 선택하십시오");
          //return;
        //}
      //}

      <% //if(!isLevelOne) { %>
      //if(document.forms[0].istargetItem != null && document.forms[0].istargetItem.checked){
        //if(checkField(document.forms[0].target, "Target 값")){
          //return;
        //}
      //}
      <% //} %>
      disabledAllBtn();

      document.forms[0].command.value = "update";
      ajax.requestFile = "/plm/servlet/e3ps/ManageProjectTaskServlet";

      ajax.URLString = getPostData(document.forms[0]);

      ajax.onCompletion = Complated;
      ajax.runAJAX();
    }
  }

  function deleteTask(obj) {
    if( document.forms[0].coid.value == "" ){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01709") %><%--삭제할 TASK가 선택되지 않았습니다 \n삭제할 TASK를 선택해 주십시요--%>");
      return;
    }
    //isAjax
    if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "00494") %><%--Task를 삭제하시겠습니까?--%>') ) {
      disabledAllBtn();
      document.forms[0].command.value = "delete";
      ajax.requestFile = "/plm/servlet/e3ps/ManageProjectTaskServlet";

      ajax.URLString = getPostData(document.forms[0]);

      ajax.onCompletion = Complated;

      ajax.runAJAX();
    }
  }

  function checkDate() {
    var cstartdate = document.forms[0].cstartdate.value;
    var cenddate = document.forms[0].cenddate.value;

    if(cstartdate.length>0 && cenddate.length>0) {
      if(cstartdate > cenddate) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "02660") %><%--종료일자가 시작일자보다 늦습니다 다시 입력해 주시기 바랍니다--%>");
        document.forms[0].cstartdate.value = "";
        document.forms[0].cenddate.value = "";
        return true;
      } else {
        return false;
      }
    }
  }

  function thiscolse() {
    parent.close();
  }

  //********************************************************************
  //문자열 일괄전환 함수
  function funcReplaceStrAll(org_str, find_str, replace_str) {
      var pos = org_str.indexOf(find_str);
      while(pos != -1) {
          pre_str  = org_str.substring(0, pos);
          post_str = org_str.substring(pos + find_str.length, org_str.length);
          org_str  = pre_str + replace_str + post_str;
          pos = org_str.indexOf(find_str);
      }
      return org_str;
  }

  //*******************************************************************
  //년월 입력시 마지막 일자
  function  getEndOfMonthDay( yy, mm ) {
      var max_days=0;
      if(mm == 1) {
          max_days = 31 ;
      } else if(mm == 2) {
          if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
          else                                                         max_days = 28;
      }
      else if (mm == 3)   max_days = 31;
      else if (mm == 4)   max_days = 30;
      else if (mm == 5)   max_days = 31;
      else if (mm == 6)   max_days = 30;
      else if (mm == 7)   max_days = 31;
      else if (mm == 8)   max_days = 31;
      else if (mm == 9)   max_days = 30;
      else if (mm == 10)  max_days = 31;
      else if (mm == 11)  max_days = 30;
      else if (mm == 12)  max_days = 31;
      else                return '';

      return max_days;
  }

  //*********************************************************************
  //날짜 유효성 검증하는 함수
  function isValidDate(obj, maxLength) {
    var retVal = true;
    var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
   //document.forms[0].duration.value = "";

  if(obj.value == "") {
    return;
  }

  val=obj.value;
  re=/[^0-9]/gi;
  obj.value=val.replace(re,"");

    var inputDate = funcReplaceStrAll(obj.value,  '년', '');
    inputDate     = funcReplaceStrAll(inputDate,  '월', '');
    inputDate     = funcReplaceStrAll(inputDate,  '일', '');

    var yyyy = inputDate.substring(0, 4);
    var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
    var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

    if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
    if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
    if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

  if(inputDate.length == 8) {
      inputDate = inputDate.substring(0, 8); //미봉책
  }else{
        alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
    return;
  }

  obj.value = yyyy+ "-" +mm+ "-" +dd;
    return true;
  }

  function viewErrMsg(obj,msg) {
    alert(obj.value + " " + msg);
  }

  function setBeforeDate(obj) {
    var startDate = document.getElementById("cstartdate").value;
    var startDate_222 = document.getElementById("cenddate").value;
    var value = parseInt(obj.value) - 1;

    if(startDate != "" && startDate_222 == ""){
      var date = new Date(  startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + value);
      var endDate = document.getElementById('cenddate');

      var year=String(date.getYear());
      var month=String(date.getMonth()+1);
      var day=String(date.getDate());

      if(month.length==1)month="0"+month;
      if(day.length==1)day="0"+day;
      endDate.value=year+ "-" + month + "-" + day;

    }else if(startDate_222 != "" && startDate == ""){
      var date = new Date(startDate_222.substring(0,4),Number(startDate_222.substring(5,7))-1, Number(startDate_222.substring(8,10))- value);
      var endDate_222 = document.getElementById('cstartdate');

      var year=String(date.getYear());
      var month=String(date.getMonth()+1);
      var day=String(date.getDate());

      if(month.length==1)month="0"+month;
      if(day.length==1)day="0"+day;
      endDate_222.value=year+ "-" +month+ "-" +day;
    }else if(startDate_222 != "" && startDate != ""){
      var date = new Date(  startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + value);
      var endDate = document.getElementById('cenddate');

      var year=String(date.getYear());
      var month=String(date.getMonth()+1);
      var day=String(date.getDate());

      if(month.length==1)month="0"+month;
      if(day.length==1)day="0"+day;
      endDate.value=year+ "-" + month + "-" + day;
    }
    else if(startDate_222 == "" && startDate == ""){

      alert("시작일자  및  종료일자 입력이  필요 합니다 ");
      obj.value = "";
      return;
    }
  }

  function isChangeDuration() {

    var startDate = document.getElementById("cstartdate").value;
      var endDate = document.getElementById("cenddate").value;
    var duration = document.forms[0].duration.value;
    var durationValue;

    if(startDate == "") {
      return;
    }

    if(duration == "") {
      return;
    } else {
       durationValue = parseInt(duration)-1;
    }

    var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue);
    var year=String(date.getYear());
      var month=String(date.getMonth()+1);
      var day=String(date.getDate());

    if(month.length==1)month="0"+month;
    if(day.length==1)day="0"+day;

    endDate=year+ "-" + month + "-" + day;

    document.forms[0].cenddate.value = endDate;
    document.forms[0].duration.value = duration;
  }

  function isChangeDuration2() {
    var startDate = document.getElementById("cstartdate").value;
      var endDate = document.getElementById("cenddate").value;
    var duration = document.forms[0].duration.value;

    var durationValue;

    if(startDate == "") {
      return;
    }else{
      var startCal = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)));
       var endCal = new Date(endDate.substring(0,4), Number(endDate.substring(5,7))-1, Number(endDate.substring(8,10)));

       var interval = endCal - startCal;
       var day = 1000*60*60*24;

       var value = (interval / day) + 1;
       //alert("AAAAA + " + value);
       if(value <= 0 && false){

         document.getElementById("duration").value = "";
         document.getElementById("cenddate").value = "";
         //document.getElementById("end").value = "";
         //document.getElementById("end").focus();
         alert("계획 시작일보다 이전 날짜를 입력할 수 없습니다.");
         return;
       }

       document.forms[0].duration.value = value;
       //return;
    }
    /*
    if(duration == "") {
      return;
    } else {
       durationValue = parseInt(duration)-1;
    }

    var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) - durationValue);
    var year=String(date.getYear());
      var month=String(date.getMonth()+1);
      var day=String(date.getDate());

    if(month.length==1)month="0"+month;
    if(day.length==1)day="0"+day;

    endDate=year+ "-" + month + "-" + day;

    document.forms[0].cstartdate.value = endDate;
    document.forms[0].duration.value = duration;
    */
  }

  function isChangeDuration3() {
    var startDate = document.getElementById("cstartdate").value;
      var endDate = document.getElementById("cenddate").value;
    var duration = document.forms[0].duration.value;

    var durationValue;

    if(endDate == "") {
      return;
    }else{
      var startCal = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)));
       var endCal = new Date(endDate.substring(0,4), Number(endDate.substring(5,7))-1, Number(endDate.substring(8,10)));

       var interval = endCal - startCal;
       var day = 1000*60*60*24;

       var value = (interval / day) + 1;
       //alert("AAAAA + " + value);
       if(value <= 0 && false){

         document.getElementById("duration").value = "";
         document.getElementById("cstartdate").value = "";
         //document.getElementById("end").value = "";
         //document.getElementById("end").focus();
         alert("<%=messageService.getString("e3ps.message.ket_message", "00804") %><%--계획 종료일보다 이후 날짜를 입력할 수 없습니다--%>");
         return;
       }

       document.forms[0].duration.value = value;
       //return;
    }
    /*
    if(duration == "") {
      return;
    } else {
       durationValue = parseInt(duration)-1;
    }

    var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) - durationValue);
    var year=String(date.getYear());
      var month=String(date.getMonth()+1);
      var day=String(date.getDate());

    if(month.length==1)month="0"+month;
    if(day.length==1)day="0"+day;

    endDate=year+ "-" + month + "-" + day;

    document.forms[0].cstartdate.value = endDate;
    document.forms[0].duration.value = duration;
    */
  }

  function checkTargetItem() {
    var cbox = document.manageProjectTaskList;
    if(cbox.istargetItem != null) {

      if(cbox.istargetItem.checked) {
        targetDisplay.style.display = "";
        return;
      } else{
        targetDisplay.style.display = "none";
        return;
      }
    }
  }

  function tcheckTargetItem() {
    var cbox = document.manageProjectTaskList;
    if(cbox.tistargetItem != null) {
      if(cbox.tistargetItem.checked) {
        ttargetDisplay.style.display = "";
        return;
      } else{
        ttargetDisplay.style.display = "none";
        return;
      }
    }
  }

  //parent.tree.location.reload();


  ///////////////////////////////
///////wbs Item Search/////////


  //function selectWBSItem(oid){

    //document.forms[0].wbsItem.value = oid;

    //if(oid != ""){
      //document.forms[0].cnameCheck.checked = true;
    //}else{
      //document.forms[0].cnameCheck.checked = false;
    //}
  //}

  function selectWBSItem(oid){
    //document.forms[0].wbsItem.value = oid;

    //if(oid != ""){
      //document.forms[0].cnameCheck.checked = true;
    //}else{
      //document.forms[0].cnameCheck.checked = false;
    //}


    var cbox=document.forms[0].wbsItem;
    if (cbox != null) {
        // 2이상 일때
          if (cbox.length) {
              // 체크된 리스트를 삭제한다.
              for (var i = cbox.length; i > 0 ; i--)  {
                  if (cbox[i - 1].value == oid) {
            cbox[i - 1].selected = true;

                  }
              }
          }
          // 1명 일때
          else if (cbox)  {
              // 체크된 리스트를 삭제한다.

              if (cbox.value == oid) {

          cbox.selected = true;
              }
           }
      }
  }

  function cnameClear(){

    if (document.forms[0].cnameCheck.checked){
      document.forms[0].cname.value = "";
    }else{
      document.forms[0].wbsItem.value = "";
      //reCheck();
    }
  }

var txtObj;
var valueObj;
var set_number;
function taskNameInput(tObj, vstr, ltdlen) {
    if(!document.forms[0].cnameCheck.checked){
      return;
    }

  form = document.forms[0];

  txtObj = tObj;
  valueObj = document.getElementById(vstr);

  var dpjtcode = txtObj.value;
  valueObj.value = '';

  if(dpjtcode.length < ltdlen) {
    offLayer('layer0');
    return;
  }

  if (window.event.keyCode == 13) {
    set_number = true;
  }
  else {
    set_number = false;
  }

  rectObj = tObj.getBoundingClientRect();
  onLayer('layer0', rectObj.left, rectObj.bottom, 250, lheight);

  form = document.forms[0];
  var param = "command=autoComplete";
  param += "&cname=" + txtObj.value;
  var url = "/plm/jsp/project/manage/WBSITemSearchAjax.jsp";


  ajax.requestFile = url;
  ajax.URLString = param;
  ajax.onCompletion = setLayerMember;
  ajax.runAJAX();

  //callServer(url, setLayerMember);
  //postCallServer(url, param, setLayerMember, false);
}

function setLayerMember(req) {
    //alert(ajax.response);
  var xmlDoc = ajax.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03094") %><%--프로젝트 정보를 가져오는 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
    return;
  }

  var showElements = xmlDoc.selectNodes("//data_info");
  var l_code = showElements[0].getElementsByTagName("l_code");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_oid = showElements[0].getElementsByTagName("l_oid");


  if(set_number && l_code.length == 1) {
    valueObj.value = decodeURIComponent(l_oid[0].text);
    txtObj.value = decodeURIComponent(l_name[0].text);
    offLayer('layer0');
    return;
  }

  var htmlStr = "";
  if(l_code != null && l_code.length > 0) {
    for(var i = 0; i < l_code.length; i++) {
      r_code = decodeURIComponent(l_code[i].text);
      r_name = decodeURIComponent(l_name[i].text);
      r_oid = decodeURIComponent(l_oid[i].text);

      s_name = r_name;
      if(s_name.length > 20) {
        s_name = s_name.substring(0, 17) + "...";
      }

      v_code = r_code;
      if(set_number != null && set_number == 'true') {
        v_code = v_code.substring(2);
      }
      //J-12345678 -->> 12345678
      htmlStr +="<li title='"+r_name+"'><a href='#' onclick=\"javascript:setLayerData('"+r_oid+"','" + r_name + "');\">"+ r_code + "&nbsp;|&nbsp;" + s_name +"</a></li>";
    }
  }
  else {
    htmlStr += "<li style='text-align:center;'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></li>";
  }

  onLayerClear("layer0content");
  var layerContent = document.getElementById("layer0content");
  layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";
}


function setLayerData(keystr, display) {
  valueObj.value = keystr;
  txtObj.value = display;

  offLayer('layer0');
}
///////////////////////////////////////////DR 목표 값

  function displayChange(){
    form = document.forms[0];
    var tasktypeValue = "";
    for(i=0; i<form.taskType.length; i++) {
      if(form.taskType[i].selected) tasktypeValue = form.taskType[i].value;
    }

    if(tasktypeValue == "DR") {
      drvalueTd1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%>";
      drvalueTd2.innerHTML = "<INPUT id=i size=7 name=drValue value=\"<%=drvalue%>\" onkeyup =\"SetNum(this)\" style='IME-MODE: disabled'>";
    }else {
      drvalueTd1.innerHTML = "&nbsp;";
      drvalueTd2.innerHTML = "&nbsp;";
    }
  }


  function excelDown(){
    document.forms[0].method = "post";
    document.forms[0].action = "/plm/jsp/project/manage/ExcelDownLoad.jsp";
    document.forms[0].submit();
  }


  function excelUp() {
    var url = "/plm/jsp/project/TaskExcelUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");
  }


</script>

<style type="text/css">
<!--
body {
  margin-top: 10px;
  margin-left:5px;
  margin-right:0;

  overflow-x:hidden;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}
-->
</style>
</head>
<body onload="Complated();">
<%@include file="/jsp/common/processing.html"%>
<form method="post" name="manageProjectTaskList">
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="coid" value="<%=oid%>">
<input type="hidden" name="tcoid" value="<%=oid%>">
<input type="hidden" name="deptOid" value="<%=deptOid%>">
<input type="hidden" name="optionType" value="<%if( "Y".equals(optiontype) ){%>true<%}else{%>false<%}%>">


<table border="0" cellpadding="1" cellspacing="1" width="700">
  <tr>
    <td valign="top" style="padding:0px 0px 0px 0px">
<%
String durString = "";
if (obj instanceof E3PSTask) {
  int dur = DateUtil.getDuration(data.taskPlanStartDate, data.taskPlanEndDate) + 1;
  durString = ""+ dur;
  //Kogger.debug("durString============>>>>>>>>>>>>       "+durString);
%>
      <!-- 현재 태스크 정보 -->
      <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
                    <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "03205") %><%--현재 태스크 정보--%></td>
          <td align="right">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
      <COL width="20%"><COL width="20%"><COL width="20%"><COL width="20%"><COL width="10%"><COL width="10%">
        <tr>
                    <td class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
          <td class="tdwhiteL0" colspan="5">
            &nbsp;<INPUT id=i name=tcname style="width:99%;border:0" value="<%=task.getTaskName()%>" READONLY>
          </td>
        </tr>
        <tr>
                    <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<INPUT id=i name=tcstartdate style="border:0" value="<%=DateUtil.getDateString(data.taskPlanStartDate, "D")%>" size=10 READONLY>
          </td>
                    <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "02659") %><%--종료일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<INPUT id=i name=tcenddate style="border:0" value="<%=DateUtil.getDateString(data.taskPlanEndDate, "D")%>" size=10 READONLY>
          </td>
          <td class="tdblueL" width="80"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
          <td class="tdwhiteL0">&nbsp;<INPUT name="taskDuration" style="border:0;" value="<%=durString%>" size=10 maxlength=7 READONLY><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
          </td>
        </tr>
        <tr>
                    <td class="tdblueL" valign="top"><%=messageService.getString("e3ps.message.ket_message", "02925") %><%--태스크 설명--%></td>
          <td class="tdwhiteL0" colspan="5">
            <textarea id=i name="tdesc" rows="4" class="fm_area" style="width:99%;border:0" READONLY><%=StringUtil.checkNull(task.getTaskDesc())%></textarea>
          </td>
        </tr>
      </table>



<%
}
%>
      <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="700" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03139") %><%--하위 태스크 목록--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr><%if(auth.isLatest && auth.isFirst && !auth.isWorkingCopy && isProject){ %>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
            <a href="javascript:excelDown();">
            <img src="/plm/portal/images/iocn_excel.png" alt="excel down" name="leftbtn_02" border="0">
            </a>
            </td>
            <td width="5">&nbsp;</td>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:excelUp();" class="btn_blue">ExcelUpload</a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td><%}%>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:upDownTask('up');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02229") %><%--위로--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:upDownTask('down');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02069") %><%--아래로--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <div style="height:210px;overflow-x:hidden;overflow-y:auto;border:0;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">

      <table border="0" cellspacing="0" cellpadding="0" width="700">
      <COL width="5%"><COL width="10%"><COL width="20%">
      <COL width="15%"><COL width="15%"><COL width="5%"><COL width="10%"><COL width="10%"><COL width="10%">
        <tr>
          <td class="tdblueM">&nbsp;</td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01983") %><%--순차--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02659") %><%--종료일자--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
               <td class="tdblueM">Milestone</td>
                     <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02928") %><%--태스크 종류--%></td>
                    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "03127") %><%--필수--%></td>

        </tr>
        <tBody id="taskTable">
        </tBody>
      </table>
      </div>

      <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table width="700" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03140") %><%--하위 태스크 편집 화면--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:createTask(this);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updateTask(this);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteTask(this);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

       <table border="0" cellpadding="0" cellspacing="0" width="700">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
      <COL width="20%"><COL width="20%"><COL width="20%"><COL width="20%"><COL width="10%"><COL width="10%">

        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%>&nbsp;<span class="style1"> *</span></td>
          <td class="tdwhiteL0" colspan="5">
          <%
          if(!isDebug){
          %>
            <INPUT  name=cname class="fm_area" style="width:40%" value="<%=taskTitle %>" <%=readonly%>>
          <%}else{%>
            <select name=cname>
          <%  while(st.hasMoreElements()){
                      String taskName = st.nextToken();
          %>
              <option value="<%=taskName %>"><%=taskName %></option>
          <%
            } %>
            </select>
          <%} %>
          </td>
        </tr>

        <tr>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%>&nbsp;<span class="style1"> *</span></td>
          <td class="tdwhiteL">
            <INPUT size=10 name=cstartdate class="fm_area" style="width:60%" value="<%=startDate %>" onChange="isValidDate(this, 8);isChangeDuration3()">&nbsp;
            <a href="#" onclick="javascript:showCal2('cstartdate', 'isChangeDuration3');"><img src="/plm/portal/images/icon_6.png"border="0"></a>

          </td>
                    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02659") %><%--종료일자--%>&nbsp;<span class="style1"> *</span></td>
          <td class="tdwhiteL0">
            <INPUT size=10 name=cenddate class="fm_area" style="width:60%" value="<%=endDate %>" onChange="isValidDate(this, 8);isChangeDuration2()" >&nbsp;
            <a href="#" onclick="javascript:showCal2('cenddate', 'isChangeDuration2');"><img src="/plm/portal/images/icon_6.png"border="0"></a>

          </td>
        </tr>
             <tr>
                <td width="175" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%>&nbsp;</td>
          <td class="tdwhiteL">
            <INPUT name=duration size=10 maxlength=7 class="fm_area" style="width:60%" value="<%=duration %>" onChange="setBeforeDate(this);SetNum(this);" onkeypress="setBeforeDate(this);SetNum(this);" onkeyup="setBeforeDate(this);SetNum(this);">
          </td>
                <td width="175" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00324") %><%--Milestone여부--%></td>
                <td width="175" class="tdwhiteL0">
          &nbsp;
          <input type="radio" value="true" name="mileStone" <%if("Y".equals(milestone)) {%>checked<%}%>/>Y
          <input type="radio" value="false" name="mileStone" <%if("N".equals(milestone)) {%>checked<%}%>/>N
            </td>
            </tr>
            <tr>
                  <td width="175" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02928") %><%--태스크 종류--%></td>
              <td width="195" class="tdwhiteL">
          <select name="taskType" onchange="javascript:displayChange();" style="width:160">
                        <option value="일반" <%if("일반".equals(tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "02345") %><%--일반--%></option>
            <%
            if("mold".equals(projectType)) { %>
              <option value="Try" <%if("Try".equals(tasktype)) {%>selected<%}%>>Try</option>

            <%
            } %>
            <%
            if(!"mold".equals(projectType)) { %>
              <option value="DR" <%if("DR".equals(tasktype)) {%>selected<%}%>>DR</option>
            <%
            } %>
          </select>
          </td>
              <td width="175" class="tdblueL" id="drvalueTd1"><%if("DR".equals(tasktype)) {%><%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%><%}%></td>
              <td width="175" class="tdwhiteL0" id="drvalueTd2">
          &nbsp;
          <%if("DR".equals(tasktype)) {%>
            <INPUT id=i size=7 name=drValue value="<%=drvalue%>" onkeyup ="SetNum(this)" style='IME-MODE: disabled;'>
          <%}%></td>
            </tr>

        <tr>
                    <td class="tdblueL" valign="top"><%=messageService.getString("e3ps.message.ket_message", "02925") %><%--태스크 설명--%></td>
          <td class="tdwhiteL0" colspan="5">
            <textarea name="desc" rows="4" class="fm_area" style="width:99%" <%=readonly%>></textarea>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright_t.html" name="copyright" width="700" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
