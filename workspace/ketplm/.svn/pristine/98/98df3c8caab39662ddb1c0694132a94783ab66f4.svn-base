package legacy.qmsInterface.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

//import ext.ket.shared.log.Kogger;

/**
 * 
 * @클래스명 : DefaultDaoManager
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public final class DefaultDaoManager implements DaoManager {

    @Override
    public <T> T queryForObject(String sSql, RowSetStrategy<T> strategy) throws Exception {
	return queryForObject(sSql, null, strategy);
    }

    @Override
    public <T> T queryForObject(String sSql, List alBindSql, RowSetStrategy<T> strategy) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;
	T t = null;

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet != null) {
		if (oResultSet.next()) {
		    t = strategy.mapRow(oResultSet);
		}
	    }

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {
	    close(oConnection, oPstmt, oResultSet);
	}

	return t;
    }

    @Override
    public <T> List<T> queryForList(String sSql, RowSetStrategy<T> strategy) throws Exception {
	return queryForList(sSql, null, strategy);
    }

    @Override
    public <T> List<T> queryForList(String sSql, List alBindSql, RowSetStrategy<T> strategy) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;
	List<T> list = new ArrayList<T>();

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet != null) {
		while (oResultSet.next()) {
		    T t = strategy.mapRow(oResultSet);
		    list.add(t);
		}
	    }

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {
	    close(oConnection, oPstmt, oResultSet);
	}

	return list;
    }

    @Override
    public <T> Map<String, T> queryForMap(String sSql, RowSetStrategyMap<T> strategy) throws Exception {
	return queryForMap(sSql, null, strategy);
    }

    @Override
    public <T> Map<String, T> queryForMap(String sSql, List alBindSql, RowSetStrategyMap<T> strategy) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;
	Map<String, T> map = new HashMap<String, T>();

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet != null) {
		while (oResultSet.next()) {
		    String key = strategy.getKey(oResultSet);
		    T t = strategy.mapRow(oResultSet);
		    map.put(key, t);
		}
	    }

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {
	    close(oConnection, oPstmt, oResultSet);
	}

	return map;
    }

    @Override
    public int update(String sSql) throws Exception {
	return update(sSql, null);
    }

    @Override
    public int update(String sSql, List alBindSql) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;

	int iVal = 0;

	try {

	    log(sSql);

	    oConnection = getConnection("qms");

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    iVal = oPstmt.executeUpdate();

	    return iVal;

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {

	    close(oConnection, oPstmt, null);
	}

    }

    @Override
    public int update2(String sSql, List alBindSql, Connection con, PreparedStatement oPstmt) throws Exception {

	int iVal = 0;

	try {

	    log(sSql);

	    oPstmt = con.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    iVal = oPstmt.executeUpdate();

	    return iVal;

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	}

    }

    @Override
    public boolean queryForBool(String sSql, RowBoolStrategy strategy) throws Exception {
	return queryForBool(sSql, null, strategy);
    }

    @Override
    public boolean queryForBool(String sSql, List alBindSql, RowBoolStrategy strategy) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;
	boolean ret = false;

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet != null) {
		if (oResultSet.next()) {
		    ret = strategy.mapRow(oResultSet);
		}
	    }

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {

	    close(oConnection, oPstmt, oResultSet);
	}

	return ret;
    }

    @Override
    public int queryForInt(String sSql, RowIntStrategy strategy) throws Exception {
	return queryForInt(sSql, null, strategy);
    }

    @Override
    public int queryForInt(String sSql, List alBindSql, RowIntStrategy strategy) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;
	int ret = -1;

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet != null) {
		if (oResultSet.next()) {
		    ret = strategy.mapRow(oResultSet);
		}
	    }

	} catch (Exception e) {
	    log(sSql, e);
	    throw e;

	} finally {

	    close(oConnection, oPstmt, oResultSet);
	}

	return ret;
    }

    @Override
    public int queryForCount(String sSql) throws Exception {
	return queryForCount(sSql, null);
    }

    @Override
    public int queryForCount(String sSql, List alBindVar) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;

	int iVal = 0;
	StringBuffer sbSql = new StringBuffer();

	try {

	    sbSql.append("SELECT COUNT(*) CNT FROM ( ").append(sSql).append(" )");

	    log(sbSql.toString());

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sbSql.toString());

	    setPrepareParameter(alBindVar, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet.next())
		iVal = oResultSet.getInt(1);

	    return iVal;

	} catch (Exception e) {

	    log(sbSql.toString(), e);
	    throw e;

	} finally {

	    close(oConnection, oPstmt, oResultSet);
	}

    }

    private void setPrepareParameter(List alBindSql, PreparedStatement oPstmt) throws SQLException {

	Object oValue = null;
	String sType = null;
	if (alBindSql != null) {

	    int iBindLength = alBindSql.size();
	    for (int iLoop = 0; iLoop < iBindLength; iLoop++) {

		oValue = alBindSql.get(iLoop);
		if (!"".equals(oValue) && null != oValue && !"null".equals(oValue)) {
		    sType = oValue.getClass().getName();

		    if (sType.equals("java.lang.String"))
			oPstmt.setString((iLoop + 1), (String) oValue);
		    else if (sType.equals("java.lang.Double"))
			oPstmt.setDouble((iLoop + 1), ((Double) oValue).doubleValue());
		    else if (sType.equals("java.lang.Float"))
			oPstmt.setFloat((iLoop + 1), ((Float) oValue).floatValue());
		    else if (sType.equals("java.lang.Integer"))
			oPstmt.setInt((iLoop + 1), ((Integer) oValue).intValue());
		    else if (sType.equals("java.lang.Long"))
			oPstmt.setLong((iLoop + 1), ((Long) oValue).longValue());
		}else{
		    oPstmt.setString((iLoop + 1), "");
		}

	    }

	}
    }

    @Override
    public ResultSet queryForRowSet(String sSql) throws Exception {
	return queryForRowSet(sSql, null);
    }

    @Override
    public ResultSet queryForRowSet(String sSql, List alBindSql) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    if (alBindSql != null)
		this.setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    return oResultSet;

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {

	    close(oConnection, oPstmt, oResultSet);
	}

    }

    @Override
    public Vector queryForRowSetVector(String sSql) throws Exception {
	return queryForRowSetVector(sSql, null);
    }

    @Override
    public Vector queryForRowSetVector(String sSql, List alBindSql) throws Exception {

	Connection oConnection = null;
	PreparedStatement oPstmt = null;
	ResultSet oResultSet = null;
	int ret = -1;
	Vector vec = new Vector();

	try {

	    log(sSql);

	    oConnection = getConnection();

	    oPstmt = oConnection.prepareStatement(sSql);

	    setPrepareParameter(alBindSql, oPstmt);

	    oResultSet = oPstmt.executeQuery();

	    if (oResultSet != null) {
		while (oResultSet.next()) {

		    ResultSetMetaData metaData = (ResultSetMetaData) oResultSet.getMetaData();
		    HashMap row = new HashMap();

		    for (int i = 1; i <= metaData.getColumnCount(); i++) {

			String colName = metaData.getColumnName(i);
			int colType = metaData.getColumnType(i);

			switch (colType) {
			case 12: // VARCHAR2

			    String colValue = oResultSet.getString(colName) == null ? "" : oResultSet.getString(colName);
			    row.put(colName, colValue);
			    break;

			case 91: // DATE

			    row.put(colName, oResultSet.getDate(colName));
			    break;

			case 2: // NUMBER

			    row.put(colName, oResultSet.getBigDecimal(colName));
			    break;

			case 1: // CHAR

			    String colValue2 = oResultSet.getString(colName) == null ? "" : oResultSet.getString(colName);
			    row.put(colName, colValue2);
			    break;

			default:
			    logDebug(colName + " : " + "Error");
			    break;
			}
		    }

		    vec.add(row);
		}
	    }

	} catch (Exception e) {

	    log(sSql, e);
	    throw e;

	} finally {

	    close(oConnection, oPstmt, oResultSet);
	}

	return vec;
    }

    @Override
    public String getPagingQuery(String query, int currentPage, int pageSize) {

	StringBuffer buffer = new StringBuffer();

	// String sPage = String.valueOf(currentPage + 1);
	// String sPageRowCnt = String.valueOf(pageSize);

	int startPos = (((currentPage + 1) - 1) * pageSize) + 1;
	String startPosStr = String.valueOf(startPos);

	int endPos = (currentPage + 1) * pageSize;
	String endPosStr = String.valueOf(endPos);

	// Paging Query Make
	buffer.append(" SELECT * FROM ( \n");
	buffer.append("  SELECT ROWNUM AS NUM, A.* FROM ( \n");
	buffer.append(query);
	buffer.append("  ) A  \n");
	// buffer.append(" ) A WHERE NUM BETWEEN (").append(sPage).append("-1) * ").append(sPageRowCnt).append("+1 AND (").append(sPage)
	// .append(") * ").append(sPageRowCnt).append(" \n");
	buffer.append(" ) A WHERE NUM BETWEEN ").append(startPosStr).append(" AND ").append(endPosStr).append(" \n");

	return buffer.toString();
    }

    @Override
    public String getPagingQuery(String query, int currentPage, int pageSize, List<Object> alBindSql) {

	StringBuffer buffer = new StringBuffer();

	int startPos = (((currentPage + 1) - 1) * pageSize) + 1;
	String startPosStr = String.valueOf(startPos);

	int endPos = (currentPage + 1) * pageSize;
	String endPosStr = String.valueOf(endPos);

	// Paging Query Make
	buffer.append(" SELECT * FROM ( \n");
	buffer.append("  SELECT ROWNUM AS NUM, A.* FROM ( \n");
	buffer.append(query);
	buffer.append("  ) A  \n");
	buffer.append(" ) A WHERE NUM BETWEEN ").append("?").append(" AND ").append("?").append(" \n");

	alBindSql.add(startPosStr);
	alBindSql.add(endPosStr);

	return buffer.toString();
    }

    private Connection getConnection() {

	return getSpringRdbConnection();
    }

    private Connection getConnection(String gubun) {

	return getSpringRdbConnection(gubun);
    }

    private Connection getSpringRdbConnection(String gubun) {

	Connection oConnection = null;
	try {
	    if ("qms".equals(gubun)) {
		oConnection = DBUtil.getConnection("qms");
	    } else {
		oConnection = DBUtil.getConnection("legacy");
	    }

	} catch (Exception e) {
	    log(e);
	}

	return oConnection;
    }

    private Connection getSpringRdbConnection() {

	Connection oConnection = null;
	try {
	    oConnection = DBUtil.getConnection("legacy");

	} catch (Exception e) {
	    log(e);
	}

	return oConnection;
    }

    // private Connection getOrigianlConnection() {
    //
    // Connection oConnection = null;
    // try {
    //
    // Class.forName("oracle.jdbc.driver.OracleDriver");
    // java.util.Properties props = new java.util.Properties();
    // props.put("user", "rehearsal_1st");
    // props.put("password", "rehearsal_1st");
    // oConnection = DriverManager.getConnection("jdbc:log4jdbc:oracle:thin:@192.168.1.116:1521:wind", props);
    //
    // } catch (Exception e) {
    // log(e);
    // }
    //
    // return oConnection;
    // }

    // public Connection getWtConnection() {
    //
    // MethodContext oMethodContext = null;
    // WTConnection wtConnection = null;
    // Connection oConnection = null;
    // try {
    //
    // oMethodContext = MethodContext.getContext();
    // wtConnection = (WTConnection) oMethodContext.getConnection();
    // oConnection = wtConnection.getConnection();
    //
    // } catch (Exception e) {
    // log(e);
    // }
    //
    // return oConnection;
    // }

    private void log(Exception e) {
	// KetLogger.ket.error(e);
	e.printStackTrace();
    }

    private void log(String sql, Exception e) {
	// KetLogger.ket.error("[WT]ERROR SQL : " + sql, e);
	// Kogger.debug(getClass(), "[DAO]ERROR SQL : " + sql);
	e.printStackTrace();
    }

    private void log(String sql) {
	// if (KetLogger.ket.logger.isDebugEnabled())
	// KetLogger.ket.debug("[WT]EXCUTE SQL : " + sql);
	// Kogger.debug(getClass(), "[DAO]EXCUTE SQL : " + sql);
	System.out.println("[DAO]EXCUTE SQL : " + sql);
    }

    private void logBind(Object oValue) {
	// if (KetLogger.ket.logger.isDebugEnabled())
	// KetLogger.ket.debug("[Bind] Value : " + oValue);
	// Kogger.debug(getClass(), "[Bind] Value : " + oValue);
    }

    private void logDebug(String logStr) {
	// if (KetLogger.ket.logger.isDebugEnabled())
	// KetLogger.ket.debug(logStr);
	// Kogger.debug(getClass(), logStr);
    }

    private void close(Connection oConnection, PreparedStatement oPstmt, ResultSet oResultSet) {

	if (oResultSet != null) {
	    try {
		oResultSet.close();
	    } catch (Exception e) {
		log(e);
	    }
	}

	if (oPstmt != null) {
	    try {
		oPstmt.close();
	    } catch (Exception e) {
		log(e);
	    }
	}

	DBUtil.close(oConnection);
    }

}
