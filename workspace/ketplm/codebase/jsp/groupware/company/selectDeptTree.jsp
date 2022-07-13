<%--
	/**
	 * @(#)	companytree.jsp
	 * Copyright (c) whois. All rights reserverd
	 *
	 * @version 1.00
	 * @since jdk 1.4.02
	 * @author Cho Sung Ok
	 */
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,e3ps.common.web.ParamUtil" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<%
	String mode = "s";
	if(request.getParameter("mode")!=null && request.getParameter("mode").length()!=0)
	    mode = request.getParameter("mode");
	String function = request.getParameter("function");
	String other = request.getParameter("other");
	String target = request.getParameter("target");
	String targetURL = "/plm/jsp/groupware/company/selectPeopleList.jsp?mode="+mode;
	if ( !function.trim().equals("") ) targetURL = targetURL + "&function="+function;
	if ( other!=null&&other.length()!=0 ) targetURL = targetURL + "&other="+other;
	if ( !"".equals(target) && target != null) targetURL = targetURL + "&target="+target;

	// Modify skyprda 2005/02/03
//	String target = request.getParameter("target");
//	if ( !target.trim().equals("") ) targetURL += "&target="+target;
	// end - skyprda
%>
<%@include file="/jsp/common/context.jsp" %>

<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script src="/plm/portal/js/newtree.js"></script>
<BODY leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form name=companytree method=post>
<input type="hidden" name="command">
<input type="hidden" name="soid">
<DIV id="scrollbox">
<table border=0 width=100%>
	<tr>
		<td>
			<table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
		</td>
	</tr>
</table>
</DIV>
<SCRIPT>
	var target = eval("parent.main");
	var tree = new Tree("treeTable","<%=CompanyState.companyName%>","/plm/portal/icon/tree/company");
	//tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
	tree.selectionMode = tree.SINGLE_TREE_SELECTION;
	tree.treeSelectionListener = function(node, event){
		this.selectNode(node, event);
		target.document.location.href = "<%=targetURL%>&oid="+node.get("oid");
	}
   tree.root.put("oid","root");
	tree.root.put("code","root");
<%
	ArrayList topList = DepartmentHelper.manager.getTopList();
	for ( int i = 0 ; i < topList.size() ; i++ ) {
		Department topDepartment = (Department)topList.get(i);
		long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
%>
	var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%>");
	node<%=topnodeid%>.put("oid","<%=CommonUtil.getOIDString(topDepartment)%>");
	node<%=topnodeid%>.put("code","<%=topDepartment.getCode()%>");
	tree.root.add(node<%=topnodeid%>);
<%
		ArrayList underList = DepartmentHelper.manager.getAllChildList(topDepartment,new ArrayList());
		for ( int j = 0 ; j < underList.size() ; j++ ) {
			Department underDepartment = (Department)underList.get(j);
			Department parentDepartment = (Department)underDepartment.getParent();
			long undernodeid = CommonUtil.getOIDLongValue(underDepartment);
			long parentnodeid = CommonUtil.getOIDLongValue(parentDepartment);
%>
	var node<%=undernodeid%> = new TreeNode("<%=underDepartment.getName()%>");
	node<%=undernodeid%>.put("oid","<%=CommonUtil.getOIDString(underDepartment)%>");
	node<%=undernodeid%>.put("code","<%=underDepartment.getCode()%>");
	node<%=parentnodeid%>.add(node<%=undernodeid%>);
<%
		}

	}
%>
	tree.loadIcons();
	tree.expandAll();
   tree.repaint();
</SCRIPT>
</form>
</body>
</html>
