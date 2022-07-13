<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.Date,
                java.util.*,
                java.text.*,
                wt.fc.*" %>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*,
                e3ps.groupware.company.*" %>
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%!
    private final SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private final SimpleDateFormat compareFormat = new SimpleDateFormat("yy/MM");
%>
<%
    String oid = request.getParameter("oid");
    HistoryManager historyMgmt = (HistoryManager)CommonUtil.getObject(oid);
    QueryResult mgmtQr = PersistenceHelper.manager.navigate(historyMgmt, "historyProject", HistoryManagerProjectLink.class, true);
    ProjectHistoryManagerData historyData = new ProjectHistoryManagerData( ( (HistoryManagerProjectLink)CommonUtil.getObject(oid) ) );
    E3PSProjectData projectData = new E3PSProjectData((E3PSProject)historyData.historyProject);
    Calendar start = Calendar.getInstance();    // 챠트의 시작월
    Calendar end = Calendar.getInstance();      // 챠트의 종료월
    start.setTime(projectData.pjtPlanStartDate);
    end.setTime(projectData.pjtPlanEndDate);
    start.add(Calendar.MONTH,-1);
    end.add(Calendar.MONTH,2);

    Calendar checkStart = (Calendar)start.clone();    // 비교시 사용할 시작일
    Calendar checkEnd = (Calendar)end.clone();      // 비교시 사용할 종료일

    TreeMap yearHash = new TreeMap();
    Vector monthStrVec = new Vector();
    while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(checkEnd.getTime(),compareFormat).trim()) ) {
        String year = DateUtil.getDateString(checkStart.getTime(),yearFormat);
        String month = DateUtil.getDateString(checkStart.getTime(),monthFormat);
        if ( yearHash.containsKey(year) ) {
            Vector vec = (Vector)yearHash.get(year);
            vec.add(month);
            yearHash.put(year,vec);
        } else {
            Vector vec = new Vector();
            vec.add(month);
            yearHash.put(year,vec);
        }
        monthStrVec.add(month);
        checkStart.add(Calendar.MONTH,1);
    }
%>
<html>
<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "03380") %><%--프로젝트 챠트 보기--%></TITLE>
<%@include file="/jsp/common/top_include.jsp" %>
<script language="JavaScript">
<!--
function viewExcel(oid){
    var windowWin = window.open("/plm/page/project/projectCurrentChartExcelPerMonth.jsp?oid="+oid,"excel","status=no,scrollbars=yes,resizable,width=900,height=600,top="+screenHeight+",left="+screenWidth);
    windowWin.focus();
}

function closeForm() {
    self.close();
}
//-->
</script>


<SCRIPT LANGUAGE="JavaScript">
<!--
//애드콘 스크립트
function scroll(){
    var a=document.body.scrollTop+40
        // 70 은 배너의 기준위치가 위쪽에서 70 픽셀 떨어지도록 설정한 것입니다
    menu.style.top=a
}
//-->
</SCRIPT>

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onScroll="scroll()"  style="overflow:scroll">
<!--툴팁//-->
<DIV ID="dek" style='POSITION:absolute;VISIBILITY:hidden;Z-INDEX:200;'></DIV>

<SCRIPT TYPE="text/javascript">
<!--

Xoffset= 30;    // 하이퍼링크로부터의 팝업창의 x축 위치
Yoffset= 10;    // 하이퍼링크로부터의 팝업창의 y축 위치

var nav,old,iex=(document.all),yyy=-1000;
if(navigator.appName=="Netscape"){(document.layers)?nav=true:old=true;}

if(!old){
var skn=(nav)?document.dek:dek.style;
if(nav)document.captureEvents(Event.MOUSEMOVE);
document.onmousemove=get_mouse;
}

function popup(msg,bak){
var content="<TABLE  WIDTH='' BORDER=0 bgcolor=black CELLPADDING=1 CELLSPACING=1 >"+
"<td BGCOLOR="+bak+"><FONT COLOR=black >"+msg+"</FONT></TD></TABLE>";
if(old){alert(msg);return;}
else{yyy=Yoffset;
if(nav){skn.document.write(content);skn.document.close();skn.visibility="visible"}
if(iex){document.all("dek").innerHTML=content;skn.visibility="visible"}
}
}

function get_mouse(e){
var x=(nav)?e.pageX:event.x+document.body.scrollLeft;skn.left=x+Xoffset;
var y=(nav)?e.pageY:event.y+document.body.scrollTop;skn.top=y+yyy;
}

function kill(){
if(!old){yyy=-1000;skn.visibility="hidden";}
}

//-->
</SCRIPT>
<!--툴팁//-->

<!--툴팁//-->
<!-----애드콘 배너 레이어는 왼쪽에서 760픽셀, 위쪽에서 70 픽셀 떨어진 곳이고, 크기는 120*240 픽셀 입니다 ---->
<div id="menu" style=" background-color:;position:absolute;left:0px; top:40px; width:100%; height:0px; z-index:1" >
<table border="0" cellpadding="0" cellspacing="1" bgcolor=AABDC6 align="center">
    <tr bgcolor="#D8E0E7" align=center>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=300><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "01192") %><%--단계/일정--%></td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=65><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td></tr></table></td>
        <!--<td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=65><tr><td  id=textblue align=center nowrap>실수행시작일</td></tr></table></td>-->
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center nowrap><%=messageService.getString("e3ps.message.ket_message", "02031") %><%--실수행종료일--%></td></tr></table></td>
        <td colspan="<%=monthStrVec.size()%>"  id=textblue ><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></td>
    </tr>
<%
    Set keySet = yearHash.keySet();
    Object[] keyArr = keySet.toArray();
%>
    <tr bgcolor="#D8E0E7" align=center>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
%>
        <td id=textblue colspan="<%=monthVec.size()%>"><%=keyArr[i]%> <%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%></td>
<%  }  %>
    </tr>
    <tr bgcolor="#D8E0E7" align=center>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
        for ( int j = 0 ; j < monthVec.size() ; j++ ) {
%>
        <td width="30" id=textblue>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr><td width="30" align="center"><%=monthVec.get(j)%></td></tr>
            </table>
        </td>
<%
        }
    }
%>
    </tr>
</table>
</div>
<!----- 애드콘 ---->
<table height=40 align=center border=0>
    <tr>
        <td width=100%>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "03101") %><%--프로젝트 챠트--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
            </table>
        </td>
        <td align=right><input type=button onclick="javascript:closeForm()" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" id=button></td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="1" bgcolor=AABDC6 align=center>
    <tr bgcolor="#D8E0E7" align=center>
        <td rowspan="3" width=300><table border=0 cellpadding=0 cellspacing=0><tr><td id=textblue align=center style='word-break:break-all;'><%=messageService.getString("e3ps.message.ket_message", "01192") %><%--단계/일정--%></td></tr></table></td>
        <td rowspan="3" width=65><table border=0 cellpadding=0 cellspacing=0><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td></tr></table></td>
        <td rowspan="3" width=70><table border=0 cellpadding=0 cellspacing=0><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td></tr></table></td>
        <td rowspan="3" width=70><table border=0 cellpadding=0 cellspacing=0><tr><td  id=textblue align=center><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td></tr></table></td>
        <!--<td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=65><tr><td  id=textblue align=center nowrap>실수행시작일</td></tr></table></td>-->
        <td rowspan="3" width=70><table border=0 cellpadding=0 cellspacing=0><tr><td  id=textblue align=center nowrap><%=messageService.getString("e3ps.message.ket_message", "02031") %><%--실수행종료일--%></td></tr></table></td>
        <td id=textblue colspan="<%=monthStrVec.size()%>">Chart</td>
    </tr>
<%
    keySet = yearHash.keySet();
    keyArr = keySet.toArray();
%>
    <tr bgcolor="#D8E0E7" align=center>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
%>
        <td id=textblue colspan="<%=monthVec.size()%>"><%=keyArr[i]%> <%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%></td>
<%  }  %>
    </tr>
    <tr bgcolor="#D8E0E7" align=center>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
        for ( int j = 0 ; j < monthVec.size() ; j++ ) {
%>
        <td width="30" id=textblue>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr><td width="30" align="center"><%=monthVec.get(j)%></td></tr>
            </table>
        </td>
<%
        }
    }
%>
    </tr>
<%
    QueryResult qr = ProjectTaskHelper.manager.getChild(projectData.E3PSProject);
    while ( qr.hasMoreElements() ) {
        Object[] objArr = (Object[])qr.nextElement();
        E3PSTaskData taskData = new E3PSTaskData((E3PSTask)objArr[0]);
        String taskBalanceStr = "&nbsp;";
        if ( taskData.taskExecEndDate!=null ) {
            if ( taskData.taskExecEndDate.before(taskData.taskPlanEndDate) ) {
                taskBalanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(taskData.taskExecEndDate, "D"),DateUtil.getDateString(taskData.taskPlanEndDate, "D"))-1)+"</font>";
            } else if ( taskData.taskExecEndDate.after(taskData.taskPlanEndDate) ) {
                taskBalanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(taskData.taskPlanEndDate, "D"),DateUtil.getDateString(taskData.taskExecEndDate, "D"))-1)+"</font>";
            }
        }

        String completeStr = "&nbsp;";
        if ( taskData.taskCompletion > 0 ) {
            if ( taskData.taskCompletion >= 100 ) completeStr = "종료됨";
            else completeStr = taskData.taskCompletion+ " %";
        }

        //책임자, 담당자 Level1
        String responsiblePersonLevel1 = "";
        String chargePersonLevel1 = "";
        QueryResult masterListLevel1 = TaskUserHelper.manager.getMaster((E3PSTask)objArr[0]);
        QueryResult memberlistLevel1 = TaskUserHelper.manager.getMember((E3PSTask)objArr[0]);
        while ( masterListLevel1.hasMoreElements() ) {
            Object o[]=(Object[])masterListLevel1.nextElement();
            TaskMemberLink link = (TaskMemberLink)o[0];
            PeopleData data = new PeopleData(link.getMember().getMember());

            responsiblePersonLevel1 = responsiblePersonLevel1 + data.name + "<br>";
        }
        while ( memberlistLevel1.hasMoreElements() ) {
            Object o1[]=(Object[])memberlistLevel1.nextElement();
            TaskMemberLink link = (TaskMemberLink)o1[0];
            PeopleData data = new PeopleData(link.getMember().getMember());

            chargePersonLevel1 = chargePersonLevel1 + data.name + "<br>";
        }

%>
    <tr bgcolor=#ffffff onMouseover="this.style.backgroundColor='#E2ECEC'" onMouseout="this.style.backgroundColor='#ffffff'" align=center>
        <td align=left width=300>
            <table border=0 cellpadding0 cellspacing=0 style="table-layout:fixed" width=300><tr><td>
            &nbsp;<%=taskData.taskName%></td></tr></table>
        </td>
        <td><%=taskData.taskCompletion%>%</td>
        <td><%=DateUtil.getDateString(taskData.taskPlanStartDate,"d")%></td>
        <td><%=DateUtil.getDateString(taskData.taskPlanEndDate,"d")%></td>
        <!--<td><%=DateUtil.getDateString(taskData.taskExecStartDate,"d")%></td>-->
        <td><%=DateUtil.getDateString(taskData.taskExecEndDate,"d")%></td>
<%
        int dayInt = StringUtil.parseInt(DateUtil.getDateString(taskData.taskPlanStartDate,dayFormat),1) - 1;

        checkStart = (Calendar)start.clone();
        checkEnd = (Calendar)end.clone();

        Calendar startProject = Calendar.getInstance();
        startProject.setTime(taskData.taskPlanStartDate);
        Calendar endProject = Calendar.getInstance();
        endProject.setTime(taskData.taskPlanEndDate);
        endProject.add(Calendar.MONTH,1);

        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(startProject.getTime(),compareFormat).trim()) ) {
%>
        <td height="30"></td>
<%
            checkStart.add(Calendar.MONTH,1);
        }

        Calendar countStart = (Calendar)checkStart.clone();
        Calendar countEnd = (Calendar)endProject.clone();
        int count = 0;
        while ( !DateUtil.getDateString(countStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(countEnd.getTime(),compareFormat).trim()) ) {
            countStart.add(Calendar.MONTH,1);
            count++;
        }

        boolean isFirst = true;
        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(endProject.getTime(),compareFormat).trim()) ) {
%>
        <td width="30" align="left" valign="top"><%if(isFirst){%><a ONMOUSEOVER="popup('<%=taskBalanceStr%>','ffffcc')"; ONMOUSEOUT="kill()"><span style='mso-ignore:vglayout;position:absolute;z-index:0;margin-left:<%=dayInt%>px;margin-top:0px;width:5px;;height:10px'><img width='<%=taskData.taskDuration%>' height=10 src='/plm/portal/icon/bar_blue.gif'></span></a><a ONMOUSEOVER="popup('<%=completeStr%>','ffffcc')"; ONMOUSEOUT="kill()"><span style='mso-ignore:vglayout;position:absolute;z-index:0;margin-left:<%=dayInt%>px;margin-top:15px;width:5px;;height:10px'><img  width='<%=(taskData.taskDuration*taskData.taskCompletion)/100%>' height=10 src='/plm/portal/icon/bar_red.gif'></span></a><%}%></td>
<%
            isFirst = false;
            checkStart.add(Calendar.MONTH,1);
        }

        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(checkEnd.getTime(),compareFormat).trim()) ) {
%>
        <td height="30"></td>
<%
            checkStart.add(Calendar.MONTH,1);
        }
%>
    </tr>
<%
        QueryResult subQr = ProjectTaskHelper.manager.getChild(taskData.E3PSTask);
        while ( subQr.hasMoreElements() ) {
            Object[] subObjArr = (Object[])subQr.nextElement();
            E3PSTask childTask = (E3PSTask)subObjArr[0];
            E3PSTaskData childTaskData = new E3PSTaskData((E3PSTask)subObjArr[0]);

            taskBalanceStr = "&nbsp;";
            if ( childTaskData.taskExecEndDate!=null ) {
                if ( childTaskData.taskExecEndDate.before(childTaskData.taskPlanEndDate) ) {
                    taskBalanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(childTaskData.taskExecEndDate, "D"),DateUtil.getDateString(childTaskData.taskPlanEndDate, "D"))-1)+"</font>";
                } else if ( childTaskData.taskExecEndDate.after(childTaskData.taskPlanEndDate) ) {
                    taskBalanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(childTaskData.taskPlanEndDate, "D"),DateUtil.getDateString(childTaskData.taskExecEndDate, "D"))-1)+"</font>";
                }
            }

            completeStr = "&nbsp;";
            if ( childTaskData.taskCompletion > 0 ) {
                if ( childTaskData.taskCompletion >= 100 ) completeStr = "종료됨";
                else completeStr = childTaskData.taskCompletion+ " %";
            }

            //책임자, 담당자 Level2
        String responsiblePersonLevel2 = "";
        String chargePersonLevel2 = "";
        QueryResult masterListLevel2 = TaskUserHelper.manager.getMaster(childTask);
        QueryResult memberlistLevel2 = TaskUserHelper.manager.getMember(childTask);

        while ( masterListLevel2.hasMoreElements() ) {
            Object o[]=(Object[])masterListLevel2.nextElement();
            TaskMemberLink link = (TaskMemberLink)o[0];
            PeopleData data = new PeopleData(link.getMember().getMember());
            Kogger.debug(data.name);
            responsiblePersonLevel2 = responsiblePersonLevel2 + data.name + "<br>";
        }
        while ( memberlistLevel2.hasMoreElements() ) {
            Object o[]=(Object[])memberlistLevel2.nextElement();
            TaskMemberLink link = (TaskMemberLink)o[0];
            PeopleData data = new PeopleData(link.getMember().getMember());

            chargePersonLevel2 = chargePersonLevel2 + data.name + "<br>";
        }
%>
    <tr bgcolor=#ffffff onMouseover="this.style.backgroundColor='#E2ECEC'" onMouseout="this.style.backgroundColor='#ffffff'" align=center>
        <td align=left>
            <table border=0 cellpadding0 cellspacing=0 style="table-layout:fixed" width=300><tr><td>
            &nbsp;&nbsp;&nbsp;<%=childTaskData.taskName%></td></tr></table>
        </td>
        <td><%=childTaskData.taskCompletion%>%</td>
        <td><%=DateUtil.getDateString(childTaskData.taskPlanStartDate,"d")%></td>
        <td><%=DateUtil.getDateString(childTaskData.taskPlanEndDate,"d")%></td>
        <!--<td><%=DateUtil.getDateString(childTaskData.taskExecStartDate,"d")%></td>-->
        <td><%=DateUtil.getDateString(childTaskData.taskExecEndDate,"d")%></td>
        <%
        dayInt = StringUtil.parseInt(DateUtil.getDateString(childTaskData.taskPlanStartDate,dayFormat),1) - 1;

        checkStart = (Calendar)start.clone();
        checkEnd = (Calendar)end.clone();

        startProject = Calendar.getInstance();
        startProject.setTime(childTaskData.taskPlanStartDate);
        endProject = Calendar.getInstance();
        endProject.setTime(childTaskData.taskPlanEndDate);
        endProject.add(Calendar.MONTH,1);

        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(startProject.getTime(),compareFormat).trim()) ) {
%>
        <td height="30"></td>
<%
            checkStart.add(Calendar.MONTH,1);
        }

        countStart = (Calendar)checkStart.clone();
        countEnd = (Calendar)endProject.clone();
        count = 0;
        while ( !DateUtil.getDateString(countStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(countEnd.getTime(),compareFormat).trim()) ) {
            countStart.add(Calendar.MONTH,1);
            count++;
        }

        isFirst = true;
        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(endProject.getTime(),compareFormat).trim()) ) {
%>
        <td width="30" align="left" valign="top"><%if(isFirst){%><a ONMOUSEOVER="popup('<%=taskBalanceStr%>','ffffcc')"; ONMOUSEOUT="kill()"><span style='mso-ignore:vglayout;position:absolute;z-index:0;margin-left:<%=dayInt%>px;margin-top:0px;width:5px;;height:10px'><img  width='<%=childTaskData.taskDuration%>' height=10 src='/plm/portal/icon/bar_blue.gif'></span></a><a ONMOUSEOVER="popup('<%=completeStr%>','ffffcc')"; ONMOUSEOUT="kill()"><span style='mso-ignore:vglayout;position:absolute;z-index:0;margin-left:<%=dayInt%>px;margin-top:15px;width:5px;;height:10px'><img  width='<%=(childTaskData.taskDuration*childTaskData.taskCompletion)/100%>' height=10 src='/plm/portal/icon/bar_red.gif'></span></a><%}%></td>
<%
            isFirst = false;
            checkStart.add(Calendar.MONTH,1);
        }

        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(checkEnd.getTime(),compareFormat).trim()) ) {
%>
        <td height="30"></td>
<%
            checkStart.add(Calendar.MONTH,1);
        }
%>
    </tr>
<%
        }
    }
%>
</table>
</body>
</html>
