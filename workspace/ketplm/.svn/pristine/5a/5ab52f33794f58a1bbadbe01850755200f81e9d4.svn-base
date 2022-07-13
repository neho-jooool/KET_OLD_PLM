package e3ps.common.code;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class NumberCodeDao {

    private Connection conn;

    public NumberCodeDao(Connection conn) {
	this.conn = conn;
    }

    /**
     * [PLM System 1차개선] 수정내용 : NumberCodeList 조회 Dao 수정일자 : 2013. 6. 18 수정자 : 김종호
     * 
     * @param Map
     *            <String, Object> parameter
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getNumberCodeList(Map<String, Object> parameter) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> numberCode = null;
	List<Map<String, Object>> numberCodeList = new ArrayList<Map<String, Object>>();

	try {

	    sb = new StringBuffer();

	    sb.append(" SELECT NCV.CODETYPE                                        \n");
	    sb.append("       ,NCV.CODE                                            \n");
	    sb.append("       ,NCV.VALUE                                           \n");
	    sb.append("       ,NCV.ABBR                                            \n");
	    sb.append("       ,NCV.DESCRIPTION                                     \n");
	    sb.append("       ,NC.SORTING                                          \n");
	    sb.append("       ,NCV.LANG                                            \n");
	    sb.append("       ,NC.VENDERCODE                                       \n");
	    sb.append("       ,NC.IDA2A2                                           \n");
	    sb.append("       ,NC.CLASSNAMEA2A2||':'||NC.IDA2A2 OID                \n");
	    sb.append("       ,NC.CLASSNAMEKEYA3||':'||NC.IDA3A3 OID2              \n");
	    sb.append("   FROM NUMBERCODE      NC                                  \n");
	    sb.append("       ,NUMBERCODEVALUE NCV                                 \n");
	    sb.append("  WHERE 1=1                                                 \n");
	    if (parameter.get("disabled") == null) {
		sb.append("    AND NC.DISABLED = 0                                 \n");
	    }
	    sb.append("    AND NC.CODETYPE = NCV.CODETYPE                          \n");
	    sb.append("    AND NC.CODE     = NCV.CODE                              \n");
	    sb.append("    AND NCV.LANG    = '" + parameter.get("locale") + "'     \n");

	    // Code가 공백이 아닌 경우는 2레벨의 부모키를 가진 3레벨 조회
	    if (parameter.get("code") != null && parameter.get("code").toString().length() > 0) {
		sb.append("    AND NC.IDA3A3 IN ( SELECT IDA2A2                                              \n");
		sb.append("                        FROM NUMBERCODE N                                         \n");
		sb.append("                       WHERE 1=1                                                  \n");
		sb.append("                         AND N.CODETYPE = '" + parameter.get("codeType") + "'     \n");
		sb.append("                         AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("N.CODE", parameter.get("code").toString(), false)).append("    \n");
		sb.append("                    )                                                             \n");
	    } else if (parameter.get("name") != null && parameter.get("name").toString().length() > 0) {
		sb.append("    AND NC.IDA3A3 IN ( SELECT IDA2A2                                              \n");
		sb.append("                        FROM NUMBERCODE N                                         \n");
		sb.append("                       WHERE 1=1                                                  \n");
		sb.append("                         AND N.CODETYPE = '" + parameter.get("codeType") + "'     \n");
		sb.append("                         AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("N.NAME", parameter.get("name").toString(), false)).append("    \n");
		sb.append("                    )                                                             \n");
	    } else if (parameter.get("oid") != null && parameter.get("oid").toString().length() > 0) {
		sb.append("    AND NC.IDA3A3 IN ( SELECT IDA2A2                                              \n");
		sb.append("                        FROM NUMBERCODE N                                         \n");
		sb.append("                       WHERE 1=1                                                  \n");
		sb.append("                         AND N.CODETYPE = '" + parameter.get("codeType") + "'     \n");
		sb.append("                         AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("N.IDA2A2", parameter.get("oid").toString(), false))
		        .append("    \n");
		sb.append("                    )                                                             \n");
	    } else if (parameter.get("myCode") != null && parameter.get("myCode").toString().length() > 0) {
		sb.append("    AND NC.CODETYPE = '" + parameter.get("codeType") + "'                         \n");
		// code 자기 자신조회
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.CODE", parameter.get("myCode").toString(), false))
		        .append("    \n");
	    } else {
		sb.append("    AND NC.CODETYPE = '" + parameter.get("codeType") + "'                         \n");

		// exclude에 포함된 Code는 제외 시킴
		if (parameter.get("exclude") != null && parameter.get("exclude").toString() != "") {
		    sb.append("    AND NC.IDA3A3 NOT IN (SELECT N.IDA2A2                                                           \n");
		    sb.append("                            FROM NUMBERCODE N                                                       \n");
		    sb.append("                           WHERE N.CODETYPE = '" + parameter.get("codeType") + "'                   \n");
		    sb.append("                             AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("N.CODE", parameter.get("exclude").toString(), false))
			    .append("    \n");
		    sb.append("                         )                                                                          \n");
		}

		// depthLevel이 top, child가 아닌 경우 codeType 전체 조회
		// top 2레레만 조회
		// child 3레벨만 조회
		if (parameter.get("depthLevel") != null && parameter.get("depthLevel").equals("child")) {
		    sb.append("    AND NC.IDA3A3 <> 0           \n");
		} else {
		    sb.append("    AND NC.IDA3A3 = 0            \n");
		}
	    }

	    // Car 에서 사용됨
	    if (parameter.get("venderCode") != null && parameter.get("venderCode").toString() != "") {
		sb.append("    AND NC.VENDERCODE = '" + parameter.get("venderCode") + "'                 \n");
	    }

	    sb.append("  ORDER BY TO_NUMBER(NC.SORTING), NC.IDA2A2         \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		numberCode = new HashMap<String, Object>();
		numberCode.put("codeType", StringUtil.checkNull(rs.getString("codeType")));
		numberCode.put("code", StringUtil.checkNull(rs.getString("code")));
		numberCode.put("value", StringUtil.checkNull(rs.getString("value")));
		numberCode.put("abbr", StringUtil.checkNull(rs.getString("abbr")));
		numberCode.put("description", StringUtil.checkNull(rs.getString("description")));
		numberCode.put("sorting", StringUtil.checkNull(rs.getString("sorting")));
		numberCode.put("lang", StringUtil.checkNull(rs.getString("lang")));
		numberCode.put("vendercode", StringUtil.checkNull(rs.getString("vendercode")));
		numberCode.put("ida2a2", StringUtil.checkNull(rs.getString("ida2a2")));
		numberCode.put("oid", StringUtil.checkNull(rs.getString("oid")));
		numberCode.put("oid2", StringUtil.checkNull(rs.getString("oid2")));

		numberCodeList.add(numberCode);
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numberCodeList;
    }

    /**
     * [PLM System 1차개선] 수정내용 : CarCategoryList 조회 Dao 수정일자 : 2013. 7. 12 수정자 : 김종호
     * 
     * @param Map
     *            <String, Object> parameter
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getCarCategoryList(Map<String, Object> parameter) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> numberCode = null;
	List<Map<String, Object>> numberCodeList = new ArrayList<Map<String, Object>>();

	try {
	    sb = new StringBuffer();
	    sb.append(" SELECT OEM.CODE                  \n");
	    sb.append("       ,OEM.NAME                  \n");
	    sb.append("   FROM NUMBERCODE     NC         \n");
	    sb.append("       ,OEMPROJECTTYPE OEM        \n");
	    sb.append("  WHERE 1=1                       \n");
	    sb.append("    AND OEM.IDA3A4  = NC.IDA2A2   \n");
	    sb.append("    AND OEM.OEMTYPE = 'CARTYPE'   \n");
	    sb.append("    AND OEM.ISDISABLED = 0        \n");
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.CODE", parameter.get("carCategory").toString(), false))
		    .append("    \n");
	    sb.append("  ORDER BY NC.SORTING, NC.IDA2A2         \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		numberCode = new HashMap<String, Object>();
		numberCode.put("code", StringUtil.checkNull(rs.getString("code")));
		numberCode.put("value", StringUtil.checkNull(rs.getString("name")));
		numberCodeList.add(numberCode);
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numberCodeList;
    }

    public void createNumberCodeValue(Map<String, Object> parameter) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;

	try {

	    sb = new StringBuffer();
	    sb.append(" MERGE INTO NUMBERCODEVALUE A                                \n");
	    sb.append(" USING (                                                     \n");
	    sb.append("     SELECT '" + parameter.get("codeType") + "' AS CODETYPE  \n");
	    sb.append("           ,'" + parameter.get("code") + "' AS CODE      \n");
	    sb.append("           ,'" + parameter.get("lang") + "' AS LANG      \n");
	    sb.append("       FROM DUAL                                             \n");
	    sb.append(" ) B                                                         \n");
	    sb.append(" ON ( A.CODETYPE = B.CODETYPE AND A.CODE = B.CODE AND A.LANG = B.LANG )    \n");
	    sb.append(" WHEN MATCHED THEN                                           \n");
	    sb.append("     UPDATE SET                                              \n");
	    sb.append("             VALUE       = '" + parameter.get("value") + "'  \n");
	    sb.append("            ,DESCRIPTION = '" + parameter.get("desc") + "'   \n");
	    sb.append("            ,ABBR = '" + parameter.get("abbr") + "'          \n");
	    sb.append("            ,MODIFY_USER = 'wcadmin'                         \n");
	    sb.append("            ,MODIFY_DATE = SYSDATE                           \n");
	    sb.append(" WHEN NOT MATCHED THEN                                       \n");
	    sb.append("     INSERT                                                  \n");
	    sb.append("     (                                                       \n");
	    sb.append("         CODETYPE                                            \n");
	    sb.append("        ,CODE                                                \n");
	    sb.append("        ,LANG                                                \n");
	    sb.append("        ,VALUE                                               \n");
	    sb.append("        ,DESCRIPTION                                         \n");
	    sb.append("        ,ABBR                                                \n");
	    sb.append("        ,CREATE_USER                                         \n");
	    sb.append("        ,CREATE_DATE                                         \n");
	    sb.append("        ,MODIFY_USER                                         \n");
	    sb.append("        ,MODIFY_DATE                                         \n");
	    sb.append("     )                                                       \n");
	    sb.append("     VALUES                                                  \n");
	    sb.append("     (                                                       \n");
	    sb.append("         '" + parameter.get("codeType") + "'                 \n");
	    sb.append("        ,'" + parameter.get("code") + "'                 \n");
	    sb.append("        ,'" + parameter.get("lang") + "'                 \n");
	    sb.append("        ,'" + parameter.get("value") + "'                 \n");
	    sb.append("        ,'" + parameter.get("desc") + "'                 \n");
	    sb.append("        ,'" + parameter.get("abbr") + "'                 \n");
	    sb.append("        ,'wcadmin'        \n");
	    sb.append("        ,SYSDATE          \n");
	    sb.append("        ,'wcadmin'        \n");
	    sb.append("        ,SYSDATE          \n");
	    sb.append("     )                    \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    pstmt.executeUpdate();
	    conn.commit();
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(pstmt);
	}
	return;
    }

    public String getNumberCodeValue(Map<String, Object> parameter) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	String numberCodeValue = null;
	try {
	    sb = new StringBuffer();
	    sb.append(" SELECT FN_GET_NUMBERCODEVALUE('" + parameter.get("codeType") + "','" + parameter.get("code") + "','"
		    + parameter.get("locale") + "') AS VALUE FROM DUAL  \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		numberCodeValue = StringUtil.checkNull(rs.getString("value"));
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numberCodeValue;
    }

    public String getNumberCodeValue_Oid(Map<String, Object> parameter) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	String numberCodeValue = null;
	try {
	    sb = new StringBuffer();
	    sb.append(" SELECT FN_GET_NUMBERCODEVALUE_OID('" + parameter.get("codeType") + "','" + parameter.get("oid") + "','"
		    + parameter.get("locale") + "') AS VALUE FROM DUAL  \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		numberCodeValue = StringUtil.checkNull(rs.getString("value"));
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numberCodeValue;
    }

    public Map<String, Object> getNumberCodeValueMap(Map<String, Object> parameter) throws Exception {

	// edited by tklee 2014-8-14
	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> numberCode = new HashMap<String, Object>();
	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT VALUE, CODE, DESCRIPTION, ABBR \n");
	    sb.append(" FROM NUMBERCODEVALUE \n");
	    sb.append(" WHERE CODETYPE = ? \n");
	    sb.append(" AND CODE     = ? \n");
	    sb.append(" AND LANG     = ? \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    pstmt.setString(1, (String) parameter.get("codeType"));
	    pstmt.setString(2, (String) parameter.get("code"));
	    pstmt.setString(3, (String) parameter.get("locale"));
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		numberCode.put("value", StringUtil.checkNull(rs.getString("value")));
		numberCode.put("desc", StringUtil.checkNull(rs.getString("description")));
		numberCode.put("abbr", StringUtil.checkNull(rs.getString("abbr")));
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numberCode;
    }

    public String getNumberCodeMap(Map<String, Object> parameter) throws Exception {

	// edited by tklee 2014-8-14
	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	// Map<String, Object> numberCode = new HashMap<String, Object>();
	String numCodeStr = "";
	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT IDA2A2 \n");
	    sb.append(" FROM NUMBERCODE \n");
	    sb.append(" WHERE CODETYPE = ? \n");
	    sb.append(" AND CODE     = ? \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    pstmt.setString(1, (String) parameter.get("codeType"));
	    pstmt.setString(2, (String) parameter.get("code"));
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		// numberCode.put("ida2a2", StringUtil.checkNull(rs.getString("IDA2A2")));
		numCodeStr = StringUtil.checkNull(rs.getString("IDA2A2"));
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numCodeStr;
    }

    /**
     * [PLM System 1차개선] 수정내용 : getNumberCodeListPopup - 사내생산처,고객처,납입처 수정일자 : 2013. 7. 12 수정자 : 김종호
     * 
     * @param Map
     *            <String, Object> parameter
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getNumberCodeListPopup(List<Map<String, Object>> conditionList) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> numberCode = null;
	List<Map<String, Object>> numberCodeList = new ArrayList<Map<String, Object>>();

	try {

	    sb = new StringBuffer();

	    sb.append(" SELECT NC.CLASSNAMEA2A2||':'||NC.IDA2A2 AS OID             \n");
	    sb.append("       ,NC.IDA2A2                        AS IDA2A2          \n");
	    sb.append("       ,NC.CODE                          AS CODE            \n");
	    sb.append("       ,NCV.VALUE                        AS NAME            \n");
	    sb.append("       ,NCV.DESCRIPTION                  AS DES            \n");
	    sb.append("       ,NC.VENDERCODE                    AS VENDERCODE      \n");
	    sb.append("   FROM NUMBERCODE      NC                                  \n");
	    sb.append("       ,NUMBERCODEVALUE NCV                                 \n");
	    sb.append("  WHERE 1=1                                                 \n");
	    sb.append("  AND NC.CODETYPE    = NCV.CODETYPE                         \n");
	    sb.append("  AND NC.CODE        = NCV.CODE                             \n");
	    sb.append("  AND NCV.LANG        = '" + conditionList.get(0).get("lang") + "'     \n");

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		if (map.getString("type").length() > 0 && map.getString("type") != null) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.CODETYPE", map.getString("type"), false))
			    .append("    \n");
		}

		if (map.getString("code") != null && map.getString("code").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.CODE", map.getString("code"), true))
			    .append("    \n");
		}

		if (map.getString("nation") != null && map.getString("nation").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.NAME", map.getString("nation"), true))
			    .append("    \n");
		}

		if (map.getString("name") != null && map.getString("name").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.NAME", map.getString("name"), true))
			    .append("    \n");
		}

		if (map.getString("description") != null && map.getString("description").length() > 0) {
		    sb.append("    AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("NC.DESCRIPTION", map.getString("description"), true))
			    .append("    \n");
		}

		if (map.getString("wctype") != null && map.getString("wctype").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.WCTYPE", map.getString("wctype"), false))
			    .append("    \n");
		}

		if (map.getString("isParent") != null && "false".equals(map.getString("isParent").toLowerCase())) {
		} else {
		    sb.append("    AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("NC.IDA3A3",
			            String.valueOf(CommonUtil.getOIDLongValue(map.getString("parent"))), false)).append("    \n");
		}

		if (map.getString("vender") != null && map.getString("vender").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.VENDERCODE", map.getString("vender"), false))
			    .append("    \n");
		}
		
		if (map.getString("disable") != null && map.getString("disable").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("NC.DISABLED", map.getString("disable"), false))
			    .append("    \n");
		}
		

	    }

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		numberCode = new HashMap<String, Object>();
		numberCode.put("oid", StringUtil.checkNull(rs.getString("oid")));
		numberCode.put("code", StringUtil.checkNull(rs.getString("code")));
		numberCode.put("name", StringUtil.checkNull(rs.getString("name")));
		numberCode.put("desc", StringUtil.checkNull(rs.getString("des")));
		numberCode.put("vendercode", StringUtil.checkNull(rs.getString("vendercode")));
		numberCode.put("ida2a2", StringUtil.checkNull(rs.getString("ida2a2"))); //

		numberCodeList.add(numberCode);
	    }
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return numberCodeList;
    }
}
