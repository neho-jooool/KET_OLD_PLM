<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.*"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<!-- %@page import="e3ps.dailyManagement.bean.DailyUtil"%-->

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

    String tagetDay = "";//request.getParameter("tagetDay");
    if(request.getParameter("tagetDay") != null){
        tagetDay = request.getParameter("tagetDay");
    }

    String popup = request.getParameter("popup");
    String tmpPopUp = "";
    if(popup != null && !popup.equals("")) {
        tmpPopUp = "&popup=popup";
    }

    Calendar gCal = Calendar.getInstance();
    //Kogger.debug("tagetDay =" + tagetDay);
    //Kogger.debug("tagetDay.length ="+tagetDay.length());

    if(tagetDay !=null && tagetDay.length()>0){
        //Kogger.debug("1111");
        //Kogger.debug("aaaaaaaaaaa");
         Timestamp startDay=DateUtil.convertStartDate2(tagetDay);
         gCal.setTime(startDay);

    }else{
        //Kogger.debug("bbbbbbbbbbbbbbb");
         gCal.setTime(new Date());
    }

    //out.println(DateUtil.getToDay());



    int year = gCal.get(Calendar.YEAR);
    int month = gCal.get(Calendar.MONTH)+1;


    int beforeYear = year - 1;
    int afterYear = year + 1;
    int beforemonth = month - 1;

    int aftermonth = month+1;

    String day_mon = "01";//gCal.get(Calendar.DAY_OF_MONTH);
    //Kogger.debug("@@@@ day = " + day_mon);
    String todate = DateUtil.getToDay("yyyy-MM-dd");

    if(tagetDay != null && tagetDay.length() > 0){
        todate = tagetDay;
    }else{
        tagetDay = todate;
    }

    String beforedate = year + "-" + beforemonth + "-" + day_mon;

    if(beforemonth == 0){
        beforedate = (year - 1) + "-" + "12" + "-" + day_mon;
    }else if(beforemonth < 10){
        beforedate = year + "-" + "0" + beforemonth + "-" + day_mon;
    }

    String afterdate= year + "-" + aftermonth + "-" + day_mon;

    if(aftermonth == 13){
        afterdate= (year + 1) + "-" + "1" + "-" + day_mon;
    }else if(aftermonth < 10){
        afterdate= year + "-" + "0" + aftermonth + "-" + day_mon;
    }

    String before = "";
    String after = "";
    if(month < 10){
        before = beforeYear + "-" + "0" + month + "-" + day_mon;
        after = afterYear + "-" + "0" + month + "-" + day_mon;;
    }else{
        before = beforeYear + "-" + month + "-" + day_mon;
        after = afterYear + "-" + month + "-" + day_mon;;
    }


//	WeekProcess intwp = DailyUtil.checkWeekProcess(todate);
    String initoid ="";
//	if(intwp !=null){
//		initoid = CommonUtil.getOIDString(intwp);
//	}


%>
<!-- %@page import="e3ps.dailyManagement.WeekProcess"%-->
<%@page import="java.sql.Timestamp"%>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<title>달력 </title>
<style>
 .num1{background-color:aaa#FFCCFF;width:15px;height:15px;text-align:center}
 .num2{background-color:#66FF33;width:15px;height:15px;text-align:center}

</style>
<script>


//parent.document.daily.location.href="/plm/jsp/project/userDaily.jsp?tagetDay=<%=todate%>&oid=<%=initoid%>";

function createDaily(date){
    //alert(date); //본문페이지 이동
    parent.contName.location.href="/plm/jsp/project/trySchdule/TryDaily.jsp?tagetDay=" + date + "<%=tmpPopUp %>";
    location.href="/plm/jsp/project/trySchdule/Calendar.jsp?tagetDay=" + date + "<%=tmpPopUp %>";
}

function moveMonth(date){
    //parent.daily.location.href="/plm/jsp/project/userDaily.jsp?tagetDay="+date
    location.href="/plm/jsp/project/trySchdule/Calendar.jsp?tagetDay=" + date + "<%=tmpPopUp %>";

}

function moveYear(date){
    //parent.daily.location.href="/plm/jsp/project/userDaily.jsp?tagetDay="+date
    location.href="/plm/jsp/project/trySchdule/Calendar.jsp?tagetDay=" + date + "<%=tmpPopUp %>";

}

function changeMonthHandler(num)
{
        if((eval(global_month) + eval(num)) < 1) {
            global_month = 12;
            changeYearHandler(num);
        } else if((eval(global_month) + eval(num)) > 12) {
            global_month = 1;
            changeYearHandler(num);
        } else {
            document.forms[0].checkParentDate.value = "false";
            document.forms[0].year.value = <%=year%>;
            document.forms[0].month.value = eval(global_month) + eval(num);
            document.forms[0].submit();
        }
}
</script>
<input type="hidden" name="tagetDay" value="<%=tagetDay %>"></input>
<table border="0" cellspacing="0" cellpadding="0" width="200" height="100%" background="/plm/portal/images/submenu_bg.png" style="background-repeat: no-repeat;">
    <tr>
        <td width="190" valign="top" align="center">
            <table border="0" cellspacing="0" cellpadding="0" width="170">
                <tr>
                    <td height="25">&nbsp;</td>
                </tr>
                <tr >
                    <td align="center" colspan="7"> <span style="cursor:hand" onclick="moveYear('<%=before %>')">◀</span> <%=messageService.getString("e3ps.message.ket_message", "00015", String.valueOf(year)) %><%--{0}년--%> <span style="cursor:hand" onclick="moveYear('<%=after %>')"> ▶</span><span style="cursor:hand" onclick="moveMonth('<%=beforedate %>')">◀</span><%=month %><%=messageService.getString("e3ps.message.ket_message", "02224") %><%--월--%><span style="cursor:hand" onclick="moveMonth('<%=afterdate %>')"> ▶</span></td>
                </tr>
                <tr>
<%
String[] weekDay = new String[]{messageService.getString("e3ps.message.ket_message", "03396", 0) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/,
                                messageService.getString("e3ps.message.ket_message", "03396", 1) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/,
                                messageService.getString("e3ps.message.ket_message", "03396", 2) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/,
                                messageService.getString("e3ps.message.ket_message", "03396", 3) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/,
                                messageService.getString("e3ps.message.ket_message", "03396", 4) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/,
                                messageService.getString("e3ps.message.ket_message", "03396", 5) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/,
                                messageService.getString("e3ps.message.ket_message", "03396", 6) /*{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}*/};
for( int j=0; j<7;j++){%>
        <td class="tdblueM"><%=weekDay[j]%></td>
<% }%>
                </tr>
                <tr>
<%
Object [][]cal = getTable(year,month);

for( int i = 0; i < cal.length; i++){
    for( int j = 0; j<cal[i].length; j++){
        boolean flag1 = false;

        String flag1color = ""; // 미작성 #pink
        String fontcolor = "black";
        String day = (String)cal[i][j];

        String date = "";

        String printDay = day;
        if(day.length() == 1){
            printDay = "0" + day;
        }

        if(month < 10){
            date = year + "-" + "0" + month + "-" + printDay ;
        }else{
            date = year + "-" + month + "-" + printDay ;
        }
        //Kogger.debug("date == " + date);
        if(j == 0){
            fontcolor = "red";
        }else if(j == 6){
            fontcolor = "blue";
        }

        if(day.length() > 0){
            if(tagetDay.equals(date)){
                flag1color = "#3d94f5";
            }
        %>
        <td !class="tdwhiteM" align="center" bgcolor="<%=flag1color%>">
        <span style="cursor:hand" onclick="createDaily('<%=date%>')"><font color="<%=fontcolor %>"><%=day %></font></span>
        <% }else{%>
        <td !class="tdwhiteL" >
        <%} %>
        </td>

<%}%>
        </tr><tr>
<% }%>
    </tr>

    </table>
    </td>
    <td width="1" bgcolor="#c6c6c6"></td>
    <td width="9" valign="top">&nbsp;</td></tr>
</table>
<%!


public static Object[][] getTable(int year, int month)
{
    Calendar cal = Calendar.getInstance();
    cal.set(year, month - 1, 1);

    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    int firstDay = cal.get(Calendar.DAY_OF_WEEK);
    Object temp[][] = new Object[6][7];
    int daycount = 1;
    for (int i = 0; i < 6; i++)
    {
        for (int j = 0; j < 7; j++)
        {
            if (firstDay - 1 > 0 || daycount > lastDay)
            {
                temp[i][j] = "";
                firstDay--;
                continue;
            }
            else
            {
                temp[i][j] = String.valueOf( daycount );
            }
            daycount++;
        }
    }
    return temp;
}


%>
