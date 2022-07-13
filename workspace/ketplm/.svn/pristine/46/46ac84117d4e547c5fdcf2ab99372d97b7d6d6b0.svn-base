<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil,e3ps.common.folder.FolderUtil,
				wt.clients.folder.FolderTaskLogic,
				wt.folder.Folder"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.common.util.WCUtil"%>
<body bgcolor="eff3f4">
<script src="/plm/portal/js/newtree.js"></script>
	<table border="0" cellspacing="0" cellpadding="0" width="130" height="" bgcolor="#ffffff">
		<tr>
			<td>
				  <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
			</td>
		</tr>
	</table>
<SCRIPT>
<%
	String classification = e3ps.common.util.CharUtil.E2K(request.getParameter("classification"));
	String folderPath = request.getParameter("folderpath");
	
	if(folderPath==null){
		folderPath = "";
	}
	String cabinetPath ="";
	if(folderPath.equals("dev")){
		folderPath ="/Default/Drawing/개발";
	}else if(folderPath.equals("maf")){
		folderPath ="/Default/Drawing/제조";
	}else{
		folderPath ="/Default/Drawing";
	}


	//Folder folder = FolderTaskLogic.getFolder(folderPath);
	
	Folder folder = FolderTaskLogic.getFolder(folderPath, WCUtil.getWTContainerRef());
	String oid = CommonUtil.getOIDString(folder);
%>
	var tree = new Tree("treeTable","<%="&nbsp;&nbsp;"+folder.getName()%>","/plm/portal/icon/tree/board");
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		parent.parent.document.frames["contName"].location.href="/plm/jsp/part/epm/FolderListEPM.jsp?islastversion=true&folderpath="+tree.selectedNode[0].get("path") +"&classification=<%=classification%>";
	}
<%
	out.print("tree.root.put('path', \""+folder.getFolderPath()+"\");");
	maketree(out, folder, oid);
%>
	tree.expandAll();
	tree.repaint();
</SCRIPT>
<%//}%>
<%!
	public void maketree(JspWriter out, Folder parent, String rootOid) throws Exception
	{
		wt.util.SortedEnumeration folderList = FolderUtil.getSubFolders(parent);
		while(folderList.hasMoreElements())
		{
			Folder folder =(Folder)folderList.nextElement();
			if("제품도".equals(folder.getName())) continue;
			String oidLong = ""+CommonUtil.getOIDLongValue(folder);
			out.print("var node"+oidLong+" = new TreeNode('"+e3ps.common.util.StringUtil.changeString(folder.getName(), "'", "&#39;")+"');");
			out.print("node"+oidLong+".put('path', \""+folder.getFolderPath()+"\");");

			String parentOidLong = ""+CommonUtil.getOIDLongValue(parent);
			if(rootOid.equals(CommonUtil.getOIDString(parent)))
				out.print("tree.root.add(node"+oidLong+");");
			else
				out.print("node"+parentOidLong+".add(node"+oidLong+");");

			maketree(out, folder, rootOid);
		}
	}
%>
</body>
