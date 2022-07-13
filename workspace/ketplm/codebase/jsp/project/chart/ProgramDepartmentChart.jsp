<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Vector"%>

<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.project.Role"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>

<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.web.QueryBroker"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.project.*"%>
<%@page import="e3ps.project.beans.*"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    boolean isAdmin = CommonUtil.isAdmin();

    String oid = StringUtil.trimToEmpty(request.getParameter("oid"));

    String command = StringUtil.trimToEmpty( request.getParameter("command") );
    String searchYear = StringUtil.trimToEmpty( request.getParameter("searchYear") );
    String searchMonth = StringUtil.trimToEmpty( request.getParameter("searchMonth") );

    Calendar cal = Calendar.getInstance();
    cal.setTime( new Date() );

    int currentYear = cal.get( Calendar.YEAR );
    int currentMonth = cal.get( Calendar.MONTH ) + 1;

    if( "".equals(command) && (searchYear.length() == 0) ) {
        searchYear = String.valueOf(currentYear);
    }
    if( "".equals(command) && (searchMonth.length() == 0) ) {
        searchMonth = String.valueOf(currentMonth);
    }

    if(searchYear.length() == 0) {
        searchMonth = "";
    }


    HashMap condmap = new HashMap();
    condmap.put("progOid", oid);
    condmap.put("yyyy", searchYear);
    condmap.put("mm", searchMonth);

    QueryResult result = (QueryResult)ProjectChartUtil.programDeptChart(condmap);
    //out.print("<br>XXXXX   " + result.size());
%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03038") %><%--프로그램 별 부서 현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/viewObject.js"></script>
<script language=JavaScript>
function search() {
    onProgressBar();

    document.forms[0].command.value = "search";
    document.forms[0].method = "post";
    document.forms[0].action = "/plm/jsp/project/chart/ProgramDepartmentChart.jsp";
    document.forms[0].submit();
}

    function viewProgram(oid) {

        var url = "/plm/jsp/project/ViewProgram.jsp?oid="+oid+"&popup=popup";
        getOpenWindow(url, "", "800", "600");
    }

    function projectViewpopup(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
    }

    function viewTask(oid){
        var url = "/plm/jsp/project/TaskView.jsp?oid="+oid;
        openOtherName(url,"","800","600","status=1,scrollbars=yes,resizable=1");
    }

</script>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;

    overflow-x:auto;
    overflow-y:auto;
    scrollbar-face-color:FFFFFF;
    scrollbar-shadow-color:929292;
    scrollbar-highlight-color:929292;
    scrollbar-3dlight-color:FFFFFF;
    scrollbar-darkshadow-color:FFFFFF;
    scrollbar-track-color:FFFFFF;
    scrollbar-arrow-color:929292;
}

ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
    font-family: verdana, arial, Helvetica, sans-serif;
}

li { margin: 0; }
// -->
</style>
<form>
<input type='hidden' name='command'>
<input type='hidden' name='oid' value='<%=oid %>'>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                  <tr>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03038") %><%--프로그램 별 부서 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03038") %><%--프로그램 별 부서 현황--%></td>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                  </tr>
                </table>
                <!-- Search -->
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width=""><%=messageService.getString("e3ps.message.ket_message", "01176") %><%--년도--%> :&nbsp;</td>
                                <td width="">
                                    <select name="searchYear" size="1" style="width:70">
                                        <option value=''>[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                                        <%  for(int i = -2; i < 3; i++) {
                                                int cyear = Integer.parseInt( ((searchYear.length()==0)? ""+currentYear:searchYear) ) + i;
                                        %>
                                                <option value='<%=cyear%>' <%=(Integer.parseInt(((searchYear.length()==0)? "0":searchYear))==cyear)? "selected":"" %>><%=cyear%></option>
                                        <% } %>
                                    </select>
                                </td>
                                <td width="">&nbsp;&nbsp;</td>
                                <td width=""><%=messageService.getString("e3ps.message.ket_message", "02224") %><%--월--%> :&nbsp;</td>
                                <td width="">
                                    <select name="searchMonth" size="1" style="width:70">
                                        <option value=''>[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
                                        <%  for(int i = 1; i < 13; i++) { %>
                                                <option value='<%=i%>' <%=(Integer.parseInt(((searchMonth.length()==0)? "0":searchMonth))==i)? "selected":""%>><%=i%></option>
                                        <% } %>
                                    </select>
                                </td>
                                <td>
                                    &nbsp;&nbsp;
                                    <a href="javascript:search();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_search.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%>" width="52" height="20" border="0">
                                    </a>
                                    &nbsp;&nbsp;
                                    <a href="#" onClick="javascript:self.close();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </tr>
                </table>
                <%
                    TreeMap progmap = new TreeMap();
                    TreeMap pjtmap = new TreeMap();
                    TreeMap teammap = new TreeMap();
                    HashMap pjtteam = new HashMap();

                    while( (result != null) && result.hasMoreElements()) {
                        Object object[] = (Object[])result.nextElement();

                        ProjectManager rProgram = (ProjectManager)object[0];
                        JELProject rProject = (JELProject)object[1];
                        JELTask rTask = (JELTask)object[2];

                        //프로그램
                        if(!progmap.containsKey(rProgram.getName())) {
                            progmap.put(rProgram.getName(), rProgram);
                        }

                        //프로그램의 프로젝트's
                        TreeMap tmap = (TreeMap)pjtmap.get(rProgram.getName());
                        if(tmap == null) {
                            tmap = new TreeMap();
                        }

                        tmap.put(rProject.getPjtNo(), rProject);
                        pjtmap.put(rProgram.getName(), tmap);

                        //팀
                        Department rTeam = null;
                        try {
                            rTeam = rTask.getDepartment();
                        }
                        catch(Exception e) {
                            Kogger.error(e);
                            rTeam = null;
                        }

                        String teamCode = "";
                        if(rTeam == null) {
                            teamCode = "NONE";
                        } else {
                            teamCode = rTeam.getCode();
                        }
                        teammap.put(teamCode, teamCode);

                        //프로젝트의 팀's
                        HashMap pteam = (HashMap)pjtteam.get(rProject.getPjtNo());
                        if(pteam == null) {
                            pteam = new HashMap();
                        }
                        ArrayList ttask = (ArrayList)pteam.get(teamCode);
                        if(ttask == null) {
                            ttask = new ArrayList();
                        }
                        ttask.add(rTask);
                        pteam.put(teamCode, ttask);
                        pjtteam.put(rProject.getPjtNo(), pteam);
                    }

                    //out.println("<br>프로그램 : " + progmap.size());
                    //out.println("<br>프로젝트 : " + pjtteam.size());
                    //out.println("<br>팀 : " + teammap.size());
                %>

                <%
                    int teamCount = 1;
                    if(teammap.size() > 0) {
                        teamCount = teammap.size();
                    }
                %>
                <!-- Search -->
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td  class="tab_btm1"></td>
                    </tr>
                </table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <col width="15%">
                <col width="15%">
                <%  for(int i = 0; i < teamCount; i++) { %>
                        <col width="*">
                <%  } %>
                <col width="5%">
                    <tr>
                        <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
                        <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                        <td class="tdblueM" colspan="<%=teamCount%>">
                            <%=messageService.getString("e3ps.message.ket_message", "01557") %><%--부서별 TASK현황--%> [ <font color="green"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></font> , <font color="blue"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></font> , <font color="red"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></font>, <font color="#9900FF"><%=messageService.getString("e3ps.message.ket_message", "03377") %><%--지연 진행--%></font>]
                        </td>
                        <td class="tdblueM0" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02706") %><%--지연Count--%></td>
                    </tr>
                    <tr>
                        <%
                            Iterator titr = teammap.values().iterator();
                            while(titr.hasNext()) {
                                String teamCode = (String)titr.next();
                                if("NONE".equals(teamCode)) {
                                    continue;
                                }
                                Department d = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(teamCode);
                                String dname = "";
                                if(d == null) {
                                    dname = teamCode;
                                } else {
                                    dname = d.getName();
                                }
                        %>
                                <td class="tdblueM"><%=dname%></td>
                        <%  } %>
                        <%  if(teammap.containsKey("NONE")) { %><td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01136") %><%--기타--%></td><%  } %>
                        <%  if(teammap.size()==0) { %><td class="tdblueM">&nbsp;</td><%  } %>
                    </tr>
                    <%
                        if(progmap.size() == 0) {
                            if(oid.length()==0) {
                    %>
                                <tr>
                                    <td class="tdwhiteM0" colspan='4'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
                                </tr>
                    <%    } else {
                                ProjectManager rProgram = (ProjectManager)CommonUtil.getObject(oid);
                    %>
                                <tr>
                                    <td class="tdwhiteM"><a href="javascript:viewProgram('<%=CommonUtil.getOIDString(rProgram)%>')">
                                    <%=rProgram.getName()%></a></td>
                                    <td class="tdwhiteM0" colspan='3'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
                                </tr>
                    <%
                            }
                        }
                    %>

                    <%
                        Iterator progitr = progmap.values().iterator();
                        while(progitr.hasNext()) {
                            ProjectManager rProgram = (ProjectManager)progitr.next();

                            TreeMap pjts = (TreeMap)pjtmap.get(rProgram.getName());
                            if(pjts == null) {
                                pjts = new TreeMap();
                            }

                            if(pjts.size()==0) {
                    %>
                                <tr>
                                    <td class="tdwhiteM">
                                    <a href="javascript:viewProgram('<%=CommonUtil.getOIDString(rProgram)%>')">
                                    <%=rProgram.getName()%></a></td>
                                    <td class="tdwhiteM0" colspan='3'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
                                </tr>
                    <%
                                continue;
                            }

                            int rowidx = 0;
                            //프로젝트 ............................................
                            Iterator pjtitr = pjts.values().iterator();
                            while(pjtitr.hasNext()) {
                                JELProject rProject = (JELProject)pjtitr.next();

                                if(rowidx++ == 0) {
                                out.println("<tr><td class='tdwhiteM' rowspan='"+pjts.size()+"' valign='top'><a href=javascript:viewProgram('"
                                +CommonUtil.getOIDString(rProgram)+"') >"+rProgram.getName()+"</a></td>");
                                }else{
                                    out.println("<tr>");
                                }

                                int delayCount = 0;

                    %>      <td class="tdwhiteM" valign='top'><a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(rProject)%>')">
                                <%=rProject.getPjtName()%></a></td>
                    <%      HashMap pteam =  (HashMap)pjtteam.get(rProject.getPjtNo());

                                Iterator dtitr = teammap.values().iterator();
                                while(dtitr.hasNext()) {
                                    String teamCode = (String)dtitr.next();
                                    if("NONE".equals(teamCode)) {
                                        continue;
                                    }

                    %>        <td class="tdwhiteM" align="left" valign="top">
                                        <div align='left'>
                                            <UL>
                    <%
                                    ArrayList ttask = (ArrayList)pteam.get(teamCode);
                                    if(ttask == null) {
                                        ttask = new ArrayList();
                                    }

                                    if(ttask.size()==0) {
                                        out.println("&nbsp;");
                                    }

                                    for(int i = 0; i < ttask.size(); i++) {
                                        JELTask rTask = (JELTask)ttask.get(i);
                                        ExtendScheduleData schedule = (ExtendScheduleData) rTask.getTaskSchedule().getObject();

                                        String fcolor="blue";
                                        if(rTask.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE){
                                            fcolor="green";
                                        } else {
                                            //if(JELTaskData.getPreferComp(rTask) < 15){
                                            //  fcolor="blue";
                                            //} else {
                                                if (JELTaskData.getDifferDateGap(rTask) < 0 ){
                                                    if(rTask.getTaskCompletion()>0){
                                                        fcolor="##9900FF";
                                                    }else{
                                                        fcolor="red";
                                                        delayCount++;
                                                    }
                                                }
                                            //}
                                        }
                    %>
                                        <LI>▷<a href="javascript:viewTask('<%=CommonUtil.getOIDString(rTask)%>')">
                                        <font color="<%=fcolor%>"><%=rTask.getTaskName()%></font>
                                        <%  if(isAdmin) { %>
                                            [<%=(schedule.getPlanStartDate()==null)? "":DateUtil.getDateString(schedule.getPlanStartDate(), "d")%>&nbsp;~&nbsp;<%=(schedule.getPlanEndDate()==null)? "":DateUtil.getDateString(schedule.getPlanEndDate(), "d")%>]
                                        <%  } %>
                    <%        }
                    %>
                                            </UL>
                                        </div>
                                    </td>
                    <%      }//end task

                                if(teammap.containsKey("NONE")) {
                    %>
                                <td class="tdwhiteM" align="left" valign="top">
                                    <div align='left'>
                                        <UL>
                                <%
                                    ArrayList ttask = (ArrayList)pteam.get("NONE");
                                    if(ttask == null) {
                                        ttask = new ArrayList();
                                    }

                                    if(ttask.size()==0) {
                                        out.println("&nbsp;");
                                    }

                                    for(int i = 0; i < ttask.size(); i++) {
                                        JELTask rTask = (JELTask)ttask.get(i);
                                        ExtendScheduleData schedule = (ExtendScheduleData) rTask.getTaskSchedule().getObject();

                                        String fcolor="blue";
                                        if(rTask.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE){
                                            fcolor="green";
                                        } else {
//                      if(JELTaskData.getPreferComp(rTask) < 15){
                                            //  fcolor="blue";
                                            //} else {
                                                if (JELTaskData.getDifferDateGap(rTask) < 0 ){
                                                    if(rTask.getTaskCompletion()>0){
                                                        fcolor="##9900FF";
                                                    }else{
                                                        fcolor="red";
                                                        delayCount++;
                                                    }
                                                }
                                            //}
                                        }
                    %>
                                        <LI>▷<a href="javascript:viewTask('<%=CommonUtil.getOIDString(rTask)%>')">
                                        <font color="<%=fcolor%>"><%=rTask.getTaskName()%></font>
                                        <%  if(isAdmin) { %>
                                            [<%=(schedule.getPlanStartDate()==null)? "":DateUtil.getDateString(schedule.getPlanStartDate(), "d")%>&nbsp;~&nbsp;<%=(schedule.getPlanEndDate()==null)? "":DateUtil.getDateString(schedule.getPlanEndDate(), "d")%>]
                                        <%  } %>
                    <%        }
                    %>
                                        </UL>
                                    </div>
                                </td>
                    <%
                                }
                    %>
                                <td class="tdwhiteM0" valign="top"><%=delayCount%></td>
                    <%      out.println("</tr>");
                            }//end project
                        }//end program
                    %>
                </table>

                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td class="space20"> </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
