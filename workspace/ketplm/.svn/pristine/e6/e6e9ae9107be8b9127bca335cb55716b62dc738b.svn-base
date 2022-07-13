<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.ProjectManager"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectManagerData"%>
<%@page import="e3ps.project.JELProject"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.TaskUserHelper"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.TaskMemberLink"%>
<%@page import="e3ps.groupware.company.PeopleData"%>

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
<title><%=messageService.getString("e3ps.message.ket_message", "03041") %><%--프로그램 지연 TASK 현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">

<script language=JavaScript>
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td valign="top" style="padding:0px 0px 0px 0px">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
				  <tr>
					<td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03041") %><%--프로그램 지연 TASK 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03041") %><%--프로그램 지연 TASK 현황--%> </td>
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
					<col width='20%'><col width='15%'><col width='15%'>
					<col width='15%'><col width='15%'><col width='5%'><col width='15%'>
						<tr>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
							<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
							<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
							<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00824") %><%--계획일자--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02046") %><%--실적일자--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02725") %><%--진척율--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02774") %><%--책임자/담당부서--%></td>
						</tr>
						<%
						Vector delayTaskVec = managerData.getDelayTaskForPJT(pjt);
						%>
						<tr>
							<td class="tdwhiteM" rowspan="<%=delayTaskVec.size() %>">&nbsp;<%=pjtData.pjtName %></td>
						<%
						for(int k = 0; k < delayTaskVec.size(); k++) {
							JELTask task = (JELTask) delayTaskVec.get(k);
							JELTaskData taskData = new JELTaskData(task);
						%>

						<%
						String taskName = "";
						if(task.getParent() != null) {
							if(task.getParent().getParent() != null) {
								//out.println("ASDF<<< "+task.getParent().getParent());
								taskName = ((JELTask)task.getParent().getParent()).getTaskName();
							} else {
								//out.println("QWER["+task.getParent()+"]");
								taskName = ((JELTask)task.getParent()).getTaskName();
							}
						} else {
							out.println("NULL!!");
						}
						%>
							<td class="tdwhiteM">&nbsp;<%=taskName %></td>
							<td class="tdwhiteM">&nbsp;<%=taskData.taskName %></td>
							<td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(taskData.taskPlanStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskPlanEndDate, "d") %></td>
							<td class="tdwhiteM">&nbsp;<%=DateUtil.getDateString(taskData.taskExecStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskExecEndDate, "d") %></td>
							<td class="tdwhiteM">&nbsp;<%=taskData.taskCompletion %> %</td>
							<td class="tdwhiteM">&nbsp;
								<%
								String plName = "";
								QueryResult masterList = TaskUserHelper.manager.getMaster(task);
								while ( masterList != null && masterList.hasMoreElements() ) {
									Object o[]=(Object[])masterList.nextElement();
									TaskMemberLink link = (TaskMemberLink)o[0];
									PeopleData data = new PeopleData(link.getMember().getMember());

									plName = data.name;
								%>
									<%=plName %>
								<%
								}
								%>
								&nbsp;/&nbsp;<%=task.getDepartment()==null?"":task.getDepartment().getName() %></td>
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
