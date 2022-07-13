<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,java.util.ArrayList"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%@page import="e3ps.project.beans.ProjectOutPutTypeHelper"%>
<%@page import="e3ps.project.outputtype.ProjectOutPutType"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<html>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/newtree.js"></script>
<BODY>
<form name=tree method=post>
<input type=button value='<%=messageService.getString("e3ps.message.ket_message", "01768") %><%--새로고침--%>' onclick='location.reload()' id='innerbutton'>
<DIV id="scrollbox">
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
  <tr>
    <td valign="top" align="left">
      <table border=0>
        <tr>
          <td>
            <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</DIV>
<SCRIPT>
  var target = eval("parent.main");
  var tree = new Tree("treeTable","산출물 인증 타입","/plm/portal/icon/tree/board");
  tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
  tree.treeSelectionListener = function(node, event){
    this.selectNode(node, event);
    target.document.location.href="/plm/jsp/project/projectType/ViewProjectType.jsp?oid="+tree.selectedNode[0].get("oid");
    /*
    if ( target.document.forms[0].cmd.value == "create" ) {
      target.document.forms[0].oid.value = tree.selectedNode[0].get("oid");
      target.document.forms[0].parentname.value = tree.selectedNode[0].name;
    } else if ( target.document.forms[0].cmd.value == "delete" ) {
      target.document.location.href = "/plm/jsp/doc/doctype/ManageDocDelete.jsp?oid="+tree.selectedNode[0].get("oid");
    } else if ( target.document.forms[0].cmd.value == "modify" ) {
      target.document.location.href = "/plm/jsp/doc/doctype/ManageDocUpdate.jsp?oid="+tree.selectedNode[0].get("oid");
    }
    */
  }
<%
  ArrayList nodeList = ProjectOutPutTypeHelper.getList();
  Object[] obj = (Object[])nodeList.get(0);
  out.print("tree.root.put(\"oid\", \""+CommonUtil.getOIDString((ProjectOutPutType)obj[1])+"\");");
  String root = ""+CommonUtil.getOIDLongValue((ProjectOutPutType)obj[1]);
  for(int i = 1; i < nodeList.size(); i++)
  {
    obj = (Object[])nodeList.get(i);
    ProjectOutPutType parent = (ProjectOutPutType)obj[0];
    ProjectOutPutType my = (ProjectOutPutType)obj[1];

    String parentNodeId = ""+CommonUtil.getOIDLongValue(parent);
    String myNodeId = ""+CommonUtil.getOIDLongValue(my);

//    Kogger.debug("child Name: " + my.getName());
//    Kogger.debug("parentNodeId: " + parentNodeId);
//    Kogger.debug("childNodeId: " + myNodeId);
%>
  var node<%=myNodeId%> = new TreeNode("<%=my.getName()%>");
  node<%=myNodeId%>.put("oid", "<%=CommonUtil.getOIDString(my)%>");

<%
  if(root.equals(parentNodeId))
    {
%>
  tree.root.add(node<%=myNodeId%>);
<%
    }
  else{
    %>
node<%=parentNodeId%>.add(node<%=myNodeId%>);
    <%
  }
  }
%>
//    tree.expandAll();
  tree.repaint();
</SCRIPT>
</form>
</body>
</html>
