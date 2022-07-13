<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.project.TaskDepartmentProjectAssign"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.E3PSTaskProjectAssignLink"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.beans.TaskUserHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="e3ps.project.TaskMemberLink"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.TaskPlanComparator"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String oid = request.getParameter("oid");

    TaskDepartmentProjectAssign ta = (TaskDepartmentProjectAssign)CommonUtil.getObject(oid);



%>
<head>
<%@include file="/jsp/common/context.jsp"%>
<title><%=messageService.getString("e3ps.message.ket_message", "00802") %><%--계획 일정 보기--%></title>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>

<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/select.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/table.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/viewObject.js"></SCRIPT>
<script>

    function ViewPlan(oid){
        openWindow('/plm/jsp/project/ProjectSchedulePlan.jsp?oid='+oid, 'ProjectSchedule',870,500);
    }

</script>



<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:12px;
    margin-right:5px;

    overflow-x:auto;
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
<style type="text/css">

.style1 {color: #FF0000}
.stype2 {color: #000000}
.stype3 {color: #daa520}
</style>
<body>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">

<!------------------------------ 본문 시작 //------------------------------>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td valign="top">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                          <tr>
                            <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00802") %><%--계획 일정 보기--%>
                            </td>
                            <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>   &gt; <%=messageService.getString("e3ps.message.ket_message", "00802") %><%--계획 일정 보기--%></td>
                            <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                          </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>

                                <td>&nbsp;</td>
                                <td class=fixLeft></td>
                                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" onClick="Javascript:self.close()"></td>
                                <td class=fixRight></td>
                                <td>&nbsp;</td>
                              </tr>
                          </table>
                    </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="space10"> </td>
                </tr>
            </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="tab_btm1"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%" >
            <col width="5%"><col width="20%"><col width="20%"><col width="20%"><col width="10%"><col width="12%"><col width="13%">
            <tr>

                <td class="tdblueM" >Level</td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td class="tdblueM" >OEM<%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02922") %><%--태스크 담당자--%></td>

            </tr>
        <%


            QueryResult qrChild = PersistenceHelper.manager.navigate(ta, "task", E3PSTaskProjectAssignLink.class, true);
            Collections.sort(qrChild.getObjectVectorIfc().getVector(), new TaskPlanComparator());

            Vector vt = new Vector();
            while(qrChild.hasMoreElements()){

                    TemplateTask task2 =(TemplateTask) qrChild.nextElement();
                    vt.add(task2);

                    QueryResult qrChild3 =  ProjectTaskHelper.manager.getChild(task2);
                    Collections.sort(qrChild3.getObjectVectorIfc().getVector(), new TaskPlanComparator());
                    boolean isCheckAddTask = false;
                    while(qrChild3.hasMoreElements()){
                        Object[] taskObj3 = (Object[])qrChild3.nextElement();
                        TemplateTask child3 = (TemplateTask) taskObj3[0];
                        vt.add(child3);
                    }



            }

        %>
            <input type=hidden name=taskSize value="<%=qrChild.size()%>">
        <%
            int planwork = 0;
            //while(qrChild2.hasMoreElements()){
            Timestamp pmPlanEndDateCheckValue = null;
            String submitCheck = "";

            for(int i = 0 ; i < vt.size() ; i++){
                Object taskObj = (Object)vt.get(i);
                TemplateTask child = (TemplateTask) taskObj;
                E3PSTaskData dependData = new E3PSTaskData( (E3PSTask)child);


                boolean isCheckAddTask = ProjectTaskHelper.manager.isChild(child);

                String taskLevel = "";






                String taskPlanStartDateStr = "";
                String taskPlanEndDatStr = "";
                String pmPlanStartDateStr = "";
                String pmPlanEndDatStr = "";
                String taskDuraction = String.valueOf(dependData.taskDuration);

                OEMProjectType et = null;
                try{
                    et = child.getOemType();

                }catch(Exception e){
                    Kogger.error(e);
                    et = null;
                    }


                String oemLevelValue = "";
                if(et != null){
                    oemLevelValue = et.getName();
                }



                if(dependData.taskPlanStartDate != null){
                    taskPlanStartDateStr = DateUtil.getDateString(dependData.taskPlanStartDate, "D");
                }
                if(dependData.taskPlanEndDate != null){
                    taskPlanEndDatStr = DateUtil.getDateString(dependData.taskPlanEndDate, "D");
                }
                if(dependData.pmPlanStartDate != null){
                    pmPlanStartDateStr = DateUtil.getDateString(dependData.pmPlanStartDate, "D");
                }
                if(dependData.pmPlanEndDate != null){
                    pmPlanEndDatStr = DateUtil.getDateString(dependData.pmPlanEndDate, "D");
                }

                String styleValue2="";
                boolean isCheckCompareTask = false;
                if(isCheckAddTask){
                    taskLevel = "2";
                    isCheckCompareTask = true;
                    styleValue2 = "style='background-color:#b0c4da;'";
                }else{
                    taskLevel = "...3";
                    pmPlanStartDateStr = taskPlanStartDateStr;
                    pmPlanEndDatStr = taskPlanEndDatStr;
                    styleValue2 = "style='background-color:#ffefd5;'";
                }


                if(isCheckCompareTask && (dependData.pmPlanEndDate != null)){
                    pmPlanEndDateCheckValue = dependData.pmPlanEndDate;

                    //Kogger.debug("pmPlanEndDateCheckValue==> " + pmPlanEndDateCheckValue);
                }

                String taskUser = "";
                boolean ischeckDept = false;
                WTUser workUser = PeopleHelper.manager.getChiefUser(ta.getDepart());

                if(child.getDepartment() != null){
                    WTUser wtuser = PeopleHelper.manager.getChiefUser(child.getDepartment());
                    if(wtuser != null && workUser != null){
                        taskUser = wtuser.getFullName();
                        if(workUser.getName().equals(wtuser.getName())){
                            ischeckDept = true;
                        }
                    }

                }

                QueryResult masterList = TaskUserHelper.manager.getMaster(child);

                PeopleData data = null;
                while ( masterList.hasMoreElements() ) {
                    Object o[]=(Object[])masterList.nextElement();
                    TaskMemberLink link = (TaskMemberLink)o[0];
                    data = new PeopleData(link.getMember().getMember());

                }
                String userOid = "";
                if(data != null){
                    userOid = data.wtuserOID;
                }

                String stylefont="style2";
                if(!isCheckCompareTask && (dependData.taskPlanEndDate != null) && (pmPlanEndDateCheckValue != null)){
                    if(dependData.taskPlanEndDate.after(pmPlanEndDateCheckValue)){
                    stylefont = "style1";
                    submitCheck = "true";
                    }
                }


            if(ischeckDept || isCheckAddTask){

        %>

            <tr>
                <td  class="tdwhiteL" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"> <%=taskLevel%></span></td>
                <td  class="tdwhiteL" <%=styleValue2%> title="<%=dependData.taskName%>" >&nbsp;<span class="<%=stylefont%>"><%=dependData.taskName%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=pmPlanStartDateStr%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=pmPlanEndDatStr%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=taskDuraction%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<span class="<%=stylefont%>"><%=oemLevelValue%></span></td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;
                <%
                if(isCheckAddTask){

                }else{
                    if(data == null){
                        out.print(" ");
                    }else{
                    out.print(StringUtil.checkNull(data.name));
                    %>

                    <%}%>
                <%} %>
                </td>

            </tr>


        <%

            planwork++;
            }
            }
        %>

        </table>

<!------------------------------ 본문 끝 //------------------------------>

        </td>
    </tr>
</table>

</body>
</html>
