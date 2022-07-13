<%@page import="wt.fc.Persistable"%>
<%@page import="ext.ket.cost.util.CostTreeUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="javax.swing.tree.DefaultMutableTreeNode"%>
<%@page import="ext.ket.cost.entity.*"%>
<%@page import="ext.ket.cost.service.CostCacheManager"%>
<%@page import="java.util.List"%>
<%@page import="ext.ket.cost.code.*"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="ext.ket.cost.*"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.fc.QueryResult"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<table>
<%
QueryResult qr = null;
QuerySpec qs = new QuerySpec();
int i = 0;
int idx = qs.appendClassList(CostFormula.class, true);
qr = PersistenceHelper.manager.find(qs);

List<String> oidList = new ArrayList<String>();

while (qr.hasMoreElements()) {
    Object[] o = (Object[]) qr.nextElement();
    CostFormula obj = (CostFormula) o[0];
    DefaultMutableTreeNode node = CostTreeUtil.manager.getCostTree(obj);
    oidList.add(CommonUtil.getOIDString(obj));
}

System.out.println("load Formula cache ###### " + qr.size());

for(String oid : oidList){
    DefaultMutableTreeNode tree = CostCacheManager.getCostFItem(oid);
}
    

%>

<%for(String oid : oidList){ DefaultMutableTreeNode tree = CostCacheManager.getCostFItem(oid); i++; if(tree != null){%>
<tr><td><%=i%> : <%=CommonUtil.getOIDString( (Persistable) tree.getUserObject() )  %></td></tr>
<%
}else{ 
%>
<tr><td>없어..<%=oid%></td></tr> 
<%
}
%>
<% } %>
</table>
</body>
</html>