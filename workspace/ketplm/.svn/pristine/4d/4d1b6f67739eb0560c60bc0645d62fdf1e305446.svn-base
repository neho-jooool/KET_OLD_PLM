<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.util.CommonUtil,e3ps.common.folder.FolderUtil,
				wt.clients.folder.FolderTaskLogic,
				wt.folder.Folder"%>
<%@include file="/jsp/common/context.jsp"%>
<html>
<head>
<LINK rel="stylesheet" type="text/css" href="../css/e3ps.css">
<script src="/plm/portal/js/newtree.js"></script>
</head>

<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >
<form method=post>
	<table border="0" cellspacing="1" cellpadding="2" width="159" height=""  >
		<tr>			
			<td>
			<DIV id="scrollbox">
				  <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
			</DIV>
			</td>			  
		</tr>
	</table>
<SCRIPT>
<%
	Folder cabinet = FolderTaskLogic.getFolder("/WorkProcess/form");
	String oid = CommonUtil.getOIDString(cabinet);
%>
	var target = eval("parent.main");
	var tree = new Tree("treeTable","G W","/plm/portal/icon/tree/board");
	//tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION; 
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
//		if("<%=oid%>" == tree.selectedNode[0].get("oid") ) return;
		parent.iframe.document.location.href=
			//"/plm/servlet/e3ps.part.servlet.PartListServlet?lview=Engineering&folderOid="
			"/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps.groupware.workprocess.servlet.ManagerFormServlet"+
			"&key=from&value=doc&key=folderoid&value="+tree.selectedNode[0].get("oid");
//			+"&key=cmd&value=search&key=folderoid&value="+tree.selectedNode[0].get("oid");
	}
<%
//	out.print("tree.root.put('oid', 'noEvent');");
	out.print("tree.root.put('oid', '"+oid+"');");
	maketree(out, cabinet, oid);
%>
//	tree.expandAll();
	tree.repaint();
	parent.document.all.txtLoading.style.display = 'none';
</SCRIPT>
<%//}%>
</form>
</body>
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
