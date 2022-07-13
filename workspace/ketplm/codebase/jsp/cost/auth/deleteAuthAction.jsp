<?xml version="1.0"?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.dao.CostAuthDao, e3ps.cost.control.CostAuthCtl"%>
<%
	String account = request.getParameter("account");
	int beUser = 0;
	int complet = 0;
	CostAuthCtl costAuthCtl = new CostAuthCtl();
	complet = costAuthCtl.deleteAuth(account);
%>
<isComplet><%=complet%></isComplet>
