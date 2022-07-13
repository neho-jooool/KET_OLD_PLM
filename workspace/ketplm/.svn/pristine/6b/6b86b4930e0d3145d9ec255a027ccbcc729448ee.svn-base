<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="e3ps.project.JELProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.ProjectManager"%>
<%@page import="e3ps.project.beans.ProjectManagerData"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.ProjectIssue"%>
<%@page import="e3ps.project.beans.ProjectIssueData"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String oid = request.getParameter("oid");
String managerOid = request.getParameter("managerOid");

JELProject pjt = (JELProject) CommonUtil.getObject(oid);
JELProjectData pjtData = new JELProjectData(pjt);

ProjectManager manager = (ProjectManager) CommonUtil.getObject(managerOid);
ProjectManagerData managerData = new ProjectManagerData(manager);
%>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03071") %><%--프로젝트 별 ISSUE 현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript>
function viewIssue(v) {
	var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
	openOtherName(url,"viewIssue","900","700","status=no,scrollbars=yes,resizable=yes");
}
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<input type='hidden' name='oid' value='<%=oid %>'>
<input type='hidden' name='managerOid' value='<%=managerOid %>'>

	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td valign="top" style="padding:0px 0px 0px 0px">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
				  <tr>
					<td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03071") %><%--프로젝트 별 ISSUE 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03071") %><%--프로젝트 별 ISSUE 현황--%></td>
					<td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
				  </tr>
				</table>
				<!-- Search -->
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<a href="#" onClick="javascript:self.close();">
									<img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
									</a>
								</td>
							</tr>
						</table>
					</tr>
				</table>
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
				<col width='10%'><col width='10%'><col width='10%'><col width='15%'>
				<col width='15%'><col width='5%'><col width='10%'><col width='5%'>
					<tr>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
						<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
						<td class="tdblueM">TASK</td>
						<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00824") %><%--계획일자--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02066") %><%--실제일자--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02725") %><%--진척율--%></td>
						<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01211") %><%--담당자/담당부서--%></td>
						<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00282") %><%--ISSUE항목--%></td>
					</tr>
					<%
					Vector issueVec = managerData.getIssueForDepartment(manager, pjt);

					if(issueVec.size() > 0) {
						for(int i = 0; i < issueVec.size(); i++) {
							ProjectIssue issue = (ProjectIssue) issueVec.get(i);
							ProjectIssueData issueData = new ProjectIssueData(issue);

							JELTask task = (JELTask) issueData.task;
							JELTaskData taskData = new JELTaskData(task);

							String tName = "";
							if(task.getParent() != null) {
								tName = ((JELTask)task.getParent()).getTaskName();
								if(task.getParent().getParent() != null) {
									tName = ((JELTask)task.getParent().getParent()).getTaskName();
								}
							}

							String departName = "";
							if(task.getDepartment() != null) {
								departName = task.getDepartment().getName();
							}
					%>
					<tr>
						<td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
						<td class="tdwhiteM">&nbsp;<%=tName %><%//=((JELTask)task.getParent().getParent()).getTaskName() %></td>
						<td class="tdwhiteM">&nbsp;<%=taskData.taskName %></td>
						<td class="tdwhiteM">&nbsp;
							<%=DateUtil.getDateString(taskData.taskPlanStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskPlanEndDate, "d") %>
						</td>
						<td class="tdwhiteM">&nbsp;
							<%=DateUtil.getDateString(taskData.taskExecStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskExecEndDate, "d") %>
						</td>
						<td class="tdwhiteM">&nbsp;<%=taskData.taskCompletion %> %</td>
						<td class="tdwhiteM">&nbsp;<%=issueData.managerUser.name %>&nbsp;/&nbsp;<%=departName %><%//=task.getDepartment().getName() %></td>
						<td class="tdwhiteM">&nbsp;
							<a href="javascript:viewIssue('<%=issueData.oid %>')">
							<B><%=issueData.subject %></B>
							</a>
						</td>
					</tr>
					<%
						}
					} else {
					%>
					<tr>
						<td class="tdwhiteM" colspan=8>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00751") %><%--결과가 없습니다--%>.</td>
					</tr>
					<%
					}
					%>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
