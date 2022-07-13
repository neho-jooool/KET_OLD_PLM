package e3ps.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import ext.ket.shared.log.Kogger;

public class PlmDBUtil {

    public static Connection getConnection() throws Exception {
	return getConnection(true);
    }

    public static Connection getConnection(boolean autoCommitFlag) throws Exception {
	Connection con = null;

	try {
	    ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
	    DataSource ds = (DataSource) applicationContext.getBean("wcDataSource");
	    con = ds.getConnection();

	    if (!autoCommitFlag)
		con.setAutoCommit(false);
	} catch (Exception e) {
	    Kogger.error(PlmDBUtil.class, e);
	    throw e;
	}

	return con;
    }

    public static void close(Connection con) {
	if (con != null) {
	    try {
		
		if (!con.isClosed() && !con.getAutoCommit()) {
		    con.rollback();
		    con.setAutoCommit(true);
		}

		if (!con.isClosed()){
		    con.close();    
		}
		
		con = null;
	    } catch (Exception e) {
		Kogger.error(PlmDBUtil.class, e);
	    } finally {
		if (con != null) {
		    try {
			if (!con.isClosed()){
			    con.close();
			}
		    } catch (SQLException e) {
			Kogger.error(PlmDBUtil.class, e);
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
