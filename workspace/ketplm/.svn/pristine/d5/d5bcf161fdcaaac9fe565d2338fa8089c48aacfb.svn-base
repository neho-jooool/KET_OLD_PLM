
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "e3ps.project.*,
				  e3ps.project.beans.*,
				  e3ps.common.util.*,
				  e3ps.common.web.*,
				  java.util.*" %>
<%@page import="wt.fc.QueryResult"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
		Kogger.debug("ManageProjectTaskSelectAjax.jsp");
		Hashtable paramHash =WebUtil.getHttpParamsFromAjax(request);

		String oid = StringUtil.checkNull((String)paramHash.get("taskoid"));
		E3PSTask taskData =(E3PSTask)CommonUtil.getObject(oid);
		E3PSTaskData data = new E3PSTaskData(taskData);



		String target = "";
		String target_desc = "";
		String wbsOid = "";
		String taskStartdate = DateUtil.getDateString(data.taskPlanStartDate,"date");
		String taskEnddate = DateUtil.getDateString(data.taskPlanEndDate,"date");

		String name = data.taskName;
		String desc = taskData.getTaskDesc();
		int dura = DateUtil.getDuration(data.taskPlanStartDate, data.taskPlanEndDate) + 1;

		if(desc == null){
			desc ="";
		}

		if(target_desc == null){
			target_desc ="";
		}

		name = StringUtil.htmlCharEncode(name);
		desc = StringUtil.htmlCharEncode(desc).replaceAll("\r\n", " ");
		//target_desc = StringUtil.htmlCharEncode(target_desc).replaceAll("\r\n", " ");
		//target = StringUtil.htmlCharEncode(target).replaceAll("\r\n", " ");

		//target_desc = WTURLEncoder.encode(target_desc);
		//target_desc = java.net.URLEncoder.encode(target_desc).replaceAll(">","\\%3E").replaceAll("<", "\\%3C").replaceAll("%2F", "\\%2F").replaceAll("+", "\\%2B");


		//name = WTURLEncoder.encode(name);
		//desc = WTURLEncoder.encode(desc);
		//target_desc = WTURLEncoder.encode(target_desc);
		//target = WTURLEncoder.encode(target);

		//name = java.net.URLEncoder.encode(name);
		//desc = java.net.URLEncoder.encode(desc);
		//target_desc = java.net.URLEncoder.encode(target_desc);
		//target = java.net.URLEncoder.encode(target);

		String taskOID = "";
		String taskName = "";
		String taskDuration = "";
		String taskUserStr = "";
		String taskCompletion = "";
		String optiontype = data.optiontype;
		String milestone = data.milestone;
		String tasktype = data.tasktype;
		String drvalue = data.drvalue;

		QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(taskData);
		if(dependList != null) {
			while ( dependList.hasMoreElements() ) {
				TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
				String linkOid = CommonUtil.getOIDString(link);
				if ( false ) {
					TemplateTaskData dependData = new TemplateTaskData(link.getDependTarget());
					taskOID = dependData.taskOID;
					taskName = dependData.name;
					taskDuration = dependData.duration+"";
					taskUserStr = dependData.getManagerStr();
				} else {
					E3PSTaskData dependData = new E3PSTaskData((E3PSTask)link.getDependTarget());
					taskOID = dependData.e3psTaskOID;
					taskName = dependData.taskName;
					taskDuration = dependData.taskDuration + "";
					taskUserStr = dependData.getTaskUserStr();
					taskCompletion = dependData.taskCompletion + "";
				}
		 	}
		}

%>

		var descValue = document.manageProjectTaskList.desc;

		document.manageProjectTaskList.coid.value = "<%=oid%>";
		document.manageProjectTaskList.cname.value = unescape("<%=name%>");
		document.manageProjectTaskList.cstartdate.value = "<%=taskStartdate%>";
		document.manageProjectTaskList.cenddate.value = "<%=taskEnddate%>";
		document.manageProjectTaskList.duration.value = "<%=dura%>";
		<%
		if("Y".equals(milestone)) {%>
			document.manageProjectTaskList.mileStone[0].checked = true;
		<%
		}else {%>
			document.manageProjectTaskList.mileStone[1].checked = true;
		<%}%>

		for(i=0; i<document.manageProjectTaskList.taskType.length; i++) {
		if(document.manageProjectTaskList.taskType[i].value == "<%=tasktype%>")
			document.manageProjectTaskList.taskType[i].selected = true;
		}
		<%
		if("DR".equals(tasktype)) {%>
			drvalueTd1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%>";
			drvalueTd2.innerHTML = "<INPUT id=i size=7 name=drValue value=\"<%=drvalue%>\" onkeyup =\"SetNum(this)\" style='IME-MODE: disabled'>";
		<%
		}else {%>
			drvalueTd1.innerHTML = "&nbsp;";
			drvalueTd2.innerHTML = "&nbsp;";
		<%
		}%>


		//document.manageProjectTaskList.ttName.value = "<%//=taskName%>";
		//document.manageProjectTaskList.ttDuration.value = "<%//=taskDuration%>";
		//document.manageProjectTaskList.ttUser.value = "<%//=taskUserStr%>";
		//document.manageProjectTaskList.ttCompletion.value = "<%//=taskCompletion%>";

		//selectWBSItem('<%=wbsOid%>');
		if(document.forms[0].wbsItem != null){
			selectWBSItem("<%=wbsOid%>");
		}

		descValue.value = unescape("<%=desc%>");
		checkTargetItem();



