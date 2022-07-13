package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.util.WTProperties;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;

public class PartDao {

    // private static final long serialVersionUID = 1476082172878154226L;
    public static final PartDao manager   = new PartDao();

    static String               dataStore = "Oracle";     // "SQLServer" ....
    private Connection          conn;

    static {
	try {
	    dataStore = WTProperties.getLocalProperties().getProperty("wt.db.dataStore");
	} catch (Exception ex) {
	    dataStore = "Oracle";
	}
    }

    public PartDao() {
    }

    public PartDao(Connection conn) {
	this.conn = conn;
    }

    /**
     * 현재 sequence 중 가장 큰 sequence 의 다음값을 반환한다.
     * 
     * @param seqName
     *            - sequence name
     * @param format
     *            - sequence format
     * @param tabName
     *            - DB table name
     * @param colName
     *            - DB column name
     * @return
     */
    public String getPartSeqNo(final String seqName, final String format, final String tabName, final String colName) throws Exception {
	MethodContext methodcontext = null;
	WTConnection wtconnection = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	try {
	    methodcontext = MethodContext.getContext();
	    wtconnection = (WTConnection) methodcontext.getConnection();
	    Connection con = wtconnection.getConnection();

	    StringBuffer sb = new StringBuffer();
	    sb.append(" SELECT TO_CHAR(TO_NUMBER( NVL(MAX(SUBSTR(").append(colName).append(", 7, 3)), ?)) + 1, ?) ");
	    sb.append("   FROM ").append(tabName);
	    sb.append("  WHERE ").append(colName).append(" LIKE ? ");

	    st = con.prepareStatement(sb.toString());

	    st.setString(1, format);
	    st.setString(2, format);
	    st.setString(3, seqName + "%");

	    rs = st.executeQuery();

	    String seqNum = null;
	    while (rs.next()) {
		seqNum = rs.getString(1);
	    }

	    if (seqNum == null) {
		DecimalFormat decimalformat = new DecimalFormat(format);
		seqNum = decimalformat.format(Long.parseLong(format) + 1);
	    }

	    return seqNum.trim();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (st != null) {
		st.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !wtconnection.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Grid 및 MultiCombo 적용 수정일자 : 2013. 6. 5 수정자 : 오명재
     */
    // 부품검색
    public ArrayList<Hashtable<String, String>> searchPartList(Map _searchCon) throws Exception {
	KETParamMapUtil searchCon = KETParamMapUtil.getMap(_searchCon);
	// Kogger.debug(getClass(), "PartDao.searchPartList: searchCon=" + searchCon);

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> part = null;
	int pstmtCnt = 1;

	StringBuffer sql = new StringBuffer();
	/*
	 * Start [PLM System 1차개선] 수정내용 : 인코딩 제거, 2013. 6. 5, 오명재
	 */
	// if( searchCon.get("isExcel") != null && searchCon.get("isExcel").length() > 0 && searchCon.get("isExcel").equals("N"))
	// {
	// sql.append( "SELECT t.*                \n" );
	// sql.append( "     FROM (            \n" );
	// }
	/*
	 * End [PLM System 1차개선] 수정내용 : 인코딩 제거, 2013. 6. 5, 오명재
	 */
	sql.append(" SELECT  ROWNUM row_id        \n");
	sql.append("   , m.wtpartnumber        \n");
	sql.append("   , m.name            \n");
	sql.append("   , p.versionida2versioninfo as version        \n");
	sql.append("   , ph.name as stateKr        \n");
	sql.append("   , p.statestate as state                            \n");
	sql.append("   , decode(p." + PartSpecEnum.SpPartType.getColumnName()
	        + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품', '제품') as type            \n");
	sql.append("   , decode(p." + PartSpecEnum.SpIsDeleted.getColumnName() + ", 'Y', '삭제됨', 'N', '  ') as isDeleted                        \n");
	sql.append("   , m.defaultunit as unit            \n");
	sql.append("   , p.versionsortida2versioninfo as versionSort                    \n");
	sql.append("   , p.iterationida2iterationinfo as iteration                        \n");
	sql.append("   , p.latestiterationinfo as latestIteration                \n");
	sql.append("   , p.statecheckoutinfo as checkout                        \n");
	sql.append("   , p.ida2a2 as oid            \n");
	sql.append("   , p.ida3masterreference as oidMaster                    \n");
	sql.append(" FROM        WTPart p        \n");
	sql.append("   ,  WTPartMaster m        \n");
	sql.append("   ,( SELECT     MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid            \n");
	sql.append("      FROM   WTPart p2, WTPartMaster m2            \n");

	String partNoTableKey = searchCon.getString("PartNoTableKey");
	if (StringUtil.checkString(partNoTableKey)) {
	    sql.append("        , tmp_input_partno_table ipt        \n");
	}

	String number = searchCon.getString("number");
	if (StringUtil.checkString(number)) {
	    sql.append(" and ").append(KETQueryUtil.getSqlQueryForMultiSearch("m2.wtpartnumber", number, false)).append("        \n");
	}

	String name = searchCon.getString("name");
	if (StringUtil.checkString(name)) {
	    sql.append(" and ").append(KETQueryUtil.getSqlQueryForMultiSearch("m2.name", name, false)).append("        \n");
	}

	String partType = searchCon.getString("partType");
	if (partType != null && partType.length() > 0 && !partType.equals("A")) {
	    if ("P".equals(partType)) {
		sql.append("         AND     p2." + PartSpecEnum.SpPartType.getColumnName() + " not in ('D', 'M', 'O')        \n");
	    }
	    else {
		sql.append("         AND     p2." + PartSpecEnum.SpPartType.getColumnName() + " = ?        \n");
	    }
	}
	// 기타도 검색되도록 수정함.
//	else {
//	    sql.append("         AND     p2." + PartSpecEnum.SpPartType.getColumnName() + " != 'O'        \n");
//	}

	String partYazaki = searchCon.getString("partYazaki");
	if (partYazaki != null && partYazaki.length() > 0) {
	    sql.append("    AND     p2." + PartSpecEnum.SpIsYazaki.getColumnName() + " = 'YES'                            \n");
	}
	else {

	}
	sql.append("    AND     p2.ida3masterreference = m2.ida2a2            \n");
	sql.append("    AND     p2.statecheckoutinfo != 'wrk'                \n");
	sql.append("    AND     p2.latestiterationinfo = 1                    \n");

	if (StringUtil.checkString(partNoTableKey)) {
	    sql.append("        and ipt.key = '").append(partNoTableKey).append("' and ipt.partNo = m2.wtpartnumber        \n");
	}

	sql.append("    GROUP BY m2.wtpartnumber ) x                        \n");
	sql.append(",  phasetemplate ph                            \n");
	sql.append(",  phaselink pl                \n");
	sql.append("WHERE     p.ida3masterreference = m.ida2a2        \n");
	sql.append("  AND     p.ida2a2 = x.objid            \n");
	sql.append("  AND     pl.ida3a5 = p.ida3a2state        \n");
	sql.append("  AND     pl.ida3b5 = ph.ida2a2            \n");
	sql.append("  AND     ph.phasestate = p.statestate    \n");
	/*
	 * Start [PLM System 1차개선] 수정내용 : 인코딩 제거, 2013. 6. 5, 오명재
	 */
	// sql.append("ORDER BY m.wtpartnumber            \n" );
	// sql.append("ORDER BY "+searchCon.get("sort") +"                \n" );

	// if( searchCon.get("isExcel") != null && searchCon.get("isExcel").length() > 0 && searchCon.get("isExcel").equals("N"))
	// {
	//
	// sql.append( "                               )t            \n" );
	// sql.append( " WHERE row_id BETWEEN ? AND ?                \n" );
	// }
	/*
	 * End [PLM System 1차개선] 수정내용 : 인코딩 제거, 2013. 6. 5, 오명재
	 */

	Kogger.debug(getClass(), "PartDao.searchPartList: sql=\n" + sql.toString());

	try {
	    pstmt = conn.prepareStatement(sql.toString());

	    if (partType != null && partType.length() > 0 && !partType.equals("A")) {
		if ("P".equals(partType)) {

		}
		else {
		    pstmt.setString(pstmtCnt++, partType);
		}
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		part = new Hashtable<String, String>();

		part.put("number", StringUtil.checkNull(rs.getString("wtpartnumber")));
		part.put("name", StringUtil.checkNull(rs.getString("name")));
		part.put("type", StringUtil.checkNull(rs.getString("type")));
		part.put("isDeleted", StringUtil.checkNull(rs.getString("isDeleted")));
		part.put("state", rs.getString("state"));
		part.put("stateKr", rs.getString("stateKr"));
		part.put("unit", StringUtil.checkNull(rs.getString("unit")));
		part.put("versionSort", StringUtil.checkNull(rs.getString("versionSort")));
		part.put("version", StringUtil.checkNull(rs.getString("version")));
		part.put("iteration", rs.getString("iteration"));
		part.put("latestIteration", rs.getString("latestIteration"));
		part.put("checkout", rs.getString("checkout"));
		part.put("oid", StringUtil.checkNull(rs.getString("oid")));
		part.put("oidMaster", StringUtil.checkNull(rs.getString("oidMaster")));

		partList.add(part);
	    }
	} catch (Exception e) {

	    throw e;
	} finally {

	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	}

	return partList;
    }

    /*
     * 결과내 재검색용(Search In Result)
     */
    public ArrayList<Hashtable<String, String>> searchPartListSIR(List<Map> mapList) throws Exception {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> part = null;

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT ROWNUM row_id                                                                \n");
	sql.append("      , m.wtpartnumber                                                               \n");
	sql.append("      , m.name                                                                       \n");
	sql.append("      , p.versionida2versioninfo as version                                          \n");
	sql.append("      , ph.name as stateKr                                                           \n");
	sql.append("      , p.statestate as state                                                        \n");
	sql.append("      , decode(p." + PartSpecEnum.SpPartType.getColumnName()
	        + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품') as type         \n");
	sql.append("      , p." + PartSpecEnum.SpPartType.getColumnName() + " as typeCode \n");
	sql.append("      , decode(p." + PartSpecEnum.SpIsDeleted.getColumnName() + ", 'Y', '삭제됨', 'N', '  ') as isDeleted                      \n");
	sql.append("      , m.defaultunit as unit                                                        \n");
	sql.append("      , p.versionsortida2versioninfo as versionSort                                  \n");
	sql.append("      , p.iterationida2iterationinfo as iteration                                    \n");
	sql.append("      , p.latestiterationinfo as latestIteration                                     \n");
	sql.append("      , p.statecheckoutinfo as checkout                                              \n");
	sql.append("      , p.ida2a2 as oid                                                              \n");
	sql.append("      , p.ida3masterreference as oidMaster                                           \n");
	sql.append("  FROM  WTPart p                                                                     \n");
	sql.append("      , WTPartMaster m                                                               \n");
	sql.append("      , ( SELECT MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid     \n");
	sql.append("            FROM WTPart p2, WTPartMaster m2                          \n");
	sql.append("            WHERE 1=1                          \n");

	// 결과내 재검색 loop
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    // Part No
	    String number = paramMap.getString("number");
	    if (StringUtil.checkString(number)) {
		sql.append("             AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m2.wtpartnumber", number, false)).append("        \n");
	    }
	    // Part Name
	    String name = paramMap.getString("name");
	    if (StringUtil.checkString(name)) {
		sql.append("             AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("m2.name", name, false)).append("        \n");
	    }
	    // Part Type
	    String partType = paramMap.getString("partType");
	    if (partType != null && partType.length() > 0 && !partType.equals("A")) {
		if ("P".equals(partType)) {
		    sql.append("             AND p2." + PartSpecEnum.SpPartType.getColumnName() + " not in ('D', 'M', 'O')        \n");

		}
		else {
		    sql.append("             AND p2." + PartSpecEnum.SpPartType.getColumnName() + " = '").append(partType).append("'        \n");
		}
	    }
	    // 기타도 검색되도록 수정함.
//	    else {
//		sql.append("         AND     p2." + PartSpecEnum.SpPartType.getColumnName() + " != 'O'        \n");
//	    }
	}

	// 결과내 재검색 loop
	boolean isYazaki = false;
	for (int i = 0; i < mapList.size(); ++i) {
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(mapList.get(i));
	    String partYazaki = paramMap.getString("partYazaki");
	    if (partYazaki != null && partYazaki.length() > 0) {
		isYazaki = true;
		break;
	    }
	}
	if (isYazaki) {
	    sql.append("             AND p2." + PartSpecEnum.SpIsYazaki.getColumnName() + " = 'YES'                                                                            \n");
	}
	else {
	}

	sql.append("             AND p2.ida3masterreference = m2.ida2a2            \n");
	sql.append("             AND p2.statecheckoutinfo != 'wrk'                 \n");
	sql.append("             AND p2.latestiterationinfo = 1                    \n");

	sql.append("           GROUP BY m2.wtpartnumber ) x            \n");
	sql.append("      , phasetemplate ph                         \n");
	sql.append("      , phaselink pl                             \n");
	sql.append("  WHERE p.ida3masterreference = m.ida2a2  \n");
	sql.append("    AND p.ida2a2 = x.objid                \n");
	sql.append("    AND pl.ida3a5 = p.ida3a2state         \n");
	sql.append("    AND pl.ida3b5 = ph.ida2a2             \n");
	sql.append("    AND ph.phasestate = p.statestate      \n");

	Kogger.debug(getClass(), "PartDao.searchPartListSIR: sql=\n" + sql.toString());

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		part = new Hashtable<String, String>();
		part.put("number", StringUtil.checkNull(rs.getString("wtpartnumber")));
		part.put("name", StringUtil.checkNull(rs.getString("name")));
		part.put("type", StringUtil.checkNull(rs.getString("type")));
		part.put("typeCode", StringUtil.checkNull(rs.getString("typeCode")));
		part.put("isDeleted", StringUtil.checkNull(rs.getString("isDeleted")));
		part.put("state", rs.getString("state"));
		part.put("stateKr", rs.getString("stateKr"));
		part.put("unit", StringUtil.checkNull(rs.getString("unit")));
		part.put("versionSort", StringUtil.checkNull(rs.getString("versionSort")));
		part.put("version", StringUtil.checkNull(rs.getString("version")));
		part.put("iteration", rs.getString("iteration"));
		part.put("latestIteration", rs.getString("latestIteration"));
		part.put("checkout", rs.getString("checkout"));
		part.put("oid", StringUtil.checkNull(rs.getString("oid")));
		part.put("oidMaster", StringUtil.checkNull(rs.getString("oidMaster")));
		partList.add(part);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	}
	return partList;
    }

    public String getPartNoTableKey(String userId, String[] inputPartNoAry) {
	PreparedStatement pstmt = null;
	boolean autoCommit = true;
	try {
	    String key = (userId + "-" + System.currentTimeMillis()); // 키 생성

	    String qry = "insert into tmp_input_partno_table (key, partNo, insertTime) values (?, ?, sysdate) ";

	    autoCommit = conn.getAutoCommit();
	    if (autoCommit == true) {
		conn.setAutoCommit(false);
	    }

	    pstmt = conn.prepareStatement(qry);

	    boolean added = false;
	    int insCnt = 0;
	    for (int i = 0; i < inputPartNoAry.length; ++i) {
		String inputPartNo = inputPartNoAry[i].trim();
		if (StringUtil.checkString(inputPartNo)) {
		    pstmt.setString(1, key);
		    pstmt.setString(2, KETQueryUtil.makeSqlLikeCondition(inputPartNo)); // '*' 검색조건 지원 (* -> %)
		    pstmt.addBatch();
		    added = true;
		    ++insCnt;

		    if (insCnt % 3000 == 0) {
			pstmt.executeBatch();
			added = false;
		    }
		}
	    }
	    if (added) {
		pstmt.executeBatch();
	    }
	    Kogger.debug(getClass(), "PartDao.getPartNoTableKey: key=[" + key + "], insCnt=" + insCnt);

	    conn.commit();

	    return key;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    try {
		conn.rollback();
	    } catch (Exception e2) {
	    }
	} finally {
	    PlmDBUtil.close(pstmt);
	    try {
		if (autoCommit == true) conn.setAutoCommit(true);
	    } catch (Exception e) {
	    }
	}
	return null;
    }

    public void clearPartNoTable(String key) {
	Statement stmt = null;
	try {
	    stmt = conn.createStatement();
	    stmt.executeUpdate("delete from tmp_input_partno_table where key = '" + key + "'");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(stmt);
	}
    }

    // 부품검색 결과 Count
    public int searchPartListCnt(Hashtable<String, String> searchCon) throws Exception {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int listCnt = 0;
	int pstmtCnt = 1;

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT  count(*)                                                                                                                                        \n");
	sql.append("FROM        WTPart p                                                                                                                                    \n");
	sql.append("          ,  WTPartMaster m                                                                                                                         \n");
	sql.append("          , ( SELECT     MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid                  \n");
	sql.append("               FROM  WTPart p2, WTPartMaster m2                                   \n");
	sql.append("              WHERE  1=1                                                                                        \n");

	if (searchCon.get("number") != null && searchCon.get("number").length() > 0) {
	    sql.append("             AND     m2.wtpartnumber LIKE ?                                                                                                    \n");
	}

	if (searchCon.get("name") != null && searchCon.get("name").length() > 0) {
	    sql.append("             AND     m2.name LIKE ?                                                                                                                \n");
	}

	if (searchCon.get("partType") != null && searchCon.get("partType").length() > 0 && !searchCon.get("partType").equals("A")) {
	    if ("P".equals(searchCon.get("partType"))) {
		sql.append("         AND     p2." + PartSpecEnum.SpPartType.getColumnName() + " not in ('D', 'M', 'O')        \n");
	    }
	    else sql.append("             AND     p2." + PartSpecEnum.SpPartType.getColumnName()
		    + " = ?                                                                                                                    \n");
	}
	// 기타도 검색되도록 수정함.
//	else {
//	    sql.append("         AND     p2." + PartSpecEnum.SpPartType.getColumnName() + " != 'O'        \n");
//	}
	sql.append("                  AND     p2.ida3masterreference = m2.ida2a2                                                                                        \n");
	sql.append("                  AND     p2.statecheckoutinfo != 'wrk'                                                                                                \n");
	sql.append("                  AND     p2.latestiterationinfo = 1                                                                                                    \n");
	sql.append("                GROUP BY m2.wtpartnumber ) x                                                                                                    \n");
	sql.append("         ,  phasetemplate ph                                                                                                                         \n");
	sql.append("         ,  phaselink pl                                                                                                                                 \n");
	sql.append("WHERE     p.ida3masterreference = m.ida2a2                                                                                                    \n");
	sql.append("  AND     p.ida2a2 = x.objid                                                                                                                            \n");
	sql.append("  AND     pl.ida3a5 = p.ida3a2state                                                                                                                    \n");
	sql.append("  AND     pl.ida3b5 = ph.ida2a2                                                                                                                        \n");
	sql.append("  AND     ph.phasestate = p.statestate                                                                                                                \n");

	try {
	    pstmt = conn.prepareStatement(sql.toString());
	    // Kogger.debug(getClass(), ">>> SQL2 : " + sql);
	    Kogger.debug(getClass(), ">>> searchCon : " + searchCon);

	    if (searchCon.get("number").length() > 0) {
		pstmt.setString(pstmtCnt++, StringUtil.getLikeQueryString(searchCon.get("number").toUpperCase()));
	    }
	    if (searchCon.get("name").length() > 0) {
		pstmt.setString(pstmtCnt++, StringUtil.getLikeQueryString(searchCon.get("name").toUpperCase()));
	    }
	    if (searchCon.get("partType").length() > 0 && !searchCon.get("partType").equals("A")) {
		if ("P".equals(searchCon.get("partType"))) {

		}
		else pstmt.setString(pstmtCnt++, searchCon.get("partType"));
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		listCnt = rs.getInt(1);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	}
	return listCnt;
    }

    // 부품검색
    public ArrayList<Hashtable<String, String>> searchPartApplet(Hashtable<String, String> searchCon) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();
	// Hashtable<String, String> part = null;
	int pstmtCnt = 1;
	Kogger.debug(getClass(), "@@@@@ searchCon : " + searchCon);

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT  ROWNUM                                                       row_id                                                                \n");
	sql.append("          , m.wtpartnumber                                                                                                                      \n");
	sql.append("          , m.name                                                                                                                                \n");
	sql.append("          , p.versionida2versioninfo                                      version                                                                \n");
	sql.append("          , ph.name                                                         stateKr                                                                \n");
	sql.append("          , p.statestate                                                      state                                                                    \n");
	sql.append("          , decode(p." + PartSpecEnum.SpPartType.getColumnName()
	        + ", 'D', 'Die No', 'M', '금형부품', 'F', '제품', 'H', '반제품', 'R', '원자재', 'K', '포장재', 'S', '스크랩', 'W', '상품','제품')   type                          \n");
	sql.append("          , p." + PartSpecEnum.SpPartType.getColumnName() + "                                                            typeValue                 \n");
	sql.append("          , decode(p." + PartSpecEnum.SpIsDeleted.getColumnName() + ", 'Y', '삭제됨', 'N', '  ')                        isDeleted                        \n");
	sql.append("          , m.defaultunit                                                    unit                                                                    \n");
	sql.append("          , p.versionsortida2versioninfo                                  versionSort                                                            \n");
	sql.append("          , p.iterationida2iterationinfo                                    iteration                                                                \n");
	sql.append("          , p.latestiterationinfo                                             latestIteration                                                        \n");
	sql.append("          , p.statecheckoutinfo                                              checkout                                                            \n");
	sql.append("          , p.ida2a2                                                          oid                                                                    \n");
	sql.append("          , p.ida3masterreference                                         oidMaster                                                            \n");
	sql.append("FROM        WTPart p                                                                                                                                \n");
	sql.append("            ,  WTPartMaster m                                                                                                                     \n");
	sql.append("            , ( SELECT     MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid                                                \n");
	sql.append("                FROM   WTPart p2, WTPartMaster m2                                                                     \n");
	sql.append("                WHERE  1=1                                                                                \n");

	if (searchCon.get("itemCode") != null && searchCon.get("itemCode").length() > 0) {
	    sql.append("            AND     m2.wtpartnumber LIKE ?                                                                                                \n");
	}

	if (searchCon.get("description") != null && searchCon.get("description").length() > 0) {
	    sql.append("            AND     m2.name LIKE ?                                                                                                            \n");
	}

	sql.append("                 AND     p2.ida3masterreference = m2.ida2a2                                                                                    \n");
	sql.append("                 AND     p2.statecheckoutinfo != 'wrk'                                                                                            \n");
	sql.append("                 AND     p2.latestiterationinfo = 1                                                                                                \n");
	sql.append("                 GROUP BY m2.wtpartnumber ) x                                                                                                \n");
	sql.append("             ,  phasetemplate ph                                                                                                                 \n");
	sql.append("             ,  phaselink pl                                                                                                                         \n");
	sql.append("WHERE     p.ida3masterreference = m.ida2a2                                                                                                \n");
	sql.append("   AND     p.ida2a2 = x.objid                                                                                                                    \n");
	sql.append("   AND     pl.ida3a5 = p.ida3a2state                                                                                                            \n");
	sql.append("   AND     pl.ida3b5 = ph.ida2a2                                                                                                                \n");
	// 기타도 검색되도록 수정함.
//	sql.append("   AND     p." + PartSpecEnum.SpPartType.getColumnName() + " != 'O'                                                                           \n");
	sql.append("   AND     ph.phasestate = p.statestate                                                                                                        \n");
	sql.append(" ORDER BY  m.wtpartnumber                                                                                                                    \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());

	    Kogger.debug(getClass(), ">>>>> sql [searchPartApplet] : " + sql);
	    Kogger.debug(getClass(), ">>>>> searchCon : " + searchCon);

	    if (searchCon.get("itemCode") != null && searchCon.get("itemCode").length() > 0) {
		pstmt.setString(pstmtCnt++, StringUtil.getLikeQueryString(searchCon.get("itemCode").toUpperCase()));
	    }
	    if (searchCon.get("description") != null && searchCon.get("description").length() > 0) {
		pstmt.setString(pstmtCnt++, StringUtil.getLikeQueryString(searchCon.get("description").toUpperCase()));
	    }

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		Hashtable<String, String> part = new Hashtable<String, String>();

		part.put("number", StringUtil.checkNull(rs.getString("wtpartnumber")));
		part.put("name", StringUtil.checkNull(rs.getString("name")));
		part.put("type", StringUtil.checkNull(rs.getString("type")));
		part.put("typeValue", StringUtil.checkNull(rs.getString("typeValue"))); // type 실제 저장되는 값 :: P, D. M
		part.put("isDeleted", StringUtil.checkNull(rs.getString("isDeleted")));
		part.put("state", rs.getString("state"));
		part.put("stateKr", rs.getString("stateKr"));
		part.put("unit", StringUtil.checkNull(rs.getString("unit")));
		part.put("versionSort", StringUtil.checkNull(rs.getString("versionSort")));
		part.put("version", StringUtil.checkNull(rs.getString("version")));
		part.put("iteration", rs.getString("iteration"));
		part.put("latestIteration", rs.getString("latestIteration"));
		part.put("checkout", rs.getString("checkout"));
		part.put("oid", StringUtil.checkNull(rs.getString("oid")));
		part.put("oidMaster", StringUtil.checkNull(rs.getString("oidMaster")));

		partList.add(part);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return partList;
    }

    // 해당 부품 관련 설계변경 조회
    public static ArrayList<Hashtable<String, String>> searchRelatedEcoEco(String partNo) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> ecoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> eco = new Hashtable<String, String>();

	StringBuffer sql = new StringBuffer();
	sql.append("  SELECT  distinct ecoHeaderNo, ecoOid                                        \n");
	sql.append("  FROM  (                                                                            \n");
	sql.append("            SELECT   o.ecoid ecoHeaderNo                                        \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2 ecoOid                \n");
	sql.append("             FROM   ketprodecopartlink l                                            \n");
	sql.append("                      , wtpart p                                                        \n");
	sql.append("                      , wtpartmaster m                                                \n");
	sql.append("                      , ketprodchangeorder o                                        \n");
	sql.append("            WHERE  l.ida3a5 = p.ida2a2                                            \n");
	sql.append("               AND  p.ida3masterreference = m.ida2a2                            \n");
	sql.append("               AND  l.ida3b5 = o.ida2a2                                             \n");
	sql.append("               AND  m.wtpartnumber = ?                                            \n");
	sql.append("           UNION ALL                                                                  \n");
	sql.append("            SELECT   o.ecoid    ecoHeaderNo                                    \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2 ecoOid                \n");
	sql.append("             FROM   ketmoldecopartlink l                                            \n");
	sql.append("                      , wtpart p                                                        \n");
	sql.append("                      , wtpartmaster m                                                \n");
	sql.append("                      , ketmoldchangeorder o                                        \n");
	sql.append("            WHERE l.ida3a5 = p.ida2a2                                            \n");
	sql.append("               AND p.ida3masterreference = m.ida2a2                            \n");
	sql.append("               AND l.ida3b5 = o.ida2a2                                             \n");
	sql.append("               AND m.wtpartnumber = ?                                            \n");
	sql.append("            UNION ALL                                                                 \n");
	sql.append("            SELECT   h.ecoheadernumber   ecoHeaderNo                        \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2     ecoOid                \n");
	sql.append("             FROM  ketprodecabomlink k                                            \n");
	sql.append("                     , ketprodchangeactivity a                                        \n");
	sql.append("                     , ketprodchangeorder o                                        \n");
	sql.append("                     , ketbomecoheader h                                            \n");
	sql.append("            WHERE  k.ida3a5 = h.ida2a2                                            \n");
	sql.append("               AND  k.ida3b5 = a.ida2a2                                            \n");
	sql.append("               AND  a.ida3a8 = o.ida2a2                                            \n");
	sql.append("               AND  h.ecoitemcode = ?                                            \n");
	sql.append("            UNION ALL                                                                 \n");
	sql.append("            SELECT  h.ecoheadernumber   ecoHeaderNo                        \n");
	sql.append("                     , o.classnamea2a2 || ':' ||  o.ida2a2     ecoOid                \n");
	sql.append("             FROM  ketmoldecabomlink k                                            \n");
	sql.append("                     , ketmoldchangeactivity a                                        \n");
	sql.append("                     , ketmoldchangeorder o                                        \n");
	sql.append("                     , ketbomecoheader h                                            \n");
	sql.append("           WHERE  k.ida3a5 = h.ida2a2                                            \n");
	sql.append("              AND  k.ida3b5 = a.ida2a2                                            \n");
	sql.append("              AND  a.ida3a8 = o.ida2a2                                            \n");
	sql.append("              AND  h.ecoitemcode = ?                                                \n");
	sql.append("            UNION ALL                                                                 \n");
	sql.append("            SELECT   o.ecrid ecoHeaderNo                                        \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2 ecoOid                \n");
	sql.append("             FROM   ketprodecrpartlink l                                            \n");
	sql.append("                      , wtpart p                                                        \n");
	sql.append("                      , wtpartmaster m                                                \n");
	sql.append("                      , ketprodchangerequest o                                        \n");
	sql.append("            WHERE  l.ida3a5 = p.ida2a2                                            \n");
	sql.append("               AND  p.ida3masterreference = m.ida2a2                            \n");
	sql.append("               AND  l.ida3b5 = o.ida2a2                                             \n");
	sql.append("               AND  m.wtpartnumber = ?                                            \n");
	sql.append("           UNION ALL                                                                  \n");
	sql.append("            SELECT   o.ecrid    ecoHeaderNo                                    \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2 ecoOid                \n");
	sql.append("             FROM   ketmoldecrpartlink l                                            \n");
	sql.append("                      , wtpart p                                                        \n");
	sql.append("                      , wtpartmaster m                                                \n");
	sql.append("                      , ketmoldchangerequest o                                        \n");
	sql.append("            WHERE l.ida3a5 = p.ida2a2                                            \n");
	sql.append("               AND p.ida3masterreference = m.ida2a2                            \n");
	sql.append("               AND l.ida3b5 = o.ida2a2                                             \n");
	sql.append("               AND m.wtpartnumber = ?                                            \n");
	sql.append("           )                                                                                \n");
	sql.append("           ORDER BY ecoHeaderNo DESC                                        \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());
	    Kogger.debug(PartDao.class, ">>>>> sql : " + sql);

	    pstmt.setString(1, partNo);
	    pstmt.setString(2, partNo);
	    pstmt.setString(3, partNo);
	    pstmt.setString(4, partNo);
	    pstmt.setString(5, partNo);
	    pstmt.setString(6, partNo);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		eco = new Hashtable<String, String>();

		eco.put("ecoHeaderNo", StringUtil.checkNull(rs.getString("ecoHeaderNo")));
		eco.put("ecoOid", StringUtil.checkNull(rs.getString("ecoOid")));

		ecoList.add(eco);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return ecoList;
    }

    public static ArrayList<Hashtable<String, String>> searchRelatedEcoEco(String partNo, String partRev) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> ecoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> eco = new Hashtable<String, String>();

	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT ecoHeaderNo \n");
	sql.append("      , ecoOid \n");
	sql.append("   FROM ( \n");
	sql.append("         SELECT eco.ecoid AS ecoHeaderNo \n");
	sql.append("              , eco.classnamea2a2 || ':' || eco.ida2a2 AS ecoOid \n");
	sql.append("              , eco.CREATESTAMPA2 \n");
	sql.append("           FROM KETProdChangeOrder eco \n");
	sql.append("              , KETProdChangeActivity eca \n");

	//sql.append("              , KETProdECABomLink bomlink \n");
	sql.append("              , (\n");
	sql.append("                 SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	sql.append("                   FROM KETProdECABomLink\n");
	sql.append("                ) bomlink \n");

	sql.append("              , ( \n");
	sql.append("                 SELECT a.ida2a2, a.ecoitemcode \n");
	sql.append("                   FROM KETBomEcoHeader a \n");
	sql.append("                UNION ALL \n");
	sql.append("                 SELECT b.ida2a2, b.ecoitemcode \n");
	sql.append("                   FROM KETBomHeader b \n");
	sql.append("                )bomheader \n");
	sql.append("          WHERE eco.ida2a2 = eca.ida3a8 \n");
	sql.append("            AND eca.ida2a2 = bomlink.ida3b5 \n");
	sql.append("            AND bomlink.ida3a5 = bomheader.ida2a2 \n");
	sql.append("            AND bomheader.ecoitemcode = ? \n");
	sql.append("            AND bomlink.afterversion = ? \n");
	sql.append("         UNION ALL \n");
	sql.append("         SELECT eco.ecoid AS ecoHeaderNo \n");
	sql.append("              , eco.classnamea2a2 || ':' || eco.ida2a2 AS ecoOid \n");
	sql.append("              , eco.CREATESTAMPA2 \n");
	sql.append("           FROM KETMoldChangeOrder eco \n");
	sql.append("              , KETMoldChangeActivity eca \n");

	//sql.append("              , KETMoldECABomLink bomlink \n");
	sql.append("              , (\n");
	sql.append("                 SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	sql.append("                   FROM KETMoldECABomLink\n");
	sql.append("                ) bomlink \n");

	sql.append("              , ( \n");
	sql.append("                 SELECT a.ida2a2, a.ecoitemcode \n");
	sql.append("                   FROM KETBomEcoHeader a \n");
	sql.append("                UNION ALL \n");
	sql.append("                 SELECT b.ida2a2, b.ecoitemcode \n");
	sql.append("                   FROM KETBomHeader b \n");
	sql.append("                )bomheader \n");
	sql.append("          WHERE eco.ida2a2 = eca.ida3a8 \n");
	sql.append("            AND eca.ida2a2 = bomlink.ida3b5 \n");
	sql.append("            AND bomlink.ida3a5 = bomheader.ida2a2 \n");
	sql.append("            AND bomheader.ecoitemcode = ? \n");
	sql.append("            AND bomlink.afterversion = ? \n");
	sql.append("         UNION ALL \n");

	sql.append("         SELECT ecr.ecrid AS ecoHeaderNo \n");
	sql.append("              , ecr.classnamea2a2 || ':' || ecr.ida2a2 AS ecoOid \n");
	sql.append("              , ecr.CREATESTAMPA2 \n");
	sql.append("           FROM KETProdChangeRequest ecr \n");
	sql.append("              , KETProdECRPartLink partlink \n");
	sql.append("          WHERE ecr.ida2a2 = partlink.ida3b5 \n");
	sql.append("            AND partlink.ida3a5 IN ( \n");
	sql.append("                                    SELECT part.ida2a2 \n");
	sql.append("                                   FROM WTPart part \n");
	sql.append("                                      , WTPartMaster partmaster \n");
	sql.append("                                  WHERE part.ida3masterreference = partmaster.ida2a2 \n");
	sql.append("                                    AND partmaster.wtpartnumber = ? \n");
	sql.append("                                    AND PART.VERSIONIDA2VERSIONINFO = ? \n");
	sql.append("                                   ) \n");
	sql.append("         UNION ALL \n");
	sql.append("         SELECT ecr.ecrid AS ecoHeaderNo \n");
	sql.append("              , ecr.classnamea2a2 || ':' || ecr.ida2a2 AS ecoOid \n");
	sql.append("              , ecr.CREATESTAMPA2 \n");
	sql.append("           FROM KETMoldChangeRequest ecr \n");
	sql.append("              , KETMoldECRPartLink partlink \n");
	sql.append("          WHERE ecr.ida2a2 = partlink.ida3b5 \n");
	sql.append("            AND partlink.ida3a5 IN ( \n");
	sql.append("                                    SELECT part.ida2a2 \n");
	sql.append("                                   FROM WTPart part \n");
	sql.append("                                      , WTPartMaster partmaster \n");
	sql.append("                                  WHERE part.ida3masterreference = partmaster.ida2a2 \n");
	sql.append("                                    AND partmaster.wtpartnumber = ? \n");
	sql.append("                                    AND PART.VERSIONIDA2VERSIONINFO = ? \n");
	sql.append("                                   ) \n");
	sql.append("       ) \n");
	sql.append(" ORDER BY CREATESTAMPA2 DESC \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());
	    Kogger.debug(PartDao.class, ">>>>> sql : " + sql);

	    pstmt.setString(1, partNo);
	    pstmt.setString(2, partRev);
	    pstmt.setString(3, partNo);
	    pstmt.setString(4, partRev);
	    pstmt.setString(5, partNo);
	    pstmt.setString(6, partRev);
	    pstmt.setString(7, partNo);
	    pstmt.setString(8, partRev);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		eco = new Hashtable<String, String>();

		eco.put("ecoHeaderNo", StringUtil.checkNull(rs.getString("ecoHeaderNo")));
		eco.put("ecoOid", StringUtil.checkNull(rs.getString("ecoOid")));

		ecoList.add(eco);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return ecoList;
    }

    // 해당 부품 관련 설계변경 조회
    public static ArrayList<Hashtable<String, String>> searchRelatedEco(String partNo) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<Hashtable<String, String>> ecoList = new ArrayList<Hashtable<String, String>>();
	Hashtable<String, String> eco = new Hashtable<String, String>();

	StringBuffer sql = new StringBuffer();
	sql.append("  SELECT  distinct ecoHeaderNo, ecoOid                                        \n");
	sql.append("  FROM  (                                                                            \n");
	sql.append("            SELECT   o.ecoid ecoHeaderNo                                        \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2 ecoOid                \n");
	sql.append("             FROM   ketprodecopartlink l                                            \n");
	sql.append("                      , wtpart p                                                        \n");
	sql.append("                      , wtpartmaster m                                                \n");
	sql.append("                      , ketprodchangeorder o                                        \n");
	sql.append("            WHERE  l.ida3a5 = p.ida2a2                                            \n");
	sql.append("               AND  p.ida3masterreference = m.ida2a2                            \n");
	sql.append("               AND  l.ida3b5 = o.ida2a2                                             \n");
	sql.append("               AND  m.wtpartnumber = ?                                            \n");
	sql.append("           UNION ALL                                                                  \n");
	sql.append("            SELECT   o.ecoid    ecoHeaderNo                                    \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2 ecoOid                \n");
	sql.append("             FROM   ketmoldecopartlink l                                            \n");
	sql.append("                      , wtpart p                                                        \n");
	sql.append("                      , wtpartmaster m                                                \n");
	sql.append("                      , ketmoldchangeorder o                                        \n");
	sql.append("            WHERE l.ida3a5 = p.ida2a2                                            \n");
	sql.append("               AND p.ida3masterreference = m.ida2a2                            \n");
	sql.append("               AND l.ida3b5 = o.ida2a2                                             \n");
	sql.append("               AND m.wtpartnumber = ?                                            \n");
	sql.append("            UNION ALL                                                                 \n");
	sql.append("            SELECT   h.ecoheadernumber   ecoHeaderNo                        \n");
	sql.append("                      , o.classnamea2a2 || ':' ||  o.ida2a2     ecoOid                \n");
	sql.append("             FROM  ketprodecabomlink k                                            \n");
	sql.append("                     , ketprodchangeactivity a                                        \n");
	sql.append("                     , ketprodchangeorder o                                        \n");
	sql.append("                     , ketbomecoheader h                                            \n");
	sql.append("            WHERE  k.ida3a5 = h.ida2a2                                            \n");
	sql.append("               AND  k.ida3b5 = a.ida2a2                                            \n");
	sql.append("               AND  a.ida3a8 = o.ida2a2                                            \n");
	sql.append("               AND  h.ecoitemcode = ?                                            \n");
	sql.append("            UNION ALL                                                                 \n");
	sql.append("            SELECT  h.ecoheadernumber   ecoHeaderNo                        \n");
	sql.append("                     , o.classnamea2a2 || ':' ||  o.ida2a2     ecoOid                \n");
	sql.append("             FROM  ketmoldecabomlink k                                            \n");
	sql.append("                     , ketmoldchangeactivity a                                        \n");
	sql.append("                     , ketmoldchangeorder o                                        \n");
	sql.append("                     , ketbomecoheader h                                            \n");
	sql.append("           WHERE  k.ida3a5 = h.ida2a2                                            \n");
	sql.append("              AND  k.ida3b5 = a.ida2a2                                            \n");
	sql.append("              AND  a.ida3a8 = o.ida2a2                                            \n");
	sql.append("              AND  h.ecoitemcode = ?                                                \n");
	sql.append("           ORDER BY ecoHeaderNo DESC                                        \n");
	sql.append("           )                                                                                \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());
	    Kogger.debug(PartDao.class, ">>>>> sql : " + sql);

	    pstmt.setString(1, partNo);
	    pstmt.setString(2, partNo);
	    pstmt.setString(3, partNo);
	    pstmt.setString(4, partNo);

	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		eco = new Hashtable<String, String>();

		eco.put("ecoHeaderNo", StringUtil.checkNull(rs.getString("ecoHeaderNo")));
		eco.put("ecoOid", StringUtil.checkNull(rs.getString("ecoOid")));

		ecoList.add(eco);
	    }
	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return ecoList;
    }

    // 설계변경사유 가져오기
    public static String getChangeReasonName(String chgReasonCode, String codeType) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String chgReasonName = "";

	StringTokenizer st = new StringTokenizer(chgReasonCode, "|");
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT    n.name        name                            \n");
	sql.append(" FROM    numbercode n                                \n");
	sql.append("WHERE    n.codetype = ?                                 \n");
	sql.append("    AND   n.code = ?                                      \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());
	    Kogger.debug(PartDao.class, ">>>>> sql [getChangeReasonName] : " + sql);

	    int tokenCnt = 0;
	    while (st.hasMoreTokens()) {
		pstmt.setString(1, codeType);
		pstmt.setString(2, st.nextToken());

		tokenCnt++;

		rs = pstmt.executeQuery();

		if (rs.next()) {
		    chgReasonName += rs.getString("name");
		    if (!rs.getString("name").equals("기타")) {
			chgReasonName += "/";
		    }
		}
	    }

	    // 마지막 "/" 제거
	    if (chgReasonName.length() > 0 && chgReasonName.indexOf("/") > 0 && chgReasonName.lastIndexOf("/") == chgReasonName.length() - 1) {
		chgReasonName = chgReasonName.substring(0, chgReasonName.length() - 1);
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return chgReasonName;
    }

    // 설계변경 구분 가져오기
    public static String getChangeTypeName(String chgCode, String codeType) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String chgReasonName = "";

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT    n.name        name                            \n");
	sql.append(" FROM    numbercode n                                \n");
	sql.append("WHERE    n.codetype = ?                                 \n");
	sql.append("    AND   n.code = ?                                      \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());
	    Kogger.debug(PartDao.class, ">>>>> sql [getChangeTypeName] : " + sql);

	    pstmt.setString(1, codeType);
	    pstmt.setString(2, chgCode);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		chgReasonName = rs.getString("name");
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return chgReasonName;
    }

    // IBA 값 가져오기
    public static String getPartIbaValue(String type, String itemCode) throws Exception {

	Registry registry = null;
	DBConnectionManager res = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String ibaValue = "";

	String columnName = "";
	if ("IsYazaki".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpIsYazaki.getColumnName();
	}
	else if ("YazakiNo".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpYazakiNo.getColumnName();
	}
	else if ("IsDeleted".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpIsDeleted.getColumnName();
	}
	else if ("partType".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpPartType.getColumnName();
	}
	else if ("PartWeight ".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpNetWeight.getColumnName();
	}
	else if ("WeightUnit".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpWeightUnit.getColumnName();
	}
	else if ("BOMFlag".equalsIgnoreCase(type)) {
	    columnName = PartSpecEnum.SpBOMFlag.getColumnName();
	}
	else if ("PartGroup".equalsIgnoreCase(type)) {
	    // columnName = PartSpecEnum.SpPartGroup.getColumnName() ;
	    return "-";
	}

	StringBuffer sql = new StringBuffer();
	sql.append("SELECT  p." + columnName + " as value                                                                                                         \n");
	sql.append(" FROM  wtpart p, wtpartmaster m                                                                \n");
	sql.append("WHERE  p.ida3masterreference = m.ida2a2                                                                        \n");
	sql.append("  AND   m.wtpartnumber = '" + itemCode + "'                                                                     \n");

	try {
	    registry = Registry.getRegistry("e3ps.bom.bom");
	    res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));
	    pstmt = conn.prepareStatement(sql.toString());
	    Kogger.debug(PartDao.class, ">>>>> sql [getPartIbaValue] : " + sql);

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
		ibaValue = rs.getString("value");
	    }
	    else {
		ibaValue = "-";
	    }

	} catch (Exception e) {
	    throw e;
	} finally {
	    sql.delete(0, sql.length());
	    if (rs != null) rs.close();
	    if (pstmt != null) pstmt.close();
	    if (conn != null) conn.close();
	    if (res != null) res.freeConnection(registry.getString("plm"), conn);
	}
	return ibaValue;
    }
}
