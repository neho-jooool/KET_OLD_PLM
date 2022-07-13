<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.groupware.company.PeopleHelper"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String oid = request.getParameter("oid");
    E3PSTask child = (E3PSTask)CommonUtil.getObject(oid);

%>
<head>
<%@include file="/jsp/common/context.jsp"%>
<title><%=messageService.getString("e3ps.message.ket_message", "00054") %><%--3레벨 태스크 정보--%></title>
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
        openWindow('/plm/jsp/project/ProjectSchedulePlan.jsp?oid='+oid, '',870,500);
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00054") %><%--3레벨 태스크 정보--%>
                            </td>
                            <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>   &gt; <%=messageService.getString("e3ps.message.ket_message", "00054") %><%--3레벨 태스크 정보--%></td>
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
            <%

            QueryResult qrChild3 =  ProjectTaskHelper.manager.getChild(child);



            %>
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
            <col width="20%"><col width="20%"><col width="20%"><col width="20%"><col width="20%">

            <tr>

                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01562") %><%--부서장--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
            </tr>

            <%
            while(qrChild3.hasMoreElements()){
                Object[] taskObj3 = (Object[])qrChild3.nextElement();
                TemplateTask child3 = (TemplateTask) taskObj3[0];


                E3PSTaskData dependData3 = new E3PSTaskData( (E3PSTask)child3);
                String taskPlanStartDateStr3 = "";
                String taskPlanEndDatStr3 = "";
                if(dependData3.taskPlanStartDate != null){
                    taskPlanStartDateStr3 = DateUtil.getDateString(dependData3.taskPlanStartDate, "D");
                }
                if(dependData3.taskPlanEndDate != null){
                    taskPlanEndDatStr3 = DateUtil.getDateString(dependData3.taskPlanEndDate, "D");
                }
                String userName = "";
                String departName = "";
                try{
                    if(child3.getDepartment() != null){
                        WTUser wu = PeopleHelper.manager.getChiefUser(child3.getDepartment());
                        if(wu != null){
                            userName = wu.getFullName();
                        }
                        departName = child3.getDepartment().getName();
                    }
                }catch(Exception e){Kogger.error(e);}

            %>
                <tr>
                <td  class="tdwhiteM">&nbsp; <%=child3.getTaskName()%></td>
                <td  class="tdwhiteM">&nbsp; <%=departName%></td>
                <td  class="tdwhiteM">&nbsp; <%=userName%></td>
                <td  class="tdwhiteM">&nbsp; <%=taskPlanStartDateStr3%></td>
                <td  class="tdwhiteM">&nbsp; <%=taskPlanEndDatStr3%></td>

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
