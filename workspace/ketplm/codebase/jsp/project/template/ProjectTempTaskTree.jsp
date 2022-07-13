<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*,java.text.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%	
	String oid = CharUtil.E2K(request.getParameter("oid"));
	Object object = CommonUtil.getObject(oid);
	
	String projectOid = oid;
	TemplateProject project = null;
	if(object instanceof TemplateTask) {
		project = ((TemplateTask)object).getProject();
		projectOid = project.getPersistInfo().getObjectIdentifier().getStringValue();
	}
	else {
		project = (TemplateProject)object;
	}
%>
<html>
<head>
<style type="text/css">
<!--
body,td {font-family:"굴림"; font-size: 9pt}
#scrollbox {width:100%; height:100%; overflow:auto; padding:0px;}
-->
</style>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/taskTree.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function manageTask(oid){	
	var url = "/plm/jsp/project/manage/ManageTemplateProjectTaskFrm.jsp?oid="+oid;
	openOtherName(url,"manageTask","800","600","status=yes,scrollbars=no,resizable=yes");
}     

function displayChange(type) {
	if ( type == '0' ) {
		normalTreeDisplay.style.display = "none";
		psoTreeDisplay.style.display = "block";
	} else {
		normalTreeDisplay.style.display = "block";
		psoTreeDisplay.style.display = "none";
	}
}
//-->
</SCRIPT>
</head>
<body bgcolor="#ffffff" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form name="projectTree" method="post">
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=projectOid%>">
<br>
<table height=100% width=100% cellpadding=0 cellspacing=0 border=0 >
	<tr bgcolor=ffffff>
		<td height='100%' style="padding-left:5px;">
			<table border=0 cellpadding=0 cellspacing=0 width=100% height=100%>
				<tr id="normalTreeDisplay" style="display:block">
					<td valign="top">
						<DIV id="scrollbox">
						<table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
						</div>
					</td>
				</tr>
				<tr id="psoTreeDisplay" style="display:none">
					<td valign="top">
						<DIV id="scrollbox">
						<table oncontextmenu="return false" onselectstart="return false"  id=psoTreeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<SCRIPT>
	var target = eval("parent.main");
	var tree = new Tree("treeTable","<%=project.getPjtName()%>","/plm/portal/icon/tree/project");
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);	
		target.document.location.href = "/plm/jsp/project/template/ProjectTempEditPage.jsp?oid="+node.get("oid");
	}
	tree.root.put("oid","<%=projectOid%>");
	tree.root.put("nodeType","project");
<%
	
	
	TemplateProjectTreeNode root = (TemplateProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
	
	Vector list = new Vector();
	makeVector(root, list);
	
	for(int i = 0; i < list.size(); i++){
		TemplateProjectTreeNode node = (TemplateProjectTreeNode)list.get(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
		
		
		if(td.getData() instanceof TemplateProject){
			/* TemplateProject 이다*/
			continue;
		}
		
		TemplateTask childTask = (TemplateTask)td.getData();
		TemplateProjectTreeNode pnode = (TemplateProjectTreeNode)node.getParent();
		TemplateProjectTreeNodeData ptd = (TemplateProjectTreeNodeData)pnode.getUserObject();
		
		String childTaskOID = CommonUtil.getOIDString( childTask);
		long childTaskOIDValue = CommonUtil.getOIDLongValue( childTask);
		
		long parentTaskOIDValue = 0;
		
		if(ptd.getData() instanceof TemplateProject){
			parentTaskOIDValue = 0;
		}else{
			parentTaskOIDValue = CommonUtil.getOIDLongValue((TemplateTask)ptd.getData());
		}
		
%>
		
			var node<%=childTaskOIDValue%> = new TreeNode("<%=childTask.getTaskName()%>");
			node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
			node<%=childTaskOIDValue%>.put("nodeType","task");
		<%if(	parentTaskOIDValue == 0){%>
				tree.root.add(node<%=childTaskOIDValue%>);
		<%}else{%>
			
			node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);
			
		<%}%>
		
<%}%>


   tree.expandAll();
   tree.repaint();
</SCRIPT>
</body>
</html>

<%!
	public void makeVector(TemplateProjectTreeNode node, Vector vector){
		vector.add(node);
		for(int i = 0; i < node.getChildCount(); i++){
			makeVector((TemplateProjectTreeNode)node.getChildAt(i), vector);
		}
	}
%>
