<%@page session="true" contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.*"%>
<%@page import="java.util.Calendar,java.util.*,java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page  import="wt.query.*,wt.fc.*,wt.org.*,e3ps.groupware.company.*,wt.org.*,wt.session.*,java.net.*"%>
<%@page import="e3ps.project.trySchdule.beans.TrySchduleHelper"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.trySchdule.TrySchdule"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.project.trySchdule.beans.TryPlanData"%>
<%@page import="e3ps.project.trySchdule.beans.TryResultCount"%>
<%@page import="e3ps.project.MoldItemInfo"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean"  scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<%
String popup = request.getParameter("popup");
String tmpPopUp = "";
if(popup != null && !popup.equals("")) {
    tmpPopUp = "&popup=popup";
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/jquery-1.3.2.min.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<!-- EJS TreeGrid Start -->
<%@include file="/jsp/common/multicombo.jsp" %>
<script type="text/javascript">

<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function goCalendar(pp){

    document.forms[0].date.value = pp;
    document.forms[0].submit();
}

function selectDate(){
    var y = document.forms[0].year.value;
    var n = document.forms[0].month.value;
    goCalendar( y + "-" + n + "-01");
}

//var $j=jQuery.noConflict();

function selectDay(oEvent, a, state, type){

    var oEvnetObj = oEvent.srcElement;
    var oEvnetObjName = oEvnetObj.name;


    var rectObj = oEvnetObj.getBoundingClientRect();


//      alert('1:'+rectObj+',a:'+a+', state:'+state+', type:'+type);
    //offLayer('layer0');

    doUnfoldingLayer(oEvnetObj, 300, 200,'layer0');

    var param = "";

    param = "state=" + state;
    param += "&date=" + a;
    param += "&type=" + type;


    var url = "/plm/jsp/project/trySchdule/TryListAjaxAction.jsp";

    /* $.post(url,param, function(xml) {
        setTryList(xml);
    }); */
    $.ajax({
    	url : url,
    	type : 'post',
    	data : param,
    	dataType : 'xml',
    	success : setTryList
    });

}

window.getTagText = function(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function setTryList(xmlDoc){

    var showElements = $(xmlDoc).find("data_info");
    var l_code = $(showElements).eq(0).find("l_code");
    var l_name = $(showElements).eq(0).find("l_name");
    var l_tryType = $(showElements).eq(0).find("l_tryType");
    var l_dieNo = $(showElements).eq(0).find("l_dieNo");
    var l_shortPart = $(showElements).eq(0).find("l_shortPart");
    var l_color = $(showElements).eq(0).find("l_color");
    var htmlStr = "";
    //alert(l_code);

    htmlStr += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td width='15%' class=\"tdblueM\">Type</td><td width='25%' class=\"tdblueM\">Die No</td><td class=\"tdblueM\">Part Name</td></tr>";
    if(l_code != null && l_code.length > 0) {
        for(var i = 0; i < l_code.length; i++) {
            r_code = unescape(getTagText(l_code[i]));
            r_name = decodeURIComponent(getTagText(l_name[i]));
            if(r_name == null || r_name == ""){
                r_name = "&nbsp;";
            }
            r_tryType = decodeURIComponent(getTagText(l_tryType[i]));
            r_dieNo = decodeURIComponent(getTagText(l_dieNo[i]));
            r_shortPart = decodeURIComponent(getTagText(l_shortPart[i]));
            if(r_shortPart == null || r_shortPart == ""){
                r_shortPart = "&nbsp;";
            }
            r_color = unescape(getTagText(l_color[i]));
            htmlStr += "<tr onmouseover=\"this.style.backgroundColor='#3d94f5'\" onmouseout=\"this.style.backgroundColor=''\" style=\"cursor:hand\" onclick=javascript:showTry('" + r_code + "')>"+ "<td width=\"15%\" title=\"" + r_tryType + "\" class=\"tdblankL text-center\"><font size='2' color='" + r_color + "'>" + r_name + "</font></td><td width='25%' class=\"tdblankL\"><font size='2' color='" + r_color + "'>" + r_dieNo + "</font></td><td class=\"tdblankL\"><font size='2' color='" + r_color + "'>" + r_shortPart + "</font></td>";
            //htmlStr += "<a href=javascript:showTry('" + r_code + "')>";
            //htmlStr += "<li><font size='3' color='" + r_color + "'>" + r_name + "</font></li></a>";

            htmlStr += "</tr>";
        }
    }

    htmlStr += "</table>";
    $('#layer0content').html('');
    //onLayerClear("layer0content");

    var layerContent = document.getElementById("layer0content");
    layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";
}

function showTry(oid){
    var url="/plm/jsp/project/trySchdule/TryView.jsp?oid=" + oid;
    openOtherName(url, "", 700, 600, "status=yes,scrollbars=no,resizable=yes");
}

function searchTry(){
    if(document.forms[0].searchKey.value == ""){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00707") %><%--검색 항목을 선택하세요--%>");
        return;
    }

    document.forms[0].submit();
}


function moveDay(){
    //var date = parent.bottomLeftFrame.tagetDay.value;
    var date = parent.bottomLeftFrame.document.getElementsByName("tagetDay")[0].value;
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

function showResult(count, type){
    if(count == 0){
        return;
    }
    var startD = document.forms[0].startDate.value;
    var endD = document.forms[0].endDate.value;
    var url = "/plm/jsp/project/trySchdule/TryResultList.jsp?startDate=" + startD + "&endDate=" + endD + "&type=" + type;
    openOtherName(url, "resultPopup", 730, 570, "status=yes,scrollbars=yes,resizable=no");
}


function display(){

    document.forms[0].action = "/plm/jsp/project/trySchdule/TryCalendar.jsp";
    document.forms[0].submit();
}

//-->
</script>
</head>

<%!
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //SimpleDateFormat simpleformat = new SimpleDateFormat("MM/dd");%>

<%
    Calendar targetDay = Calendar.getInstance();
    Calendar today = Calendar.getInstance();

    String todayStr = format.format(today.getTime());

    String date = request.getParameter("date");

    if (date == null) {
        date = "";
    }

    if (date != null && date.length() > 0) {
        Date selDate = format.parse(date);
        targetDay.setTime(selDate);
    }

    int year = targetDay.get(Calendar.YEAR);
    int month = targetDay.get(Calendar.MONTH);

    Calendar calendar = Calendar.getInstance();

    calendar.set(year, month, 1);

    String todayString = format.format(calendar.getTime());

    String month_str = String.valueOf(month + 1);

    if (month_str.length() < 2) {
        month_str = "0" + month_str;
    }

    String startDate = month_str + "-" + "01";

    String endDate = month_str + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    String back = "";
    String next = "";
    calendar.add(Calendar.MONTH, -1);
    back = format.format(calendar.getTime());
    calendar.add(Calendar.MONTH, 2);
    next = format.format(calendar.getTime());
    calendar.add(Calendar.MONTH, -1);

    String sKey = request.getParameter("searchKey");
    String sValue = request.getParameter("searchValue");

    if (sValue == null) {
        sValue = "";
    }
    Kogger.debug("skey = " + sKey + "sValue = " + sValue);

    QueryResult rs = TrySchduleHelper.getTotalTry(year, month + 1, sKey, sValue);

    //Kogger.debug("size = " + rs.size());
    Hashtable hdatas = new Hashtable();
    TryResultCount rcount = new TryResultCount();
    while (rs.hasMoreElements()) {

        Object objs[] = (Object[]) rs.nextElement();
        rcount.add(objs[0]);

        String keyDate = "";
        TryPlanData tplan = null;

        if (objs[0] instanceof E3PSTask) {

            E3PSTask task = (E3PSTask) objs[0];
            ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
            boolean isExecStartDateExist = false;

            if (schedule.getExecEndDate() != null) {
                keyDate = format.format(schedule.getExecEndDate());

            }else if (schedule.getExecStartDate() != null) {
                isExecStartDateExist = true;
                keyDate = format.format(schedule.getExecStartDate());
            } else {
                keyDate = format.format(schedule.getPlanStartDate());
            }

            boolean todayBefore = todayStr.compareTo(keyDate) > 0;

            if (hdatas.containsKey(keyDate)) {
                tplan = (TryPlanData) hdatas.get(keyDate);
            } else {
                tplan = new TryPlanData(todayBefore);
            }
            tplan.add(task);
            hdatas.put(keyDate, tplan);

        } else if (objs[0] instanceof TrySchdule) {

            TrySchdule trySchdule = (TrySchdule) objs[0];

            keyDate = format.format(trySchdule.getPlanDate());

            boolean todayBefore = todayStr.compareTo(keyDate) > 0;

            if (hdatas.containsKey(keyDate)) {
                tplan = (TryPlanData) hdatas.get(keyDate);
            } else {
                tplan = new TryPlanData(todayBefore);
            }

            tplan.add(trySchdule);
            hdatas.put(keyDate, tplan);
        }

    }


    String startDate_str = year + "-" + startDate;
    String endMonth = String.valueOf(month + 1);

    if(endMonth.length() < 2){
        endMonth = "0" + endMonth;
    }


    //Timestamp stamp = DateUtil.getTimestampFormat(startDate_str, "yyyy/MM/dd");

%>

<body>

<form method=post action="/plm/jsp/project/trySchdule/TryCalendar.jsp">

<input type=hidden name=startDate value='<%=startDate_str%>'>
<input type=hidden name=endDate value='<%=next%>'>
<input type=hidden name=date value='<%=date%>'>
<input type=hidden name=popup value='<%=popup%>'>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top"><table width="95%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%></td>
                        <%if(!("popup".equals(popup))){ %>
                        <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02225") %><%--월간--%></td>
                        <%} %>
                    </tr>
                </table>
                </td>
            </tr>
            <tr>
                <td class="head_line"></td>
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
                        <td background="/plm/portal/images/tab_6.png"><a href="#" onclick="javascript:moveDay();"  class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02331") %><%--일간--%></a></td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                </table>
                </td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="/plm/portal/images/tab_4.png"></td>
                        <td background="/plm/portal/images/tab_6.png"><a href="#" onclick="javascript:moveWeek();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02670") %><%--주간--%></a></td>
                        <td><img src="/plm/portal/images/tab_5.png""></td>
                    </tr>
                </table>
                </td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="/plm/portal/images/tab_1.png"></td>
                        <td background="/plm/portal/images/tab_3.png"><a href="#" onclick="javascript:moveMonth();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02225") %><%--월간--%></a></td>
                        <td><img src="/plm/portal/images/tab_2.png"></td>
                    </tr>
                </table>
                </td>
                <td width="20">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="25" align="center"><a href="#" onclick="JavaScript:goCalendar('<%=back%>')"><img  src="/plm/portal/images/btn_arrow5.png" border="0"></a></td>
                        <td width="200" align="center"><b> <select name=year onchange=selectDate()>
                            <%
                                for (int i = 2002; i < 2020; i++) {
                            %>
                            <option value='<%=i%>' <%=year == i ? "selected" : ""%>><%=i%>
                            <%
                                }
                            %>

                        </select><%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%> <select name=month onchange=selectDate()>
                            <%
                                for (int i = 1; i < 13; i++) {
                                    String mv = Integer.toString(i);
                                    if (i < 10) {
                                        mv = "0" + mv;
                                    }
                            %>
                            <option value='<%=mv%>' <%=(month + 1) == i ? "selected" : ""%>><%=i%>
                            <%
                                }
                            %>

                        </select><%=messageService.getString("e3ps.message.ket_message", "02224") %><%--월--%> </b></td>
                        <td width="25" align="right"><a  href="#" onclick="JavaScript:goCalendar('<%=next%>')"><img src="/plm/portal/images/btn_arrow6.png" border="0"></a></td>
                    </tr>
                </table>
                </td>
                <td width="20">&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
        <table width="95%" border="0" cellspacing="0" cellpadding="10" class="table_border2">
            <tr>
                <td valign="top"><table width="760" height=20 " border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02227") %><%--월간 Try 실적--%></td>
                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><select name="searchKey" class="fm_jmp"  style="width: 100">
                                    <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                                    <option value="<%=MoldItemInfo.DIE_NO%>" <%=MoldItemInfo.DIE_NO.equals(sKey) ? "selected" : ""%>>Die No</option>
                                    <option value="<%=MoldItemInfo.PART_NO%>" <%=MoldItemInfo.PART_NO.equals(sKey) ? "selected" : ""%>>Part No</option>
                                    <option value="<%=MoldItemInfo.PART_NAME%>" <%=MoldItemInfo.PART_NAME.equals(sKey) ? "selected" : ""%>>Part Name</option>
                                </select> &nbsp;</td>
                                <td><input type="text" name="searchValue" value="<%=sValue%>" class="txt_field" style="width: 100" id="textfield"> &nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javaScrip:searchTry();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                                </td>
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
                        <td width="235" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                        <td colspan="2" class="tdblueM">T0</td>
                        <td colspan="2" class="tdblueM">T1~N</td>
                        <td width="75" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02084") %><%--양산개조--%></td>
                        <td width="75" rowspan="2" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td>
                        <td width="75" rowspan="2" class="tdblueM0">Total</td>
                    </tr>
                    <tr>
                        <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></td>
                        <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></td>
                        <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%></td>
                        <td width="75" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%></td>
                    </tr>

                    <tr>
                        <td width="235" class="tdwhiteM"><%=startDate%>~<%=endDate%></td>
                        <td width="75" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount.in_t0%>, '<%=TryResultCount.IN_T0%>')"><%=rcount.in_t0%></a></td>
                        <td width="75" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount.out_t0%>, '<%=TryResultCount.OUT_T0%>')"><%=rcount.out_t0%></a></td>
                        <td width="75" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount.in_tN%>, '<%=TryResultCount.IN_TN%>')"><%=rcount.in_tN%></a></td>
                        <td width="75" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount.out_tN%>, '<%=TryResultCount.OUT_TN%>')"><%=rcount.out_tN%></a></td>
                        <td width="75" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount.mR%>, '<%=TryResultCount.MR%>')"><%=rcount.mR%></a></td>
                        <td width="75" class="tdwhiteM"><a href="#" onclick="JavaScript:showResult(<%=rcount.etc%>, '<%=TryResultCount.ETC%>')"><%=rcount.etc%></a></td>
                        <td width="75" class="tdwhiteM0"><a href="#" onclick="JavaScript:showResult(<%=rcount.getTotal()%>, '<%=TryResultCount.TOTAL%>')"><%=rcount.getTotal()%></a></td>
                    </tr>

                </table>
                <table width="760" border="0" cellspacing="0" cellpadding="5">
                    <tr>
                        <td align="right" ><img src="/plm/portal/images/icon_try1.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02159") %><%--예정--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try2.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try4.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "03228") %><%--확정--%>&nbsp;&nbsp;<img src="/plm/portal/images/icon_try3.gif" width="15" height="15" align="absmiddle">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>

                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="760">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" width="760">
                    <tr>
                        <td width="112" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 0) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                        <td width="108" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 1) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                        <td width="108" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 2) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                        <td width="108" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 3) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                        <td width="108" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 4) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                        <td width="108" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 5) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                        <td width="108" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00004", 6) %><%--{0,choice,0#일|1#월|2#화|3#수|4#목|5#금|6#토}--%></td>
                    </tr>
                    <tr>
                        <%

                            String dateColor = "";
                            String dateColorTop = "";
                            for (int d = 0; d < calendar.get(Calendar.DAY_OF_WEEK) - 1; d++) {
                        %>
                        <!-- <td width="84" valign="top" class="tdredM" style="height:85">&nbsp;</td> -->
                        <td width="108" valign="top" class="tdwwhiteM">&nbsp;</td>
                        <%
                            }
                            while (calendar.get(Calendar.MONTH) == month) {
                                String td = format.format(calendar.getTime());
                                String fontColor = "black";
                                if (calendar.equals(today)) {
                                    dateColor = "today";
                                    dateColorTop = "day";
                                }else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                                    dateColor = "tdwwhiteM";
                                    dateColorTop = "day";
                                    fontColor = "red";
                                }else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                                    dateColor = "tdwwhiteM";
                                    dateColorTop = "day";
                                    fontColor = "blue";
                                }else{
                                    dateColor = "tdwwhiteM";
                                    dateColorTop = "day";
                                }

                                if (calendar.get(Calendar.DATE) != 1
                                        && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        %>
                    </tr>
                    <tr>
                        <%
                            }

                                int day = calendar.get(Calendar.DATE);

                                TryPlanData tryPlanData = (TryPlanData) hdatas.get(td);

                                String mt = "-";
                                String pt = "-";

                                String mdelay = "-";
                                String mcomplate = "-";

                                String pdelay = "-";
                                String pcomplate = "-";

                                String mNonplan = "-";
                                String mPlan = "-";

                                String pNonplan = "-";
                                String pPlan = "-";


                                boolean todayBefore = todayStr.compareTo(td) > 0;
                                boolean isToday = todayStr.compareTo(td) == 0;

                                boolean isMold = false;
                                boolean isPress = false;

                                if (tryPlanData != null) {

                                    Hashtable hd = tryPlanData.getStateData();

                                    Vector total = tryPlanData.getTotal();
                                    int moldTotal = TryPlanData.getType(total, "M").size();
                                    int pressTotal = TryPlanData.getType(total, "P").size();

                                    int mDelyCount = TryPlanData.getType(hd, TryPlanData.DELAY, "M").size();

                                    int pDelyCount = TryPlanData.getType(hd, TryPlanData.DELAY, "P").size();

                                    int mNotPlanCount = TryPlanData.getType(hd, TryPlanData.TRYNONPLAN, "M").size();

                                    int pNotPlanCount = TryPlanData.getType(hd, TryPlanData.TRYNONPLAN, "P").size();

                                    int mCompletedCount = TryPlanData.getType(hd, TryPlanData.COMPLATED, "M").size();

                                    int pCompletedCount = TryPlanData.getType(hd, TryPlanData.COMPLATED, "P").size();

                                    int pPlanCount = TryPlanData.getType(hd, TryPlanData.TRYPLAN, "P").size();

                                    int mPlanCount = TryPlanData.getType(hd, TryPlanData.TRYPLAN, "M").size();


                                    if (moldTotal > 0) {
                                        mt = String.valueOf(moldTotal);
                                        isMold = true;
                                    }

                                    if (pressTotal > 0) {
                                        pt = String.valueOf(pressTotal);
                                        isPress = true;
                                    }

                                    if (mDelyCount > 0) {
                                        mdelay = String.valueOf(mDelyCount);
                                    }

                                    if (mCompletedCount > 0) {
                                        mcomplate = String.valueOf(mCompletedCount);
                                    }

                                    if (pDelyCount > 0) {
                                        pdelay = String.valueOf(pDelyCount);
                                    }

                                    if (pCompletedCount > 0) {
                                        pcomplate = String.valueOf(pCompletedCount);
                                    }


                                    if (mNotPlanCount > 0) {
                                        mNonplan = String.valueOf(mNotPlanCount);
                                    }

                                    if (mPlanCount > 0) {
                                        mPlan = String.valueOf(mPlanCount);
                                    }

                                    if (pNotPlanCount > 0) {
                                        pNonplan = String.valueOf(pNotPlanCount);
                                    }

                                    if (pPlanCount > 0) {
                                        pPlan = String.valueOf(pPlanCount);
                                    }
                                }

                                String color1 = "";
                                String color2 = "";
                                String color3 = "";

                                String mtValue = "";
                                String mValue1 = "";
                                String mValue2 = "";
                                String mValue3 = "";

                                String ptValue = "";
                                String pValue1 = "";
                                String pValue2 = "";
                                String pValue3 = "";

                                int tstate = 0;
                                int state1 = -1;
                                int state2 = -1;
                                int state3 = -1;

                                if(todayBefore){
                                    color1 = "red";
                                    color2 = "#4398BC";

                                    mtValue = mt;
                                    ptValue = pt;

                                    mValue1 = mdelay;
                                    mValue2 = mcomplate;

                                    pValue1 = pdelay;
                                    pValue2 = pcomplate;

                                    state1 = TryPlanData.DELAY;
                                    state2 = TryPlanData.COMPLATED;

                                }
                                else if(isToday){
                                    color1 = "gray";
                                    color2 = "black";
                                    color3 = "#4398BC";

                                    mtValue = mt;
                                    ptValue = pt;

                                    mValue1 = mNonplan;
                                    mValue2 = mPlan;
                                    mValue3 = mcomplate;

                                    pValue1 = pNonplan;
                                    pValue2 = pPlan;
                                    pValue3 = pcomplate;

                                    state1 = TryPlanData.TRYNONPLAN;
                                    state2 = TryPlanData.TRYPLAN;
                                    state3 = TryPlanData.COMPLATED;

                                }else{

                                    color1 = "gray";
                                    color2 = "black";

                                    mtValue = mt;
                                    ptValue = pt;

                                    mValue1 = mNonplan;
                                    mValue2 = mPlan;

                                    pValue1 = pNonplan;
                                    pValue2 = pPlan;

                                    state1 = TryPlanData.TRYNONPLAN;
                                    state2 = TryPlanData.TRYPLAN;

                                }


                        %>

                        <td  valign="top" class="<%=dateColor%>"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td  onclick="selectDay(event, '<%=td%>', '0', '')" class="<%=dateColorTop%>"><font color="<%=fontColor%>"><b><%=day%></b></font></td>
                            </tr>
                            <tr>
                                <td height="1"></td>
                            </tr>
                            <tr>

                                <td>
                                <%
                                    if (isMold) {
                                %> &nbsp;M : <span class="black"><b><a href="#"  class="black" onclick="selectDay(event, '<%=td%>', '<%=tstate%>', 'M')"><font color="black"><%=mtValue%></font></a></b></span>
                                /<span><b><a href="#" class="<%=color1%>" onclick="selectDay(event, '<%=td%>', '<%=state1%>', 'M')"><%=mValue1%></a></b></span>
                                /<span><b><a href="#" class="<%=color2%>"  onclick="selectDay(event, '<%=td%>', '<%=state2%>', 'M')"><font color="<%=color2%>"><%=mValue2%></font></a></b></span>
                                <%if(isToday){ %>
                                /<span><b><a href="#" class="<%=color3%>"  onclick="selectDay(event, '<%=td%>', '<%=state3%>', 'M')"><%=mValue3%></a></b></span><br>

                                <%} else{%>
                                 <br>
                                <%} %>
                                <%
                                    } else {
                                %> <br>
                                <%
                                    }
                                %> <%
                                     if (isPress) {
                                 %> &nbsp;P : <span><b><a href="#" class="black" onclick="selectDay(event, '<%=td%>', '<%=tstate%>', 'P')"><font color="black"><%=ptValue%></font></a></b></span>
                                 /<span><b><a href="#" class="<%=color1%>" onclick="selectDay(event, '<%=td%>', '<%=state1%>', 'P')"><%=pValue1%></a></b></span>
                                /<span><b><a href="#" class="<%=color2%>"  onclick="selectDay(event, '<%=td%>', '<%=state2%>', 'P')"><font color="<%=color2%>"><%=pValue2%></font></a></b></span>
                                <%if(isToday){ %>
                                /<span><b><a href="#" class="<%=color3%>"  onclick="selectDay(event, '<%=td%>', '<%=state2%>', 'P')"><%=pValue3%></a></b></span>

                                <%}%>
                                <%
                                    } else {
                                %> <br>
                                <%
                                    }
                                %>

                                </td>


                            </tr>
                        </table>
                        </td>
                        <%
                            calendar.add(Calendar.DATE, 1);

                            }
                        %>

                        <%
                            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                                for (int d = calendar.get(Calendar.DAY_OF_WEEK); d < Calendar.SATURDAY + 1; d++) {
                        %>
                        <td width="108" valign="top" class="tdwwhiteM">&nbsp;</td>
                        <%
                                }
                            }
                        %>
                    </tr>

                </table>
                </td>
            </tr>
        </table>
        </form>

        <%@include file="/jsp/common/AutoCompleteLayer.jsp"%>
</body>
</html>
