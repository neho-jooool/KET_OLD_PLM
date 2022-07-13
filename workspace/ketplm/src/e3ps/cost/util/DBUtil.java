package e3ps.cost.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import e3ps.common.dao.conn.DbcpManager;

public class DBUtil {

    public static Connection getConnection() throws Exception {
	return getConnection(true);
    }

    public static Connection getConnection(boolean autoCommitFlag) throws Exception {
	Connection con = null;

	try {
	    // Class.forName("oracle.jdbc.driver.OracleDriver");
	    // // [PLM System 1차 고도화] DB접속정보 운영->개발로 변경(운영주석처리), 2014-06-12, 김근우
	    // con = DriverManager.getConnection("jdbc:log4jdbc:oracle:thin:@192.168.1.116:1521:WIND", "rehearsal_1st", "rehearsal_1st");//
	    // 개발서버
	    // // con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.116:1521:WIND", "wcadmin", "wcadmin");// 운영
	    ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
	    if (applicationContext == null) {
		// Spring Test에서 RDB깨져서 임시로 넣어둠;
		// TODO TKLEE 삭제 필요?!
		con = DbcpManager.getInstance().getConnection();
		if (!autoCommitFlag)
		    con.setAutoCommit(false);
		return con;
	    }

	    DataSource ds = (DataSource) applicationContext.getBean("wcDataSource");
	    con = ds.getConnection();
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
