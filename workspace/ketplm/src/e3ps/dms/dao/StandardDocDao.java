/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : StandardDocDao.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 12. 16.
 */
package e3ps.dms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : StandardDocDao 설명 : 표준양식 조회 작성자 : 김경희 작성일자 : 2010. 12. 16.
 */
public class StandardDocDao {

    private Connection conn;

    public StandardDocDao(Connection conn) {
	this.conn = conn;
    }

    /**
     * 함수명 : ViewStandardDocList 설명 :
     * 
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws 작성자
     *             : 김경희 작성일자 : 2010. 12. 16.
     */
    public List<Map<String, Object>> ViewStandardDocList(List<Map<String, Object>> conditionList) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> standardDoc = new HashMap<String, Object>();
	List<Map<String, Object>> standardDocList = new ArrayList<Map<String, Object>>();

	try {

	    sb = new StringBuffer();

	    sb.append("SELECT FN_GET_NUMBERCODEVALUE('DIVISIONFLAG', ST.DIVISIONCODE, 'ko')  DIVISION  \n");
	    sb.append("      ,ST.DEPTCODE                                                    DEPT      \n");
	    sb.append("      ,ST.TITLE                                                       TITLE     \n");
	    sb.append("      ,ST.TEMPLATEDESCRIPTION                                         DOCDESC   \n");
	    sb.append("      ,ST.CLASSNAMEA2A2 || ':' || ST.IDA2A2                           OID       \n");
	    sb.append("      ,ST.VERSIONIDA2VERSIONINFO                                      REV       \n");
	    sb.append("      ,ST.MODIFYSTAMPA2                                               LASTDATE  \n");
	    sb.append("  FROM KETSTANDARDTEMPLATE    ST                                                \n");
	    sb.append(" WHERE 1=1                                                                      \n");
	    sb.append("   AND ST.IDA2A2 IN ( SELECT MAX(KST.IDA2A2)                                    \n");
	    sb.append("                        FROM KETSTANDARDTEMPLATE KST                            \n");
	    sb.append("                    GROUP BY KST.IDA3MASTERREFERENCE )                          \n");

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		if (map.getString("docName").length() > 0) {
		    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("ST.TITLE", map.getString("docName"), true))
			    .append("    \n");
		}

		if (map.getString("docDesc").length() > 0) {
		    sb.append("   AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("ST.TEMPLATEDESCRIPTION", map.getString("docDesc"), true))
			    .append("    \n");
		}

		String divisionList = map.getString("divisionList");
		if (divisionList != null && divisionList.trim().length() > 0 && !divisionList.equalsIgnoreCase("null")) {
		    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("ST.DIVISIONCODE", divisionList, false))
			    .append("    \n");
		}

		if (map.getString("deptCode").length() > 0) {
		    sb.append("   AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("ST.DEPTCODE", map.getString("deptCode"), true))
			    .append("    \n");
		}
	    }

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		standardDoc = new HashMap<String, Object>();
		standardDoc.put("divisionCode", StringUtil.checkNull(rs.getString("division"))); // 사업부
		standardDoc.put("deptCode", StringUtil.checkNull(rs.getString("dept"))); // 관리부서
		standardDoc.put("docName", StringUtil.checkNull(rs.getString("title"))); // 문서명
		standardDoc.put("docDesc", StringUtil.checkNull(rs.getString("docdesc"))); // 양식설명
		standardDoc.put("oid", StringUtil.checkNull(rs.getString("oid"))); // oid
		standardDoc.put("rev", StringUtil.checkNull(rs.getString("rev"))); // rev
		standardDoc.put("lastdate", StringUtil.checkNull(rs.getString("lastdate"))); // lastdate
		standardDocList.add(standardDoc);
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
	return standardDocList;
    }
}
