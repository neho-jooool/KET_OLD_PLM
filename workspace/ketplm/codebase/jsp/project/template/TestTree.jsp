<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*,java.text.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*,e3ps.project.historyprocess.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%

  //e3ps.project.TemplateProject:20449627
  //e3ps.project.TemplateProject:20448367
  String oids[] = request.getParameterValues("oid");

  String newOid = oids[0];
  String oldOid = oids[1];

  TemplateProject newProject = (TemplateProject)CommonUtil.getObject(newOid);
  TemplateProject oldProject = (TemplateProject)CommonUtil.getObject(oldOid);

  TemplateProjectTreeNode root = (TemplateProjectTreeNode)HistoryHelper.getCompareProject(newProject, oldProject);
  String rootFont = getTaskStateFont(root);

%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<style type="text/css">
<!--
body,td {font-family:"굴림"; font-size: 9pt}
#scrollbox {width:100%; height:100%; overflow:auto; padding:0px;}
-->
</style>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/newtree.js"></script>
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
function reloadTree(){
  document.location.reload();
}


//-->
</SCRIPT>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body bgcolor="#ffffff" leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form name="projectTree" method="post">
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=oid%>">
<table height=100% width=100% cellpadding=0 cellspacing=0 border=0 >
<%  if(CommonUtil.isMember("PM_GROUP") || CommonUtil.isAdmin()){%>
  <tr>
    <td width="110" align="left" height=20 >
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=fixLeft></td>
          <td ><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "00480") %><%--Task 관리--%>' id="button" onclick="javascript:manageTask('<%=oid%>');"></td>
          <td class=fixRight></td>
        </tr>
      </table>
    </td>
    <td width="90" align="left" width="0" height=20 >
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=fixLeft></td>
          <td ><input type=button class="btnTras" value='???????' id="button" onclick="javascript:reloadTree();"></td>
          <td class=fixRight></td>
        </tr>
      </table>
    </td>
    <td width="0" align="left" width="0" height=20 >
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=fixLeft></td>
          <td ><input type=button class="btnTras" value='<%=messageService.getString("e3ps.message.ket_message", "01768") %><%--새로고침--%>' id="button" onclick="javascript:reloadTree();"></td>
          <td class=fixRight></td>
        </tr>
      </table>
    </td>

  </tr>
<%}%>
  <tr bgcolor=ffffff>
    <td height=100%>
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
  var target = eval("parent.body");
  var tree = new Tree("treeTable","<%=rootFont%><%=newProject.getPjtName()%></Font>","/plm/portal/icon/tree/project");
  tree.selectionMode = tree.SINGLE_TREE_SELECTION;
  tree.treeSelectionListener = function(node, event){
    this.selectNode(node, event);
    if ( node.get("nodeType") == "project" ) {
      target.document.location.href = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+node.get("oid");
    } else if ( node.get("nodeType") == "task" ) {
      target.document.location.href = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+node.get("oid") + "&compareOid=" + node.get("compareTaskOid");
    }
  }
  tree.root.put("oid","<%=oid%>");
  tree.root.put("nodeType","project");
<%
  String endFONT = "</FONT>";





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
    TemplateTask compareTask = node.getCompareTask();

    String compareTaskOid = "";

    if(compareTask != null){
      compareTaskOid = CommonUtil.getOIDString(compareTask);
    }

%>

       var node<%=childTaskOIDValue%> = new TreeNode("<%=getTaskStateFont(node)%><%=childTask.getTaskName()%><%=endFONT%>");


      node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
      node<%=childTaskOIDValue%>.put("nodeType","task");
      node<%=childTaskOIDValue%>.put("compareTaskOid", "<%=compareTaskOid%>");
    <%if(  parentTaskOIDValue == 0){%>
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

private String getTaskStateFont(TemplateProjectTreeNode node) {

  int result = node.getCompareResult();

  String fontStr = "";

  switch(result){
    case DefaultProjectTreeNode.ADD:
      fontStr ="<FONT color='0033CC' title='" + messageService.getString("e3ps.message.ket_message", "02868")/*추가된 태스크 입니다*/ + "'>";
      break;
    case DefaultProjectTreeNode.DELTE:
      fontStr ="<FONT color='FF3300' title='" + messageService.getString("e3ps.message.ket_message", "01702")/*삭제된 태스크 입니다*/ + "'>";
      break;
    case DefaultProjectTreeNode.MODIFY:
      fontStr ="<FONT color='yellowgreen' title='" + messageService.getString("e3ps.message.ket_message", "01949")/*수정된 태스크 입니다*/ + "'>";
      break;
    case DefaultProjectTreeNode.DEFAULT:
      fontStr ="<FONT color='black' title='" + messageService.getString("e3ps.message.ket_message", "01501")/*변경 이력 없음*/ + "'>";
      break;
  }

  return fontStr;
}

  public void makeVector(TemplateProjectTreeNode node, Vector vector){
    vector.add(node);
    for(int i = 0; i < node.getChildCount(); i++){
      makeVector((TemplateProjectTreeNode)node.getChildAt(i), vector);
    }
  }
%>
