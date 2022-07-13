
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "e3ps.project.*,
				  e3ps.project.beans.*,
				  e3ps.common.util.*,
				  e3ps.common.web.*,
				  java.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
		Hashtable paramHash = WebUtil.getHttpParamsFromAjax(request);

		String oid = StringUtil.checkNull((String)paramHash.get("taskoid"));

		TemplateTask taskData =(TemplateTask)CommonUtil.getObject(oid);
		TemplateTaskData data = new TemplateTaskData(taskData);




		String name = data.name;
		String taskoid =CommonUtil.getOIDString(taskData);
		String desc = data.description;
		String seq = data.seq + "";
		String dura = data.duration + "";
		//String std = data.stdWork + "";

		String optiontype = data.optiontype;
		String milestone = data.milestone;
		String tasktype = data.tasktype;
		String drvalue = data.drvalue;

		if(desc == null){
			desc = "";
		}

		desc = desc.replaceAll("\r\n", "\\\\n");

%>

		document.forms[0].coid.value = "<%=taskoid%>";

		if(document.forms[0].cname != null){
			document.forms[0].cname.value = "<%=name%>";
		}

		document.forms[0].desc.value= "<%=desc%>";
		document.forms[0].cseq.value = "<%=seq %>";
		document.forms[0].duration.value = "<%=dura %>";

		<%
		if("Y".equals(optiontype)) {%>
			document.forms[0].optionType[0].checked = true;
		<%
		}else {%>
			document.forms[0].optionType[1].checked = true;
		<%
		}%>

		<%
		if("Y".equals(milestone)) {%>
			document.forms[0].mileStone[0].checked = true;
		<%
		}else {%>
			document.forms[0].mileStone[1].checked = true;
		<%
		}%>

		for(i=0; i<document.forms[0].taskType.length; i++) {
			if(document.forms[0].taskType[i].value == "<%=tasktype%>")
				document.forms[0].taskType[i].selected = true;
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




