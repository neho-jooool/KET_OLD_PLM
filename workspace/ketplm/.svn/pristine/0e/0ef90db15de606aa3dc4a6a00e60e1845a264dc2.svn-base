<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.Date,
                java.util.*,
                java.text.*,
                wt.fc.*" %>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
    private final SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private final SimpleDateFormat compareFormat = new SimpleDateFormat("yy/MM");
%>
<%
    String oid = request.getParameter("oid");
    String projectOid = request.getParameter("projectOid");
    HistoryManagerProjectLink link = ProjectHistoryManagerHelper.manager.getHistoryManagerProjectLink( (HistoryManager)CommonUtil.getObject(oid) );
    ProjectHistoryManagerData historyData = new ProjectHistoryManagerData(link);

    Calendar start = Calendar.getInstance();    // 챠트의 시작월
    Calendar end = Calendar.getInstance();      // 챠트의 종료월
    start.setTime(historyData.planStartDate);
    end.setTime(historyData.planEndDate);
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
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language="JavaScript">
<!--
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

function viewDoc( oid ) {
    var url = "/plm/jsp/doc/docView.jsp?oid="+oid+"&projectValue=true";
    openOtherName(url,"viewDoc","850","600","status=no,scrollbars=no,resizable=no");
}

function excelView(oid){
    var url = "/plm/jsp/project/ExcelProject.jsp?oid="+oid;
    openOtherName(url,"excelView","830","600","status=1,scrollbars=no,resizable=1");
}


//-->
</SCRIPT>

<!--툴팁//-->
<!-----애드콘 배너 레이어는 왼쪽에서 760픽셀, 위쪽에서 70 픽셀 떨어진 곳이고, 크기는 120*240 픽셀 입니다 ---->
<div id="menu" style=" background-color:;position:absolute;left:0px; top:40px; width:100%; height:0px; z-index:1" >
<table border="0" cellpadding="0" cellspacing="1" bgcolor=AABDC6 align="center">
    <!--tr bgcolor="#D8E0E7" align=center>
        <td rowspan="3" style="table-layout:fixed" ><table border=0 cellpadding=0 cellspacing=0 width=300><tr><td  id=textblue align=center>단계/일정</td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center>계획<br>시작일자</td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center>계획<br>종료일자</td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center>실제<br>시작일자</td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=70><tr><td  id=textblue align=center>실제<br>종료일자</td></tr></table></td>
        <td rowspan="3"><table border=0 cellpadding=0 cellspacing=0 width=150><tr><td  id=textblue align=center>산출물</td></tr></table></td>
        <td colspan="<%=monthStrVec.size()%>">Chart</td>
    </tr-->
<%
    Set keySet = yearHash.keySet();
    Object[] keyArr = keySet.toArray();
%>
    <tr bgcolor="#D8E0E7" align=center>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
%>
        <!--td id=textblue colspan="<%=monthVec.size()%>"><%=keyArr[i]%> 년</td-->
<%  }  %>
    </tr>
    <tr bgcolor="#D8E0E7" align=center>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
        for ( int j = 0 ; j < monthVec.size() ; j++ ) {
%>
        <!--td width="30" id=textblue>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr><td width="30" align="center"><%=monthVec.get(j)%></td></tr>
            </table>
        </td-->
<%
        }
    }
%>
    </tr>
</table>
</div>
<!----- 애드콘 ---->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding-left:24">
<!-------------------------------------- 상단버튼 시작 //-------------------------------------->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" class="tab_w02">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align=right width=70><input type=button class="btnTras" onclick="javascript:excelView('<%=projectOid%>')" value="<%=messageService.getString("e3ps.message.ket_message", "00236") %><%--Excel보기--%>" id=button></td>
                                <td align=right width=70><input type=button class="btnTras" onclick="javascript:closeForm()" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" id=button></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
<!-------------------------------------- 상단버튼 끝 //-------------------------------------->
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
<!------------------------------ 본문 시작 //------------------------------>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w02"/>
                <jsp:param name="td_class" value="tab_btm2"/>
            </jsp:include>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false">
                <jsp:param name="tb_class" value="tab_w02"/>
                <jsp:param name="td_class" value="tab_btm1"/>
            </jsp:include>
            <table class="tab_w02" border="0" cellspacing="0" cellpadding="0" bgcolor=AABDC6>
                <tr>
                    <td class="tdblueM" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "01190") %><%--단계--%></td>
                    <td class="tdblueM" width="40%" style="table-layout:fixed" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                    <td class="tdblueM" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "00819", "<br>") %><%--계획{0}시작일자--%></td>
                    <td class="tdblueM" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "00827", "<br>") %><%--계획{0}종료일자--%></td>
                    <td class="tdblueM" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "02055") %><%--실제--%><br><%=messageService.getString("e3ps.message.ket_message", "02019") %><%--시작일자--%></td>
                    <td class="tdblueM" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "02055") %><%--실제--%><br><%=messageService.getString("e3ps.message.ket_message", "02659") %><%--종료일자--%></td>
                    <td class="tdblueM" width="30%" rowspan="3"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
                    <td class="tdblueM" colspan="<%=monthStrVec.size()%>">Chart</td>
                </tr>
<%
    keySet = yearHash.keySet();
    keyArr = keySet.toArray();
%>
                <tr>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
%>
                    <td class="tdblueM" colspan="<%=monthVec.size()%>">&nbsp;<%=keyArr[i]%> <%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%></td>
<%  }  %>
                </tr>
                <tr>
<%
    for ( int i = 0 ; i < keySet.size() ; i++ ) {
        Vector monthVec = (Vector)yearHash.get(keyArr[i]);
        for ( int j = 0 ; j < monthVec.size() ; j++ ) {
%>
                    <td class="tdblueM">&nbsp;<%=monthVec.get(j)%></td>
<%
        }
    }
%>
                </tr>
                <!-- Level1 TASK Information -->
<%
    QueryResult qr = ProjectTaskHelper.manager.getChild(historyData.historyProject);
    while ( qr.hasMoreElements() ) {
        Object[] objArr = (Object[])qr.nextElement();
        E3PSTask task = (E3PSTask)objArr[0];
        E3PSTaskData taskHistoryData = new E3PSTaskData(task);

        String taskBalanceStr = "&nbsp;";

        if ( taskHistoryData.taskExecEndDate!=null ) {
            if ( taskHistoryData.taskExecEndDate.before(taskHistoryData.taskPlanEndDate) ) {
                taskBalanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(taskHistoryData.taskExecEndDate, "D"),DateUtil.getDateString(taskHistoryData.taskPlanEndDate, "D"))-1)+"</font>";
            } else if ( taskHistoryData.taskExecEndDate.after(taskHistoryData.taskPlanEndDate) ) {
                taskBalanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(taskHistoryData.taskPlanEndDate, "D"),DateUtil.getDateString(taskHistoryData.taskExecEndDate, "D"))-1)+"</font>";
            }
        }

        Calendar sDate = Calendar.getInstance();
        sDate.setTime(taskHistoryData.taskPlanStartDate);
        int compPer = taskHistoryData.taskCompletion;
        double d = (double)compPer/100D;
        d *= taskHistoryData.taskDuration;
        sDate.add(5, (int)d);
        Calendar currentDate = Calendar.getInstance();
        long continueTimeGap = sDate.getTime().getTime() - currentDate.getTime().getTime();
        int differDateGap = (int)(continueTimeGap / 0x5265c00L);
        if ( differDateGap > 0 ) {
            taskBalanceStr =  "<font color=blue>(+)"+ differDateGap +"</font>";
        } else if ( differDateGap == 0 ) {
            taskBalanceStr =  "<font color=blue>"+ differDateGap +"</font>";
        } else {
            taskBalanceStr = "<font color=red>(-)"+ differDateGap +"</font>";
        }


        String completeStr = "&nbsp;";
        if ( taskHistoryData.taskCompletion > 0 ) {
            if ( taskHistoryData.taskCompletion >= 100 ) completeStr = "종료됨";
            else completeStr = taskHistoryData.taskCompletion+ " %";
        }
%>
                <!-- //Level1 TASK Information -->
                <!-- Level2 TASK Information -->
<%
        QueryResult subQr = ProjectTaskHelper.manager.getChild(taskHistoryData.E3PSTask);

        //ADD(20080524)
        int parentTaskSize = subQr.size();
        String parentTaskName = taskHistoryData.taskName;

        //while ( subQr.hasMoreElements() ) {
        for(int i = 0; i < subQr.size(); i++) {
            Object[] subObjArr = (Object[])subQr.nextElement();
            E3PSTask childTask = (E3PSTask)subObjArr[0];
            E3PSTaskData childTaskHistoryData = new E3PSTaskData(childTask);

            taskBalanceStr = "&nbsp;";
            if ( childTaskHistoryData.taskExecEndDate!=null ) {
                if ( childTaskHistoryData.taskExecEndDate.before(childTaskHistoryData.taskPlanEndDate) ) {
                    taskBalanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(childTaskHistoryData.taskExecEndDate, "D"),DateUtil.getDateString(childTaskHistoryData.taskPlanEndDate, "D"))-1)+"</font>";
                } else if ( childTaskHistoryData.taskExecEndDate.after(childTaskHistoryData.taskPlanEndDate) ) {
                    taskBalanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(DateUtil.getDateString(childTaskHistoryData.taskPlanEndDate, "D"),DateUtil.getDateString(childTaskHistoryData.taskExecEndDate, "D"))-1)+"</font>";
                }
            }
            sDate = Calendar.getInstance();
            sDate.setTime(childTaskHistoryData.taskPlanStartDate);
            compPer = childTaskHistoryData.taskCompletion;
            d = (double)compPer/100D;
            d *= taskHistoryData.taskDuration;
            sDate.add(5, (int)d);
            continueTimeGap = sDate.getTime().getTime() - currentDate.getTime().getTime();
            differDateGap = (int)(continueTimeGap / 0x5265c00L);
            if ( differDateGap > 0 ) {
                taskBalanceStr =  "<font color=blue>(+)"+ differDateGap +"</font>";
            } else if ( differDateGap == 0 ) {
                taskBalanceStr =  "<font color=blue>"+ differDateGap +"</font>";
            } else {
                taskBalanceStr = "<font color=red>(-)"+ differDateGap +"</font>";
            }

            completeStr = "&nbsp;";
            if ( childTaskHistoryData.taskCompletion > 0 ) {
                if ( childTaskHistoryData.taskCompletion >= 100 ) completeStr = "종료됨";
                else completeStr = childTaskHistoryData.taskCompletion+ " %";
            }
%>
                <% if(i == 0) { %>
                <tr>
                    <td class="tdwhiteL" rowspan="<%=parentTaskSize%>"><b><%=parentTaskName%></b></td>
                <% } else { %>
                <tr>
                <% } %>
                    <td class="tdwhiteL" style="table-layout:fixed">
                        <img src="/plm/portal/icon/task.gif">&nbsp;<%=childTaskHistoryData.taskName%>
                    </td>
                    <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(childTaskHistoryData.taskPlanStartDate, "D")%></td>
                    <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(childTaskHistoryData.taskPlanEndDate, "D")%></td>
                    <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(childTaskHistoryData.taskExecStartDate, "D")%></td>
                    <td class="tdwhiteL">&nbsp;<%=DateUtil.getDateString(childTaskHistoryData.taskExecEndDate, "D")%></td>
                    <td class="tdwhiteL">
                        <!-- 산출물 -->
                        <%
                            QueryResult childOutputQR = ProjectOutputHelper.manager.getTaskOutput((TemplateTask)childTask);
                            if(childOutputQR.size() > 0) {
                                while(childOutputQR.hasMoreElements()) {
                                    Object[] childObj = (Object[])childOutputQR.nextElement();
                                    ProjectOutput childOutput = (ProjectOutput)childObj[0];
                                    ProjectOutputData childOutputData = new ProjectOutputData(childOutput);
                                    if(childOutputData.document != null) {
                        %>
                                <a href="javascript:viewDoc('<%=CommonUtil.getOIDString(childOutputData.document)%>')"><font color="blue"><%= childOutputData.document.getName() %>[<%= e3ps.common.obj.ObjectUtil.getVersion(childOutputData.document).substring(0,1) %>]</font></a><br>
                        <%
                                    } else {
                        %>
                                <%= childOutputData.name %><br>
                        <%
                                    }
                                }
                            } else {
                        %>
                            &nbsp;
                        <%  } %>
                    </td>
        <%
        int dayInt = StringUtil.parseInt(DateUtil.getDateString(childTaskHistoryData.taskPlanStartDate,dayFormat),1) - 1;

        checkStart = (Calendar)start.clone();
        checkEnd = (Calendar)end.clone();

        Calendar startProject = Calendar.getInstance();
        startProject.setTime(childTaskHistoryData.taskPlanStartDate);
        Calendar endProject = Calendar.getInstance();
        endProject.setTime(childTaskHistoryData.taskPlanEndDate);
        endProject.add(Calendar.MONTH,1);

        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(startProject.getTime(),compareFormat).trim()) ) {
%>
                    <td class="tdwhiteL" height="30">&nbsp;</td>
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
        int duration = 1;
        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(endProject.getTime(),compareFormat).trim()) ) {

%>
                    <td class="tdwhiteL" width="30" valign="top">&nbsp;<%if(isFirst){%><a ONMOUSEOVER="popup('<%=taskBalanceStr%>','ffffcc')"; ONMOUSEOUT="kill()"><span style='mso-ignore:vglayout;position:absolute;z-index:0;margin-left:<%=dayInt%>px;margin-top:0px;width:5px;;height:10px'><img  width='<%=childTaskHistoryData.taskDuration%>' height=10 src='/plm/portal/icon/bar_blue.gif'></span></a><a ONMOUSEOVER="popup('<%=completeStr%>','ffffcc')"; ONMOUSEOUT="kill()"><span style='mso-ignore:vglayout;position:absolute;z-index:0;margin-left:<%=dayInt%>px;margin-top:15px;width:5px;;height:10px'><img  width='<%=(childTaskHistoryData.taskDuration*childTaskHistoryData.taskCompletion)/100%>' height=10 src='/plm/portal/icon/bar_red.gif'></span></a><%}%></td>
<%
            isFirst = false;
            checkStart.add(Calendar.MONTH,1);
        }

        while ( !DateUtil.getDateString(checkStart.getTime(),compareFormat).trim().equals(DateUtil.getDateString(checkEnd.getTime(),compareFormat).trim()) ) {
%>
                    <td class="tdwhiteL" height="30">&nbsp;</td>
<%
            checkStart.add(Calendar.MONTH,1);
        }
%>
                </tr>
                <!-- //Level2 TASK Information -->
<%
        }
    }
%>
</table>
<!------------------------------ 본문 끝 //------------------------------>
        </td>
    </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</body>
</html>
