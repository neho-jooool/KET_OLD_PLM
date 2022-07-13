package ext.ket.project.purchase.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class EPDBUtil {

    public static Connection getConnection() throws Exception {
	return getConnection(true);
    }

    public static Connection getConnection(boolean autoCommitFlag) throws Exception {
	Connection con = null;
	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	    String connURL = "";
	    connURL = "jdbc:sqlserver://192.168.1.132:1433;database=xclick_ket;user=dpuser;password=dpuser12!@";

	    con = DriverManager.getConnection(connURL);

	    // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	    //
	    // java.util.Properties prop = new java.util.Properties();
	    // prop.put("user", "ghost_ket");
	    // prop.put("password", "rhtmxm123!@#");
	    //
	    // con = DriverManager.getConnection( "jdbc:odbc:KET_GW", prop );

	    if (!autoCommitFlag)
		con.setAutoCommit(false);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	}

	return con;
    }

    public static void close(Connection con) {
	try {
	    if (!con.getAutoCommit()) {
		con.rollback();
		con.setAutoCommit(true);
	    }

	    if (con != null)
		con.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
