<?xml version="1.0"?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%
	String account = request.getParameter("account");
	String auth = request.getParameter("auth");
	String group = request.getParameter("group");
	int complet = 0;
	CostAuthCtl costAuthCtl = new CostAuthCtl();
	complet = costAuthCtl.modifyAuth(account, auth, group);
%>
<isComplet><%=complet%></isComplet>
