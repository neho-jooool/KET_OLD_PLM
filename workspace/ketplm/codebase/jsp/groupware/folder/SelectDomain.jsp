<%@page contentType="text/html; charset=UTF-8" %>
<%@page import = "e3ps.common.util.CommonUtil,
        wt.admin.*,
        wt.inf.container.WTContainerRef,
        java.util.ArrayList"%>
<%@include file="/jsp/common/context.jsp"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01252") %><%--도메인 선택--%></TITLE>
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
  opener.document.forms[0].<%=target%>.value = document.forms[0].domain_oid.value;
  opener.document.forms[0].<%=target%>name.value = document.forms[0].domain_name.value;
  self.close();
}

//-->
</SCRIPT>
</head>
<BODY  marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" >
<FORM>
<!-----------상단타이틀 및 버튼//-------------------->
<table width="95%" height=40 align=center border=0>
  <tr>
    <td width="296" align="left">
        <table border=0 cellpadding=0 cellspacing=0 >
        <tr>
          <td><img src=/plm/portal/images/title2_left.gif></td>
          <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01252") %><%--도메인 선택--%></td>
          <td><img src=/plm/portal/images/title2_right.gif></td>
        </tr>
        </table>
    </td>
  </tr>
</table>
<!--------상단타이틀 끝//--------->
<table border="0" cellpadding="1" cellspacing="1" width="95%" bgcolor="#AABDC6" align="center" height=300>
  <tr  bgcolor="#D8E0E7" align=center height=20>
    <td width="1%" bgcolor="#D8E0E7"><input type='text' name='domain_name' value='' onfocus='blur()' id=i>
      <input type='hidden' name='domain_oid'>&nbsp;<input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>' onclick='doSubmit()' id=button>
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
<TABLE class="main" width="" border="0" cellspacing="0" cellpadding="0">
  <TR>
    <TD>
<%
    wt.inf.container.WTContainerRef conRef = wt.inf.container.WTContainerHelper.getClassicRef();
    AdminDomainRef root = AdministrativeDomainHelper.getAdminDomainRef(AdministrativeDomainHelper.ROOT_DOMAIN);
%>
    <SCRIPT>
      var tree = new Tree("treeTable","<%=root.getName()%>","/plm/portal/icon/tree/board");
      //tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
      //////////////트리 한번에 선택하기//////////////
      tree.selectionMode = tree.SINGLE_TREE_SELECTION;
      tree.treeSelectionListener = function(node, event){
        this.selectNode(node, event);
        document.forms[0].domain_oid.value = tree.selectedNode[0].get("oid");
        document.forms[0].domain_name.value = tree.selectedNode[0].name;
      }

      tree.root.put("oid", "<%=AdministrativeDomainHelper.manager.getDomainPath((AdministrativeDomain)root.getObject())%>");
<%
      makeTree(out, root, ""+CommonUtil.getOIDLongValue(root.getObject()), conRef);
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
private void makeTree(JspWriter out, AdminDomainRef parent, String rootId, WTContainerRef conRef) throws Exception
{
    ArrayList list = AdministrativeDomainHelper.manager.getChildDomains(parent);
    for(int i=0 ; i<list.size() ; i++)
    {
        AdminDomainRef child = (AdminDomainRef)list.get(i);

        AdministrativeDomain domain = (AdministrativeDomain)child.getObject();
        if(!conRef.equals(domain.getContainerReference())) continue;

        String parentId = ""+CommonUtil.getOIDLongValue(parent.getObject());
        String childId = ""+CommonUtil.getOIDLongValue(child.getObject());

        out.print("var node"+childId + " = new TreeNode('"+child.getName()+"');");
        out.print("node"+childId+".put('oid', '"+AdministrativeDomainHelper.manager.getDomainPath((AdministrativeDomain)child.getObject())+"');");

        if(rootId.equals(parentId))
        {
            out.print("tree.root.add(node"+childId+");");
        }
        else
        {
            out.print("node"+parentId+".add(node"+childId+");");
        }

        makeTree(out, child, rootId, conRef);
    }
}
%>
