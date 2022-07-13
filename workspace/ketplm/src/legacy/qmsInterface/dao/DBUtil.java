package legacy.qmsInterface.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    public static Connection getConnection(String gubun) throws Exception {
	return getConnection(true,gubun);
    }

    public static Connection getConnection(boolean autoCommitFlag,String gubun) throws Exception {
	Connection con = null;
	try {
	    
	    String connURL  = null;
	    String user     = null;
	    String password = null;
	    	
	    if("qms".equals(gubun)){
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	connURL = "jdbc:sqlserver://192.168.1.170:1433;database=KetQMS";
	    	user = "sa";
	    	password = "KQISket1151";
	    }
	    
	    if("legacy".equals(gubun)){
		Class.forName("oracle.jdbc.driver.OracleDriver");
	    	//connURL = "jdbc:oracle:thin:@192.168.1.217:1521:KETDB"; 구 인사DB
		connURL = "jdbc:oracle:thin:@192.168.1.50:1521:PEHRDB";
	    	user = "EHR_KET";
	    	password = "ehr#1128p";
	    }
	    
	    
	    con = DriverManager.getConnection(connURL,user,password);

	    if (!autoCommitFlag)
		con.setAutoCommit(false);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	}

	return con;
    }

    public static void close(Connection con) {
	if (con != null) {
	    try {
		if (!con.getAutoCommit()) {
		    con.rollback();
		    con.setAutoCommit(true);
		}

		con.close();
		con = null;
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		if (con != null) {
		    try {
			con.close();
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
    }

    public static void close(Statement stmt) {
	if (stmt != null) {
	    try {
		stmt.close();
	    } catch (Exception e) {
	    }
	}
    }

    public static void close(ResultSet rs) {
	if (rs != null) {
	    try {
		rs.close();
	    } catch (Exception e) {
	    }
	}
    }

}
