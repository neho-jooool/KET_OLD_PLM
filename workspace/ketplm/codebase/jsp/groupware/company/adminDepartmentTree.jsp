<%--
  /**
   * @(#)  companytree.jsp
   * Copyright (c) whois. All rights reserverd
   *
   * @version 1.00
   * @since jdk 1.4.02
   * @author Cho Sung Ok
   */
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%@include file="/jsp/common/context.jsp"%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<script src="/plm/portal/js/newtree.js"></script>
<script>
function createNode(){
  if ( isNullData(document.companytree.soid.value) ) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01797") %><%--생성할 부서의 부모부서를 선택하셔야 합니다--%>");
    return;
  } else {
    if ( isNullData(document.companytree.cname.value) ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01798") %><%--생성할 부서의 부서명을 입력하셔야 합니다--%>");
      document.companytree.cname.focus();
      return;
    }
    if ( isNullData(document.companytree.ccode.value) ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01799") %><%--생성할 부서의 부서코드를 입력하셔야 합니다--%>");
      document.companytree.ccode.focus();
      return;
    }
    if ( isNullData(document.companytree.csort.value) ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "01800") %><%--생성할 부서의 소트넘버를 입력하셔야 합니다--%>");
      document.companytree.csort.focus();
      return;
    }
    document.companytree.command.value = "create";
    document.companytree.action = "/plm/servlet/e3ps/ManageDepartmentServlet";
    document.companytree.submit();
  }
}

function deleteNode(){
  if ( isNullData(document.companytree.soid.value) ) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01711") %><%--삭제할 부서를 선택하셔야 합니다--%>");
    return;
  } else {
    if ( tree.selectedNode[0] == tree.root ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "03248") %><%--회사명은 삭제할 수 없습니다--%>");
         return;
    }
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03334") %><%--정말 삭제하시겠습니까?--%>") ) {
      document.companytree.command.value = "delete";
      document.companytree.action = "/plm/servlet/e3ps/ManageDepartmentServlet";
      document.companytree.submit();
    }
  }
}

function updateNode(){
  if ( isNullData(document.companytree.soid.value) ) {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01958") %><%--수정할 부서를 선택하셔야 합니다--%>");
    return;
  } else {
    if ( tree.selectedNode[0] == tree.root ) {
      alert("<%=messageService.getString("e3ps.message.ket_message", "03249") %><%--회사명은 수정할 수 없습니다--%>");
      return;
    }

    document.companytree.command.value = "update";
    document.companytree.action = "/plm/servlet/e3ps/ManageDepartmentServlet";
    document.companytree.submit();
  }
}
</SCRIPT>

<style type="text/css">
<!--
#scrollbox {width:230; height:100%; overflow:auto; padding:0px;}
-->
</style>
<BODY leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
<form name=companytree method=post>
<input type="hidden" name="command">
<input type="hidden" name="soid">
<!--테이블1//-->
<table align=center border=0 width=100% height=100%>
<tr>
<td>
      <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABCC7 align=center>
                <tr bgcolor="#D9E2E7" align=center><td colspan=2 id=title><%=messageService.getString("e3ps.message.ket_message", "01550") %><%--부서 추가--%></td></tr>
        <tr>
                    <td align=center width=80 bgcolor=eeeeee><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
          <td align=center width=100% bgcolor=ffffff><INPUT id=input size=15 name=cname></td>
        </tr>
        <tr>
                    <td align=center nowrap bgcolor=eeeeee><%=messageService.getString("e3ps.message.ket_message", "01563") %><%--부서코드--%></td>
          <td align=center nowrap bgcolor=ffffff><INPUT id=input size=15 name=ccode></td>
        </tr>
        <tr>
          <td align=center nowrap bgcolor=eeeeee><%=messageService.getString("e3ps.message.ket_message", "01922") %><%--소트넘버--%></td>
          <td align=center nowrap bgcolor=ffffff><INPUT id=input size=15 name=csort></td>
        </tr>
        <tr>
          <td colspan=2 align=center width=100% bgcolor=ffffff><input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>' onclick="javascript:createNode();"></td>
        </tr>
      </table>
<!--테이블1끝//-->
<br>
<!--테이블2//-->
      <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor=AABCC7 align=center>
        <tr bgcolor="#D9E2E7" align=center><td colspan=2 id=title><%=messageService.getString("e3ps.message.ket_message", "01803") %><%--선택 부서--%></td></tr>
        <tr>
                    <td align=center width=80 bgcolor=eeeeee><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
          <td align=center width=100% bgcolor=ffffff><INPUT id=input size=15 name=sname></td>
        </tr>
        <tr>
                    <td align=center nowrap bgcolor=eeeeee><%=messageService.getString("e3ps.message.ket_message", "01563") %><%--부서코드--%></td>
          <td align=center nowrap bgcolor=ffffff><INPUT id=input size=15 name=scode></td>
        </tr>
        <tr>
          <td align=center nowrap bgcolor=eeeeee><%=messageService.getString("e3ps.message.ket_message", "01922") %><%--소트넘버--%></td>
          <td align=center width=100% bgcolor=ffffff><INPUT id=input size=15 name=ssort></td>
        </tr>
        <tr>
          <td align=center nowrap bgcolor=eeeeee nowrap><%=messageService.getString("e3ps.message.ket_message", "01759") %><%--상위부서코드--%></td>
          <td align=center width=100% bgcolor=ffffff><INPUT id=input size=15 name=sdept></td>
        </tr>
        <tr>
          <td colspan=2 align=center width=100% bgcolor=ffffff>
            <input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>' onclick="javascript:updateNode();">&nbsp;
            <input type=button id=button2 value='<%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%>' onclick="javascript:deleteNode();">
          </td>
        </tr>
      </table>
<!--테이블2끝//-->
</td>
</tr>
<tr>
<td height=100% valign=top>
<hr size=2 color="#425E6E">
<DIV id="scrollbox">
<table border=0 width=241 height=100% valign=top>
  <tr>
    <td valign=top>
      <table oncontextmenu="return false" onselectstart="return false"  id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
    </td>
  </tr>
</table>
</DIV>

</td>
</tr>
</table>


<SCRIPT>

  var target = eval("parent.main");
  var tree = new Tree("treeTable","부서","<%=iconUrl%>/tree/company");
  tree.selectionMode = tree.DISCONTIGUOUS_TREE_SELECTION;
  tree.treeSelectionListener = function(node, event){
    this.selectNode(node, event);
    document.companytree.soid.value = node.get("oid");
    document.companytree.sname.value = node.name;
    document.companytree.scode.value = node.get("code");
    document.companytree.ssort.value = node.get("sort");
    document.companytree.sdept.value = node.get("dept");
    target.document.location.href = "/plm/servlet/e3ps/ManagePeopleServlet?oid="+node.get("oid");

  }
    tree.root.put("oid","root");
  tree.root.put("code","root");
  tree.root.put("sort","0");
  tree.root.put("dept","root");
<%
  ArrayList topList = DepartmentHelper.manager.getTopList();
  for ( int i = 0 ; i < topList.size() ; i++ ) {
    Department topDepartment = (Department)topList.get(i);

    long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
    String tempCode = null;
    if((Department)topDepartment.getParent() != null)
      tempCode = ((Department)topDepartment.getParent()).getCode();
    else
      tempCode = "root";
%>
  var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%> [<%=topDepartment.getCode()%>][<%=(topDepartment.getCcCode()==null)?"":topDepartment.getCcCode()%>]");
  node<%=topnodeid%>.put("oid","<%=CommonUtil.getOIDString(topDepartment)%>");
  node<%=topnodeid%>.put("code","<%=topDepartment.getCode()%>");
  node<%=topnodeid%>.put("sort","<%=topDepartment.getSort()%>");
  node<%=topnodeid%>.put("dept","<%=tempCode%>");
  tree.root.add(node<%=topnodeid%>);
<%
    ArrayList underList = DepartmentHelper.manager.getAllChildList(topDepartment,new ArrayList());
    for ( int j = 0 ; j < underList.size() ; j++ ) {
      Department underDepartment = (Department)underList.get(j);
      Department parentDepartment = (Department)underDepartment.getParent();
      long undernodeid = CommonUtil.getOIDLongValue(underDepartment);
      long parentnodeid = CommonUtil.getOIDLongValue(parentDepartment);
%>
  var node<%=undernodeid%> = new TreeNode("<%=underDepartment.getName()%> [<%=underDepartment.getCode()%>][<%=(underDepartment.getCcCode()==null)?"":underDepartment.getCcCode()%>]");
  node<%=undernodeid%>.put("oid","<%=CommonUtil.getOIDString(underDepartment)%>");
  node<%=undernodeid%>.put("code","<%=underDepartment.getCode()%>");
  node<%=undernodeid%>.put("sort","<%=underDepartment.getSort()%>");
  node<%=undernodeid%>.put("dept","<%=parentDepartment.getCode()%>");
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
