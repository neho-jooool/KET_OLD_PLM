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
<%@page import="e3ps.common.query.SearchUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
    String oid = request.getParameter("oid");

    E3PSProject pjt = (E3PSProject)CommonUtil.getObject(oid);
    TaskDepartmentProjectAssign ta = null;


%>
<head>
<%@include file="/jsp/common/context.jsp"%>
<title><%=messageService.getString("e3ps.message.ket_message", "03087") %><%--프로젝트 일정 수립 진행 현황--%></title>
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
                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03088") %><%--프로젝트 일정 수립 진행 현황 보기--%>
                            </td>
                            <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>   &gt; <%=messageService.getString("e3ps.message.ket_message", "03088") %><%--프로젝트 일정 수립 진행 현황 보기--%></td>
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
            QuerySpec qs = new QuerySpec(TaskDepartmentProjectAssign.class);
            qs.appendWhere(new SearchCondition(TaskDepartmentProjectAssign.class, "projectReference.key.id","=", CommonUtil.getOIDLongValue(pjt)), new int[] {0});

            SearchUtil.setOrderBy(qs, TaskDepartmentProjectAssign.class, 0, "thePersistInfo.modifyStamp", "modifyStamp", true);


            QueryResult qr = PersistenceHelper.manager.find(qs);
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

                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "02179") %><%--완료일--%></td>
                <td class="tdblueM" ><%=messageService.getString("e3ps.message.ket_message", "00809") %><%--계획보기--%></td>
            </tr>



            <%
            Object[] object = null;
            while(qr.hasMoreElements()){
                object = (Object[]) qr.nextElement();
                ta =(TaskDepartmentProjectAssign)object[0];

                String depart = "";
                String departUser = "";
                String state = "";
                String endDate = "-";

                if(ta.getDepart() != null){
                    depart = ta.getDepart().getName();
                    WTUser wtuser = PeopleHelper.manager.getChiefUser(ta.getDepart());
                    if(wtuser != null){
                        departUser = wtuser.getFullName();

                    }
                }
                state = ta.getLifeCycleState().getDisplay(Locale.KOREA);
                if(state.equals("완료됨")){
                    endDate = DateUtil.getDateString(ta.getModifyTimestamp(), "d");
                }
            %>
            <tr>
                <td  class="tdwhiteM">&nbsp; <%=depart%></td>
                <td  class="tdwhiteM">&nbsp; <%=departUser%></td>
                <td  class="tdwhiteM">&nbsp; <%=state%></td>
                <td  class="tdwhiteM">&nbsp; <%=endDate%></td>
                <td  class="tdwhiteM">&nbsp; <input type=button value="<%=messageService.getString("e3ps.message.ket_message", "00809") %><%--계획보기--%>" onclick="JavaScript:ViewPlan('<%=CommonUtil.getOIDString(ta)%>');"></td>

            </tr>


            <%}%>
            </table>

<!------------------------------ 본문 끝 //------------------------------>

        </td>
    </tr>
</table>

</body>
</html>
