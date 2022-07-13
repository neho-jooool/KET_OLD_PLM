package e3ps.cost.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class HomePageDBUtil {

    public static Connection getConnection() throws Exception {
	return getConnection(true);
    }

    public static Connection getConnection(boolean autoCommitFlag) throws Exception {
	Connection con = null;

	try {
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    String connURL = "jdbc:sqlserver://192.168.1.35:1433;user=ketweb;password=ket5006";
	    //String connURL = "jdbc:sqlserver://192.168.1.37:1433;database=KETPLM;user=ketweb;password=ket2019";
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
