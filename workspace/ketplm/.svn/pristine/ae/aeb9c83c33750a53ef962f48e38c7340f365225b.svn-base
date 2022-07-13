<%@page contentType="text/html; charset=UTF-8"%>
<!--%@page import="com.ptc.windchill.uwgm.soap.uwgmdb.Folder"%-->
<%@page import="wt.folder.FolderHelper,
				wt.folder.FolderEntry,
				wt.fc.*,
				wt.lifecycle.State,
				wt.org.*"%>
<%@page import="java.util.*" %>
<%@page import="e3ps.groupware.company.*,
				e3ps.project.*,
				e3ps.project.historyprocess.*,
				e3ps.project.beans.*,
				e3ps.common.util.*" %>
<%@include file="/jsp/common/context.jsp" %>
<html>

<head>
<%
	String oid = request.getParameter("oid");
	TemplateProject project = (TemplateProject)CommonUtil.getObject(oid);
	
	TemplateProjectTreeNode root = (TemplateProjectTreeNode)ProjectScheduleHelper.manager.getRoot(project, false);
	
	
	settingSeq(root);
	

%>

<%!
	public void settingSeq(TemplateProjectTreeNode root)throws Exception{
		for(int i = 0; i < root.getChildCount(); i++){
			TemplateProjectTreeNode node = (TemplateProjectTreeNode)root.getChildAt(i);
			TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData)node.getUserObject();
			TemplateTask task = (TemplateTask)td.getData();
			task.setTaskSeq(i + 1);
			PersistenceHelper.manager.save(task);
			settingSeq(node);
		}
	}

%>

<title>Insert title here</title>
</head>
<body>

</body>
</html>
