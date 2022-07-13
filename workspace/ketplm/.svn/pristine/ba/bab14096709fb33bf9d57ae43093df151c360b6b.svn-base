<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.ProjectManager"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectManagerData"%>
<%@page import="e3ps.project.JELProject"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.TemplateTaskData"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>
<%@page import="wt.lifecycle.LifeCycleTemplate"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="wt.lifecycle.PhaseTemplate"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="wt.lifecycle.State"%>
<%@page import="java.util.Hashtable"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
	String oid = request.getParameter("oid");

	String command = request.getParameter("command");

	ProjectManager manager = (ProjectManager) CommonUtil.getObject(oid);
	ProjectManagerData managerData = new ProjectManagerData(manager);


%>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03072") %><%--프로젝트 별 대일정 현황--%></title>
<style type="text/css">

body {
	background-image: url(/plm/portal/images/img_default/background2.gif);
	background-repeat:repeat-x;
	background-color: #ffffff;
	margin-top: 24px;
	margin-left:15px;
	margin-right:10px;

	overflow-x:hidden;
	overflow-y:auto;
	scrollbar-highlight-color:#f4f6fb;
	scrollbar-3dlight-color:#c7d0e6;
	scrollbar-face-color:#f4f6fb;
	scrollbar-shadow-color:#f4f6fb;
	scrollbar-darkshadow-color:#c7d0e6;
	scrollbar-track-color:#f4f6fb;
	scrollbar-arrow-color:#607ddb;
}

</style>
</head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<script language=JavaScript>
	function search() {
		//document.forms[0].pjtState.value = document.forms[0].tmpState[document.forms[0].tmpState.selectedIndex].value;

		onProgressBar();

		document.forms[0].command.value = "search";
		document.forms[0].method = "post";
		document.forms[0].action = "/plm/jsp/project/chart/ProjectChart.jsp";
		document.forms[0].submit();
	}

	function viewProgram(oid) {

		var url = "/plm/jsp/project/ViewProgram.jsp?oid="+oid+"&popup=popup";
		getOpenWindow(url, "viewProgramPopup", "800", "600");
	}

	function projectViewpopup(oid){
		openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
	}

</script>



<body>

<form>
<input type='hidden' name='command'>
<input type='hidden' name='pjtState'>
<input type='hidden' name='oid' value="<%=oid %>">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td valign="top" style="padding:24px 0px 15px 15px">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
				  <tr>
					<td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03072") %><%--프로젝트 별 대일정 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03072") %><%--프로젝트 별 대일정 현황--%></td>
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
									&nbsp;&nbsp;
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
				<col width='10%'><col width='10%'><col width='10%'><col width='10%'><col width='10%'>
					<tr>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                        <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
						<td class="tdblueM">Sub PM</td>
						<td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
						<%
						TemplateProject tmpPjt = (TemplateProject) CommonUtil.getObject(managerData.managerTemplate);
						QueryResult tmpQr = ProjectTaskHelper.manager.getChild(tmpPjt);
						int aa = 0;
						Vector nameV = new Vector();
						if(tmpQr.size() > 0) {
							while(tmpQr.hasMoreElements()) {
								Object[] obj = (Object[]) tmpQr.nextElement();
								TemplateTask tmpTask = (TemplateTask) obj[0];
								TemplateTaskData tmpTaskData = new TemplateTaskData(tmpTask);
								nameV.add(tmpTaskData.name);
						%>
						<td class="tdblueM"><%=tmpTaskData.name %></td>
						<%
								aa++;
							}
						}
						%>
					</tr>
					<% if(managerData.jelProjectVec.size() == 0) { %>
						<tr>
							<td class="tdwhiteM" colspan=<%=aa+5 %>>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00751") %><%--결과가 없습니다--%>.</td>
						</tr>
					<% } else if(managerData.jelProjectVec.size() == 1) { %>
					<%
						JELProject pjt = (JELProject) managerData.jelProjectVec.get(0);
						JELProjectData pjtData = new JELProjectData(pjt);
						if(pjt != null) {
					%>
					<tr>
						<td class="tdwhiteM" rowspan="2">&nbsp;
						<a href="javascript:viewProgram('<%=CommonUtil.getOIDString(manager)%>')">
						<B><%=(managerData==null)?"":managerData.managerName %></B></a>
						</td>
						<td class="tdwhiteM" rowspan="2">&nbsp;
						<a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(pjt)%>')">
						<B><%=pjtData.pjtName %></B></a>
						</td>
						<td class="tdwhiteM" rowspan="2">&nbsp;<%=pjtData.getStateStr(pjtData.stateKorea) %></td>
						<td class="tdwhiteM" rowspan="2">&nbsp;<%=pjtData.pjtPmName %></td>
						<td class="tdwhiteM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
						<%

						Vector taskVecA = ProjectTaskHelper.manager.getLevelOneTask(pjt);


						for(int a = 0; a < taskVecA.size(); a++) {
							JELTask task = (JELTask) CommonUtil.getObject((String)taskVecA.get(a));
							JELTaskData taskData = new JELTaskData(task);
						%>
						<td class="tdwhiteM">
							<%=DateUtil.getDateString(taskData.taskPlanStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskPlanEndDate, "d") %>
						</td>
						<%
						}
						%>
					</tr>

					<tr>
                        <td class="tdwhiteM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02724") %><%--진척--%></td>
						<%
						Vector taskVecC = ProjectTaskHelper.manager.getLevelOneTask(pjt);
						for(int c = 0; c < taskVecC.size(); c++) {
							JELTask task = (JELTask) CommonUtil.getObject((String)taskVecC.get(c));
							JELTaskData taskData = new JELTaskData(task);
						%>
						<td class="tdwhiteM">
							<!-- 진행률 그림 -->
							<jsp:include page="/jsp/project/ProjectProgressBarView.jsp" flush="false">
								<jsp:param name="planValue" value="<%=taskData.getPreferCompStr()%>"/>
								<jsp:param name="workValue" value="<%=taskData.taskCompletion%>"/>
							</jsp:include>
							<!-- //진행률 그림 -->
						</td>
						<%
						}
						%>
					</tr>
					<%		} %>
					<% } else { %>
					<tr>
						<td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size()*3 %>">&nbsp;
							<a href="javascript:viewProgram('<%=CommonUtil.getOIDString(manager)%>')">
						<B><%=managerData.managerName %></B></a></td>
						<% for(int i = 0; i < managerData.jelProjectVec.size(); i++) { %>
						<%
							JELProject pjt = (JELProject) managerData.jelProjectVec.get(i);
							JELProjectData pjtData = new JELProjectData(pjt);


						%>
						<td class="tdwhiteM" rowspan="2">&nbsp;<a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(pjt)%>')">
						<B><%=pjtData.pjtName %></B></a></td>
						<td class="tdwhiteM" rowspan="2">&nbsp;<%=pjtData.getStateStr(pjtData.stateKorea) %></td>
						<td class="tdwhiteM" rowspan="2">&nbsp;<%=pjtData.pjtPmName %></td>
						<td class="tdwhiteM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
						<%
						Vector taskVecA = ProjectTaskHelper.manager.getLevelOneTask(pjt);

						Hashtable hash = new Hashtable();

						for(int a = 0; a < taskVecA.size(); a++) {
							JELTask task = (JELTask) CommonUtil.getObject((String)taskVecA.get(a));
							JELTaskData taskData = new JELTaskData(task);
							hash.put(taskData.taskName, taskData);
						}
						for(int a = 0; a < nameV.size(); a++){
							JELTaskData taskData = (JELTaskData)hash.get(nameV.get(a));
							if(taskData == null){%>

							<td class="tdwhiteM">

							&nbsp;

						    </td>


							<%

								continue;
							}%>


						<td class="tdwhiteM">
							<%=DateUtil.getDateString(taskData.taskPlanStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskPlanEndDate, "d") %>
						</td>
						<%
						}
						%>
					</tr>

					<tr>
                        <td class="tdwhiteM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02724") %><%--진척--%></td>
						<%
						Vector taskVecC = taskVecA;
						//ProjectTaskHelper.manager.getLevelOneTask(pjt);
						//for(int c = 0; c < taskVecC.size(); c++) {
							//JELTask task = (JELTask) CommonUtil.getObject((String)taskVecC.get(c));
							//JELTaskData taskData = new JELTaskData(task);

						for(int a = 0; a < nameV.size(); a++){
							JELTaskData taskData = (JELTaskData)hash.get(nameV.get(a));
							if(taskData == null){%>

							<td class="tdwhiteM">

							&nbsp;

						    </td>


							<%

								continue;
							}%>

						<td class="tdwhiteM">
							<jsp:include page="/jsp/project/ProjectProgressBarView.jsp" flush="false">
								<jsp:param name="planValue" value="<%=taskData.getPreferCompStr()%>"/>
								<jsp:param name="workValue" value="<%=taskData.taskCompletion%>"/>
							</jsp:include>
						</td>
						<%
						}
						%>
					</tr>
						 <% } %>
					<% } %>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
