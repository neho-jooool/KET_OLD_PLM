<?xml version="1.0"?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%
	String account = request.getParameter("account");
	String name = request.getParameter("name");
	name = new String(name.getBytes("8859_1"), "euc-kr");
	String auth = request.getParameter("auth");
	String group = request.getParameter("group");
	int beUser = 0;
	int complet = 0;
	CostAuthCtl costAuthCtl = new CostAuthCtl();
	beUser = costAuthCtl.searchAuthUser(account);
	if(beUser > 0){
		complet = 99;
	}else{
		complet = costAuthCtl.insertAuth(account, name, auth, group);
	}
%>
<isComplet><%=complet%></isComplet>
