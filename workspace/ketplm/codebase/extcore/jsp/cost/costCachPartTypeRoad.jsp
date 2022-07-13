<%@page import="ext.ket.cost.code.service.CostCodeHelper"%>
<%@page import="wt.fc.Persistable"%>
<%@page import="ext.ket.cost.util.CostTreeUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="javax.swing.tree.DefaultMutableTreeNode"%>
<%@page import="ext.ket.cost.entity.*"%>
<%@page import="ext.ket.cost.service.CostCacheManager"%>
<%@page import="java.util.*"%>
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

Map<String, String> childEnumProp = new HashMap<String, String>();
String eNum = "";
String eNumKeys = "";
String childEnum = "";
String childEnumKeys = "";
QueryResult qr = null;
QuerySpec qs = new QuerySpec();
int idx = qs.appendClassList(CostPartType.class, true);
qr = PersistenceHelper.manager.find(qs);
String partTypeOid = "";
int i=0;
while (qr.hasMoreElements()) {
    Object[] o = (Object[]) qr.nextElement();
    CostPartType obj = (CostPartType) o[0];
    partTypeOid = CommonUtil.getOIDLongValue2Str(obj);
    List<Map<String, Object>> list = CostCodeHelper.service.getCostFactoryTreeInfoByPartType(partTypeOid);
    
    if(list.size() != 0){
	   List<Map<String, Object>> cachelist = CostCacheManager.getCostPTItem(partTypeOid); 
	   if(cachelist.size() == 0){
	  
%>

<tr><td>없어..<%=partTypeOid%></td></tr> 

<% 
	   }else{
	       for (Map<String, Object> mftFactory : cachelist) {

                   String pOid = (String) mftFactory.get("numberParentOid");
                   if (pOid != null && pOid != "") {

                       childEnum = childEnumProp.get("mftFactory2Enum" + pOid);
                       childEnumKeys = childEnumProp.get("mftFactory2EnumKeys" + pOid);

                       if (childEnum != null) {
                           childEnum += "|" + mftFactory.get("numberCodeName");
                           childEnumKeys += "|" + mftFactory.get("numberOidLong");
                       } else {
                           childEnum = "|" + mftFactory.get("numberCodeName");
                           childEnumKeys = "|" + mftFactory.get("numberOidLong");
                       }

                       childEnumProp.put("mftFactory2Enum" + pOid, childEnum);
                       childEnumProp.put("mftFactory2EnumKeys" + pOid, childEnumKeys);

                   } else {
                       eNum += "|" + (String) mftFactory.get("numberCodeName");
                       eNumKeys += "|" + (String) mftFactory.get("numberOidLong");
                   }
                   %>
                   
                   <tr><td>1<%=eNum %><%=eNumKeys %></td></tr>
                   
                   <%
               }

	   }
    }
}
%>
</table>
</body>
</html>