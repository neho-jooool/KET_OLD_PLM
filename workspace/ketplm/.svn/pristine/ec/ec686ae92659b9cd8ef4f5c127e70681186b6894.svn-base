<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%
//out.println("=======>"+request.getParameter("m_maker"));
	Connection conn = null;
	conn = DBUtil.getConnection();

	CostComDao codeDao = new CostComDao(conn);
	String m_maker = "풍산";
	String meterial = "풍산";
	ArrayList SearchList = codeDao.getSearchGrade(m_maker,meterial);//비철
%>
