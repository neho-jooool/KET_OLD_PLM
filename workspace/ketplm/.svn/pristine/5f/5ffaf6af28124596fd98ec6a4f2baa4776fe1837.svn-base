<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, java.sql.*"%>
<%
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  StringBuffer sb = new StringBuffer();
  
  sb.append(" SELECT name FROM destiny_ket.cduser ");
  
  try{
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    String connURL = "jdbc:sqlserver://192.168.1.122:1433;user=ghost_ket;password=gost123!@#";
    conn = DriverManager.getConnection(connURL);
    
    pstmt = conn.prepareStatement(sb.toString());
    
    rs = pstmt.executeUpdate();
    
    while(rs.next()){
      out.println(rs.getString("")+"<br/>");
    }
    
    pstmt.close();
  }catch(Exception e){
    out.println(e.toString());
  }
%>
