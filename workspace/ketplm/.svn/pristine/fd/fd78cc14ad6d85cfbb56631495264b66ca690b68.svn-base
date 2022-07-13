<%--
    /**
     * @(#)	companytree.jsp
     * Copyright (c) whois. All rights reserverd
     *
     * @version 1.00
     * @since jdk 1.4.02
     * @author Cho Sung Ok
     */
     // 이 페이지는 topnode 하위에 사용자가 출력됨
     // 사업부 생성 이후 페이지 수정
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<%@page import="wt.fc.*, wt.org.WTUser" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%
    String reqType = request.getParameter("target");
%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script src="/plm/portal/js/newtree.js"></script>
<style type="text/css">
<!--
body,td {font-family:"굴림"; font-size: 9pt}
#scrollbox {width:100%; height:100%; overflow:auto; padding:0px;}
-->
</style>
<BODY leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" >
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
    var type = "<%= request.getParameter("target")%>";
    var target = eval("parent.main");
    var tree = new Tree("treeTable","<%=CompanyState.companyName%>","<%=iconUrl%>/tree/company");
    if(type=="workflow"){
        var selMember = "";
        tree.treeSelectionListener = function(node, event){
            if(node.get("userid")==null){
                alert('<%=messageService.getString("e3ps.message.ket_message", "01678") %><%--사용자만 선택하실 수 있습니다--%>');
                return;
            }
            selectNode(node, event);
            parent.addUser(selMember);

        }
        selectNode = function(node, event){
            if(node.isSelected()){
                tree.unselectNode(node);
                selMember = selMember.replace(node.get("userid")+"," , "");
            }else {
                tree.selectedNode[tree.selectedNode.length] = node;
                selMember = selMember + node.get("userid") + ",";
              }
           tree.addChangedNode(node);
           tree.repaintAll();
           }
           function unselectAll(){
               var rowsize = tree.selectedNode.length;
               for(var idx=0; idx<rowsize; idx++){
                   selMember = selMember.replace(tree.selectedNode[0].get("userid")+"," , "");
                   tree.unselectNode(tree.selectedNode[0]);
                   if(idx!=rowsize-1)tree.addChangedNode(tree.selectedNode[0]);
               }
               tree.repaintAll();
           }

    }
    else {
        tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
        tree.selectionMode = tree.SINGLE_TREE_SELECTION;
        tree.treeSelectionListener = function(node, event){
            this.selectNode(node, event);
            document.location.href = "/plm/jsp/common/loading.jsp?url="
            +"/plm/servlet/e3ps/ManagePeopleServlet&key=oid&value="+node.get("oid");
            }
    }
   tree.root.put("oid","root");
<%
    ArrayList topList = DepartmentHelper.manager.getTopList();
    for ( int i = 0 ; i < topList.size() ; i++ ) {
        Department topDepartment = (Department)topList.get(i);
        long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
%>
    var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%>");
    node<%=topnodeid%>.put("oid","<%=CommonUtil.getOIDString(topDepartment)%>");
<%	if(reqType.equals("workflow")){
        QueryResult qr = DepartmentHelper.manager.getDepartmentPeople(topDepartment);
        People pdata = new People();
        while(qr.hasMoreElements()){
            Object[] data = (Object[])qr.nextElement();
            pdata = (People)data[0];
%>		if(type=="workflow"){
            var node<%=CommonUtil.getOIDLongValue(pdata)%> = new TreeNode("<%=pdata.getName()%>");
            node<%=CommonUtil.getOIDLongValue(pdata)%>.put("userid","<%=pdata.getId()%>");
            node<%=topnodeid%>.add(node<%=CommonUtil.getOIDLongValue(pdata)%>);
        }
<%		}
    }
%>
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
    node<%=parentnodeid%>.add(node<%=undernodeid%>);
<%
        }
    }
%>
    tree.expandAll();
   tree.repaint();
</SCRIPT>
</form>
</body>
</html>
