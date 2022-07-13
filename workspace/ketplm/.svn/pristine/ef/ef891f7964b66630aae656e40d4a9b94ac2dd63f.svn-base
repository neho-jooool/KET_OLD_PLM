<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*,java.text.*" %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*,e3ps.project.historyprocess.*"%>
<%@include file="/jsp/common/context.jsp" %>
<%
    String oids[] = request.getParameterValues("oid");

    String oid = oids[0];
    String oldOid = oids[1];

    E3PSProject newProject = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProject oldProject = (E3PSProject)CommonUtil.getObject(oldOid);

    //Kogger.debug(newProject);
    //Kogger.debug(oldProject);



    int newVersion = newProject.getPjtHistory();
    int oldVersion = oldProject.getPjtHistory();

    DefaultProjectTreeNode root = (DefaultProjectTreeNode)HistoryHelper.getCompareProject(newProject, oldProject, new HashMap());
    //TemplateProjectTreeNodeData td2 = (TemplateProjectTreeNodeData)root.getUserObject();

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




    <tr bgcolor=ffffff>
        <td height=100% style="padding-top: 5px" >
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
    <tr>
        <td>
            <font color="#0033CC">■</font> <%=messageService.getString("e3ps.message.ket_message", "02867") %><%--추가된 태스크--%></a> &nbsp;
            <font color="#FF3300">■</font> <%=messageService.getString("e3ps.message.ket_message", "01701") %><%--삭제된 태스크--%></a> &nbsp;
            <br>
            <font color="#66FF33">■</font> <%=messageService.getString("e3ps.message.ket_message", "01948") %><%--수정된 태스크--%></a> &nbsp;
            <font color="#000011">■</font> <%=messageService.getString("e3ps.message.ket_message", "01501") %><%--변경 이력 없음--%></a> &nbsp;
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
            target.document.location.href = "/plm/jsp/project/ProjectCompareProjectView.jsp?oid="+node.get("oid")+ "&oid=" + node.get("oldOid");
        } else if ( node.get("nodeType") == "task" ) {
            target.document.location.href = "/plm/jsp/project/ProjectCompareTaskView.jsp?oid="+node.get("oid") +
            "&compareTaskOid=" + node.get("compareTaskOid") +
            "&compareResult=" + node.get("compareResult") +
            "&newVersion="  + node.get("newVersion") +
            "&oldVersion=" + node.get("oldVersion");
        }
    }
    tree.root.put("oid","<%=oid%>");
    tree.root.put("oldOid","<%=oldOid%>");
    tree.root.put("nodeType","project");
<%
    String endFONT = "</FONT>";

    Vector list = new Vector();
    makeVector(root, list);

    for(int i = 0; i < list.size(); i++){

        DefaultProjectTreeNode node = (DefaultProjectTreeNode)list.get(i);
        TreeNodeData td = (TreeNodeData)node.getUserObject();


        if(td.getData() instanceof TemplateProject){
            /* TemplateProject 이다*/
            continue;
        }

        TemplateTask childTask = (TemplateTask)td.getData();

        DefaultProjectTreeNode pnode = (DefaultProjectTreeNode)node.getParent();
        TreeNodeData ptd = (TreeNodeData)pnode.getUserObject();

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
        String historyVersion = "";

        if(compareTask != null){
            compareTaskOid = CommonUtil.getOIDString(compareTask);
        }
        int compareResult = node.getCompareResult();

        if(node.getCompareResult() == DefaultProjectTreeNode.DELTE){
            compareTaskOid = childTaskOID;
            childTaskOID = "";
        }
%>

           var node<%=childTaskOIDValue%> = new TreeNode("<%=getTaskStateFont(node)%><%=childTask.getTaskName()%><%=endFONT%>");


            node<%=childTaskOIDValue%>.put("oid","<%=childTaskOID%>");
            node<%=childTaskOIDValue%>.put("nodeType","task");
            node<%=childTaskOIDValue%>.put("compareTaskOid", "<%=compareTaskOid%>");
            node<%=childTaskOIDValue%>.put("compareResult", "<%=compareResult%>");
            node<%=childTaskOIDValue%>.put("newVersion", "<%=newVersion%>");
            node<%=childTaskOIDValue%>.put("oldVersion", "<%=oldVersion%>");

        <%if(  parentTaskOIDValue == 0){%>
                tree.root.add(node<%=childTaskOIDValue%>);
        <%}else{%>

            node<%=parentTaskOIDValue%>.add(node<%=childTaskOIDValue%>);

        <%}%>

<%}%>


   tree.expandAll();
   tree.repaint();
</SCRIPT>

</form>
</body>

</html>
<%!

private String getTaskStateFont(DefaultProjectTreeNode node) {

    int result = node.getCompareResult();

    String fontStr = "";

    switch(result){
        case DefaultProjectTreeNode.ADD:
            fontStr ="<FONT color='0033CC'>";
            break;
        case DefaultProjectTreeNode.DELTE:
            fontStr ="<FONT color='FF3300'>";
            break;
        case DefaultProjectTreeNode.MODIFY:
            fontStr ="<FONT color='66FF33'>";
            break;
        case DefaultProjectTreeNode.DEFAULT:
            fontStr ="<FONT color='000011'>";
            break;
    }

    return fontStr;
}

    public void makeVector(DefaultProjectTreeNode node, Vector vector){
        vector.add(node);
        for(int i = 0; i < node.getChildCount(); i++){
            makeVector((DefaultProjectTreeNode)node.getChildAt(i), vector);
        }
    }
%>

