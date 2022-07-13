<?xml version="1.0"?>
<%response.setContentType("text/xml");%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.net.URLDecoder, java.sql.*, e3ps.cost.util.DBUtil, e3ps.cost.control.CostAuthCtl"%>
<%
	Connection conn = null;
	conn = DBUtil.getConnection();


	String consent_txt = request.getParameter("consent_txt");
	String report_pk = request.getParameter("report_pk");
	String Ename = request.getParameter("Ename");

	consent_txt = java.net.URLDecoder.decode(request.getParameter("consent_txt"), "utf-8");
	Ename = java.net.URLDecoder.decode(request.getParameter("Ename"), "utf-8");

	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();

	consent_txt = "["+Ename+"] "+consent_txt;

	sb.append(" UPDATE cost_report                                                               ");
    sb.append("      SET NOTE_4 = NOTE_4||'" +consent_txt+"'||chr(13)||chr(10)" );
    sb.append("  WHERE CRP_GROUP ="+report_pk );

    pstmt = null;
    try{
        pstmt = conn.prepareStatement(sb.toString());
        pstmt.executeUpdate();

    }catch(Exception e){
        e.printStackTrace();
    }finally{
        sb.delete( 0 , sb.length() );
        if(pstmt!=null) {pstmt.close();}
        if(conn!=null) {conn.close();}
    }
%>

