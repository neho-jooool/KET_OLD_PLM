<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.project.TaskDepartmentProjectAssign"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.project.beans.TaskPlanComparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.project.beans.OEMTypeHelper"%>
<%@page import="e3ps.project.ProjectManager"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="java.util.ArrayList"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>


<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String oid = request.getParameter("oid");

    E3PSProject pjt = (E3PSProject)CommonUtil.getObject(oid);
    TaskDepartmentProjectAssign ta = null;
    ProjectManager pm = null;
    try{
        pm = pjt.getManager();
    }catch(Exception e){
	Kogger.error(e);
    }


    // Program 의 OEM 정보
        Vector oemV = new Vector();
    if(pm!=null){
        OEMProjectType typeObject = (OEMProjectType)pm.getOemType();
        ArrayList oem_al = new ArrayList();
        oem_al = OEMTypeHelper.getOEMTypeTree(typeObject);
        for(int i = 0 ; i < oem_al.size() ; i++){

            OEMProjectType oemType = (OEMProjectType)CommonUtil.getObject((String)oem_al.get(i));

            oemV.add(oemType.getCode());
        }
    }

    //   Program 의 OEM 정보

%>
<head>
<%@include file="/jsp/common/context.jsp"%>
<title><%=messageService.getString("e3ps.message.ket_message", "03086") %><%--프로젝트 일정 계획--%></title>
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
    function projectDepartSchedule(oid){
        openWindow('/plm/jsp/project/ProjectSchedule.jsp?oid='+oid, 'ProjectSchedule',700,500);
    }

    function taskView3(oid){
        openWindow('/plm/jsp/project/Project3LevelView.jsp?oid='+oid, 'ProjectSchedule',700,500);
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
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03086") %><%--프로젝트 일정 계획--%>
                        </td>
                        <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>   &gt; <%=messageService.getString("e3ps.message.ket_message", "03086") %><%--프로젝트 일정 계획--%></td>
                        <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                      </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td>
                    <font color="red">■</font> <%=messageService.getString("e3ps.message.ket_message", "00379") %><%--PM 일정 벗어남--%>&nbsp;<font color="#b0c4da">■</font> <%=messageService.getString("e3ps.message.ket_message", "02519") %><%--정상 일정--%>&nbsp;<font color="#f0e68c">■</font> <%=messageService.getString("e3ps.message.ket_message", "00346") %><%--OEM 일정 벗어남--%>
                </td>
                <td align="right">
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>&nbsp;</td>
                            <td class=fixLeft></td>
                            <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01560") %><%--부서별일정수립현황--%>" onClick="javascript:projectDepartSchedule('<%=CommonUtil.getOIDString(pjt)%>');"></td>
                            <td class=fixRight></td>
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
            <col width="10%"><col width="35%"><col width="15%"><col width="15%"><col width="10%"><col width="15%">
            <tr>
                <td class="tdblueM" >Level</td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00343") %><%--OEM 개발 단계--%></td>
            </tr>
        <%
            QueryResult qrChild = ProjectTaskHelper.manager.getChild(pjt);
            Collections.sort(qrChild.getObjectVectorIfc().getVector(), new TaskPlanComparator());
            QueryResult qrChild2 = null;
            Vector vt = new Vector();
            while(qrChild.hasMoreElements()){
                Object[] taskObj = (Object[]) qrChild.nextElement();
                TemplateTask child = (TemplateTask) taskObj[0];
                vt.add(child);
                qrChild2 = ProjectTaskHelper.manager.getChild(child);
                Collections.sort(qrChild2.getObjectVectorIfc().getVector(), new TaskPlanComparator());
                Object[] taskObj2 = null;
                while(qrChild2.hasMoreElements()){
                    taskObj2 = (Object[]) qrChild2.nextElement();
                    TemplateTask child2 = (TemplateTask) taskObj2[0];
                    vt.add(child2);
                }
            }

        %>
            <input type=hidden name=taskSize value="<%=qrChild2.size()%>">
        <%
            int planwork = 0;
            for(int i = 0 ; i < vt.size() ; i++){
                Object taskObj = (Object)vt.get(i);
                TemplateTask child = (TemplateTask) taskObj;
                ExtendScheduleData schedule = (ExtendScheduleData)child.getTaskSchedule().getObject();
                String styleValue="";
                String taskLevel = "";
                String styleValue2="";
                boolean checkLevel = false;
                if(child.getParent() == null){
                    taskLevel = "1";
                    styleValue2 = "style='background-color:#f4a460;'";
                }else{
                    taskLevel = "...2";
                    checkLevel = true;
                    styleValue2 = "style='background-color:#b0c4da;'";
                }
                String taskPlanStartDateStr = "";
                String taskPlanEndDatStr = "";
                String pmPlanStartDateStr = "";
                String pmPlanEndDatStr = "";
                String taskDuraction = String.valueOf(DateUtil.getDuration(schedule.getPlanStartDate(), schedule.getPlanEndDate()) + 1);
                OEMProjectType et = null;
                try{
                    et = child.getOemType();

                }catch(Exception e){
                    Kogger.error(e);
                    et = null;
                    }
                String oemLevelValue = "";
                Timestamp oem_ts = null;
                String oemDsEndStr = "";
                OEMPlan oplan = null;
                Timestamp pmPlanEnd_ts = null;

                if(et != null){

                    oemLevelValue = et.getName();

                    if(oemV.size()>0 && !oemV.contains(et.getCode())){
                        oemLevelValue = "";
                    }
                    if(pm != null){
                        oplan = OEMTypeHelper.getOEMPlan(pm, et);
                        if(oplan != null){
                            oem_ts = oplan.getDsEndDate();
                        }
                        if(oem_ts != null){
                            oemDsEndStr = DateUtil.getDateString(oem_ts, "D");
                        }
                    }
                }

                if(schedule.getPlanStartDate() != null){
                    taskPlanStartDateStr = DateUtil.getDateString(schedule.getPlanStartDate(), "D");
                }
                if(schedule.getPlanEndDate() != null){
                    taskPlanEndDatStr = DateUtil.getDateString(schedule.getPlanEndDate(), "D");
                }
                if(schedule.getPmStartDate() != null){
                    pmPlanStartDateStr = DateUtil.getDateString(schedule.getPmStartDate(), "D");
                }
                if(schedule.getPmEndDate() != null){
                    pmPlanEndDatStr = DateUtil.getDateString(schedule.getPmEndDate(), "D");
                }

                String child2Str = "";
                QueryResult qrChild3 =  ProjectTaskHelper.manager.getChild(child);
                boolean isCheckAddTask = false;
                String titleTaskName = "";

                while(qrChild3.hasMoreElements()){
                    Object[] taskObj3 = (Object[])qrChild3.nextElement();
                    TemplateTask child3 = (TemplateTask) taskObj3[0];
                    try{
                        if(child3.getDepartment() != null){
                            WTUser wu = PeopleHelper.manager.getChiefUser(child3.getDepartment());
                            String userName = "";
                            if(wu != null){
                                isCheckAddTask = true;
                                userName = wu.getFullName();
                            }
                        }
                    }catch(Exception e){Kogger.error(e);}
                }
                if(isCheckAddTask){child2Str = CommonUtil.getOIDString(child);}

                String msgState = "";
                String stylefont="style2";
                String pmStyleColor = "color:black;";
                if(schedule.getPmEndDate() != null){
                    if(schedule.getPlanEndDate().after(schedule.getPmEndDate())){
                        styleValue2 = "style='color:red;background-color:#b0c4da'";
                    }
                    if(oem_ts != null){
                        if(schedule.getPmEndDate().after(oem_ts)){
                            styleValue2 = "style='background-color:#f0e68c;'";
                        }
                    }
                }
        %>
            <tr>
                <%if(checkLevel){ %>
                <td  class="tdwhiteL"  <%=styleValue2%> >&nbsp;<%=taskLevel%></td>
                <td  class="tdwhiteL"  <%=styleValue2%>  >&nbsp;
                <a href="javascript:taskView3('<%=CommonUtil.getOIDString(child)%>');">
                <%=child.getTaskName()%>
                </a>
                </td>
                <td  class="tdwhiteM" <%=styleValue2%> >&nbsp;<%=pmPlanStartDateStr%>
                </td>
                <td  class="tdwhiteM" <%=styleValue2%>>&nbsp;<%=pmPlanEndDatStr%>
                </td>
                <td  class="tdwhiteM"  <%=styleValue2%> >&nbsp;<%=taskDuraction%></td>
                <td  class="tdwhiteM"  <%=styleValue2%> >&nbsp;<%=oemLevelValue%></td>
                <%
                planwork++;
                }else{ %>

                <td  class="tdwhiteL"  style='background-color:#f4a460;' >&nbsp;<%=taskLevel%></td>
                <td  class="tdwhiteL"  style='background-color:#f4a460;' >&nbsp;<%=child.getTaskName()%></td>
                <td  class="tdwhiteM"  style='background-color:#f4a460;' >&nbsp;</td>
                <td  class="tdwhiteM"  style='background-color:#f4a460;' >&nbsp;</td>
                <td  class="tdwhiteM"  style='background-color:#f4a460;' >&nbsp;</td>
                <td  class="tdwhiteM"  style='background-color:#f4a460;' >&nbsp;</td>
                <%} %>
            </tr>
        <%

            }
        %>
        </table>

<!------------------------------ 본문 끝 //------------------------------>

        </td>
    </tr>
</table>

</body>
</html>
