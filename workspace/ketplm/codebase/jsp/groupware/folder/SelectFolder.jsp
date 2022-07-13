<%@page contentType="text/html; charset=UTF-8" %>
<%@page import = "e3ps.common.util.CommonUtil,
        e3ps.common.folder.FolderUtil,
        wt.folder.Folder,
        java.util.ArrayList"%>
<%@include file="/jsp/common/context.jsp"%>
<html>
<head>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/newtree.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function doSubmit()
{
<%
  String target = request.getParameter("target");
  if( target == null ) target = "temp";
%>
  opener.document.forms[0].<%=target%>.value = document.forms[0].folderOid.value;
  opener.document.forms[0].<%=target%>Name.value = document.forms[0].folderName.value;
  self.close();
}

//-->
</SCRIPT>
</head>
<BODY  marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" >
<FORM>

<table width=99% height=99% border=0>
<tr>
<td>
<!-----------상단타이틀 및 버튼//-------------------->
<table width="95%" height=40 align=center border=0>
  <tr>
    <td width="296" align="left">
        <table border=0 cellpadding=0 cellspacing=0 >
        <tr>
          <td><img src=/plm/portal/images/title2_left.gif></td>
          <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "03000") %><%--폴더 선택--%></td>
          <td><img src=/plm/portal/images/title2_right.gif></td>
        </tr>
        </table>
    </td>
  </tr>
</table>
<!--------상단타이틀 끝//--------->
</td>
</tr>
<tr>
<td height=100%>
  <table border="0" cellpadding="1" cellspacing="1" width="90%" bgcolor="#AABDC6" align="center" height="100%">
    <tr  bgcolor="#D8E0E7" align=center height=20>
      <td width="1%" bgcolor="#D8E0E7"><input type='text' name='folderName' value='' onfocus='blur()' id=i>
        <input type='hidden' name='folderOid'>&nbsp;<input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' onclick='doSubmit()' id=button>
      </TD>
    </TR>
    <tr bgcolor=ffffff height="100%">
      <td valign="top" align="left" height="100%">

        <table border=0 height="100%" width="100%">
          <tr height=100%>
            <td height="100%" valign="top">
                  <DIV id="scrollbox" height="100%">
              <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0 ></table>
                    </DIV>
            </td>
          </tr>
        </table>

      </td>
    </tr>
  </table>
</td>
</tr>
</table>

<TABLE class="main" width="" border="0" cellspacing="0" cellpadding="0">
  <TR>
    <TD>
<%
    String levelLimit = request.getParameter("levelLimit");
    int limit = -1;
    if( levelLimit != null )
      limit = Integer.parseInt(levelLimit);

    Folder rootFolder = FolderUtil.getSelectFolder(request.getParameter("folderpath"));
    ArrayList nodeList = new ArrayList();
    nodeList = makeList(rootFolder, nodeList, 0, limit);
%>
    <SCRIPT>
      var tree = new Tree("treeTable","<%=rootFolder.getName()%>","/plm/portal/icon/tree/board");
      //tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
      //////////////트리 한번에 선택하기//////////////
      tree.selectionMode = tree.SINGLE_TREE_SELECTION;
      tree.treeSelectionListener = function(node, event){
        this.selectNode(node, event);
        document.forms[0].folderOid.value = tree.selectedNode[0].get("oid");
        document.forms[0].folderName.value = tree.selectedNode[0].name;
      }
<%
    out.print("tree.root.put(\"oid\", \""+CommonUtil.getOIDString(rootFolder)+"\");");
    out.print("tree.root.put(\"loc\", \""+rootFolder.getLocation()+"/"+rootFolder.getName()+"\");");
    Folder parent = null;
    Folder my = null;
    Object[] obj = null;
    for(int i = 0; i < nodeList.size(); i++)
    {
      obj = (Object[])nodeList.get(i);
      parent = (Folder)obj[0];
      my = (Folder)obj[1];

      String parentNodeId = ""+CommonUtil.getOIDLongValue(parent);
      String myNodeId = ""+CommonUtil.getOIDLongValue(my);
%>
      var node<%=myNodeId%> = new TreeNode("<%=my.getName()%>");
      node<%=myNodeId%>.put("oid", "<%=CommonUtil.getOIDString(my)%>");
      node<%=myNodeId%>.put("loc", "<%=my.getLocation()+"/"+my.getName()%>");
<%
      if((""+CommonUtil.getOIDLongValue(rootFolder)).equals(""+CommonUtil.getOIDLongValue(parent)))
      {
%>
      tree.root.add(node<%=myNodeId%>);
<%
      }
      else
      {
%>
      node<%=parentNodeId%>.add(node<%=myNodeId%>);
<%
      }
    }
%>
    tree.repaint();
    </SCRIPT>
    </TD>
  </TR>
</TABLE>


</FORM>
</BODY>
</html>
<%!
private ArrayList makeList(Folder folder, ArrayList list, int level, int levelLimit) throws Exception
{
  if( levelLimit != -1 && level >= levelLimit) return list;

  wt.util.SortedEnumeration folderList = FolderUtil.getSubFolders(folder);
  level++;
  while(folderList.hasMoreElements())
  {
    Folder subfolder =(Folder)folderList.nextElement();

    list.add(new Object[]{folder, subfolder});
    list = makeList(subfolder, list, level, levelLimit);
  }

  return list;
}
%>
