<%@page contentType="application/vnd.ms-excel;charset=UTF-8" %>
<%@page import="java.util.*,java.text.*" %>
<%@page import="wt.fc.*" %>
<%@page import="e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%!
    private final SimpleDateFormat monthFormat = new SimpleDateFormat("MM 월");
    private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
%>
<%
    response.setHeader("Content-Disposition", "attachment;filename=ProjectSchedule.xls");
    out.clearBuffer();

    String oid = request.getParameter("oid");
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);
   Date startDate = projectData.pjtPlanStartDate;
    Calendar start = Calendar.getInstance();
    Calendar checkStart = Calendar.getInstance();
    start.setTime(startDate);
    start.add(Calendar.DATE,-3);
    checkStart.setTime(startDate);
    checkStart.add(Calendar.DATE,-3);

    Date endDate = projectData.pjtPlanEndDate;
    Calendar end = Calendar.getInstance();
    Calendar checkEnd = Calendar.getInstance();
    end.setTime(endDate);
    end.add(Calendar.DATE,5);
    checkEnd.setTime(endDate);
    checkEnd.add(Calendar.DATE,5);

    int totolDur = DateUtil.getDuration(startDate, endDate) + 1;

    Hashtable monthCheckTable = new Hashtable();
    Vector monthStrVec = new Vector();
    while ( !DateUtil.getDateString(checkStart.getTime(),"d").trim().equals(DateUtil.getDateString(checkEnd.getTime(),"d").trim()) ) {
        String month = DateUtil.getDateString(checkStart.getTime(),monthFormat);
        if ( monthCheckTable.containsKey(month) ) {
            String countStr = (String)monthCheckTable.get(month);
            int count = Integer.parseInt(countStr) + 1;
            monthCheckTable.put(month,count+"");
        } else {
            monthCheckTable.put(month,"1");
            monthStrVec.add(month);
        }
        checkStart.add(Calendar.DATE,1);
    }
%>
<html>
<head>
</head>
<table width="98%" border="1" cellpadding="0" cellspacing="0" align=center>
    <tr bgcolor="#D8E0E7" align=center>
        <td id=textblue colspan=3><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
        <td width=90 id=textblue rowspan=3><%=messageService.getString("e3ps.message.ket_message", "02018") %><%--시작일--%></td>
        <td width=90 id=textblue rowspan=3><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
        <td width=90 id=textblue rowspan=3><%=messageService.getString("e3ps.message.ket_message", "02067") %><%--실제종료일--%></td>
        <td width=50 id=textblue rowspan=3><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
        <!--<td width=30 id=textblue rowspan=3>Balance</td>-->
        <td width=50 id=textblue rowspan=3><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
        <td id=textblue colspan=<%= totolDur+7%>><%=messageService.getString("e3ps.message.ket_message", "02348") %><%--일정--%></td>
    </tr>
    <tr bgcolor="#D8E0E7" align=center>
        <td id=textblue rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03046") %><%--프로젝트--%></td>
        <td id=textblue rowspan=2 colspan=2>TASK</td>

<%
    for ( int i = 0 ; i < monthStrVec.size() ; i++ ) {
        String monthStr = (String)monthStrVec.get(i);
        String countStr = (String)monthCheckTable.get(monthStr);%><td id=textblue colspan=<%=countStr%>><%=monthStr%></td>
<%  }  %>
    </tr>
    <tr bgcolor="#B4BCAD" align=center>
<%
    while ( !DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(end.getTime(),"d").trim()) ) {
        String colorStr = "color=";
        if ( start.get(Calendar.DAY_OF_WEEK) == 1 ) colorStr = colorStr + "red";
        else if ( start.get(Calendar.DAY_OF_WEEK) == 7 ) colorStr = colorStr + "blue";
%>
    <td id=textblue><font <%=colorStr%>><%=DateUtil.getDateString(start.getTime(),dayFormat)%></font></td>
<%
        start.add(Calendar.DATE,1);
    }
    start.setTime(startDate);
    start.add(Calendar.DATE,-3);

    String balanceStr = "&nbsp;";
    /*if ( project.getEndDate()!=null ) {
        if ( project.getEndDate().before(project.getLimitDate()) ) {
            balanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(project.getEndDate(),project.getLimitDate())-1)+"</font>";
        } else if ( project.getEndDate().after(project.getLimitDate()) ) {
            balanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(project.getLimitDate(),project.getEndDate())-1)+"</font>";
        }
    }*/

%>
    </tr>
    <tr bgcolor=#F4F6F3 align=center>
        <td><%=projectData.pjtName%></td>
        <td></td>
        <td></td>
        <td><%=DateUtil.getDateString(projectData.pjtPlanStartDate, "d")%></td>
        <td><%=DateUtil.getDateString(projectData.pjtPlanEndDate, "d")%></td>
        <td><%if(projectData.pjtExecEndDate != null){%><font color="red"><%=DateUtil.getDateString(projectData.pjtExecEndDate, "d")%></font><%}else{%><font color="blue"><%=messageService.getString("e3ps.message.ket_message", "03362") %><%--종료안됨--%></font><%}%></td>
        <td><%=totolDur%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%></td>
        <!--<td><%=balanceStr%></td>-->
        <td><%=projectData.pjtCompletion%>&nbsp;%</td>
<%
    Calendar projectStart = Calendar.getInstance();
    projectStart.setTime(startDate);
    Calendar projectEnd = Calendar.getInstance();
    projectEnd.setTime(endDate);

    while ( !DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(end.getTime(),"d").trim()) ) {
        String colorStr = "bgcolor=";
        if ( start.get(Calendar.DAY_OF_WEEK) == 1 ) colorStr = colorStr + "F1CFCF";
        else if ( start.get(Calendar.DAY_OF_WEEK) == 7 ) colorStr = colorStr + "C2D6EB";


        if ( DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(projectStart.getTime(),"d").trim()) ) {
            start = projectEnd;
            int duration = totolDur;

            if ( projectData.pjtCompletion> 1 ) {
                int completion = projectData.pjtCompletion;
                int beforeDuration = (duration*completion)/100;
                int afterDuration = duration - beforeDuration;
                for ( int i = 0 ; i < beforeDuration ; i++ ) {
%>
        <td bgcolor="red"></td>
<%
                }
                for ( int i = 0 ; i < afterDuration ; i++ ) {
%>
        <td bgcolor="blue"></td>
<%
                }
            } else {
                for ( int i = 0 ; i < duration ; i++ ) {
%>
        <td bgcolor="blue"></td>
<%        }
            }
        } else {
%>
        <td <%=colorStr%>></td>
<%
        }
        start.add(Calendar.DATE,1);
    }
    start.setTime(startDate);
    start.add(Calendar.DATE,-3);
%>
    </tr>
<%
    QueryResult qr = ProjectTaskHelper.manager.getChild(project);
    Kogger.debug(qr.size());
    while ( qr.hasMoreElements() ) {
        Object[] objArr = (Object[])qr.nextElement();
        E3PSTask task = (E3PSTask)objArr[0];
        E3PSTaskData taskData = new E3PSTaskData(task);
        String taskBalanceStr = "&nbsp;";
    /*  if ( taskData.taskExecEndDate!=null ) {
            if ( taskData.taskExecEndDate.before(taskData.taskPlanEndDate) ) {
                taskBalanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(task.getEndDate(),taskData.taskPlanEndDate)-1)+"</font>";
            } else if ( task.getEndDate().after(taskData.taskPlanEndDate) ) {
                taskBalanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(taskData.taskPlanEndDate,task.getEndDate())-1)+"</font>";
            }
        }*/

%>
    <tr bgcolor=#F4F6F3 align=center>
        <td></td>
        <td><%=taskData.taskName%></td>
        <td></td>
        <td><%=DateUtil.getDateString(taskData.taskPlanStartDate, "d")%></td>
        <td><%=DateUtil.getDateString(taskData.taskPlanEndDate, "d")%></td>
        <td><%if(taskData.taskExecEndDate!=null){%><font color="red"><%=DateUtil.getDateString(taskData.taskExecEndDate, "d")%></font><%}else{%><font color="blue"><%=messageService.getString("e3ps.message.ket_message", "03362") %><%--종료안됨--%></font><%}%></td>
        <td><%=taskData.taskDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%></td>
    <!--  <td><%=taskBalanceStr%></td>-->
        <td><%=taskData.taskCompletion%>&nbsp;%</td>
<%
        Calendar taskStart = Calendar.getInstance();
        taskStart.setTime(taskData.taskPlanStartDate);
        Calendar taskEnd = Calendar.getInstance();
        taskEnd.setTime(taskData.taskPlanEndDate);
        while ( !DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(end.getTime(),"d").trim()) ) {
            String colorStr = "bgcolor=";
            if ( start.get(Calendar.DAY_OF_WEEK) == 1 ) colorStr = colorStr + "F1CFCF";
            else if ( start.get(Calendar.DAY_OF_WEEK) == 7 ) colorStr = colorStr + "C2D6EB";

            if ( DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(taskStart.getTime(),"d").trim()) ) {
                start = taskEnd;
                int duration = taskData.taskDuration;
                if ( taskData.taskCompletion > 1 ) {
                    int completion = taskData.taskCompletion;
                    int beforeDuration = (duration*completion)/100;
                    int afterDuration = duration - beforeDuration;
                    for ( int i = 0 ; i < beforeDuration ; i++ ) {
%>
        <td bgcolor="red"></td>
<%
                    }
                    for ( int i = 0 ; i < afterDuration ; i++ ) {
%>
        <td bgcolor="blue"></td>
<%
                    }
                } else {
                    for ( int i = 0 ; i < duration ; i++ ) {
%>
        <td bgcolor="blue"></td>
<%          }
                }
            } else {
%>
        <td <%=colorStr%>></td>
<%
            }
            start.add(Calendar.DATE,1);
        }
        start.setTime(startDate);
        start.add(Calendar.DATE,-3);
%>
    </tr>
<%
        QueryResult subQr = ProjectTaskHelper.manager.getChild(task);
        while ( subQr.hasMoreElements() ) {
            Object[] subObjArr = (Object[])subQr.nextElement();
            E3PSTask childTask = (E3PSTask)subObjArr[0];
            E3PSTaskData  childTaskData = new E3PSTaskData(childTask);
            String childTaskBalanceStr = "&nbsp;";
            /*
            if ( childTaskData.taskExecEndDate !=null ) {
                if ( childTask.getEndDate().before(childTaskData.taskPlanEndDate) ) {
                    childTaskBalanceStr="<font color=blue>(-)"+(ProjectScheduleHelper.manager.getDuration(childTask.getEndDate(),childTaskData.taskPlanEndDate)-1)+"</font>";
                } else if ( childTask.getEndDate().after(childTaskData.taskPlanEndDate) ) {
                    childTaskBalanceStr="<font color=red>(+)"+(ProjectScheduleHelper.manager.getDuration(childTaskData.taskPlanEndDate,childTask.getEndDate())-1)+"</font>";
                }
            }*/

%>
    <tr bgcolor=#F4F6F3 align=center>
        <td></td>
        <td></td>
        <td><%=childTaskData.taskName%></td>
        <td><%=DateUtil.getDateString(childTaskData.taskPlanStartDate, "d")%></td>
        <td><%=DateUtil.getDateString(childTaskData.taskPlanEndDate, "d")%></td>
        <td><%if(childTaskData.taskExecEndDate!=null){%><font color="red"><%=DateUtil.getDateString(childTaskData.taskExecEndDate, "d")%></font><%}else{%><font color="blue"><%=messageService.getString("e3ps.message.ket_message", "03362") %><%--종료안됨--%></font><%}%></td>
        <td><%=childTaskData.taskDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%></td>
        <!--<td><%=childTaskBalanceStr%></td>-->
        <td><%=childTaskData.taskCompletion%>&nbsp;%</td>
<%
            Calendar subTaskStart = Calendar.getInstance();
            subTaskStart.setTime(childTaskData.taskPlanStartDate);
            Calendar subTaskEnd = Calendar.getInstance();
            subTaskEnd.setTime(childTaskData.taskPlanEndDate);
            while ( !DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(end.getTime(),"d").trim()) ) {
                String colorStr = "bgcolor=";
                if ( start.get(Calendar.DAY_OF_WEEK) == 1 ) colorStr = colorStr + "F1CFCF";
                else if ( start.get(Calendar.DAY_OF_WEEK) == 7 ) colorStr = colorStr + "C2D6EB";

                if ( DateUtil.getDateString(start.getTime(),"d").trim().equals(DateUtil.getDateString(subTaskStart.getTime(),"d").trim()) ) {
                    start = subTaskEnd;
                    int duration = childTaskData.taskDuration;
                    if ( childTaskData.taskDuration > 1 ) {
                        int completion = childTaskData.taskCompletion;
                        int beforeDuration = (duration*completion)/100;
                        int afterDuration = duration - beforeDuration;
                        for ( int i = 0 ; i < beforeDuration ; i++ ) {
%>
        <td bgcolor="red"></td>
<%
                        }
                        for ( int i = 0 ; i < afterDuration ; i++ ) {
%>
        <td bgcolor="blue"></td>
<%
                        }
                    } else {
                        for ( int i = 0 ; i < duration ; i++ ) {
%>
        <td bgcolor="blue"></td>
<%            }
                    }
                } else {
%>
        <td <%=colorStr%>></td>
<%
                }
                start.add(Calendar.DATE,1);
            }
            start.setTime(startDate);
            start.add(Calendar.DATE,-3);
%>
    </tr>
<%
        }
    }
%>
</table>
</body>
</html>
