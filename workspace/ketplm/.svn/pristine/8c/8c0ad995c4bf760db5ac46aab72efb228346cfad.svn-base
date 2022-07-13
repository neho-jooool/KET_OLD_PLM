<%@page session="true" contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.trySchdule.beans.TryResultCount"%>
<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleData"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.web.WebUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>

<%!
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat simpleformat = new SimpleDateFormat("MM-dd");
%>

<%

    String displ = request.getParameter("displ");
    if(displ != null &&  displ.length() > 0){
        displ = request.getParameter("displ");
        WebUtil.setCookie(response, "tryType", displ);
    }else{
        displ = WebUtil.getCookie(request, "tryType");
        //Kogger.debug("displ = " + displ);
        if(displ == null){
            displ = "Press";
        }
    }

    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if(popup != null && !popup.equals("")) {
        tmpPopUp = "&popup=popup";
    }

    //Kogger.debug("hhhhhhhhhhhhhhhhhhhhhhh = " + displ);

    String date = request.getParameter("date");

    Calendar targetDay = Calendar.getInstance();
    Calendar today = Calendar.getInstance();

    String todayStr = format.format(today.getTime());

    if(date!= null && date.length() > 0){
        Date selDate = format.parse(date);
        targetDay.setTime(selDate);
    }

    int weekInMonth = targetDay.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    int month = targetDay.get(Calendar.MONTH) + 1;
    int year = targetDay.get(Calendar.YEAR);

    if(targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
        weekInMonth += 1;
    }

    String title = messageService.getString("e3ps.message.ket_message", "00015", String.valueOf(year))/*{0}년*/ + " " + messageService.getString("e3ps.message.ket_message", "00039", month)/*{0}월*/ + " " + weekInMonth + messageService.getString("e3ps.message.ket_message", "02681")/*{0}주차*/;

    int day_of_week = targetDay.get(Calendar.DAY_OF_WEEK) - 1;

    targetDay.add(Calendar.DATE, -1 * day_of_week);


    Calendar temp = (Calendar)targetDay.clone();

    temp.add(Calendar.DATE, -7);


    String preWeek = format.format(temp.getTime());

    temp.add(Calendar.DATE, 14);

    String nextWeek = format.format(temp.getTime());

    //Kogger.debug("ggggkkk = " + targetDay);

    String sKey = request.getParameter("searchKey");
    String sValue = request.getParameter("searchValue");

    if(sValue == null){
        sValue = "";
    }

    QueryResult rs = null;

    if(displ.equals("Mold")){
        rs = TrySchduleHelper.getWeekTry(targetDay, 2, sKey, sValue, TrySchduleHelper.MOLD);
    }else if(displ.equals("Press")){
        rs = TrySchduleHelper.getWeekTry(targetDay, 2, sKey, sValue, TrySchduleHelper.PRESS);
    }


    //Kogger.debug("size = " + rs.size());

    Hashtable hdatas = new Hashtable();

    Timestamp startStamp = new Timestamp(targetDay.getTimeInMillis());



    temp = (Calendar)targetDay.clone();

    temp.add(Calendar.DATE, 8);

    Timestamp endStamp = new Timestamp(temp.getTimeInMillis());




    Calendar tt = (Calendar)targetDay.clone();

    tt.add(Calendar.DATE, 6);

    String term1 = simpleformat.format(startStamp) + "~" + simpleformat.format(tt.getTime());



    TryResultCount rcount1 = new TryResultCount(startStamp, endStamp);

    String startDate_str1 = DateUtil.getDateString(startStamp, "d");
    String endDate_str1 = DateUtil.getDateString(endStamp, "d");




    temp.add(Calendar.DATE, -1);



    startStamp = new Timestamp(temp.getTimeInMillis());
    temp.add(Calendar.DATE, 8);

    endStamp = new Timestamp(temp.getTimeInMillis());

    tt = (Calendar)targetDay.clone();

    tt.add(Calendar.DATE, 13);

    String term2 = simpleformat.format(startStamp) + "~" + simpleformat.format(tt.getTime());


    TryResultCount rcount2 = new TryResultCount(startStamp, endStamp);

    String startDate_str2 = DateUtil.getDateString(startStamp, "d");
    String endDate_str2 = DateUtil.getDateString(endStamp, "d");

    while(rs.hasMoreElements()){

        Object objs[] = (Object[])rs.nextElement();
        rcount1.add(objs[0]);
        rcount2.add(objs[0]);
        String keyDate = "";
        TryPlanData tplan = null;
        if(objs[0] instanceof E3PSTask){

            E3PSTask task = (E3PSTask)objs[0];
            ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();

            if(schedule.getExecEndDate() != null){
                keyDate = format.format(schedule.getExecEndDate());
            }
            else if(schedule.getExecStartDate() != null){
                keyDate = format.format(schedule.getExecStartDate());
            }else{
                keyDate = format.format(schedule.getPlanStartDate());
            }

            boolean todayBefore = todayStr.compareTo(keyDate) > 0;

            if(hdatas.containsKey(keyDate)){
                tplan = (TryPlanData)hdatas.get(keyDate);
            }else{
                tplan = new TryPlanData(todayBefore);
            }
            tplan.add(task);
            hdatas.put(keyDate, tplan);

        }else if(objs[0] instanceof TrySchdule){

            TrySchdule trySchdule = (TrySchdule)objs[0];

            keyDate = format.format(trySchdule.getPlanDate());

            boolean todayBefore = todayStr.compareTo(keyDate) > 0;

            if(hdatas.containsKey(keyDate)){
                tplan = (TryPlanData)hdatas.get(keyDate);
            }else{
                tplan = new TryPlanData(todayBefore);
            }

            tplan.add(trySchdule);
            hdatas.put(keyDate, tplan);
        }

    }

%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function moveDay(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
    location.href = "/plm/jsp/project/trySchdule/TryDaily.jsp?tagetDay=" + date + "<%=tmpPopUp %>";
}

function move(date){
    location.href = "/plm/jsp/project/trySchdule/TryDaily.jsp?tagetDay=" + date + "<%=tmpPopUp %>";
}

function moveWeek(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
    location.href = "/plm/jsp/project/trySchdule/TryWeek.jsp?date=" + date + "<%=tmpPopUp %>";
}

function moveMonth(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
    location.href = "/plm/jsp/project/trySchdule/TryCalendar.jsp?date=" + date + "<%=tmpPopUp %>";
}

function searchTry(){
    if(document.forms[0].searchKey.value == ""){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00707") %><%--검색 항목을 선택하세요--%>");
        return;
    }

    document.forms[0].submit();
}

function view(oid){
    var url="/plm/jsp/project/trySchdule/TryView.jsp?oid=" + oid;
    openOtherName(url, "", 700, 600, "status=yes,scrollbars=no,resizable=yes");
}

function showResult(count, type, action){
    if(count == 0){
        return;
    }
    var startD = "";
    var endD = "";
    if(action = 1){
        startD = document.forms[0].startDate1.value;
        endD = document.forms[0].endDate1.value;
    }else{
        startD = document.forms[0].startDate2.value;
        endD = document.forms[0].endDate2.value;
    }

    var url = "/plm/jsp/project/trySchdule/TryResultList.jsp?startDate=" + startD + "&endDate=" + endD + "&type=" + type;
    openOtherName(url, "resultPopup", 730, 570, "status=yes,scrollbars=yes,resizable=no");
}

function display(){

    document.forms[0].action = "/plm/jsp/project/trySchdule/TryWeek.jsp";
    document.forms[0].submit();
}
//-->
</script>
</head>
<body>

<form method=post action="/plm/jsp/project/trySchdule/TryWeek.jsp">
<input type=hidden name=date value='<%=date%>'>
<input type=hidden name=popup value='<%=popup%>'>

<input type=hidden name=startDate1 value='<%=startDate_str1%>'>
<input type=hidden name=endDate1 value='<%=endDate_str1%>'>

<input type=hidden name=startDate2 value='<%=startDate_str2%>'>
<input type=hidden name=endDate2 value='<%=endDate_str2%>'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="95%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%></td>
                <%if(!("popup".equals(popup))){ %>
                <td align="right">
                    <img src="/plm/portal/images/icon_navi.gif">Home
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02670") %><%--주간--%>
                </td>
                <%} %>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5"></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/plm/portal/images/tab_4.png"></td>
                <td background="/plm/portal/images/tab_6.png"><a href="#" onclick="javascript:moveDay();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02331") %><%--일간--%></a></td>
                <td><img src="/plm/portal/images/tab_5.png""></td>
              </tr>
            </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/plm/portal/images/tab_1.png"></td>
                <td background="/plm/portal/images/tab_3.png"><a href="#" onclick="javascript:moveWeek();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02670") %><%--주간--%></a></td>
                <td><img src="/plm/portal/images/tab_2.png"></td>
              </tr>
            </table></td>
          <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/plm/portal/images/tab_4.png"></td>
                <td background="/plm/portal/images/tab_6.png"><a href="#" onclick="javascript:moveMonth();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02225") %><%--월간--%></a></td>
                <td><img src="/plm/portal/images/tab_5.png""></td>
              </tr>
            </table></td>
          <td width="20">&nbsp;</td>
          <td><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="25" align="center"><a href="/plm/jsp/project/trySchdule/TryWeek.jsp?date=<%=preWeek%>&displ=<%=displ %>"><img src="/plm/portal/images/btn_arrow5.png" border="0"></a></td>
                <td width="200" align="center"><b><%=title%></b></td>
                <td width="25" align="right"><a href="/plm/jsp/project/trySchdule/TryWeek.jsp?date=<%=nextWeek%>&displ=<%=displ %>"><img src="/plm/portal/images/btn_arrow6.png" border="0"></a></td>
              </tr>
            </table></td>
          <td width="20">&nbsp;</td>
          <td width="240">&nbsp;</td>
        </tr>
      </table>
      <table width="95%" border="0" cellspacing="0" cellpadding="10" class="table_border2">
        <tr>
          <td valign="top"><table width="760" height=20" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02227") %><%--월간 Try 실적--%></td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>

                       <select name="searchKey" class="fm_jmp" style="width:100">

                            <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                            <option value="<%=MoldItemInfo.DIE_NO%>" <%=MoldItemInfo.DIE_NO.equals(sKey) ? "selected": ""%>  >Die No</option>
                            <option value="<%=MoldItemInfo.PART_NO%>" <%=MoldItemInfo.PART_NO.equals(sKey) ? "selected": ""%>>Part No</option>
                            <option value="<%=MoldItemInfo.PART_NAME%>" <%=MoldItemInfo.PART_NAME.equals(sKey) ? "selected": ""%>>Part Name</option>
                        </select>

                        &nbsp;</td>
                      <td><input type="text" name="searchValue" value="<%=sValue%>" class="txt_field"  style="width:100" id="textfield">
                        &nbsp; </td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javaScrip:searchTry();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="760">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="760">
              <tr>
                <td class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="760">
              <tr>
                <td width="165" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td colspan="2" class="tdblueM">T0</td>
                <td colspan="2" class="tdblueM">T1~N</td>
                <td width="85" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02084") %><%--양산개조--%></td>
                <td width="85" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td>
                <td width="85" rowspan="2" class="tdblueM0">Total</td>
              </tr>
              <tr>
                <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></td>
                <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></td>
                <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></td>
                <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></td>
              </tr>
              <tr>
              <td width="165" class="tdwhiteM"><%=term1%></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount1.in_t0%>, '<%=TryResultCount.IN_T0%>', 1)"><%=rcount1.in_t0%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount1.out_t0%>, '<%=TryResultCount.OUT_T0%>', 1)"><%=rcount1.out_t0%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount1.in_tN%>, '<%=TryResultCount.IN_TN%>', 1)"><%=rcount1.in_tN%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount1.out_tN%>, '<%=TryResultCount.OUT_TN%>', 1)"><%=rcount1.out_tN%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount1.mR%>, '<%=TryResultCount.MR%>', 1)"><%=rcount1.mR%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount1.etc%>, '<%=TryResultCount.ETC%>', 1)"><%=rcount1.etc%></a></td>
              <td width="85" class="tdwhiteM0"><a href="#" onclick="JavaScript:showResult(<%=rcount1.getTotal()%>, '<%=TryResultCount.TOTAL%>', 1)"><%=rcount1.getTotal()%></a></td>
              </tr>
              <tr>
              <td width="165" class="tdwhiteM"><%=term2%></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount2.in_t0%>, '<%=TryResultCount.IN_T0%>', 2)"><%=rcount2.in_t0%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount2.out_t0%>, '<%=TryResultCount.OUT_T0%>', 2)"><%=rcount2.out_t0%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount2.in_tN%>, '<%=TryResultCount.IN_TN%>', 2)"><%=rcount2.in_tN%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount2.out_tN%>, '<%=TryResultCount.OUT_TN%>', 2)"><%=rcount2.out_tN%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount2.mR%>, '<%=TryResultCount.MR%>', 2)"><%=rcount2.mR%></a></td>
              <td width="85" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount2.etc%>, '<%=TryResultCount.ETC%>', 2)"><%=rcount2.etc%></a></td>
              <td width="85" class="tdwhiteM0"><a href="#" onclick="JavaScript:showResult(<%=rcount2.getTotal()%>, '<%=TryResultCount.TOTAL%>', 2)"><%=rcount2.getTotal()%></a></td>

              </tr>
            </table>
            <table width="1050" border="0" cellspacing="0" cellpadding="5">
              <tr>
                <td>
                  <select name="displ" class="fm_jmp" style="width:80" onchange="javascript:display()">
                    <option value="Mold" <%if(displ.equals("Mold")){ %>selected<%} %>>Mold</option>
                    <option value="Press" <%if(displ.equals("Press")){ %>selected<%} %>>Press</option>
                  </select>
                </td>
                <td align="right"><img src="/plm/portal/images/icon_try1.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02159") %><%--예정--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try2.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try4.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03228") %><%--확정--%>&nbsp;<img src="/plm/portal/images/icon_try3.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>

              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="1050">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="1050">
              <tr>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 0) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 1) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 2) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 3) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 4) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 5) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 6) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
              </tr>
              <tr>
<%

   int weekMax = 0;

   for(int i = 0; i < 14; i++){


        String td = format.format(targetDay.getTime());
        String fontColor = "black";

        if (targetDay.equals(today))
        {
            //backColor = TODAY_BACK;
            //dateColor = TODAY_DATE;
        }

        if (targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            fontColor = "blue";
        }

        if(targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            fontColor = "red";
        }

        if (i != 0 && targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {

%>
            </tr>
            <tr>
       <%
               weekMax =0;

        }


        int day = targetDay.get(Calendar.DATE);


        TryPlanData tryPlanData = (TryPlanData)hdatas.get(td);



        Vector datas = new Vector();
        if(tryPlanData != null){
            datas = tryPlanData.getTotal();
        }

        if(weekMax < datas.size()){
            weekMax = datas.size();
        }

       %>


                <td width="150" valign="top" class="tdwhiteM"><table width="98%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <tdclass="day"><a href="#" onclick="javascript:move('<%=td %>');"><font color="<%=fontColor%>"><b><%=day%></b></font></a></td>
                    </tr>
                    <tr>
                      <td height="1"></td>
                    </tr>

                    <%

                    for(int d = 0; d < datas.size(); d++){

                        Object obj = datas.get(d);
                        TrySchduleData tryData = null;

                        if(obj instanceof E3PSTask){
                            tryData = new TrySchduleData((E3PSTask)obj);
                        }else if(obj instanceof TrySchdule){
                            tryData = new TrySchduleData((TrySchdule)obj);
                        }

                        String oid = tryData.oid;

                        String dieNo = "2238100";
                        String partName = "425 08 HS'Gre";
                        String tryType = "양";
                        if(tryData.dieNo != null){
                            dieNo = tryData.dieNo;
                        }
                           if(tryData.partName != null){
                               partName = tryData.partName;

                           }

                           String shortpartName = StringUtil.getLeft(partName, 6);
                           if(tryData.tryType != null){
                               tryType = tryData.tryType;
                               if(tryType.length() > 2){
                                   tryType = tryType.substring(0, 1);
                               }

                           }

                           //String fullDisplay = tryData.getDisplayShortType() + "_" + dieNo + "_" + partName;
                           //String display = tryData.getDisplayShortType() + "_" + dieNo + "_" + shortpartName;
                           String display = dieNo + "<font color='blue'> | </font>" + shortpartName;
                           String color = "black";
                           switch(tryData.state){

                            case TryPlanData.DELAY : {color = "red"; break;}
                            case TryPlanData.COMPLATED : {color = "blue"; break;}
                            case TryPlanData.TRYPLAN : {color = "black"; break;}
                            case TryPlanData.TRYNONPLAN : {color = "gray"; break;}
                        default: break;

                        }
                    %>

                    <% if(d != 0){%>
                    <tr>
                      <td></td>
                    </tr>
                    <%}%>
                    <% if(tryData.state == 2){%>
                       <tr>
                    <td style="cursor: hand"><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>
                    <td width="25"><a href="#" title="<%=tryData.tryType %>" onclick="javascript:view('<%=oid %>');" class="<%=color%>"><font color="black"><%=tryData.shortType %></font></a></td>
                    <td width="60"><a href="#" onclick="javascript:view('<%=oid %>');" class="<%=color%>"><font color='blue'>|</font><font color="black"><%=dieNo%></font></a></td>
                    <td><a href="#" title="<%=partName %>" onclick="javascript:view('<%=oid %>');" class="<%=color%>"><font color='blue'>|</font><font color="black"><%=shortpartName%></font></a></td>
                    </tr></table></td>
                    </tr>
                    <% }else{%>
                    <tr>
                    <td style="cursor: hand"><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>
                    <td width="25"><a href="#" title="<%=tryData.tryType %>" onclick="javascript:view('<%=oid %>');" class="<%=color%>"><%=tryData.shortType %></a></td>
                    <td width="60"><a href="#" onclick="javascript:view('<%=oid %>');" class="<%=color%>"><font color='blue'>|</font><%=dieNo%></a></td>
                    <td><a href="#" title="<%=partName %>" onclick="javascript:view('<%=oid %>');" class="<%=color%>"><font color='blue'>|</font><%=shortpartName%></a></td>
                    </tr></table></td>
                    </tr>
                    <%}%>
                    <%}%>

                    <%if(targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && weekMax < 12){
                         for(int k = datas.size(); k < 12; k++){
                       %>
                           <tr>
                             <td height="1"></td>
                           </tr>
                           <tr>
                            <td>&nbsp;</td>
                           </tr>
                    <%}}%>

                  </table>
                </td>

          <%

          targetDay.add(Calendar.DATE, 1);
           }%>
             </tr>


      </table></td>
  </tr>
</table>
</form>
</body>
</html>
