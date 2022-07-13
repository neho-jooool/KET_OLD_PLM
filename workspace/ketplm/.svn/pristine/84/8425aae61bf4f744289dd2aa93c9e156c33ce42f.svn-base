<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, java.sql.*" %>
<%
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  StringBuffer sb = new StringBuffer();
  
  sb.append(" SELECT name FROM cduser ");
  
  try{
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    String connURL = "jdbc:sqlserver://192.168.1.122:1433;user=ghost_ket;password=rhtmxm123!@#";
    conn = DriverManager.getConnection(connURL);
    
    pstmt = conn.prepareStatement(sb.toString());
    
    rs = pstmt.executeQuery();
    
    while(rs.next()){
      out.println(rs.getString("name")+"<br/>");
    }
    
    pstmt.close();
  }catch(Exception e){
  	out.println("안됨\n");
    out.println(e.toString());
  }
%>
