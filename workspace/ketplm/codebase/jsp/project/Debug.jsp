<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="java.util.StringTokenizer"%>


<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<%
    // [START] [PLM System 1차개선] 일정변경 사유(디버깅 사유) 공통코드 목록 가져오기, 2013-07-15, BoLee
    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "HISTORYCHANGETYPE");
    List<Map<String, Object>> historyChangeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // [END] [PLM System 1차개선] 일정변경 사유(디버깅 사유) 공통코드 목록 가져오기, 2013-07-15, BoLee

    String making = request.getParameter("making");
    String taskNames = "";
    String oid = request.getParameter("oid");
    Kogger.debug("######oid==" + oid);

    String popup = "";
    if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
        popup = request.getParameter("popup");
    }

    int count = 0;

    int nCha = ProjectTaskHelper.getDeBugChaSh((E3PSProject)CommonUtil.getObject(oid));

    if(making.equals("사내")){
        taskNames = ConfigImpl.getInstance().getString("debgingName_in");

        //taskNames = new String[]{"제품도", "금형설계", "금형부품", "금형조립","금형 Try","검사 의뢰", "제품 측정", "제품 검토 협의"};
    }else{

        taskNames = ConfigImpl.getInstance().getString("debgingName_out");

        //taskNames = new String[]{"제품도", "외주 금형 수정", "금형 Try", "검사 의뢰", "제품 측정", "제품 검토 협의"};
    }

    StringTokenizer st = new StringTokenizer(taskNames, ";");

%>

<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01340") %><%--디버깅 차수 등록--%></title>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<script language="JavaScript" src="/plm/portal/js/Calendar.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script type="text/javascript">

function dvTab(i){
  document.getElementById('tr' + i).style.display = 'none';
  document.getElementById('img' + i).style.display = 'none';
  document.getElementById('start' + i).value = "";
  document.getElementById('end' + i).value = "";

  for(j = 0; j < <%=st.countTokens()%>; j++){
      if(document.getElementById('tr' + j).style.display != 'none'){
          document.getElementById('img' + j).style.display = '';
          document.getElementById('space' + j).style.display = 'none';
          break;
      }
  }

  duration();
}

function reset(){
    for(j=0; j < <%=st.countTokens()%>; j++){
        document.getElementById('tr' + j).style.display = '';
    }
    document.getElementById('img0').style.display = '';
    document.getElementById('space0').style.display = 'none';
    for(j=1; j < <%=st.countTokens()%>; j++){
        document.getElementById('img' + j).style.display = 'none';
        document.getElementById('space' + j).style.display = '';
    }
    document.forms[0].reset();
}

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
function onSave(){
    if ( document.forms[0].reason.value == "" ) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01683") %><%--사유를 입력하세요--%>");
        return;
    }
    else if ( document.forms[0].special.value == "" ) {
        alert("<%=messageService.getString("e3ps.message.ket_message", "03398") %><%--특이사항을 입력하세요.--%>");
        return;
    }
    else if(checkDate()){
        if(confirm(<%=nCha%> + "<%=messageService.getString("e3ps.message.ket_message", "03385") %><%--{0}차 디버깅을 생성하시겠습니까?--%>")){

        	showProcessing();

            document.forms[0].action="/plm/jsp/project/DebugSave.jsp";
            document.forms[0].submit();
        }

    }
}
function checkDate() {
    if(document.getElementById('reason').value == ""){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01683") %><%--사유를 입력하세요--%>');
        return false;
    }

    for(i=0; i < <%=st.countTokens()%>; i++){
        var cstartdate = document.getElementById('start' + i).value;
        var cenddate = document.getElementById('end' + i).value;

        if(document.getElementById('tr' + i).style.display == ''){
            if(cstartdate.length == 0 || cenddate.length == 0){
                alert('<%=messageService.getString("e3ps.message.ket_message", "02347") %><%--일자를 입력해주세요--%>');
                return false;
            }
        }else{
            cstartdate = "";
            cenddate = "";
        }
        if(cstartdate.length > 0 && cenddate.length > 0) {
            if(cstartdate > cenddate) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02660") %><%--종료일자가 시작일자보다 늦습니다 다시 입력해 주시기 바랍니다--%>');
                document.getElementById('start'+i).value = "";
                document.getElementById('end'+i).value = "";
                return false
            }
        }
    }
    return true;
}

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


function changeDate(num){

    var startDate = document.getElementById("start" + num).value;
     var endDate = document.getElementById("end" + num).value;
     var duration = document.getElementById("duration" + num).value;

     var durationValue;

     // 변경 Task의 기간 구하기
     if(startDate != ""){
         var startCal = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)));
         var endCal = new Date(endDate.substring(0,4), Number(endDate.substring(5,7))-1, Number(endDate.substring(8,10)));

         var interval = endCal - startCal;
         var day = 1000*60*60*24;

         var value = (interval / day) + 1;

         if(value <= 0){

             document.getElementById("duration" + num).value = "";
             document.getElementById("end" + num).value = "";
             document.getElementById("end" + num).focus();
             alert("계획 시작일보다 이전 날짜를 입력할 수 없습니다.");
             return;
         }

         document.getElementById("duration" + num).value = value;

     }else{
        return;
     }

     for(i=0; i < <%=st.countTokens()%>; i++){
        if(document.getElementById('tr' + i).style.display != ''){
            continue;
        }

        var startDate = document.getElementById("start" + i).value;
          var endDate = document.getElementById("end" + i).value;
        var duration = document.getElementById("duration" + i).value;
        var durationValue;
        if(startDate == "") {
            return;
        }

        if(duration == "") {
            return;
        }else if(duration == 0){
            document.getElementById("duration" + i).focus();
            alert("기간에 0을 입력 할 수 없습니다.");
            return;
        } else {
           durationValue = parseInt(duration)-1;
        }

        var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue);
        var year=String(date.getFullYear());
        var month=String(date.getMonth()+1);
        var day=String(date.getDate());

        if(month.length==1)month="0"+month;
        if(day.length==1)day="0"+day;

        endDate = year+ "-" + month + "-" + day;

        document.getElementById("end" + i).value = endDate;
        document.getElementById("duration" + i).value = duration;


        var nextDate = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue + 1);
        var nextYear=String(nextDate.getFullYear());
        var nextMonth=String(nextDate.getMonth()+1);
        var nextDay=String(nextDate.getDate());

        if(nextMonth.length==1)nextMonth="0"+nextMonth;
        if(nextDay.length==1)nextDay="0"+nextDay;

        if(i < (<%=st.countTokens()%> - 1) && document.getElementById('tr' + (i + 1)).style.display != ''){
            for(j = i + 1; j < <%=st.countTokens()%>; j++){
                if(document.getElementById('tr' + j).style.display == ''){
                    i = j - 1;
                    break;
                }
            }
        }

        nextStartDate = nextYear+ "-" + nextMonth + "-" + nextDay;
        if(i < (<%=st.countTokens()%>-1)){
            document.getElementById("start" + (i + 1)).value = nextStartDate;
        }
    }

}


function duration(){

    for(i=0; i < <%=st.countTokens()%>; i++){
        if(document.getElementById('tr' + i).style.display != ''){
            continue;
        }

        var startDate = document.getElementById("start" + i).value;
         var endDate = document.getElementById("end" + i).value;
        var duration = document.getElementById("duration" + i).value;
        var durationValue;
        if(startDate == "") {
            return;
        }

        if(duration == "") {
            return;
        }else if(duration == 0){
            document.getElementById("duration" + i).focus();
            alert("기간에 0을 입력 할 수 없습니다.");
            return;
        } else {
           durationValue = parseInt(duration)-1;
        }

        var date = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue);
        var year=String(date.getFullYear());
        var month=String(date.getMonth()+1);
        var day=String(date.getDate());

        if(month.length==1)month="0"+month;
        if(day.length==1)day="0"+day;

        endDate = year+ "-" + month + "-" + day;

        document.getElementById("end" + i).value = endDate;
        document.getElementById("duration" + i).value = duration;


        var nextDate = new Date(startDate.substring(0,4), Number(startDate.substring(5,7))-1, Number(startDate.substring(8,10)) + durationValue + 1);
        var nextYear=String(nextDate.getFullYear());
        var nextMonth=String(nextDate.getMonth()+1);
        var nextDay=String(nextDate.getDate());

        if(nextMonth.length==1)nextMonth="0"+nextMonth;
        if(nextDay.length==1)nextDay="0"+nextDay;

        if(i < (<%=st.countTokens()%> - 1) && document.getElementById('tr' + (i + 1)).style.display != ''){
            for(j = i + 1; j < <%=st.countTokens()%>; j++){
                if(document.getElementById('tr' + j).style.display == ''){
                    i = j - 1;
                    break;
                }
            }
        }

        nextStartDate = nextYear+ "-" + nextMonth + "-" + nextDay;
        if(i < (<%=st.countTokens()%>-1)){
            document.getElementById("start" + (i + 1)).value = nextStartDate;
        }
    }

}
$(document).ready(function(){
	<%
	for(int i=0;i < st.countTokens();i++){
    %>
        // Calener field 설정
        CalendarUtil.dateInputFormat('start<%=i%>');
        CalendarUtil.dateInputFormat('end<%=i%>', function(){changeDate(<%=i%>)});
    <%
	}
    %>
});
</script>

</head>
<body>
    <form>
        <input type="hidden" name="oid" value="<%=oid%>" /> <input type="hidden" name="popup" value="<%=popup%>"></input>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01">디버깅 차수(<%=making%>)등록
                                        </td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="center"><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><%--저장--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td width="5">&nbsp;</td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:reset();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><%--취소--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                        <td width="5">&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <td><table border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                        </tr>
                                                    </table></td>
                                            </table></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td valign="top"><table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="100" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01682")%><%--사유--%></td>
                                        <td width="320" class="tdwhiteL0"><select id="reason" name="reason" class="fm_jmp" style="width: 50%">
                                                <!-- [START] [PLM System 1차개선] 일정변경 사유(디버깅 사유) 공통코드 목록 가져오기, 2013-07-15, BoLee -->
                                                <option value=""></option>
                                                <%
                                                    for (int i = 0; i < historyChangeNumCode.size(); i++) {
                                                		if (!historyChangeNumCode.get(i).get("code").toString().equals("0")) {
                                                %>
                                                <option value="<%=historyChangeNumCode.get(i).get("code")%>"><%=historyChangeNumCode.get(i).get("value")%></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                                <!-- [END] [PLM System 1차개선] 일정변경 사유(디버깅 사유) 공통코드 목록 가져오기, 2013-07-15, BoLee -->
                                        </select></td>
                                    </tr>
                                    <tr>
                                        <td width="100" style="height: 80" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03350")%><%--특이사항--%>
                                            <span class="red">*</span></td>
                                        <td width="320" style="height: 80" class="tdwhiteL0"><textarea name="special" class="txt_field" id="textfield12" style="width: 95%; height: 96%"></textarea></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                                    <tr>
                                        <td width="200px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02920")%><%--태스크--%></td>
                                        <td width="120px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817")%><%--계획{0}시작일--%></td>
                                        <td width="120px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                                        <td width="70px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                                        <td width="40px" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                                    </tr>
                                    <%
                                      int i = 0;
                                      while(st.hasMoreElements()){
                                          String taskName = st.nextToken();
                                      %>
                                    <tr id="tr<%=i%>">
                                        <td class="tdwhiteR text-center"><%=taskName%><input type="hidden" name="taskNames" value="<%=taskName%>" /></td>
                                        <td class="tdwhiteL"><input type="text" id="start<%=i %>" name="start<%=i %>" class="txt_field" style="width: 70px"></td>
                                        <td class="tdwhiteM"><input type="text" id="end<%=i %>" name="end<%=i %>" class="txt_field" style="width: 70px" onchange="changeDate(<%=i %>)"></td>
                                        <td class="tdwhiteM"><input type="text" name="duration<%=i %>" class="txt_field" style="width: 40px" id="duration<%=i %>" onChange="javascript:duration()"></input><%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
                                        <td class="tdwhiteM0"><a href="javascript:dvTab(<%=i++ %>);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                                    </tr>
                                    <%
                                     }
                                     %>
                                </table></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
