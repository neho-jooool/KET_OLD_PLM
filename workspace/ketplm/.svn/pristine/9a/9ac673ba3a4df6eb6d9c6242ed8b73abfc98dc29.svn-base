package e3ps.qms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import e3ps.common.util.KETQueryUtil;
import e3ps.cost.util.DBUtil;
import e3ps.qms.util.QMSDBUtil;
import ext.ket.common.util.ObjectUtil;

public class QmsPartInterfaceUtil implements RemoteAccess {

    public static final QmsPartInterfaceUtil manager = new QmsPartInterfaceUtil();

    private void execute(List<String> queryList) {

	Connection conn = null;
	PreparedStatement pstmt = null;

	String printQuery = "";

	try {

	    conn = QMSDBUtil.getConnection(false);

	    for (String query : queryList) {
		printQuery = query;
		pstmt = conn.prepareStatement(query);
		pstmt.executeUpdate();
	    }

	    conn.commit();

	} catch (Exception e) {
	    System.out.println(printQuery);
	    e.printStackTrace();
	    try {
		conn.rollback();
	    } catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	} finally {
	    QMSDBUtil.close(conn);
	    DBUtil.close(pstmt);
	}

    }

    private void readySave(String partNos, boolean isFirst) throws Exception {
	List<Map<String, Object>> partList = getPartInfoByWtPartno(partNos, isFirst);
	List<String> queryList = new ArrayList<String>();

	for (Map map : partList) {
	    queryList.add(getSaveQuery(map));
	}

	execute(queryList);
    }

    public void qmsPartSave(String partNos) throws Exception {

	readySave(partNos, true);

    }

    public void qmsPartSave(String partNos, boolean isFirst) throws Exception {
	readySave(partNos, isFirst);
    }

    public void qmsPartDelete(String partNos) throws Exception {
	List<String> queryList = new ArrayList<String>();

	String[] partNo_ = partNos.split(",");

	for (String partNo : partNo_) {

	    String query = "DELETE FROM TBL_ERP_GOODS WHERE PARTNO = '" + partNo + "' AND CORP_CD = '1' AND PLANT_CD = '9999'";
	    queryList.add(query);

	}

	execute(queryList);
    }

    private String singleQuotationDefence(String words) {
	return words.replace("\'", "\''").replace("\"", "\\\"");// 홑따옴표,쌍따옴표 오류 방지
    }

    private String getSaveQuery(Map<String, Object> map) {
	Iterator<String> keys = map.keySet().iterator();

	String update = "";
	String insertColumns = "";
	String insertValues = "";

	StringBuffer saveQuery = new StringBuffer();

	while (keys.hasNext()) {
	    String key = (String) keys.next();
	    String val = (String) map.get(key);

	    if ("null".equals(val) || StringUtils.isEmpty(val)) {
		continue;
	    }
	    val = singleQuotationDefence(val);// 홑따옴표,쌍따옴표 오류 방지
	    update += key + "='" + val + "',";
	    insertColumns += key + ",";
	    insertValues += "'" + val + "',";
	}

	update = StringUtils.removeEnd(update, ",");
	insertColumns = StringUtils.removeEnd(insertColumns, ",");
	insertValues = StringUtils.removeEnd(insertValues, ",");

	saveQuery.append("MERGE TBL_ERP_GOODS AS A \n");
	saveQuery
	        .append("      USING (SELECT '"
	                + singleQuotationDefence((String) map.get("PARTNO"))
	                + "' AS PARTNO, '1' as CORP_CD, '9999' as PLANT_CD) AS B ON (A.PARTNO = B.PARTNO AND A.CORP_CD = B.CORP_CD AND A.PLANT_CD = B.PLANT_CD)  \n");
	saveQuery.append("WHEN  MATCHED THEN \n");
	saveQuery.append("	UPDATE SET " + update);
	saveQuery.append("WHEN NOT MATCHED THEN \n");
	saveQuery.append("     INSERT (" + insertColumns + ") \n");
	saveQuery.append("	VALUES (" + insertValues + ");");

	return saveQuery.toString();

    }

    private String get4MSaveQuery(Map<String, Object> map) {
	Iterator<String> keys = map.keySet().iterator();

	String update = "";
	String insertColumns = "";
	String insertValues = "";

	StringBuffer saveQuery = new StringBuffer();

	while (keys.hasNext()) {
	    String key = (String) keys.next();
	    String val = (String) map.get(key);

	    if ("null".equals(val) || StringUtils.isEmpty(val)) {
		continue;
	    }
	    val = singleQuotationDefence(val);// 홑따옴표,쌍따옴표 오류 방지
	    if ("MD_NO".equals(key)) {
		update = key + "='" + val + "',";
	    }
	    insertColumns += key + ",";
	    insertValues += "'" + val + "',";
	}

	update = StringUtils.removeEnd(update, ",");
	insertColumns = StringUtils.removeEnd(insertColumns, ",");
	insertValues = StringUtils.removeEnd(insertValues, ",");

	saveQuery.append("MERGE TBL_INF_ECO_4M_OP AS A \n");
	saveQuery.append("      USING (SELECT '" + singleQuotationDefence((String) map.get("MD_NO"))
	        + "' AS MD_NO) AS B ON (A.MD_NO = B.MD_NO)  \n");
	saveQuery.append("WHEN  MATCHED THEN \n");
	saveQuery.append("	UPDATE SET " + update);
	saveQuery.append("WHEN NOT MATCHED THEN \n");
	saveQuery.append("     INSERT (" + insertColumns + ") \n");
	saveQuery.append("	VALUES (" + insertValues + ");");

	return saveQuery.toString();

    }

    public List<Map<String, Object>> extractList(String query) throws Exception {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    rs = stat.executeQuery(query);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}
	return list;
    }

    private List<Map<String, Object>> getPartInfoByWtPartno(String partNos, boolean isFirst) throws Exception {
	return extractList(getWtPartQueryByPartNo(partNos, isFirst));
    }

    private String getWtPartQueryByPartNo(String partNos, boolean isFirst) {
	StringBuffer sql = new StringBuffer();

	partNos = singleQuotationDefence(partNos);

	sql.append(" SELECT '1' AS CORP_CD                                                                                                                              \n");
	sql.append("       ,'9999' AS PLANT_CD                                                                                                                          \n");
	sql.append("       ,M.WTPARTNUMBER AS PARTNO                                                                                                                    \n");
	sql.append("       ,M.NAME AS PARTNM                                                                                                                            \n");
	sql.append("       ,(SELECT T.VENDERCODE FROM NUMBERCODE T WHERE T.CODETYPE = 'SPECPARTTYPE' AND T.CODE = P.PTC_STR_3TYPEINFOWTPART) AS PARTGUNCD               \n");
	sql.append("       ,(SELECT T.WCTYPE FROM NUMBERCODE T WHERE T.CODETYPE = 'SPECPARTTYPE' AND T.CODE = P.PTC_STR_3TYPEINFOWTPART ) AS PARTGUN                    \n");
	sql.append("       ,(SELECT C.ERPPRODCODE                                                                                                                       \n");
	sql.append("           FROM KETPARTCLASSLINK B , KETPARTCLASSIFICATION C                                                                                        \n");
	sql.append("          WHERE M.IDA2A2 = B.IDA3B5                                                                                                                 \n");
	sql.append("            AND B.IDA3A5 = C.IDA2A2                                                                                                                 \n");
	sql.append("        ) AS PARTGROUP        -- 자재그룹코드                                                                                                           \n");
	sql.append("       ,P.PTC_STR_21TYPEINFOWTPART AS SR_CODE                                                                                                       \n");
	sql.append("       ,(SELECT T.NAME FROM NUMBERCODE T WHERE T.CODETYPE = 'SPECSERIES' AND T.CODE = P.PTC_STR_21TYPEINFOWTPART) AS SR_NAME                        \n");

	// **************************************************************************************************************************** //
	// **************************************************************************************************************************** //
	// ******** ******* //
	// ******** 아래는 오라클 정규식이 사용되었는데 백슬래시는 자바에서 문제를 발생하므로 \\ 로 표기함. 아래 쿼리를 추출해서 실행해보려면 \\ 를 백슬래시로 바꿔서 실행해야 올바른 결과를 얻을 수 있다 ******** //
	// ******** ******* //
	// **************************************************************************************************************************** //
	// **************************************************************************************************************************** //
	// 오라클 정규식 Start

	sql.append("       ,NVL(DECODE(REGEXP_INSTR(P.PTC_STR_9TYPEINFOWTPART,'^[+-]?\\d*(\\.?\\d*)$'),1,ROUND(P.PTC_STR_9TYPEINFOWTPART,0)||'.000'),'0.000') AS BRGEW  -- 총중량 (반올림 후 소숫점 3자리로 관리)    실수,정수가 아닌 문자열일 경우 0.000                                                          \n");
	sql.append("       ,NVL(DECODE(REGEXP_INSTR(P.PTC_STR_8TYPEINFOWTPART,'^[+-]?\\d*(\\.?\\d*)$'),1,ROUND(P.PTC_STR_8TYPEINFOWTPART,0)||'.000'),'0.000') AS NTGEW  -- 부품중량 (반올림 후 소숫점 3자리로 관리)  실수,정수가 아닌 문자열일 경우 0.000                                                         \n");

	// 오라클 정규식 End

	sql.append("       ,'G' AS GEWEI    -- 중량단위로 'G'로 셋팅                                                                                                          \n");
	sql.append("       ,SUBSTR(M.DEFAULTUNIT,5) AS UNIT_TYPE  -- 기본단위                                                                                              \n");
	sql.append("       ,CASE WHEN P.PTC_STR_49TYPEINFOWTPART IS NOT NULL THEN (SELECT T.GRADE FROM MOLDMATERIAL T WHERE T.IDA2A2 = P.PTC_STR_49TYPEINFOWTPART)      \n");
	sql.append("             WHEN P.PTC_STR_50TYPEINFOWTPART IS NOT NULL THEN (SELECT T.GRADE FROM MOLDMATERIAL T WHERE T.IDA2A2 = P.PTC_STR_50TYPEINFOWTPART)      \n");
	sql.append("             ELSE NULL                                                                                                                              \n");
	sql.append("        END AS MAT_GROUP                                                                                                                            \n");
	sql.append("       ,'Y' AS SHOWYN  -- Y:사용 N:삭제(미사용)                                                                                                          \n");
	sql.append("       ,M.CREATESTAMPA2 AS WDATE  -- 작성일                                                                                                           \n");
	sql.append(" FROM WTPARTMASTER M                                                                                                                                \n");
	sql.append("     ,WTPART P                                                                                                                                      \n");
	sql.append("     ,( SELECT  MAX(I.IDA2A2) AS WT_PARTID                                                                                                          \n");
	sql.append("          FROM WTPART I, WTPARTMASTER J                                                                                                             \n");
	sql.append("         WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                                                                                                     \n");
	sql.append("           AND I.LATESTITERATIONINFO = 1                                                                                                            \n");
	sql.append("           AND I.STATECHECKOUTINFO != 'wrk'                                                                                                         \n");
	sql.append(" AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("J.WTPARTNUMBER", partNos, false)).append(" \n");
	sql.append("         GROUP BY I.IDA3MASTERREFERENCE                                                                                                             \n");
	sql.append("      ) MAXVERPART                                                                                                                                  \n");
	sql.append("  WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                                                                                                            \n");
	sql.append("  AND P.LATESTITERATIONINFO = 1                                                                                                                     \n");
	sql.append("  AND P.STATECHECKOUTINFO != 'wrk'                                                                                                                  \n");

	if (isFirst) {
	    sql.append("  AND P.VERSIONIDA2VERSIONINFO = 0                                                                                                                  \n"); // 최초버전만
	}

	sql.append("  AND P.IDA2A2 = MAXVERPART. WT_PARTID                                                                                                              \n");
	return sql.toString();
    }

    public void qms4mSave() throws Exception {

	List<Map<String, Object>> partList = getAddMoldInfo4M();
	List<String> queryList = new ArrayList<String>();

	for (Map map : partList) {
	    queryList.add(get4MSaveQuery(map));
	}
	execute(queryList);

    }

    private List<Map<String, Object>> getAddMoldInfo4M() throws Exception {
	return extractList(getAddMoldInfo4MQuery());
    }

    private String getAddMoldInfo4MQuery() {
	StringBuffer sql = new StringBuffer();

	sql.append(" WITH TMP AS (                                                                                \n");
	sql.append(" SELECT MII.PARTNO                                                                            \n");
	sql.append("       ,(SELECT U.PERNO                                                                       \n");
	sql.append("           FROM PROJECTMEMBERLINK ML                                                          \n");
	sql.append("               ,PEOPLE U                                                                      \n");
	sql.append("          WHERE PJ.IDA2A2 = ML.IDA3A5                                                         \n");
	sql.append("            AND ML.PJTROLE = 'Team_PRODUCT24'                                                 \n");
	sql.append("            AND ML.IDA3B5 = U.IDA3B4                                                          \n");
	sql.append("         ) AS PROD_MANAGER_SABUN                                                              \n");
	sql.append("       ,MII.IDA2A2                                                                            \n");
	sql.append("   FROM PRODUCTPROJECT PJ                                                                     \n");
	sql.append("       ,MOLDITEMINFO  MII                                                                     \n");
	sql.append("      ,E3PSPROJECTMASTER PM                                                                   \n");
	sql.append("  WHERE MII.IDA3A3 = PJ.IDA2A2 -- 제품프로젝트 아이디                                                \n");
	sql.append("    AND PM.IDA2A2  = PJ.IDA3B8                                                                \n");
	sql.append("    AND PJ.PJTTYPE = 2        -- 자동차, 전자 구분                                                 \n");
	sql.append("    AND PJ.LASTEST = 1        -- LAST PJT 여부                                                  \n");
	sql.append("    AND PJ.CHECKOUTSTATE <> 'c/o'                                                             \n");
	sql.append("    AND INSTR(PM.PJTNO,'C') > 0                                                               \n");
	sql.append("    AND PJ.STATESTATE IN ( 'PROGRESS', 'PLANCHANGE', 'UNDERREVIEW', 'REWORK', 'REJECTED' )    \n");
	sql.append(" )                                                                                            \n");
	sql.append(" SELECT 2 AS PRC_GB -- 처리상태 2 : 등록대기                                                        \n");
	sql.append("       ,TO_CHAR(SYSDATE, 'YYYY.MM.DD') AS REQ_DAY                                             \n");
	sql.append("       ,PROD_MANAGER_SABUN AS CHG_ID -- 제품PJT 생산관리담당자 사번                                    \n");
	sql.append("       ,PM.PJTNO AS MD_NO -- 금형번호                                                            \n");
	sql.append("       ,T.PARTNO AS PART_NO                                                                   \n");
	sql.append("       ,(SELECT A.NAME                                                                        \n");
	sql.append("           FROM WTPARTMASTER A                                                                \n");
	sql.append("          WHERE A.WTPARTNUMBER = T.PARTNO                                                     \n");
	sql.append("        ) AS PART_NM                                                                          \n");
	sql.append("       ,'추가금형' AS ALT_CONT                                                                   \n");
	sql.append("       ,1 AS IF_GB -- I/F 시스템 구분 ( 1:PLM 2:MES)                                              \n");
	sql.append("       ,2 AS FM_GB -- 4M구분 (2:Machine)                                                        \n");
	sql.append("     FROM MOLDPROJECT PJ                                                                      \n");
	sql.append("         ,E3PSPROJECTMASTER  PM                                                               \n");
	sql.append("         ,E3PSTASK TK                                                                         \n");
	sql.append("         ,EXTENDSCHEDULEDATA TS                                                               \n");
	sql.append("         ,TMP T                                                                               \n");
	sql.append("    WHERE PM.IDA2A2 = PJ.IDA3B8  -- 프로젝트마스터ID                                               \n");
	sql.append("      AND PJ.LASTEST = 1        -- LAST PJT 여부                                                \n");
	sql.append("      AND PJ.CHECKOUTSTATE <> 'c/o'                                                           \n");
	sql.append("      AND TK.IDA3A4 = TS.IDA2A2                                                               \n");
	sql.append("      AND PJ.IDA2A2 = TK.IDA3B4                                                               \n");
	sql.append("      AND TK.TASKNAME LIKE '금형Try_[양산검증%'                                                    \n");
	sql.append("      AND T.IDA2A2 = PJ.IDA3A10                                                               \n");
	sql.append("      AND TK.TASKCOMPLETION = 100                                                             \n");
	sql.append("      AND TS.EXECENDDATE >= SYSDATE - 7  -- 지난 1주일간 완료된 TASK 대상                             \n");
	sql.append("      AND TS.EXECENDDATE <= SYSDATE      -- 지난 1주일간 완료된 TASK 대상                             \n");

	return sql.toString();
    }

}
