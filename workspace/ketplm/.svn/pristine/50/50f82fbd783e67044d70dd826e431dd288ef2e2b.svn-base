<?xml version="1.0"?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable"%>
<SearchList>
<%
	String account = request.getParameter("account");

	CostAuthCtl costAuthCtl = new CostAuthCtl();
	ArrayList searchEPuser = costAuthCtl.searchEPuser(account);

	Hashtable epItem = null;

	String EPaccount = "";
	String name = "";

		for(int i = 0 ; i < searchEPuser.size(); i++){
			epItem = (Hashtable)searchEPuser.get(i);
			EPaccount = (String)epItem.get("account");
			name = (String)epItem.get("name");
%>
		<List account="<%=EPaccount%>" no="<%=i+1%>"><%=name%></List>
<%
		}
%>
</SearchList>
