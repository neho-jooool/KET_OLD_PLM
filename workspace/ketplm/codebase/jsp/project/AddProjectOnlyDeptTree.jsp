<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                e3ps.common.util.CommonUtil,
                e3ps.groupware.company.Department,
                e3ps.groupware.company.DepartmentHelper,
                e3ps.groupware.company.CompanyState"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String command = request.getParameter("command");
    String mode = request.getParameter("mode");
    String invokeMethod = request.getParameter("invokeMethod");
    if ( command == null )
        command = "";
    if ( mode == null || mode.length() == 0 )
        mode = "s";
    if ( invokeMethod == null || invokeMethod.length() == 0 )
        invokeMethod = "";

    String param = "mode=" + mode + "&invokeMethod=" + invokeMethod;
    String targetURL = "/plm/jsp/project/AddProjectPeopleList.jsp?" + param;
%>

<html>
<head>
<base target="_self">

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<script src="/plm/portal/js/newtree.js"></script>

</head>

<body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">

<form name="companytree" method="post">
<input type="hidden" name="command">
<input type="hidden" name="soid">

<div id="scrollbox">
<table style="width: 100%; border: 0px">
    <tr>
        <td>
            <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
        </td>
    </tr>
</table>
</div>

<script>
    var target = eval("parent.main");
    var tree = new Tree("treeTable","<%=CompanyState.companyName%>","/plm/portal/icon/tree/company");
    var selectDept = "";
    tree.treeSelectionListener = function(node, event){
        if ( node.get("oid") == null ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01183") %><%--다시 선택해주세요--%>');
            return;
        }

        if ( node.get("code") != "root" ) {
            if ( "<%=mode%>" != "s" ) {
                selectNode(node, event);
                parent.addDept(selectDept);
            }
            else {
                parent.seletDept(node.get("oid"));
            }
        }
    }
    selectNode = function(node, event){
        if ( node.isSelected() ) {
            tree.unselectNode(node);
            selectDept = selectDept.replace(node.get("oid")+"," , "");
        }else {
            tree.selectedNode[tree.selectedNode.length] = node;
            selectDept = selectDept + node.get("oid") + ",";
        }
        tree.repaintAll();
    }

    tree.root.put("oid","root");
    tree.root.put("code","root");
    <%
    ArrayList topList = DepartmentHelper.manager.getTopList();
    for ( int i = 0 ; i < topList.size() ; i++ ) {
        Department topDepartment = (Department)topList.get(i);
        ArrayList underListroot = DepartmentHelper.manager.getAllChildList(topDepartment, new ArrayList());
        long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
        if ( underListroot.size() == 0 ) {
    %>
            var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%>");
            node<%=topnodeid%>.put("oid","<%=CommonUtil.getOIDString(topDepartment)%>");
            node<%=topnodeid%>.put("code","<%=topDepartment.getCode()%>");
            tree.root.add(node<%=topnodeid%>);
    <%
        }
        else {
    %>
            var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%>");
            node<%=topnodeid%>.put("oid","root");
            node<%=topnodeid%>.put("code","root");
            tree.root.add(node<%=topnodeid%>);
    <%
        }
        ArrayList underList = DepartmentHelper.manager.getAllChildList(topDepartment, new ArrayList());
        for ( int j = 0 ; j < underList.size() ; j++ ) {
            Department underDepartment = (Department)underList.get(j);
            ArrayList underChildList = DepartmentHelper.manager.getAllChildList(underDepartment ,new ArrayList());
            Department parentDepartment = (Department)underDepartment.getParent();
            long undernodeid = CommonUtil.getOIDLongValue(underDepartment);
            long parentnodeid = CommonUtil.getOIDLongValue(parentDepartment);
            if ( underChildList.size() == 0 ) {
    %>
                var node<%=undernodeid%> = new TreeNode("<%=underDepartment.getName()%>");
                node<%=undernodeid%>.put("oid","<%=CommonUtil.getOIDString(underDepartment)%>");
                node<%=undernodeid%>.put("code","<%=underDepartment.getCode()%>");
                node<%=parentnodeid%>.add(node<%=undernodeid%>);
    <%
            }
            else {
    %>
                var node<%=undernodeid%> = new TreeNode("<%=underDepartment.getName()%>");
                node<%=undernodeid%>.put("oid","root");
                node<%=undernodeid%>.put("code","root");
                node<%=parentnodeid%>.add(node<%=undernodeid%>);
    <%
            }
        }

    }
    %>
    tree.loadIcons();
    tree.expandAll();
    tree.repaint();
</script>
</form>
</body>
</html>
