<?xml version="1.0" encoding="utf-8" ?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.net.URLDecoder"%>
<SearchList>
<%
	String userName = request.getParameter("user");
	CostAuthCtl costAuthCtl = new CostAuthCtl();
	ArrayList epUserList = costAuthCtl.searchEPUserList(userName);

	Hashtable epItem = null;

	String account = "";
	String name = "";
	String groupName = "";
	String email = "";
	String position = "";
	String auth = "";

		for(int i = 0 ; i < epUserList.size(); i++){
			epItem = (Hashtable)epUserList.get(i);
			account = (String)epItem.get("account");
			name = (String)epItem.get("name");
			groupName = (String)epItem.get("groupName");
			email = (String)epItem.get("email");
			position = (String)epItem.get("position");
			auth = (String)epItem.get("auth");
%>
		<List account="<%=account%>" email="<%=email%>" position="<%=position%>" auth="<%=auth%>" groupName="<%=groupName%>" no="<%=i%>"><%=name%></List>
<%
		}
%>
</SearchList>