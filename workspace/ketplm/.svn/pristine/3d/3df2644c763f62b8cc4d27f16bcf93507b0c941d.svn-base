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
    String obj = ParamUtil.getStrParameter(request.getParameter("obj"));
%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
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
    var tree = new Tree("treeTable","한국단자","<%=iconUrl%>/tree/company");
    tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
    tree.treeSelectionListener = function(node, event){
        this.selectNode(node, event);
        if ( node.get("name") == "root" ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01552") %><%--부서를 선택하세요--%>');
            return;
        } else {
            opener.document.forms[0].<%=obj%>.value = node.get("name");
            self.close();
        }
    }
   tree.root.put("name","root");
   tree.root.put("oid","root");
    tree.root.put("code","root");
<%
    ArrayList topList = DepartmentHelper.manager.getTopList();
    for ( int i = 0 ; i < topList.size() ; i++ ) {
        Department topDepartment = (Department)topList.get(i);
        long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
%>
    var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%>");
    node<%=topnodeid%>.put("name","<%=topDepartment.getName()%>");
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
    node<%=undernodeid%>.put("name","<%=underDepartment.getName()%>");
    node<%=undernodeid%>.put("oid","<%=CommonUtil.getOIDString(underDepartment)%>");
    node<%=undernodeid%>.put("code","<%=underDepartment.getCode()%>");
    node<%=parentnodeid%>.add(node<%=undernodeid%>);
<%
        }

    }
%>
    tree.loadIcons();
   tree.repaint();
</SCRIPT>
</form>
</body>
</html>
