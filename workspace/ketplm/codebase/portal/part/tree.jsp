<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil,e3ps.common.folder.FolderUtil,
				wt.clients.folder.FolderTaskLogic,
				wt.folder.Folder"%>
<%@include file="/jsp/common/context.jsp"%>
<body bgcolor="eff3f4">
<script src="/plm/portal/js/newtree.js"></script>
	<table border="0" cellspacing="0" cellpadding="0" width="130" height="" >
		<tr>
			<td>
				  <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
			</td>
		</tr>
	</table>
<SCRIPT>
<%
	boolean isAuth = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("PRT", "부품");
	boolean isAdmin = e3ps.common.util.CommonUtil.isAdmin() || isAuth;
	
	String where_state = isAdmin ? "" : "&key=state&value=RELEASED";
	
	String viewName = request.getParameter("viewName");
	String folderPath = "/Part";
	Folder cabinet = FolderTaskLogic.getFolder(folderPath);
	String oid = CommonUtil.getOIDString(cabinet);
	String name = "설계";
	if("Manufacturing".equals(viewName))
		name = "생산";
%>
	var target = eval("parent.main");
	var tree = new Tree("treeTable","<%=name%> 부품","/plm/portal/icon/tree/board");
	//tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION; 
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		if("<%=oid%>" == tree.selectedNode[0].get("oid") ) return;
		parent.parent.document.frames["contName"].location.href=
			//"/plm/servlet/e3ps.part.servlet.PartListServlet?lview=Engineering&folderOid="
			"/plm/jsp/common/loading.jsp?url=/plm/jsp/part/FolderListPart.jsp&key=view&value=<%=viewName%>"
			+"&key=islastversion&value=true&key=folderoid&value="+tree.selectedNode[0].get("oid")+"<%=where_state%>";
	}
<%
//	out.print("tree.root.put('oid', 'noEvent');");
	out.print("tree.root.put('oid', '"+oid+"');");
	maketree(out, cabinet, oid);
%>
//	tree.expandAll();
	tree.repaint();
	//parent.document.all.txtLoading.style.display = 'none';
</SCRIPT>
<%//}%>
<%!
	public void maketree(JspWriter out, Folder parent, String rootOid) throws Exception 
	{
		wt.util.SortedEnumeration folderList = FolderUtil.getSubFolders(parent);
		while(folderList.hasMoreElements())
		{
			Folder folder =(Folder)folderList.nextElement();
			String oidLong = ""+CommonUtil.getOIDLongValue(folder);
			out.print("var node"+oidLong+" = new TreeNode('"+e3ps.common.util.StringUtil.changeString(folder.getName(), "'", "&#39;")+"');");
			out.print("node"+oidLong+".put('oid', '"+CommonUtil.getOIDString(folder)+"');");

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
