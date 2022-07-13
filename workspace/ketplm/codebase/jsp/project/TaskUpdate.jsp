<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="java.text.*"%>
<%@page import ="wt.fc.*"%>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.common.code.*,
                java.util.*" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<HTML>
<%!private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  %>
<%
    String oid = request.getParameter("oid");
    E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
    E3PSTaskData taskData = new E3PSTaskData(task);
    E3PSProject project = taskData.e3psProject;
    Object obj = (Object)project;
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isPMO = CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin || isPMO;
    String titlePrint = "";
    if(obj instanceof e3ps.project.MoldProject) {
        titlePrint = messageService.getString("e3ps.message.ket_message", "00997")/*금형*/;
    }else if(obj instanceof e3ps.project.ProductProject){
        titlePrint = messageService.getString("e3ps.message.ket_message", "02536")/*제품*/;
    }else{
        titlePrint = messageService.getString("e3ps.message.ket_message", "00716")/*검토*/;
    }

    String description = StringUtil.checkNull(request.getParameter("description"));
    String command = StringUtil.checkNull(request.getParameter("command"));
    String cenddate =  StringUtil.checkNull(request.getParameter("cenddate"));
    String cstartdate =  StringUtil.checkNull(request.getParameter("cstartdate"));
    String taskName =  StringUtil.checkNull(request.getParameter("taskName"));
    //String duration =  StringUtil.checkNull(request.getParameter("duration"));
    String optionType = StringUtil.checkNull(request.getParameter("optionType"));
    String mileStone = StringUtil.checkNull(request.getParameter("mileStone"));
    String taskType = StringUtil.checkNull(request.getParameter("taskType"));
    String taskTypeCategory = StringUtil.checkNull(request.getParameter("taskTypeCategory"));
    String drValue = StringUtil.checkNull(request.getParameter("drValue"));

    // [PLM System 1차개선] Project 일정 조회/변경 화면에서 오픈(T)했는지 여부, 2013-06-19, BoLee
    String isScheduleOpen = StringUtil.checkNull(request.getParameter("isScheduleOpen"));

    // 하위 태스크가 있는지 체크
    QueryResult qrChild = ProjectTaskHelper.manager.getChild(task);

    if ( command.equals("update") ) {
        task.setTaskName(taskName);
        task.setTaskDesc(description);
        if(optionType.length() > 0 ){
            task.setOptionType(Boolean.valueOf(optionType).booleanValue());
        }
        task.setMileStone(Boolean.valueOf(mileStone).booleanValue());
        task.setTaskType(taskType);
        task.setTaskTypeCategory(taskTypeCategory);
        if(drValue == null){
            drValue = "";
        }
        task.setDrValue(drValue);
//         ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
//         if(schedule!=null) {
//             Timestamp startTime = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd:HH-mm-ss" ).parse (execStartDate + ":12-59-59" ,  new ParsePosition ( 0 ) ).getTime () ) );
//             Timestamp ednTime = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd:HH-mm-ss" ).parse (execEndDate + ":12-59-59" ,  new ParsePosition ( 0 ) ).getTime () ) );
//             schedule.setExecStartDate(startTime);
//             schedule.setExecEndDate(ednTime);
//             schedule = (ExtendScheduleData) PersistenceHelper.manager.modify(schedule);
//         }

        task = (E3PSTask)TaskHelper.manager.modifyTask(task);



    /*
        if( (qrChild == null) || (qrChild.size() == 0) ) {
            Calendar tempCal = Calendar.getInstance();
            E3PSTaskData taskData2 = new E3PSTaskData(task);
            ExtendScheduleData schedule = taskData2.schedule;
            int tempDuration = DateUtil.getDaysFromTo(cenddate, cstartdate);
            schedule.setDuration(tempDuration);
            tempCal.setTime(DateUtil.parseDateStr(cstartdate));
            schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
            tempCal.setTime(DateUtil.parseDateStr(cenddate));
            schedule.setPlanEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
            schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
            ProjectScheduleHelper.manager.post_modify_Schedule(task);
        }
    */

    }
%>

<HEAD>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "00488") %><%--TASK 정보수정--%></TITLE>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<SCRIPT language=JavaScript src="/plm/portal/js/script.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<%@include file="/jsp/common/multicombo.jsp" %>

<script language="javascript">
<!--

    function clearDate(str) {
        var tartxt = document.getElementById(str);
        tartxt.value = "";
    }

    function update() {

        if(document.forms[0].taskName.value == ""){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02924") %><%--태스크 명을 입력해주세요--%>');
            return;
        }
        if(document.forms[0].cstartdate != null){
            if(document.forms[0].cstartdate.value == ""){
                alert("<%=messageService.getString("e3ps.message.ket_message", "00801") %><%--계획 시작일자를 입력해주세요--%>");
                return;
            }

            if(document.forms[0].cenddate.value == ""){
                alert("<%=messageService.getString("e3ps.message.ket_message", "00806") %><%--계획 종료일자를 입력해주세요--%>");
                return;
            }
        }
        if(document.forms[0].taskType.value == "DR") {
            var v = document.forms[0].drValue.value;
            if(!isNotNumData(v) && parseInt(v)<=100){

            } else {
                alert("<%=messageService.getString("e3ps.message.ket_message", "00008") %><%--{0} 입력값이 잘못되었습니다\n입력값은 0~100 사이어야 합니다--%>");
                document.forms[0].drValue.focus();
                return;
            }
        }

        document.forms[0].command.value = "update";
        document.forms[0].submit();
    }

    <%
        if ( command.equals("update") ) {

            // [PLM System 1차개선] Project 일정 변경 화면에서 오픈(T)한 경우에는 Task 상세조회 화면으로 이동하지 않음, 2013-06-19, BoLee
            if ( !"T".equals(isScheduleOpen) ) {
               String targetUrl = "";
//             if("Gate".equals(task.getTaskType())) {
//                 targetUrl = "/plm/jsp/project/TaskGateView.jsp?oid="+oid;
//             }else if("신뢰성평가".equals(task.getTaskType())) {
//                 targetUrl = "/plm/jsp/project/TaskTrustView.jsp?oid="+oid;
//                }else {
//                    targetUrl = "/plm/jsp/project/TaskView.jsp?oid="+oid;
//             }
               targetUrl = "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
    %>
opener.parent.document.location.href="<%=targetUrl%>"; 
// opener.goReloadPage();
    <%
            }
    %>
    self.close();
    <%
        }
    %>



    //********************************************************************
    //문자열 일괄전환 함수
    function funcReplaceStrAll(org_str, find_str, replace_str)
    {
        var pos = org_str.indexOf(find_str);
        while(pos != -1)
        {
            pre_str  = org_str.substring(0, pos);
            post_str = org_str.substring(pos + find_str.length, org_str.length);
            org_str  = pre_str + replace_str + post_str;
            pos = org_str.indexOf(find_str);
        }
        return org_str;
    }

    //*******************************************************************
    //년월 입력시 마지막 일자
    function  getEndOfMonthDay( yy, mm )
    {
        var max_days=0;
        if(mm == 1)
        {
            max_days = 31 ;
        }
        else if(mm == 2)
        {
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
    function isValidDate(obj, maxLength)
    {
    var retVal = true;
    var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
     //document.forms[0].duration.value = "";


    if(obj.value == "")
    {
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


    obj.value = yyyy+ "/" +mm+ "/" +dd;
    return true;
    }


    function viewErrMsg(obj,msg)
    {
        alert(obj.value + " " + msg);
    }

    function setBeforeDate(obj){
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
          endDate.value=year+ "/" + month + "/" + day;

      }else if(startDate_222 != "" && startDate == ""){
          var date = new Date(startDate_222.substring(0,4),Number(startDate_222.substring(5,7))-1, Number(startDate_222.substring(8,10))- value);
          var endDate_222 = document.getElementById('cstartdate');

          var year=String(date.getYear());
          var month=String(date.getMonth()+1);
          var day=String(date.getDate());

          if(month.length==1)month="0"+month;
          if(day.length==1)day="0"+day;
          endDate_222.value=year+ "/" +month+ "/" +day;
      }else if(startDate_222 != "" && startDate != ""){
          var date = new Date(  startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + value);
          var endDate = document.getElementById('cenddate');

          var year=String(date.getYear());
          var month=String(date.getMonth()+1);
          var day=String(date.getDate());

          if(month.length==1)month="0"+month;
          if(day.length==1)day="0"+day;
          endDate.value=year+ "/" + month + "/" + day;
      }
      else if(startDate_222 == "" && startDate == ""){

          alert("시작일자 및 종료일자 입력이 필요 합니다");
          obj.value = "";
          return;
      }
    }

    function isChangeDuration(obj){
        var startDate = document.getElementById("cstartdate").value;
          var endDate = document.getElementById("cenddate").value;
        var duration = document.forms[0].duration.value;

        var durationValue;

        if(startDate == ""){
            return;
        }

        if(duration == ""){
            return;
        }else{
           durationValue = parseInt(duration)-1;
        }

        var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue);
        var year=String(date.getYear());
        var month=String(date.getMonth()+1);
        var day=String(date.getDate());

        if(month.length==1)month="0"+month;
        if(day.length==1)day="0"+day;

        endDate=year+ "/" + month + "/" + day;

        document.forms[0].cenddate.value = endDate;
        document.forms[0].duration.value = duration;

    }

    function isChangeDuration2(obj){
        var startDate = document.getElementById("cenddate").value;
          var endDate = document.getElementById("cstartdate").value;
        var duration = document.forms[0].duration.value;

        var durationValue;

        if(startDate == ""){
            return;
        }

        if(duration == ""){
            return;
        }else{
           durationValue = parseInt(duration)-1;
        }

        var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) - durationValue);
        var year=String(date.getYear());
        var month=String(date.getMonth()+1);
        var day=String(date.getDate());

        if(month.length==1)month="0"+month;
        if(day.length==1)day="0"+day;

        endDate=year+ "/" + month + "/" + day;

        document.forms[0].cstartdate.value = endDate;
        document.forms[0].duration.value = duration;

    }

    function displayChange(){
        form = document.forms[0];
        var tasktypeValue = "";
        for(i=0; i<form.taskType.length; i++) {
            if(form.taskType[i].selected) tasktypeValue = form.taskType[i].value;
        }

        if(tasktypeValue == "Gate") {
            $("#taskTypeCategory").show();

        }else {
            $("#taskTypeCategory").hide();
        }
        
        if(tasktypeValue == "DR") {
            $("#drTr").show();
            drvalueTd1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%>";
            drvalueTd2.innerHTML = "<INPUT id=i size=7 name=drValue value=\"<%=(task.getDrValue()==null)?"":task.getDrValue()%>\" onkeyup =\"SetNum(this)\" style='IME-MODE: disabled'>";
        }else {
            $("#drTr").hide();
            drvalueTd1.innerHTML = "&nbsp;";
            drvalueTd2.innerHTML = "&nbsp;";
        }
    }


    $(document).ready(function(){
<%
String taskCateStrImsi = StringUtil.checkNull(task.getTaskTypeCategory());
if("DR".equals(task.getTaskType())) {
%>
    $("#drTr").show();
<%
}else {
%>
    $("#drTr").hide();
        
<%
}
%>
    });
//-->
</script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</HEAD>
<%@include file="/jsp/common/processing.html"%>
<body>
<form name="projectUpdate" method="post">
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=command >

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=titlePrint %> <%=messageService.getString("e3ps.message.ket_message", "02926") %><%--태스크 수정--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top"><table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:update();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02923") %><%--태스크 명--%></td>
                <td width="330" class="tdwhiteL0">
                <input name="taskName" type="hidden" class="txt_field" id="taskName"  style="width:100%;border:0" readOnly value="<%=task.getTaskName()%>">
                <%=task.getTaskName()%>
                </td>
              </tr>
              <%
                if( (qrChild == null) || (qrChild.size() == 0)){
              %>
                <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
                <td width="330" class="tdwhiteL0">
                  <input name="cstartdate" type="hidden" value="<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>" style="border:0" readonly>
                  <%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>
                </td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
                <td width="330" class="tdwhiteL0">
                  <input name="cenddate" type="hidden" value="<%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>" style="border:0" readonly>
                  <%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>
                </td>
              </tr>
              <%} %>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td width="330" class="tdwhiteL0">
                    <INPUT name=duration type="hidden" style="width:10%" value ="<%=taskData.taskDuration%>" style="border:0" readonly>
                    <%=taskData.taskDuration%>
                    <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%></td>
                <td width="330" class="tdwhiteL0">
                    <%
                        // [PLM System 1차개선] Project 일정 변경 화면에서 오픈(T)한 경우에는 필수여부 수정 불가, 2013-06-19, BoLee
                        if ( !isAdmin || "T".equals(isScheduleOpen)) {

                              if ( task.isOptionType() ) {
                                  out.print("Y");
                              }
                              else {
                                  out.print("N");
                              }
                        }
                        else {
                    %>
                    <select name="optionType" style="width:65" class="fm_jmp">
                        <option value="true" <% if ( task.isOptionType() ) { %>selected<% } %>>Y</option>
                        <option value="false" <% if ( !task.isOptionType() ) { %>selected<% } %>>N</option>
                    </select>
                    <%
                        }
                    %>
                </td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00324") %><%--Milestone여부--%></td>
                <td width="330" class="tdwhiteL0">
                    <%
                        // [PLM System 1차개선] Project 일정 변경 화면에서 오픈(T)한 경우에는 Milestone여부 수정 불가, 2013-06-19, BoLee
                        if ( "T".equals(isScheduleOpen) ) {

                            if ( task.isMileStone() ) {
                                out.print("Y");
                            }
                            else {
                                out.print("N");
                            }
                        }
                        else {
                    %>
                    <select name="mileStone" style="width:65" class="fm_jmp">
                        <option value="true" <% if ( task.isMileStone() ) { %>selected<% } %>>Y</option>
                        <option value="false" <% if ( !task.isMileStone() ) { %>selected<% } %>>N</option>
                    </select>
                    <%
                        }
                    %>
                </td>
              </tr>
              <tr>
                <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00502") %><%--Task종류--%></td>
                <td width="330" class="tdwhiteL0">
                    <%
                        // [PLM System 1차개선] Project 일정 변경 화면에서 오픈(T)한 경우에는 Task 종류 수정 불가, 2013-06-19, BoLee
                        if ( "T".equals(isScheduleOpen) ) {

                            out.print( task.getTaskType() == null ? "일반" : task.getTaskType() );
                        }
                        else {
                    %>
                    <select name="taskType" id="taskType" onchange="javascript:displayChange();" style="width:65" class="fm_jmp">
                        <!-- <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option> -->
                        <option value="일반" <% if ( "일반".equals(task.getTaskType()) ) { %>selected<% } %>><%=messageService.getString("e3ps.message.ket_message", "02345") %><%--일반--%></option>
                        <%
                            if ( obj instanceof e3ps.project.MoldProject ) {
                        %>
                        <option value="Try" <% if ( "Try".equals(task.getTaskType()) ) { %>selected<% } %>>Try</option>
                        <%
                            }
                            if ( obj instanceof e3ps.project.ProductProject ) {
                        %>
                        <option value="DR" <% if ( "DR".equals(task.getTaskType()) ) { %>selected<% } %>>DR</option>
                        <option value="신뢰성평가" <% if ( "신뢰성평가".equals(task.getTaskType()) ) { %>selected<% } %>>신뢰성평가</option>
                        <option value="Gate" <% if ( "Gate".equals(task.getTaskType()) ) { %>selected<% } %>>Gate</option>
                        <%
                            }
                        %>
                        <option value="디버깅" <% if ( "디버깅".equals(task.getTaskType()) ) { %>selected<% } %>><%=messageService.getString("e3ps.message.ket_message", "01339") %><%--디버깅--%></option>
                    </select>
                    <select name="taskTypeCategory" id="taskTypeCategory" 
                        style="width:65;<% if(!"Gate".equals(task.getTaskType())) out.print("display:none;"); %>" class="fm_jmp">
                        <option value="" <% if(StringUtil.isEmpty(task.getTaskTypeCategory())) out.print("selected"); %>>- <%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택 --%> -</option>
<%
Vector targetTypeVec = NumberCodeHelper.manager.getNumberCodeForSortingQuery("GATELEVELNAME");
String taskCateStr = StringUtil.checkNull(task.getTaskTypeCategory());
if (targetTypeVec != null) {
    for (int i = 0; i < targetTypeVec.size(); i++) {
        NumberCode code = (NumberCode) targetTypeVec.get(i);
        String gateTypeStr = ((String)code.getName()).replaceAll("Gate", "");
%>
                        <option value="<%=gateTypeStr %>" <% if(taskCateStr.equals(gateTypeStr)) out.print("selected"); %>><%=gateTypeStr %></option>
<%
    }
}
%>

                    </select>

                    <%
                        }
                    %>
                </td>
              </tr>
              <tr id="drTr">
                <td width="130" class="tdblueL" id="drvalueTd1">&nbsp;
                    <% if ( "DR".equals(task.getTaskType()) ) { %><%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%><% } %>
                </td>
                <td width="330" class="tdwhiteL0" id="drvalueTd2">
                    <%
                        if ( "DR".equals(task.getTaskType()) ) {
                    %>
                    <!-- [PLM System 1차개선] Project 일정 변경 화면에서 오픈(T)한 경우에는 DR 목표 값 수정 불가, 2013-06-19, BoLee -->
                    <INPUT id=i size=7 name=drValue value="<%=task.getDrValue() != null ? task.getDrValue() : ""%>" onkeyup ="SetNum(this)" style='IME-MODE: disabled' <% if ( "T".equals(isScheduleOpen) ) { %>style="border:0" readonly<% } %>>
                    <%
                        }
                        else {
                            out.print("&nbsp;");
                        }
                    %>
                </td>
              </tr>
              
              <tr>
                <td width="130" class="tdblueL" valign="middle"><%=messageService.getString("e3ps.message.ket_message", "02925") %><%--태스크 설명--%></td>
                <td width="330" class="tdwhiteL0">
                  <!-- <input type="text" name="description" class="txt_field"  style="width:100%;height:90%" id="description" value="<%=task.getTaskDesc()==null?"":task.getTaskDesc()%>"> -->
                  <textarea name="description" rows="5" style="width:100%" ><%=task.getTaskDesc()==null?"":task.getTaskDesc()%></textarea>
                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</BODY>
</HTML>
