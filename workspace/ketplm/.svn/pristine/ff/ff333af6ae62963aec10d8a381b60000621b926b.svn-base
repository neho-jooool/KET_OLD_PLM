<%@page import="wt.session.SessionHelper"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="e3ps.groupware.company.*,e3ps.common.util.*"%>
<%@page import="wt.fc.*, wt.org.WTUser"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String reqType = request.getParameter("target");
    Department department = DepartmentHelper.manager.getDepartment((WTUser) SessionHelper.manager.getPrincipal());
    String sessionUserDeptOid = CommonUtil.getOIDLongValue2Str(department);
%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp"%>
<script src="/plm/portal/js/newtree.js"></script>
<style type="text/css">
<!--
body,td {
	font-family: "굴림";
	font-size: 9pt
}
-->
</style>
<BODY leftmargin="0" marginwidth="0" topmargin="0" marginheight="0">
  <form name=companytree method=post>
    <input type="hidden" name="command">
    <input type="hidden" name="soid">
    <table border=0 width=100%>
      <tr>
        <td>
          <table oncontextmenu="return false" onselectstart="return false" id=treeTable ondragstart="return false" cellSpacing=0 cellPadding=0 border=0></table>
        </td>
      </tr>
    </table>
    <SCRIPT>
    var type = "<%= request.getParameter("target")%>";
    var target = eval("parent.main");
    var tree = new Tree("treeTable","<%=CompanyState.companyName%>","<%=iconUrl%>/tree/company1");
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
   
   tree.root.put("oid","root");
<%
    ArrayList topList = DepartmentHelper.manager.getTopList();
    for ( int i = 0 ; i < topList.size() ; i++ ) {
        Department topDepartment = (Department)topList.get(i);
        long topnodeid = CommonUtil.getOIDLongValue(topDepartment);
%>
    var node<%=topnodeid%> = new TreeNode("<%=topDepartment.getName()%>");
    node<%=topnodeid%>.put("oid","<%=CommonUtil.getOIDString(topDepartment)%>");
    tree.root.add(node<%=topnodeid%>);
<%
        ArrayList underList = DepartmentHelper.manager.getAllChildList(topDepartment,new ArrayList());
        for ( int j = 0 ; j < underList.size() ; j++ ) {
            Department underDepartment = (Department)underList.get(j);
            Department parentDepartment = (Department)underDepartment.getParent();
            long undernodeid = CommonUtil.getOIDLongValue(underDepartment);
            long parentnodeid = CommonUtil.getOIDLongValue(parentDepartment);
            long peoplenodeid = 0;

%>
            var node<%=undernodeid%> = new TreeNode("<%=underDepartment.getName()%>");
            node<%=undernodeid%>.put("oid","<%=CommonUtil.getOIDString(underDepartment)%>");
            node<%=parentnodeid%>.add(node<%=undernodeid%>);
<%
                QueryResult qr = DepartmentHelper.manager.getDepartmentPeople(underDepartment);
                People pdata = new People();
                while(qr.hasMoreElements()){
                    Object[] data = (Object[])qr.nextElement();
                    pdata = (People)data[0];
                    peoplenodeid = CommonUtil.getOIDLongValue(pdata);
                    if( pdata.getDepartment().equals(underDepartment)){
                	    String peopleName = pdata.getName();
                        String chief = StringUtil.checkNull(pdata.getChief());
                        String deptName = (pdata.getDepartment() == null)?"":pdata.getDepartment().getName();
                        if(chief.equals(deptName)) peopleName += "[팀장]";
                    %>
                        var node<%=peoplenodeid%> = new TreeNode("<%=peopleName%>");
                        node<%=peoplenodeid%>.put("userid","<%=pdata.getId()%>");
                        node<%=undernodeid%>.add(node<%=peoplenodeid%>);

        <%
                }
            }
        }
    }
%>
var userDeptNode = node<%=sessionUserDeptOid%>;
if (userDeptNode != null) {
    userDeptNode.isCollapsed = 0;
    var parentNode = userDeptNode.parent;
    while (parentNode != tree.root) {
        parentNode.isCollapsed = 0;
        parentNode = parentNode.parent;
    }
}
tree.repaintAll();
                </SCRIPT>
  </form>
</body>
</html>
