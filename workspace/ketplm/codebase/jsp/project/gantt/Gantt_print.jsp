<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectTreeNode"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.ProjectTreeNodeData"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.beans.E3PSTaskData"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.ProjectStateFlag"%>
<%@page import="wt.project.Role"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="e3ps.project.MoldProject"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<html>

<%!
	public void makeVector(ProjectTreeNode node, Vector vector){
		vector.add(node);
		for(int i = 0; i < node.getChildCount(); i++){
			makeVector((ProjectTreeNode)node.getChildAt(i), vector);
		}
		
	}

	private String getTaskStateFont(E3PSTask task)throws Exception {
		
		//E3PSTaskData E3PSTaskData = new E3PSTaskData(task);
		
		if (task.getTaskState()==ProjectStateFlag.TASK_STATE_COMPLETE){
			return "74C600";
		}
	
		
		if(E3PSTaskData.getPreferComp(task) < 15){
			
			return "0033CC";
		}
	
		
		
		if ( E3PSTaskData.getDifferDateGap(task) >= 0 ){
			return "0033CC";//blue
		}
		else{
			return "FF3300";//red
		}
	}
%>
<link rel="stylesheet" type="text/css" href="/plm/jsp/project/gantt/css/jsgantt_print.css"/>
<script language="javascript" src="/plm/jsp/project/gantt/js/jsgantt_print.js"></script>
<script>


function scrollAll(){
	document.all.leftside.scrollTop = document.all.rightside.scrollTop;
	
}


</script>

<%
	String oid = StringUtil.checkNull(request.getParameter("oid"));
	E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
	ProjectTreeNode root = (ProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, true);
	Vector list = new Vector();
	makeVector(root, list);
	
	String name = project.getPjtNo() + "(" + project.getPjtName() + ")";
	
	ExtendScheduleData sdata = (ExtendScheduleData)project.getPjtSchedule().getObject();
	
	Timestamp pstartDate = sdata.getPlanStartDate();
	Timestamp pendDate = sdata.getPlanEndDate();
	
	String carType = "";
	
	E3PSProjectData productData = new E3PSProjectData(project);
	
	if(project instanceof MoldProject){
		productData = new E3PSProjectData(((MoldProject)project).getMoldInfo().getProject());
	}
	
	if(productData.representModel != null && productData.representModel.length() > 0){
		carType = productData.representModel;
	}
	String customer = "";
	if(productData.subcontractorVec != null && productData.subcontractorVec.size() > 0) {
		customer = ((e3ps.common.code.NumberCode)productData.subcontractorVec.get(0)).getName();
	}

	String start = DateUtil.getTimeFormat(pstartDate, "MM/dd/yyyy");
	String end = DateUtil.getTimeFormat(pendDate, "MM/dd/yyyy");
	Kogger.debug("end = " + end);
	
	int completion = project.getPjtCompletion();
	
	int pjtState = project.getPjtState();
	String color = "#B4B4B4";
	
	if(project.getLifeCycleState().toString().equals("COMPLETED")){
		color ="74C600";
	}
	
	else if(pjtState == ProjectStateFlag.PROJECT_STATE_DELAY){
		color ="FF3300";
		
	}
	
	else if(pjtState == ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS){
		color ="ORANGE";
		
	}
	
	else if(pjtState == ProjectStateFlag.PROJECT_STATE_PROGRESS){
		color = "0033CC";
	}
	
%>

<title><%=project.getPjtName()%></title></head>
<body onLoad="screenSize();" onResize="screenSize();">





<div style="position:relative" class="gantt" id="GanttChartDIV"></div>
<script><!--


  // here's all the html code neccessary to display the chart object

  // Future idea would be to allow XML file name to be passed in and chart tasks built from file.

    var g = new JSGantt.GanttChart('g', document.getElementById('GanttChartDIV'), 'week');

	g.setCarType('<%=carType%>');
	g.setCustomer('<%=customer%>');

	g.setShowRes(1); // Show/Hide Responsible (0/1)
	g.setShowDur(1); // Show/Hide Duration (0/1)
	g.setShowComp(1); // Show/Hide % Complete(0/1)
	
	g.setShowStartDate(1);
	g.setShowEndDate(1);
	
    g.setCaptionType('Complete');  // Set to Show Caption (None,Caption,Resource,Duration,Complete)
	g.setFormatArr("day","week","month");
	
	//g.setDateDisplayFormat('yyyy-mm-dd'); 
  //var gr = new Graphics();

  if( g ) {
		//TaskItem(pID, pName, pStart, pEnd, pColor, pLink, pMile, pRes, pComp, pGroup, pParent, pOpen, pDepend)
	  
	  g.AddTaskItem(new JSGantt.TaskItem(<%=1%>, "<%=name%>", '<%=start%>', '<%=end%>', '<%=color%>', '', <%=0%>, '', <%=completion%>, <%=1%>, <%=0%>, <%=1%>));
   
	  
	<%for(int i = 0; i < list.size(); i++){
		ProjectTreeNode node = (ProjectTreeNode)list.get(i);
		ProjectTreeNodeData td = (ProjectTreeNodeData)node.getUserObject();
		if(td.getData() instanceof TemplateProject){

			continue;
		}
		E3PSTask childTask = (E3PSTask)td.getData();
		
		
		ProjectTreeNode pnode = (ProjectTreeNode)node.getParent();
		ProjectTreeNodeData ptd = (ProjectTreeNodeData)pnode.getUserObject();
		String childTaskOID = CommonUtil.getOIDString( childTask);
		
		long childTaskOIDValue = CommonUtil.getOIDLongValue(childTask);
		long parentTaskOIDValue = 0;
		if(ptd.getData() instanceof E3PSProject){
			parentTaskOIDValue = 1;
		}else{
			parentTaskOIDValue = CommonUtil.getOIDLongValue((E3PSTask)ptd.getData());
		}

		String pName = childTask.getTaskName();
		String perSonRole = childTask.getPersonRole();
		
		ExtendScheduleData schdule = (ExtendScheduleData)childTask.getTaskSchedule().getObject();
		Timestamp startDate = schdule.getPlanStartDate();
		Timestamp endDate = schdule.getPlanEndDate();
		
		String pID = String.valueOf(childTaskOIDValue);
		String pParent = String.valueOf(parentTaskOIDValue);
		
		String pStart = DateUtil.getTimeFormat(startDate, "MM/dd/yyyy");
		String pEnd = DateUtil.getTimeFormat(endDate, "MM/dd/yyyy");
		String pColor = getTaskStateFont(childTask);
		String pLink = "";
		String pMile = "0";
		String pRes = "";
		
		Role role = null;
		if(perSonRole != null && !"PM".equals(perSonRole)){
			role = Role.toRole(perSonRole);
		}
		if(role != null){
			pRes = role.getDisplay(Locale.KOREA);
		}else{
			pRes = perSonRole;
		}
		
		if(pRes == null){
			pRes = "";
		}
		
		String pComp = String.valueOf(childTask.getTaskCompletion());
		String pGroup = "0";
		
		if(node.getChildCount() > 0){
			pGroup = "1";
		}
		String pOpen = "1";
		
		Vector v = node.getPreTasks();
		String pDepend = "";
		if(v.size() > 0){
			//Kogger.debug("##################");
			ProjectTreeNode depend = (ProjectTreeNode)v.get(v.size() - 1);
			ProjectTreeNodeData dependNode = (ProjectTreeNodeData)depend.getUserObject();
			pDepend = String.valueOf(CommonUtil.getOIDLongValue((E3PSTask)dependNode.getData()));
		}
		
		
		
	%>	
		
	//TaskItem(pID, pName, pStart, pEnd, pColor, pLink, pMile, pRes, pComp, pGroup, pParent, pOpen, pDepend)
	<%
	 if(pDepend != null && pDepend.length() > 0){
	%>
	
	
		g.AddTaskItem(new JSGantt.TaskItem(<%=pID%>, "<%=pName%>", '<%=pStart%>', '<%=pEnd%>', '<%=pColor%>', '<%=pLink%>', <%=pMile%>, "<%=pRes%>", <%=pComp%>, <%=pGroup%>, <%=pParent%>, <%=pOpen%>, <%=pDepend%>));
   
			
	<%}else{%>
	
   		g.AddTaskItem(new JSGantt.TaskItem(<%=pID%>, "<%=pName%>", '<%=pStart%>', '<%=pEnd%>', '<%=pColor%>', '<%=pLink%>', <%=pMile%>, "<%=pRes%>", <%=pComp%>, <%=pGroup%>, <%=pParent%>, <%=pOpen%>));
   
    <%}%>
   			
 			
  //  g.AddTaskItem(new JSGantt.TaskItem(1,   'Add minutes/hours',     		'',          		'',          		'ff0000', 'http://help.com',	 	0, 'Ilan',     0, 1, 0, 1));
   // g.AddTaskItem(new JSGantt.TaskItem(11,  'Add support for half days',    '5/14/2009', 	'5/14/2009', 	'ff00ff', 'http://www.jsgantt.com', 0, 'Ilan',  100, 0, 1, 1));
  //  g.AddTaskItem(new JSGantt.TaskItem(12,  'Add minute view',         	'5/14/2009',  '5/14/2009',  '00ff00', '',						0, 'Ilan',   40, 0, 1, 1));
   // g.AddTaskItem(new JSGantt.TaskItem(13, 	'Add hours view',     			'5/14/2009', 	'5/14/2009',  '00ffff', 'http://www.yahoo.com', 	0, 'Ilan', 60, 0, 1, 1));
   // g.AddTaskItem(new JSGantt.TaskItem(14, 	'Add support for format options',     			'5/14/2009', 	'64/14/2009',  '00ffff', 'http://www.yahoo.com', 	0, 'Shlomy', 60, 0, 14, 1));
 <%}%>
    g.Draw();	
    g.DrawDependencies();

	//window.print();
	
  }

function screenSize(){
	//document.getElementById("leftside").style.height = document.body.clientHeight - 30;
	//document.getElementById("rightside").style.height = document.body.clientHeight - 15;
	//document.getElementById("rightside").style.width = document.body.clientWidth - 515;
	//g.Draw();	
	//g.DrawDependencies();
}


 

--></script>
