package e3ps.common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ext.ket.shared.log.Kogger;

public class QueryUtil {

    public static int getRowCount(String conPoolName, String tableName, String indexColumName, String where) {

	Connection con = null;
	Statement st = null;
	ResultSet rs = null;

	int rowCount = 0;

	try {
	    // connect 객체를 얻어온다
	    con = DBCPManager.getConnection(conPoolName);
	    st = con.createStatement();

	    String condition = "";
	    if (where != null && where.trim().length() != 0) {
		condition = " where " + where;
	    }
	    rs = st.executeQuery("select count(" + indexColumName + ") from " + tableName + condition);

	    if (rs.next()) {
		rowCount = rs.getInt(1);
	    }

	} catch (Exception e) {
	    Kogger.error(QueryUtil.class, e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (st != null)
		    st.close();
		if (con != null)
		    con.close();
	    } catch (SQLException se2) {
	    }
	}

	return rowCount;
    }

    /**
     * SQL 내장함수 count() 를 한다.
     * 
     * @param conPoolName
     * @param tableName
     * @param indexColumName
     * @param where
     * @return
     */
    public static int getRowCount(Connection con, String tableName, String indexColumName, String where) {
	Statement stmt = null;
	ResultSet rs = null;

	int rowCount = 0;
	try {
	    stmt = con.createStatement();

	    String condition = "";
	    if (where != null && where.trim().length() != 0)
		condition = " WHERE " + where;

	    rs = stmt.executeQuery("SELECT count(" + indexColumName + ") FROM " + tableName + condition);

	    if (rs.next())
		rowCount = rs.getInt(1);
	} catch (Exception e) {
	    Kogger.error(QueryUtil.class, e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (stmt != null)
		    stmt.close();
	    } catch (SQLException ex1) {
	    }
	}

	// Kogger.debug(QueryUtil.class, "rowCount---> " + rowCount);

	return rowCount;
    }

    /**
     * SQL 내장함수 MAX() 를 한다.
     * 
     * @param con
     * @param tableName
     * @return
     * @throws SQLException
     */
    public static int getMaxNo(Connection con, String tableName) throws SQLException {
	Statement st = null;
	ResultSet rs = null;

	int maxNo = 0;

	try {
	    st = con.createStatement();
	    String sql = "SELECT MAX(no) FROM " + tableName;
	    rs = st.executeQuery(sql);
	    if (rs.next()) {
		maxNo = rs.getInt(1);
	    }
	} catch (Exception e) {
	    Kogger.error(QueryUtil.class, e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (st != null)
		    st.close();
	    } catch (SQLException ex1) {
	    }
	}
	return maxNo;
    }
}
