<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import ="e3ps.project.*,
                 e3ps.common.util.*,
                 e3ps.project.beans.*,
                 e3ps.groupware.company.*,
                 java.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<title>Insert title here</title>
<script language="JavaScript">
<!--
function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","450","status=no,scrollbars=no,resizable=no");
}
-->
</script>
<style type="text/css">
<!--
body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
}
-->
</style>
</head>
<body>
<table height="37" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding:0px 0px 0px 0px">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
              <tr>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03053") %><%--프로젝트 TargetItem 목록--%></td>
                <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03053") %><%--프로젝트 TargetItem 목록--%> </td>
                <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
              </tr>
            </table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td valign="top" style="padding-left:12">
<!------------------------------ 본문 시작 //------------------------------>
<%
           String oid = request.getParameter("oid");
           E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
           Vector vector = ProjectHelper.getTargetItemsWithPath(project);
%>
            <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm1"> </td>
                </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tdblueM">NO</td>
                    <td class="tdblueM">TASK</td>
                    <td class ="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02725") %><%--진척율--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00803") %><%--계획 종료--%></td>
                    <td class="tdblueM">Target</td>
                    <td class="tdblueM">Current Status</td>
                </tr>
    <%
        for(int i = 0; i < vector.size(); i++){
            TargetItemData data = (TargetItemData)vector.get(i);
            E3PSTaskData taskData = new E3PSTaskData(data.getTask());
            String path = data.getPath();
            TargetItem item = data.getTargetItem();
            String target = item.getTarget();
            String value = item.getValue();
            if(value == null){
                value = "";
            }
        %>
                <tr>
                    <td class="tdwhiteM">&nbsp;<%= i+1 %></td>
                    <td class="tdwhiteL" title = "<%=path%>" >
                        <div style="width:250;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr>
                            &nbsp;<img src="/plm/portal/icon/task.gif"><a href="/plm/jsp/project/TaskView.jsp?oid=<%=taskData.E3PSTaskOID%>">
                            <%=taskData.taskName%></a>
                            </nobr>
                        </div>
                    </td>
                    <td class="tdwhiteM">&nbsp;<%=taskData.taskCompletion + " %"%></td>
                    <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%></td>
                    <td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%></td>
                    <td class="tdwhiteL" title="<%=target%>">
                        <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr>
                            &nbsp;<%=target%>
                            </nobr>
                        </div>
                    </td>
                    <td class="tdwhiteL" title="<%=value%>">
                        <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                            <nobr>
                            &nbsp;<%=value%>
                            </nobr>
                        </div>
                    </td>
                </tr>
    <%} %>
    <%if(vector.size() == 0) {%>
                <tr>
                    <td colspan="7" class=tdwhiteM0><font color="red"><%=messageService.getString("e3ps.message.ket_message", "00709") %><%--검색된 항목이 없습니다--%></td>
                </tr>
    <%} %>
            </table>
</center>
</body>
</html>
