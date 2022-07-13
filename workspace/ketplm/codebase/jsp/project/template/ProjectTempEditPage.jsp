<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*" %>
<%@page import="e3ps.project.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%
	String oid = request.getParameter("oid");
	if(oid == null)
		oid = "";

	Object tObj = null;
	ReferenceFactory rf = new ReferenceFactory();
	tObj = rf.getReference(oid).getObject();

	TemplateProject templateProject = null;
	TemplateTask templateTask = null;
	if(tObj instanceof TemplateProject) {
		templateProject = (TemplateProject)tObj;
	}
	else if(tObj instanceof TemplateTask) {
		templateTask = (TemplateTask)tObj;
	}


	String listUrl = "";
	String infoUrl = "";
	String taskUrl = "";
	String outputUrl = "";
	String memberUrl = "";

	if(templateProject != null) {
		out.println("Project Template >>>>>>> : " + templateProject.getPjtName());

		listUrl = "/plm/jsp/project/template/ProjectTempInfoEdit.jsp?oid=" + oid;
		infoUrl = "/plm/jsp/project/template/ProjectTempInfoEdit.jsp?oid=" + oid;
		taskUrl = "/plm/jsp/project/template/ProjectTempSubTaskEdit.jsp?oid=" + oid;
		//outputUrl = "/plm/jsp/project/template/ProjectTempOutputEdit.jsp?oid=" + oid;
		memberUrl = "/plm/jsp/project/template/ProjectTempMemberEdit.jsp?oid=" + oid;
	}

	if(templateTask != null) {
		out.println("Task Template >>>>>>> : " + templateTask.getTaskName());

		listUrl = "/plm/jsp/project/template/ProjectTempTaskInfoEdit.jsp?oid=" + oid;
		infoUrl = "/plm/jsp/project/template/ProjectTempTaskInfoEdit.jsp?oid=" + oid;
		taskUrl = "/plm/jsp/project/template/ProjectTempSubTaskEdit.jsp?oid=" + oid;
		outputUrl = "/plm/jsp/project/template/ProjectTempOutputEdit.jsp?oid=" + oid;
	}

	

%>
<html>
<head>
<title>Project Template Edit Page</title>
<style type="text/css">	
body {
	font: 0.8em arial, helvetica, sans-serif;
}

#header {
	padding:0 1em;
}

#header ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

.unselected {
	float: left;
	border: 1px solid #bbb;
	border-bottom-width: 0;
	margin-top: 0;
	margin-right: 1px;
	margin-bottom: 0;
	margin-left: 0;
}

.unselectedA {
	text-decoration: none;
	display: block;
	background: #eee;
	padding: 0.24em 1em;
	color: #00c;
	width: 8em;
	text-align: center;
}

#header a:hover {
	background: #ddf;
}

.selected {
	float: left;
	border: 1px solid #bbb;
	border-bottom-width: 0;
	margin-top: 0;
	margin-right: 1px;
	margin-bottom: 0;
	margin-left: 0;
	border-color: black;
}

.selectedA {
	position: relative;
	top: 1px;
	background: white;
	color: black;
	font-weight: bold;
	text-decoration: none;
	display: block;
	padding: 0.24em 1em;
	width: 8em;
	text-align: center;
}

#content {
	padding: 0 1em;
	border: 1px solid black;	
	width:840px;
}

h1 {
	margin: 0;
	padding: 0 0 1em 0;
}	
</style>
<script language="javascript">
<!-- 
function onEditPage(url, idx) {
	/*
	alert(idx);
	form = document.forms[0];

	form.method = "post";
	form.action= url;
	form.target = "list";
	form.submit();
	*/
	selectedTab(idx);
	list.document.location.href = url;
}

function selectedTab(idx) {
	var sTab1 = document.getElementById("tab1");
	var sTab2 = document.getElementById("tab2");
	var sTab3 = document.getElementById("tab3");
	var sTab4 = document.getElementById("tab4");

	var sTab1A = sTab1.children(0);
	var sTab2A = sTab2.children(0);
	var sTab3A = sTab3.children(0);
	var sTab4A = sTab4.children(0);

	if(idx == 1) {
		sTab1.className = "selected";
		sTab1A.className= "selectedA";
	}else{
		sTab1.className = "unselected";
		sTab1A.className= "unselectedA";
	}

	if(idx == 2) {
		sTab2.className = "selected";
		sTab2A.className= "selectedA";
	}else{
		sTab2.className = "unselected";
		sTab2A.className= "unselectedA";
	}

	if(idx == 3) {
		sTab3.className = "selected";
		sTab3A.className= "selectedA";
	}else{
		sTab3.className = "unselected";
		sTab3A.className= "unselectedA";
	}

	if(idx == 4) {
		sTab4.className = "selected";
		sTab4A.className= "selectedA";
	}else{
		sTab4.className = "unselected";
		sTab4A.className= "unselectedA";
	}

}
// -->
</script>
</head>

<body>
<div id="header">
	<ul>
		<li id="tab1" class="selected" onClick="javascript:onEditPage('<%=infoUrl%>', 1);"><a href="#" class="selectedA">Information</a></li>
		<li id="tab2" class="unselected" onClick="javascript:onEditPage('<%=taskUrl%>', 2);"><a href="#" class="unselectedA">Task</a></li>
		<li id="tab3" class="unselected" onClick="javascript:onEditPage('<%=outputUrl%>', 3);" <%if(templateTask==null){%>style="display:none"<%}%>><a href="#" class="unselectedA"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></a></li>
		<li id="tab4" class="unselected" onClick="javascript:onEditPage('<%=memberUrl%>', 4);"><a href="#" class="unselectedA">Member</a></li>
	</ul>
</div>
<div id="content">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td valign="top">
				<iframe src="<%=listUrl%>" id="list" name="list" frameborder="0" width="100%" height='600' leftmargin="0" topmargin="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
</div>

</body>
</html>
