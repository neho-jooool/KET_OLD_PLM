package ext.ket.sales.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import e3ps.common.code.NumberCodeHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.cost.util.StringUtil;
import e3ps.groupware.company.Department;
import ext.ket.sales.entity.KETSalesTask;

public class salesDao {

    public List<Map<String, Object>> getTaskLineUpList(String oid, String moreData) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, Object>> taksLineUpList = null;

	String mainSignal_ = searchMainCheck(oid);

	String temp[] = mainSignal_.split("\\|");
	mainSignal_ = temp[0];

	final Object mainSignal = mainSignal_;

	String sSql = getSalesTaskOrderByQuery(oid, moreData);
	taksLineUpList = oDaoManager.queryForList(sSql, new RowSetStrategy<Map<String, Object>>() {
	    @Override
	    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		Map<String, Object> taskLineUp = new HashMap<String, Object>();
		taskLineUp.put("resultDate", StringUtil.checkNull(rs.getString("RESULTDATE")));
		taskLineUp.put("planDate", StringUtil.checkNull(rs.getString("PLANDATE")));
		taskLineUp.put("classKey", StringUtil.checkNull(rs.getString("classKey")));

		KETSalesTask task = (KETSalesTask) CommonUtil.getObject(rs.getString("classKey"));
		taskLineUp.put("subject", task.getSubject().replaceAll(System.getProperty("line.separator"), "<br>"));
		Department dept = (Department) task.getCollaboTeam();
		taskLineUp.put("collaboTeam", dept.getName());
		taskLineUp.put("planCheck", StringUtil.checkNull(task.getPlanCheck()));
		taskLineUp.put("propelwebEditor", StringUtil.checkNull((String) task.getPropelwebEditor()));
		taskLineUp.put("propelwebEditorText", StringUtil.checkNull((String) task.getPropelwebEditorText()));
		taskLineUp.put("problemwebEditor", StringUtil.checkNull((String) task.getProblemwebEditor()));
		taskLineUp.put("problemwebEditorText", StringUtil.checkNull((String) task.getProblemwebEditorText()));
		taskLineUp.put("planwebEditor", StringUtil.checkNull((String) task.getPlanwebEditor()));
		taskLineUp.put("stepNo", task.getStepNo());

		if (task.getPlanCheck().equals(mainSignal)) {
		    taskLineUp.put("mainSignal", StringUtil.checkNull(task.getPlanCheck()));
		}

		String hex = "";

		if ("green".equals(task.getPlanCheck())) {
		    hex = "#B0D148";
		} else if ("yellow".equals(task.getPlanCheck())) {
		    hex = "#F8D200";
		} else if ("red".equals(task.getPlanCheck())) {
		    hex = "#E42F42";
		}

		taskLineUp.put("hex", hex);

		taskLineUp.put("gubun", rs.getString("GUBUN"));
		taskLineUp.put("tempplanDate", rs.getString("tempplanDate"));

		return taskLineUp;
	    }
	});

	return taksLineUpList;

    }

    public String getSalesTaskOrderByQuery(String oid, String moreData) {
	StringBuffer buffer = new StringBuffer();

	String tempQry = "AND PLANDATE <= SYSDATE";

	if ("Y".equals(moreData)) {
	    tempQry = "";
	}

	buffer.append("SELECT TO_CHAR(PLANDATE,'YY/MM/DD') AS PLANDATE, 'AFTER' AS GUBUN , classKey, TO_CHAR(PLANDATE,'YYYYMMDD') as tempplanDate,TO_CHAR(RESULTDATE,'YY/MM/DD') as RESULTDATE FROM (					\n");
	buffer.append("SELECT PLANDATE, classnamea2a2||':'||ida2a2 as classKey, RESULTDATE, ROWNUM AS CNT FROM (						\n");
	buffer.append("SELECT PLANDATE, IDA2A2,classnamea2a2,RESULTDATE FROM KETSALESTASK								\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "')					\n");
	buffer.append("AND PLANDATE < ( 													\n");
	buffer.append("SELECT MIN(PLANDATE) FROM KETSALESTASK 										\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "')					\n");
	buffer.append("   AND TRUNC(ABS(SYSDATE-PLANDATE)) = (SELECT MIN(TRUNC(ABS(SYSDATE-PLANDATE))) 					\n");
	buffer.append("                                           FROM KETSALESTASK 								\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "') AND PLANDATE <= SYSDATE))  		\n");
	buffer.append("ORDER BY PLANDATE DESC)) 										\n");
	buffer.append("UNION ALL														\n");
	buffer.append("SELECT TO_CHAR(PLANDATE,'YY/MM/DD') AS PLANDATE, 'BASIC' AS GUBUN , classnamea2a2||':'||ida2a2 as classKey, TO_CHAR(PLANDATE,'YYYYMMDD') as tempplanDate,TO_CHAR(RESULTDATE,'YY/MM/DD') as RESULTDATE FROM KETSALESTASK	\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "')						\n");
	buffer.append("   AND PLANDATE = ( 														\n");
	buffer.append("SELECT MIN(PLANDATE) FROM KETSALESTASK 											\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "') 						\n");
	buffer.append("   AND TRUNC(ABS(SYSDATE-PLANDATE)) = (SELECT MIN(TRUNC(ABS(SYSDATE-PLANDATE))) 						\n");
	buffer.append("                                           FROM KETSALESTASK 													\n");
	buffer.append("                                          WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "') " + tempQry + "))     	\n");
	buffer.append("UNION ALL																			\n");
	buffer.append("SELECT TO_CHAR(PLANDATE,'YY/MM/DD') AS PLANDATE,'AFTER' GUBUN, classKey,TO_CHAR(PLANDATE,'YYYYMMDD') as tempplanDate,TO_CHAR(RESULTDATE,'YY/MM/DD') as RESULTDATE FROM (										\n");
	buffer.append("SELECT PLANDATE, classnamea2a2||':'||ida2a2 as classKey, ROWNUM AS CNT,RESULTDATE FROM (										\n");
	buffer.append("SELECT PLANDATE, IDA2A2,classnamea2a2,RESULTDATE FROM KETSALESTASK												\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "')									\n");
	buffer.append("   AND PLANDATE > ( 																	\n");
	buffer.append("SELECT MIN(PLANDATE) FROM KETSALESTASK 														\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "') 									\n");
	buffer.append("  AND TRUNC(ABS(SYSDATE-PLANDATE)) = (SELECT MIN(TRUNC(ABS(SYSDATE-PLANDATE))) 									\n");
	buffer.append("                                           FROM KETSALESTASK 												\n");
	buffer.append("                                          WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "') " + tempQry + " ))  	\n");
	buffer.append("ORDER BY PLANDATE ASC)) 																\n");
	buffer.append("ORDER BY PLANDATE ASC																	\n");

	return buffer.toString();
    }

    public String getCSapproveCountByQuery(String oid) {

	StringBuffer buffer = new StringBuffer();
	buffer.append("SELECT COUNT(*) AS CNT FROM KETSALESCSMEETINGAPPROVAL WHERE IDA2A2 IN (\n");
	buffer.append("SELECT IDA3A6 FROM KETSALESCSMEETINGMANAGELINK WHERE IDA2A2 = '" + oid + "') \n");

	return buffer.toString();
    }

    public String getCSapproveCountInfo(String oid) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	String CNT = oDaoManager.queryForObject(getCSapproveCountByQuery(oid), new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String CNT = (String) rs.getString("CNT");
		return CNT;
	    }
	});

	return CNT;
    }

    public String searchMainCheck(String oid) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	String MainCheck = oDaoManager.queryForObject(getMainCheck(oid), new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String MainCheck = "";
		if (StringUtils.isNotEmpty((String) rs.getString("CURREUNTCHECK"))) {
		    MainCheck = (String) rs.getString("CURREUNTCHECK") + "|" + (String) rs.getString("CURREUNTCLASSKEY");
		} else {
		    MainCheck = (String) rs.getString("ATTERCHECK") + "|" + (String) rs.getString("ATTERCLASSKEY");
		}
		MainCheck += "|" + (String) rs.getString("ISDELAY");
		return MainCheck;
	    }
	});

	return MainCheck;
    }

    public String getMainCheck(String oid) {// 실적일자 기준으로 현재 신호등을 체크
	StringBuffer buffer = new StringBuffer();

	buffer.append("SELECT 																				\n");
	/*
	 * buffer.append("(SELECT PLANCHECK 																		\n"); buffer.append("   FROM KETSALESTASK 																		\n");
	 * buffer.append
	 * ("  WHERE IDA2A2 IN (SELECT MIN(IDA2A2) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	 * + oid + "') 			\n"); buffer.append(
	 * "    AND RESULTDATE IN (SELECT MAX(RESULTDATE) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	 * + oid + "') AND RESULTDATE <= SYSDATE))) AS CURREUNTCHECK ,	\n");
	 */
	buffer.append("(SELECT DECODE( FN_GET_NUMBERCODENAME ('SALESPJTSTATE', idA3I11),'실패','red', FN_GET_SALES_PJT_ANDON(a0.ida2a2) ) \n");
	buffer.append("   FROM KETSALESPROJECT A0 WHERE IDA2A2 = '" + oid + "') AS CURREUNTCHECK ,														\n");
	buffer.append("(SELECT classnamea2a2||':'||ida2a2 as classKey 																		\n");
	buffer.append("   FROM KETSALESTASK 																		\n");
	buffer.append("  WHERE IDA2A2 IN (SELECT MIN(IDA2A2) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "') 			\n");
	buffer.append("    AND RESULTDATE IN (SELECT MAX(RESULTDATE) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "') AND RESULTDATE <= SYSDATE))) AS CURREUNTCLASSKEY ,	\n");
	buffer.append("(SELECT PLANCHECK 																						\n");
	buffer.append("  FROM KETSALESTASK 																						\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT MIN(IDA2A2) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "')								\n");
	buffer.append("   AND RESULTDATE IN (SELECT MIN(RESULTDATE) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "') AND RESULTDATE > SYSDATE))) AS ATTERCHECK,	\n");
	buffer.append("(SELECT classnamea2a2||':'||ida2a2 as classKey  																						\n");
	buffer.append("  FROM KETSALESTASK 																						\n");
	buffer.append(" WHERE IDA2A2 IN (SELECT MIN(IDA2A2) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "')								\n");
	buffer.append("   AND RESULTDATE IN (SELECT MIN(RESULTDATE) FROM KETSALESTASK WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '"
	        + oid + "') AND RESULTDATE > SYSDATE))) AS ATTERCLASSKEY,	\n");
	buffer.append("(SELECT DECODE(COUNT(*),0,'','지연') AS ISDELAY  										\n");
	buffer.append("   FROM KETSALESTASK                                             					\n");
	buffer.append("  WHERE IDA2A2 IN (SELECT IDA3B5 FROM KETSALESTASKLINK WHERE IDA3A5 = '" + oid + "') AND RESULTDATE IS NULL	\n");
	buffer.append("    AND 1 = (SELECT '1' FROM KETSALESPROJECT WHERE IDA2A2 = '" + oid
	        + "' AND IDA3I11 = (SELECT IDA2A2 FROM NUMBERCODE WHERE CODETYPE = 'SALESPJTSTATE' AND CODE = 'I')) \n");
	buffer.append("    AND TO_DATE(PLANDATE) < TO_DATE(SYSDATE)) AS ISDELAY							\n");
	buffer.append(" FROM DUAL 																							\n");
	return buffer.toString();
    }

    public String getCSLastManageInfo(String Close) {
	StringBuffer buffer = new StringBuffer();

	buffer.append(" SELECT 'ext.ket.sales.entity.KETSalesCSMeetingManage:'||IDA2A2 as classKey FROM KETSALESCSMEETINGMANAGE 	\n");
	buffer.append("WHERE YEAR||MONTH||DEGREE IN (				\n");
	buffer.append(" SELECT NVL(MAX(TO_NUMBER(YEAR||MONTH||DEGREE)),0) AS CS_DEGREE 		\n");
	buffer.append("   FROM KETSALESCSMEETINGMANAGE )          	\n");
	// buffer.append("  WHERE YEAR = TO_CHAR(SYSDATE,'YYYY')    	\n");
	// buffer.append("    AND MONTH = TO_CHAR(SYSDATE,'MM'))  	        \n");

	if ("N".equals(Close)) {
	    buffer.append("    AND NVL(CSCLOSE,'N') != 'Y'  	        \n");
	}

	return buffer.toString();
    }

    public String searchCSLastManageInfo(String Close) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	String classKey = oDaoManager.queryForObject(getCSLastManageInfo(Close), new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		String classKey = (String) rs.getString("classKey");
		return classKey;
	    }
	});

	return classKey;
    }

    public String getCSInfo() {
	StringBuffer buffer = new StringBuffer();

	buffer.append("SELECT NVL(MAX(TO_NUMBER(DEGREE)),0)+1 AS CS_DEGREE, MAX(CSCLOSE) AS CSCLOSE \n");
	buffer.append("  FROM KETSALESCSMEETINGMANAGE           \n");
	buffer.append(" WHERE YEAR = TO_CHAR(SYSDATE,'YYYY')	\n");
	buffer.append("   AND MONTH = TO_CHAR(SYSDATE,'MM')	\n");

	return buffer.toString();
    }

    public List<Map<String, String>> searchCSInfo() throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, String>> csmeetingInfo = null;

	csmeetingInfo = oDaoManager.queryForList(this.getCSInfo(), new RowSetStrategy<Map<String, String>>() {

	    @Override
	    public Map<String, String> mapRow(ResultSet oResultSet) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, String> info = new HashMap<String, String>();
		info.put("CS_DEGREE", oResultSet.getString("CS_DEGREE"));
		info.put("CSCLOSE", oResultSet.getString("CSCLOSE"));
		return info;
	    }
	});

	return csmeetingInfo;
    }

    public String isPossibleCSMeetingQuery() {
	StringBuffer buffer = new StringBuffer();

	buffer.append(" SELECT NEXTSTARTDATE FROM KETSALESCSMEETINGMANAGE						\n");
	buffer.append("  WHERE YEAR||MONTH||DEGREE = (SELECT MAX(YEAR||MONTH||DEGREE) FROM KETSALESCSMEETINGMANAGE)	\n");
	buffer.append("    AND SYSDATE >= NEXTSTARTDATE									\n");

	return buffer.toString();
    }

    public boolean isPossibleCSMeeting() throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	int cnt = oDaoManager.queryForCount(isPossibleCSMeetingQuery());

	return cnt > 0;
    }

    public List<Map<String, String>> getSalesPresentConditionInfo() throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, String>> salesPresentList = null;

	String sSql = getSalesPresentConditionQuery();
	salesPresentList = oDaoManager.queryForList(sSql, new RowSetStrategy<Map<String, String>>() {
	    @Override
	    public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		Map<String, String> salesPresent = new HashMap<String, String>();
		salesPresent.put("CENTERNAME", StringUtil.checkNull(rs.getString("CENTERNAME")));

		salesPresent.put("THIS_YEAR_NEW_TOTAL", StringUtil.checkNull(rs.getString("THIS_YEAR_NEW_TOTAL"))); // 당해년도 신규 등록 건수(TOTAL)
		salesPresent.put("THIS_YEAR_NEW_S_CNT", StringUtil.checkNull(rs.getString("THIS_YEAR_NEW_S_CNT"))); // 당해년도 신규 성공 건수
		salesPresent.put("THIS_YEAR_NEW_F_CNT", StringUtil.checkNull(rs.getString("THIS_YEAR_NEW_F_CNT"))); // 당해년도 신규 실패 건수
		salesPresent.put("THIS_YEAR_NEW_C_CNT", StringUtil.checkNull(rs.getString("THIS_YEAR_NEW_C_CNT"))); // 당해년도 신규 취소 건수
		salesPresent.put("THIS_YEAR_NEW_I_CNT", StringUtil.checkNull(rs.getString("THIS_YEAR_NEW_I_CNT"))); // 당해년도 신규 진행 건수
		salesPresent.put("THIS_YEAR_NEW_SFC_CNT", StringUtil.checkNull(rs.getString("THIS_YEAR_NEW_SFC_CNT"))); // 당해년도 신규
		                                                                                                        // 성공+실패+취소+진행 합계

		salesPresent.put("CS_NEW_TOTAL", StringUtil.checkNull(rs.getString("CS_NEW_TOTAL"))); // CS회의대상 신규 건수(현재일기준 2주안에 등록되었으면 신규)
		salesPresent.put("CS_NEW_S_CNT", StringUtil.checkNull(rs.getString("CS_NEW_S_CNT"))); // CS회의대상 성공 건수
		salesPresent.put("CS_NEW_F_CNT", StringUtil.checkNull(rs.getString("CS_NEW_F_CNT"))); // CS회의대상 실패 건수
		salesPresent.put("CS_NEW_C_CNT", StringUtil.checkNull(rs.getString("CS_NEW_C_CNT"))); // CS회의대상 취소 건수
		salesPresent.put("CS_NEW_SFC_CNT", StringUtil.checkNull(rs.getString("CS_NEW_SFC_CNT"))); // CS회의대상 성공+실패+취소 합계

		salesPresent.put("CS_ANDON_RED_CNT", StringUtil.checkNull(rs.getString("CS_ANDON_RED_CNT"))); // CS회의대상 진행중이면서 신호등 RED 건수
		salesPresent.put("CS_ANDON_YELLOW_CNT", StringUtil.checkNull(rs.getString("CS_ANDON_YELLOW_CNT"))); // CS회의대상 진행중이면서 신호등
		                                                                                                    // YELLOW 건수
		salesPresent.put("CS_ANDON_GREEN_CNT", StringUtil.checkNull(rs.getString("CS_ANDON_GREEN_CNT"))); // CS회의대상 진행중이면서 신호등 GREEN
		                                                                                                  // 건수
		salesPresent.put("CS_ANDON_RYG_CNT", StringUtil.checkNull(rs.getString("CS_ANDON_RYG_CNT"))); // CS회의대상 진행중이면서 신호등
		                                                                                              // RED+YELLOW+GREEN 합계

		salesPresent.put("CS_MAIN_CATEGORY_CNT_1", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_1"))); // CS회의대상제품군( 친환경
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_2", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_2"))); // CS회의대상제품군( PCB
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_3", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_3"))); // CS회의대상제품군( 방수
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_4", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_4"))); // CS회의대상제품군( 비방수
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_5", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_5"))); // CS회의대상제품군( Fuse
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_6", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_6"))); // CS회의대상제품군( W/H
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_7", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_7"))); // CS회의대상제품군( 기타
		                                                                                                          // )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_8", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_8"))); // CS회의대상제품군(
		                                                                                                          // JOINT )건수
		salesPresent.put("CS_MAIN_CATEGORY_CNT_9", StringUtil.checkNull(rs.getString("CS_MAIN_CATEGORY_CNT_9"))); // CS회의대상제품군(
		                                                                                                          // I/O커넥터 )건수

		if (StringUtils.isEmpty(rs.getString("CENTERCODE"))) {
		    salesPresent.put("CENTERCODE", "subtotal");
		} else {
		    salesPresent.put("CENTERCODE", StringUtil.checkNull(rs.getString("CENTERCODE")));
		}
		salesPresent.put("PARENTCODE", StringUtil.checkNull(rs.getString("PARENTCODE")));

		return salesPresent;
	    }
	});

	return salesPresentList;

    }

    public String getSalesPresentConditionQuery() throws Exception {

	Map<String, Object> parameter = new HashMap<String, Object>();

	parameter.put("codeType", "SALESMAINCATEGORY");

	List<Map<String, Object>> codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

	String mcate1 = ""; // 방수 커넥터(W to W)
	String mcate2 = ""; // 비방수 커넥터(W to W)
	String mcate3 = ""; // PCB 커넥터
	String mcate4 = ""; // 친환경 제품군
	String mcate5 = ""; // Fuse
	String mcate6 = ""; // W/H
	String mcate7 = ""; // 기타
	String mcate8 = ""; // JOINT
	String mcate9 = ""; // I/O커넥터

	for (Map<String, Object> map : codeList) {
	    if ("mcate1".equals(map.get("code"))) {
		mcate1 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate2".equals(map.get("code"))) {
		mcate2 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate3".equals(map.get("code"))) {
		mcate3 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate4".equals(map.get("code"))) {
		mcate4 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate5".equals(map.get("code"))) {
		mcate5 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate6".equals(map.get("code"))) {
		mcate6 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate7".equals(map.get("code"))) {
		mcate7 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate8".equals(map.get("code"))) {
		mcate8 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("mcate9".equals(map.get("code"))) {
		mcate9 = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	}

	parameter.put("codeType", "SALESPJTSTATE");

	codeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

	String SUCCESS = "";
	String FAIL = "";
	String CANCEL = "";
	String PROGRESS = "";

	for (Map<String, Object> map : codeList) {
	    if ("S".equals(map.get("code"))) {
		SUCCESS = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("F".equals(map.get("code"))) {
		FAIL = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	    if ("C".equals(map.get("code"))) {
		CANCEL = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }

	    if ("I".equals(map.get("code"))) {
		PROGRESS = CommonUtil.getOIDLongValue2Str((String) map.get("oid"));
	    }
	}

	/*
	 * String state = NumberCodeHelper.manager.getNumberCodeOid("SALESPJTSTATE", "I");//진행 state =
	 * CommonUtil.getOIDLongValue2Str(state);
	 */
	StringBuffer buffer = new StringBuffer();

	buffer.append("SELECT CENTERNAME 																			\n");
	buffer.append("      ,THIS_YEAR_NEW_TOTAL          --당해년도 신규 등록 건수(TOTAL)                                                                                   \n");
	buffer.append("      ,THIS_YEAR_NEW_S_CNT          --당해년도 신규 성공 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_F_CNT          --당해년도 신규 실패 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_C_CNT          --당해년도 신규 취소 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_I_CNT          --당해년도 신규 진행 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_SFC_CNT        --당해년도 신규 성공+실패+취소 합계                                         \n");
	buffer.append("      ,CS_NEW_TOTAL                 --CS회의대상 신규 건수(현재일기준 2주안에 등록되었으면 신규)                                                                     \n");
	buffer.append("      ,CS_NEW_S_CNT                     --CS회의대상 성공 건수                                                                                       \n");
	buffer.append("      ,CS_NEW_F_CNT                     --CS회의대상 실패 건수                                                                                       \n");
	buffer.append("      ,CS_NEW_C_CNT                     --CS회의대상 취소 건수                                                                                       \n");
	buffer.append("      ,CS_NEW_SFC_CNT --CS회의대상 성공+실패+취소 합계                                                                      \n");
	buffer.append("      ,CS_ANDON_RED_CNT                 --CS회의대상 진행중이면서 신호등 RED 건수                                                                           \n");
	buffer.append("      ,CS_ANDON_YELLOW_CNT              --CS회의대상 진행중이면서 신호등 YELLOW 건수                                                                        \n");
	buffer.append("      ,CS_ANDON_GREEN_CNT               --CS회의대상 진행중이면서 신호등 GREEN 건수                                                                         \n");
	buffer.append("      ,CS_ANDON_RYG_CNT  --CS회의대상 진행중이면서 신호등 RED+YELLOW+GREEN 합계                               \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_1        -- CS회의대상제품군( 친환경 )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_2        -- CS회의대상제품군( PCB )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_3        -- CS회의대상제품군( 방수 )건수                                                                                 \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_4        -- CS회의대상제품군( 비방수 )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_5        -- CS회의대상제품군( Fuse )건수                                                                               \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_6        -- CS회의대상제품군( W/H )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_7        -- CS회의대상제품군( 기타 )건수                                                                                 \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_8        -- CS회의대상제품군( JOINT )건수                                                                              \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_9        -- CS회의대상제품군( I/O커넥터 )건수                                                                             \n");
	buffer.append("      ,(SELECT CODE FROM DEPARTMENT WHERE NAME = CENTERNAME) AS CENTERCODE			        \n");
	buffer.append("      ,(SELECT CODE FROM DEPARTMENT WHERE NAME = '영업본부') AS PARENTCODE			        \n");
	buffer.append("  FROM (																					\n");
	buffer.append("SELECT DECODE(CENTERNAME,NULL,'소계',CENTERNAME) AS CENTERNAME                                                                                                             \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_TOTAL) AS THIS_YEAR_NEW_TOTAL          --당해년도 신규 등록 건수(TOTAL)                                                                                   \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_S_CNT) AS THIS_YEAR_NEW_S_CNT          --당해년도 신규 성공 건수                                                                                          \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_F_CNT) AS THIS_YEAR_NEW_F_CNT          --당해년도 신규 실패 건수                                                                                          \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_C_CNT) AS THIS_YEAR_NEW_C_CNT          --당해년도 신규 취소 건수                                                                                          \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_I_CNT) AS THIS_YEAR_NEW_I_CNT          --당해년도 신규 진행 건수                                                                                          \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_S_CNT)+SUM(THIS_YEAR_NEW_F_CNT)+SUM(THIS_YEAR_NEW_C_CNT)+SUM(THIS_YEAR_NEW_I_CNT) AS THIS_YEAR_NEW_SFC_CNT --당해년도 신규 성공+실패+취소+진행 합계                                         \n");
	buffer.append("      ,SUM(CS_NEW_TOTAL)        AS CS_NEW_TOTAL                 --CS회의대상 신규 건수(현재일기준 2주안에 등록되었으면 신규)                                                                     \n");
	buffer.append("      ,SUM(CS_NEW_S_CNT)        AS CS_NEW_S_CNT                     --CS회의대상 성공 건수                                                                                       \n");
	buffer.append("      ,SUM(CS_NEW_F_CNT)        AS CS_NEW_F_CNT                     --CS회의대상 실패 건수                                                                                       \n");
	buffer.append("      ,SUM(CS_NEW_C_CNT)        AS CS_NEW_C_CNT                     --CS회의대상 취소 건수                                                                                       \n");
	buffer.append("      ,SUM(CS_NEW_S_CNT)+SUM(CS_NEW_F_CNT)+SUM(CS_NEW_C_CNT) AS CS_NEW_SFC_CNT --CS회의대상 성공+실패+취소 합계                                                                      \n");
	buffer.append("      ,SUM(CS_ANDON_RED_CNT)    AS CS_ANDON_RED_CNT                 --CS회의대상 진행중이면서 신호등 RED 건수                                                                           \n");
	buffer.append("      ,SUM(CS_ANDON_YELLOW_CNT) AS CS_ANDON_YELLOW_CNT              --CS회의대상 진행중이면서 신호등 YELLOW 건수                                                                        \n");
	buffer.append("      ,SUM(CS_ANDON_GREEN_CNT)  AS CS_ANDON_GREEN_CNT               --CS회의대상 진행중이면서 신호등 GREEN 건수                                                                         \n");
	buffer.append("      ,SUM(CS_ANDON_RED_CNT)+SUM(CS_ANDON_YELLOW_CNT)+SUM(CS_ANDON_GREEN_CNT) AS CS_ANDON_RYG_CNT  --CS회의대상 진행중이면서 신호등 RED+YELLOW+GREEN 합계                               \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_1) AS CS_MAIN_CATEGORY_CNT_1        -- CS회의대상제품군( 친환경 )건수                                                                                \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_2) AS CS_MAIN_CATEGORY_CNT_2        -- CS회의대상제품군( PCB )건수                                                                                \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_3) AS CS_MAIN_CATEGORY_CNT_3        -- CS회의대상제품군( 방수 )건수                                                                                 \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_4) AS CS_MAIN_CATEGORY_CNT_4        -- CS회의대상제품군( 비방수 )건수                                                                                \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_5) AS CS_MAIN_CATEGORY_CNT_5        -- CS회의대상제품군( Fuse )건수                                                                               \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_6) AS CS_MAIN_CATEGORY_CNT_6        -- CS회의대상제품군( W/H )건수                                                                                \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_7) AS CS_MAIN_CATEGORY_CNT_7        -- CS회의대상제품군( 기타 )건수                                                                                 \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_8) AS CS_MAIN_CATEGORY_CNT_8        -- CS회의대상제품군( JOINT )건수                                                                              \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_9) AS CS_MAIN_CATEGORY_CNT_9        -- CS회의대상제품군( I/O커넥터 )건수                                                                             \n");
	buffer.append("      ,SORT                                                                                                                                                              \n");
	buffer.append("  FROM                                                                                                                                                                   \n");
	buffer.append("(                                                                                                                                                                        \n");
	buffer.append("SELECT CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') THEN 1                                                                                       \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_TOTAL --당해년도 신규 등록 건수(TOTAL)                                                                                                                    \n");
	buffer.append("      ,CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') AND A0.idA3I11 = '" + SUCCESS
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_S_CNT --당해년도 신규 성공 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') AND A0.idA3I11 = '" + FAIL
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_F_CNT --당해년도 신규 실패 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') AND A0.idA3I11 = '" + CANCEL
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_C_CNT --당해년도 신규 취소 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN A0.idA3I11 = '" + PROGRESS
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_I_CNT --신규 진행 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND TO_DATE(A0.CREATESTAMPA2) >= TO_DATE(SYSDATE-14) THEN 1                                                                               \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_TOTAL --CS회의대상 신규 건수(현재일기준 2주안에 등록되었으면 신규)                                                                                                             \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3I11 = '" + SUCCESS
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_S_CNT --CS회의대상 성공 건수                                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3I11 = '" + FAIL
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_F_CNT --CS회의대상 실패 건수                                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3I11 = '" + CANCEL
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_C_CNT --CS회의대상 취소 건수                                                                                                                                   \n");
	/*
	 * buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A2.ANDON = 'R' AND A0.idA3I11 = '"+state+
	 * "' THEN 1                                                                                     \n");
	 */
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND (NVL(DECODE(idA3I11,'1079546143','R',DECODE(FN_GET_SALES_PJT_ANDON(a0.ida2a2),'yellow', 'Y','green', 'G','red', 'R','gray','P')),'G') ) = 'R' THEN 1                                                                                     \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_ANDON_RED_CNT --CS회의대상 진행중이면서 신호등 RED 건수                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND (NVL(DECODE(idA3I11,'1079546143','R',DECODE(FN_GET_SALES_PJT_ANDON(a0.ida2a2),'yellow', 'Y','green', 'G','red', 'R','gray','P')),'G') ) = 'Y' THEN 1                                                                                     \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_ANDON_YELLOW_CNT --CS회의대상 진행중이면서 신호등 YELLOW 건수                                                                                                             \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND (NVL(DECODE(idA3I11,'1079546143','R',DECODE(FN_GET_SALES_PJT_ANDON(a0.ida2a2),'yellow', 'Y','green', 'G','red', 'R','gray','P')),'G') ) = 'G' THEN 1                                                                                     \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_ANDON_GREEN_CNT --CS회의대상 진행중이면서 신호등 GREEN 건수                                                                                                               \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate4
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_1 -- CS회의대상제품군( 친환경 )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate3
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_2 -- CS회의대상제품군( PCB )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate1
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_3 -- CS회의대상제품군( 방수 )건수                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate2
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_4 -- CS회의대상제품군( 비방수 )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate5
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_5 -- CS회의대상제품군( Fuse )건수                                                                                                                 \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate6
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_6 -- CS회의대상제품군( W/H )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate7
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_7 -- CS회의대상제품군( 기타 )건수                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate8
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_8 -- CS회의대상제품군( JOINT )건수                                                                                                                \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate9
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_9 -- CS회의대상제품군( I/O커넥터 )건수                                                                                                               \n");
	buffer.append("      ,A1.NAME AS CENTERNAME                                                                                                                                             \n");
	buffer.append("      ,A1.SORT                                                                                                                                                           \n");
	buffer.append("  FROM KETSALESPROJECT A0, DEPARTMENT A1                                                                                                           \n");
	buffer.append(" WHERE ( (A0.IDA3MASTERREFERENCE, A0.VERSIONIDA2VERSIONINFO) IN (  SELECT A1.IDA3MASTERREFERENCE, MAX (A1.VERSIONIDA2VERSIONINFO)                                        \n");
	buffer.append("                                                                     FROM WTDOCUMENTMASTER A0, KETSALESPROJECT A1                                                        \n");
	buffer.append("                                                                    WHERE (    (A0.IDA2A2 = A1.IDA3MASTERREFERENCE)                                                      \n");
	buffer.append("                                                                      AND (A1.LATESTITERATIONINFO = 1))                                                                  \n");
	buffer.append("                                                                 GROUP BY A1.IDA3MASTERREFERENCE) )                                                                      \n");
	buffer.append("   AND (A0.LATESTITERATIONINFO = 1)                                                                                                                                      \n");
	buffer.append("   AND (A0.CENTERCODE = A1.CODE or A0.IDA3L11 = A1.IDA2A2)                                                                                                                                          \n");
	buffer.append("   AND A1.IDA3PARENTREFERENCE = (SELECT IDA2A2 FROM DEPARTMENT WHERE NAME='영업본부')                                                                                                                                  \n");
	buffer.append("ORDER BY A1.SORT                                                                                                                                                         \n");
	buffer.append(")                                                                                                                                                                        \n");
	buffer.append("GROUP BY ROLLUP(CENTERNAME, SORT) HAVING GROUPING_ID(CENTERNAME, SORT) IN (0, 3)                                                                                         \n");
	buffer.append(")                                                                                                                                                                        \n");
	buffer.append("UNION ALL                                                                                                                                                                \n");
	buffer.append("SELECT CENTERNAME 																			\n");
	buffer.append("      ,THIS_YEAR_NEW_TOTAL          --당해년도 신규 등록 건수(TOTAL)                                                                                   \n");
	buffer.append("      ,THIS_YEAR_NEW_S_CNT          --당해년도 신규 성공 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_F_CNT          --당해년도 신규 실패 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_C_CNT          --당해년도 신규 취소 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_I_CNT          --당해년도 신규 진행 건수                                                                                          \n");
	buffer.append("      ,THIS_YEAR_NEW_SFC_CNT        --당해년도 신규 성공+실패+취소 합계                                         \n");
	buffer.append("      ,CS_NEW_TOTAL                 --CS회의대상 신규 건수(현재일기준 2주안에 등록되었으면 신규)                                                                     \n");
	buffer.append("      ,CS_NEW_S_CNT                     --CS회의대상 성공 건수                                                                                       \n");
	buffer.append("      ,CS_NEW_F_CNT                     --CS회의대상 실패 건수                                                                                       \n");
	buffer.append("      ,CS_NEW_C_CNT                     --CS회의대상 취소 건수                                                                                       \n");
	buffer.append("      ,CS_NEW_SFC_CNT --CS회의대상 성공+실패+취소 합계                                                                      \n");
	buffer.append("      ,CS_ANDON_RED_CNT                 --CS회의대상 진행중이면서 신호등 RED 건수                                                                           \n");
	buffer.append("      ,CS_ANDON_YELLOW_CNT              --CS회의대상 진행중이면서 신호등 YELLOW 건수                                                                        \n");
	buffer.append("      ,CS_ANDON_GREEN_CNT               --CS회의대상 진행중이면서 신호등 GREEN 건수                                                                         \n");
	buffer.append("      ,CS_ANDON_RYG_CNT  --CS회의대상 진행중이면서 신호등 RED+YELLOW+GREEN 합계                               \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_1        -- CS회의대상제품군( 친환경 )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_2        -- CS회의대상제품군( PCB )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_3        -- CS회의대상제품군( 방수 )건수                                                                                 \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_4        -- CS회의대상제품군( 비방수 )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_5        -- CS회의대상제품군( Fuse )건수                                                                               \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_6        -- CS회의대상제품군( W/H )건수                                                                                \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_7        -- CS회의대상제품군( 기타 )건수                                                                                 \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_8        -- CS회의대상제품군( JOINT )건수                                                                              \n");
	buffer.append("      ,CS_MAIN_CATEGORY_CNT_9        -- CS회의대상제품군( I/O커넥터 )건수                                                                             \n");
	buffer.append("      ,(SELECT CODE FROM DEPARTMENT WHERE NAME = CENTERNAME) AS CENTERCODE			        \n");
	buffer.append("      ,(SELECT CODE FROM DEPARTMENT WHERE NAME = 'Global사업부') AS PARENTCODE			        \n");
	buffer.append("  FROM (																					 \n");
	buffer.append("SELECT DECODE(CENTERNAME,NULL,'소계',CENTERNAME) AS CENTERNAME                                                                                                             \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_TOTAL) AS THIS_YEAR_NEW_TOTAL                                                                                                                   \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_S_CNT) AS THIS_YEAR_NEW_S_CNT                                                                                                                   \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_F_CNT) AS THIS_YEAR_NEW_F_CNT                                                                                                                   \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_C_CNT) AS THIS_YEAR_NEW_C_CNT                                                                                                                   \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_I_CNT) AS THIS_YEAR_NEW_I_CNT                                                                                                                   \n");
	buffer.append("      ,SUM(THIS_YEAR_NEW_S_CNT)+SUM(THIS_YEAR_NEW_F_CNT)+SUM(THIS_YEAR_NEW_C_CNT)++SUM(THIS_YEAR_NEW_I_CNT) AS THIS_YEAR_NEW_SFC_CNT --당해년도 신규 성공+실패+취소+진행 합계                                         \n");
	buffer.append("      ,SUM(CS_NEW_TOTAL)        AS CS_NEW_TOTAL                                                                                                                          \n");
	buffer.append("      ,SUM(CS_NEW_S_CNT)        AS CS_NEW_S_CNT                                                                                                                          \n");
	buffer.append("      ,SUM(CS_NEW_F_CNT)        AS CS_NEW_F_CNT                                                                                                                          \n");
	buffer.append("      ,SUM(CS_NEW_C_CNT)        AS CS_NEW_C_CNT                                                                                                                          \n");
	buffer.append("      ,SUM(CS_NEW_S_CNT)+SUM(CS_NEW_F_CNT)+SUM(CS_NEW_C_CNT) AS CS_NEW_SFC_CNT --CS회의대상 성공+실패+취소 합계                                                                      \n");
	buffer.append("      ,SUM(CS_ANDON_RED_CNT)    AS CS_ANDON_RED_CNT                                                                                                                      \n");
	buffer.append("      ,SUM(CS_ANDON_YELLOW_CNT) AS CS_ANDON_YELLOW_CNT                                                                                                                   \n");
	buffer.append("      ,SUM(CS_ANDON_GREEN_CNT)  AS CS_ANDON_GREEN_CNT                                                                                                                    \n");
	buffer.append("      ,SUM(CS_ANDON_RED_CNT)+SUM(CS_ANDON_YELLOW_CNT)+SUM(CS_ANDON_GREEN_CNT) AS CS_ANDON_RYG_CNT  --CS회의대상 진행중이면서 신호등 RED+YELLOW+GREEN 합계                               \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_1) AS CS_MAIN_CATEGORY_CNT_1                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_2) AS CS_MAIN_CATEGORY_CNT_2                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_3) AS CS_MAIN_CATEGORY_CNT_3                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_4) AS CS_MAIN_CATEGORY_CNT_4                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_5) AS CS_MAIN_CATEGORY_CNT_5                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_6) AS CS_MAIN_CATEGORY_CNT_6                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_7) AS CS_MAIN_CATEGORY_CNT_7                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_8) AS CS_MAIN_CATEGORY_CNT_8                                                                                                             \n");
	buffer.append("      ,SUM(CS_MAIN_CATEGORY_CNT_9) AS CS_MAIN_CATEGORY_CNT_9                                                                                                             \n");
	buffer.append("      ,SORT                                                                                                                                                              \n");
	buffer.append("  FROM                                                                                                                                                                   \n");
	buffer.append("(                                                                                                                                                                        \n");
	buffer.append("SELECT CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') THEN 1                                                                                       \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_TOTAL --당해년도 신규 등록 건수(TOTAL)                                                                                                                    \n");
	buffer.append("      ,CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') AND A0.idA3I11 = '" + SUCCESS
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_S_CNT --당해년도 신규 성공 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') AND A0.idA3I11 = '" + FAIL
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_F_CNT --당해년도 신규 실패 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY') AND A0.idA3I11 = '" + CANCEL
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_C_CNT --당해년도 신규 취소 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN A0.idA3I11 = '" + PROGRESS
	        + "' THEN 1                                                           \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END THIS_YEAR_NEW_I_CNT --신규 진행 건수                                                                                                                           \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND TO_DATE(A0.CREATESTAMPA2) >= TO_DATE(SYSDATE-14) THEN 1                                                                               \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_TOTAL --CS회의대상 신규 건수(현재일기준 2주안에 등록되었으면 신규)                                                                                                             \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3I11 = '" + SUCCESS
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_S_CNT --CS회의대상 성공 건수                                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3I11 = '" + FAIL
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_F_CNT --CS회의대상 실패 건수                                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3I11 = '" + CANCEL
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_NEW_C_CNT --CS회의대상 취소 건수                                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND (NVL(DECODE(idA3I11,'1079546143','R',DECODE(FN_GET_SALES_PJT_ANDON(a0.ida2a2),'yellow', 'Y','green', 'G','red', 'R','gray','P')),'G') )  = 'R' THEN 1                                                                                     \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_ANDON_RED_CNT --CS회의대상 진행중이면서 신호등 RED 건수                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND (NVL(DECODE(idA3I11,'1079546143','R',DECODE(FN_GET_SALES_PJT_ANDON(a0.ida2a2),'yellow', 'Y','green', 'G','red', 'R','gray','P')),'G') ) = 'Y' THEN 1                                                                                     \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_ANDON_YELLOW_CNT --CS회의대상 진행중이면서 신호등 YELLOW 건수                                                                                                             \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND (NVL(DECODE(idA3I11,'1079546143','R',DECODE(FN_GET_SALES_PJT_ANDON(a0.ida2a2),'yellow', 'Y','green', 'G','red', 'R','gray','P')),'G') ) = 'G' THEN 1                                                                                     \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_ANDON_GREEN_CNT --CS회의대상 진행중이면서 신호등 GREEN 건수                                                                                                               \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate4
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_1 -- CS회의대상제품군( 친환경 )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate3
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_2 -- CS회의대상제품군( PCB )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate1
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_3 -- CS회의대상제품군( 방수 )건수                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate2
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_4 -- CS회의대상제품군( 비방수 )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate5
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_5 -- CS회의대상제품군( Fuse )건수                                                                                                                 \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate6
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_6 -- CS회의대상제품군( W/H )건수                                                                                                                  \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate7
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_7 -- CS회의대상제품군( 기타 )건수                                                                                                                   \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate8
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_8 -- CS회의대상제품군( JOINT )건수                                                                                                                \n");
	buffer.append("      ,CASE WHEN A0.CSYN = 'Y' AND A0.idA3M11 = '" + mcate9
	        + "' THEN 1                                                                                                        \n");
	buffer.append("       ELSE 0                                                                                                                                                            \n");
	buffer.append("       END CS_MAIN_CATEGORY_CNT_9 -- CS회의대상제품군( I/O커넥터 )건수                                                                                                               \n");
	buffer.append("      ,A1.NAME AS CENTERNAME                                                                                                                                             \n");
	buffer.append("      ,A1.SORT                                                                                                                                                           \n");
	buffer.append("  FROM KETSALESPROJECT A0, DEPARTMENT A1                                                                                                           \n");
	buffer.append(" WHERE ( (A0.IDA3MASTERREFERENCE, A0.VERSIONIDA2VERSIONINFO) IN (  SELECT A1.IDA3MASTERREFERENCE, MAX (A1.VERSIONIDA2VERSIONINFO)                                        \n");
	buffer.append("                                                                     FROM WTDOCUMENTMASTER A0, KETSALESPROJECT A1                                                        \n");
	buffer.append("                                                                    WHERE (    (A0.IDA2A2 = A1.IDA3MASTERREFERENCE)                                                      \n");
	buffer.append("                                                                      AND (A1.LATESTITERATIONINFO = 1))                                                                  \n");
	buffer.append("                                                                 GROUP BY A1.IDA3MASTERREFERENCE) )                                                                      \n");
	buffer.append("   AND (A0.LATESTITERATIONINFO = 1)                                                                                                                                      \n");
	buffer.append("   AND A0.IDA3L11 = A1.IDA2A2                                                                                                                                            \n");
	buffer.append("   AND A0.CENTERCODE IN ('10059','10073')                                                                                                                                         \n");
	buffer.append("ORDER BY A1.SORT                                                                                                                                                         \n");
	buffer.append(")                                                                                                                                                                        \n");
	buffer.append("GROUP BY ROLLUP(CENTERNAME, SORT) HAVING GROUPING_ID(CENTERNAME, SORT) IN (0, 3)                                                                                         \n");
	buffer.append("ORDER BY SORT                                                                                                                                                            \n");
	buffer.append(")                                                                                                                                                                        \n");

	return buffer.toString();
    }

    public String getSalesPresentConditionDetailQuery(String type, String dept, String parent) throws Exception {
	StringBuffer buffer = new StringBuffer();

	buffer.append("SELECT A0.IDA2A2 AS oid 																								\n");
	buffer.append("  FROM KETSALESPROJECT A0, (SELECT IDA2A2,DECODE(NAME,'영업1팀',1,'영업2팀',2,'영업3팀',3,'영업4팀',4,'영업5팀',5,'영업6팀',6,'EU／NA팀',7,'China팀',8) AS SORT FROM DEPARTMENT) A1                                                                                                               \n");
	buffer.append(" WHERE ( (A0.IDA3MASTERREFERENCE, A0.VERSIONIDA2VERSIONINFO) IN (  SELECT A1.IDA3MASTERREFERENCE, MAX (A1.VERSIONIDA2VERSIONINFO)        \n");
	buffer.append("                                                                     FROM WTDOCUMENTMASTER A0, KETSALESPROJECT A1                        \n");
	buffer.append("                                                                    WHERE (    (A0.IDA2A2 = A1.IDA3MASTERREFERENCE)                      \n");
	buffer.append("                                                                      AND (A1.LATESTITERATIONINFO = 1))                                  \n");
	buffer.append("                                                                 GROUP BY A1.IDA3MASTERREFERENCE) )                                      \n");
	buffer.append("   AND (A0.LATESTITERATIONINFO = 1)                                                                                                      \n");
	buffer.append("   AND A0.IDA3L11 = A1.IDA2A2                                                                                                      \n");

	if ("subtotal".equals(dept)) {// 소계
	    buffer.append("   AND A0.IDA3L11 IN  (                                                                                                        \n");
	    buffer.append(" 				SELECT IDA2A2										           \n");
	    buffer.append("				  FROM DEPARTMENT DPART										   \n");
	    // buffer.append("                              WHERE CONNECT_BY_ISLEAF = 1 									   \n");
	    buffer.append("                         START WITH DPART.IDA2A2 = (SELECT IDA2A2 FROM DEPARTMENT DPART WHERE CODE = '" + parent
		    + "' AND ENABLED = 1)   \n");
	    buffer.append("                         CONNECT BY PRIOR DPART.IDA2A2 = IDA3PARENTREFERENCE )						   \n");
	} else if (StringUtils.isNotEmpty(dept)) {
	    buffer.append("   AND (A0.CENTERCODE = '" + dept + "' OR A0.IDA3L11 = (SELECT IDA2A2 FROM DEPARTMENT WHERE CODE = '" + dept
		    + "'))   \n");
	}

	/*
	 * if("THIS_YEAR_NEW_TOTAL_SUM".equals(type) || "THIS_YEAR_NEW_S_CNT_SUM".equals(type) || "THIS_YEAR_NEW_F_CNT_SUM".equals(type) ||
	 * "THIS_YEAR_NEW_C_CNT_SUM".equals(type) || "THIS_YEAR_NEW_SFC_CNT_SUM".equals(type)){
	 * buffer.append("   AND TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY')   \n"); }
	 */

	if (type.startsWith("THIS_YEAR_NEW_TOTAL") || type.startsWith("THIS_YEAR_NEW_S_CNT") || type.startsWith("THIS_YEAR_NEW_F_CNT")
	        || type.startsWith("THIS_YEAR_NEW_C_CNT") || type.startsWith("THIS_YEAR_NEW_I_CNT")
	        || type.startsWith("THIS_YEAR_NEW_SFC_CNT")) {// 당해년도
	    if (!type.startsWith("THIS_YEAR_NEW_I_CNT") && !type.startsWith("THIS_YEAR_NEW_SFC_CNT")) {
		buffer.append("   AND TO_CHAR(A0.CREATESTAMPA2,'YYYY') = TO_CHAR(SYSDATE,'YYYY')                                                                        \n");
	    }

	    if (type.startsWith("THIS_YEAR_NEW_S_CNT")) { // 당해년도 신규 성공 건수
		buffer.append(" AND A0.idA3I11 = 1079546151 \n");
	    }

	    if (type.startsWith("THIS_YEAR_NEW_F_CNT")) { // 당해년도 신규 실패 건수
		buffer.append(" AND A0.idA3I11 = 1079546143 \n");
	    }

	    if (type.startsWith("THIS_YEAR_NEW_C_CNT")) { // 당해년도 신규 취소 건수
		buffer.append(" AND A0.idA3I11 = 1079546142 \n");
	    }

	    if (type.startsWith("THIS_YEAR_NEW_I_CNT")) { // 당해년도 신규 진행 건수
		buffer.append(" AND A0.idA3I11 = 1079546145 \n");
	    }

	    if (type.startsWith("THIS_YEAR_NEW_SFC_CNT")) { // 당해년도 신규 성공+실패+취소 합계
		buffer.append(" AND A0.idA3I11 IN (1079546142,1079546143,1079546145,1079546151) \n");
	    }

	} else {
	    buffer.append(" AND A0.CSYN = 'Y' \n");

	    if (type.startsWith("CS_NEW_TOTAL")) {
		buffer.append(" AND TO_DATE(A0.CREATESTAMPA2) >= TO_DATE(SYSDATE-14) \n");
	    }

	    if (type.startsWith("CS_NEW_S_CNT")) {
		buffer.append(" AND A0.idA3I11 = 1079546151 \n");
	    }

	    if (type.startsWith("CS_NEW_F_CNT")) { // 당해년도 신규 실패 건수
		buffer.append(" AND A0.idA3I11 = 1079546143 \n");
	    }

	    if (type.startsWith("CS_NEW_C_CNT")) { // 당해년도 신규 취소 건수
		buffer.append(" AND A0.idA3I11 = 1079546142 \n");
	    }

	    if (type.startsWith("CS_NEW_SFC_CNT")) { // 당해년도 신규 성공+실패+취소 합계
		buffer.append(" AND A0.idA3I11 IN (1079546142,1079546143,1079546145) \n");
	    }

	    if (type.startsWith("CS_ANDON_RED_CNT")) { // CS회의대상 진행중이면서 신호등 RED 건수
		buffer.append(" AND A0.PROJECTNO IN (SELECT PROJECTNO FROM F_SALES_PJT_ANDON WHERE ANDON = 'R') \n");
		/* buffer.append(" AND A0.idA3I11 = (SELECT IDA2A2 FROM NUMBERCODE WHERE CODETYPE = 'SALESPJTSTATE' AND CODE = 'I') \n"); */
	    }

	    if (type.startsWith("CS_ANDON_YELLOW_CNT")) { // CS회의대상 진행중이면서 신호등 YELLOW 건수
		buffer.append(" AND A0.PROJECTNO IN (SELECT PROJECTNO FROM F_SALES_PJT_ANDON WHERE ANDON = 'Y') \n");
		/* buffer.append(" AND A0.idA3I11 = (SELECT IDA2A2 FROM NUMBERCODE WHERE CODETYPE = 'SALESPJTSTATE' AND CODE = 'I') \n"); */
	    }

	    if (type.startsWith("CS_ANDON_GREEN_CNT")) { // CS회의대상 진행중이면서 신호등 GREEN 건수
		buffer.append(" AND A0.PROJECTNO IN (SELECT PROJECTNO FROM F_SALES_PJT_ANDON WHERE ANDON = 'G') \n");
		/* buffer.append(" AND A0.idA3I11 = (SELECT IDA2A2 FROM NUMBERCODE WHERE CODETYPE = 'SALESPJTSTATE' AND CODE = 'I') \n"); */
	    }

	    if (type.startsWith("CS_ANDON_RYG_CNT")) { // CS회의대상 진행중이면서 신호등 RED 건수
		buffer.append(" AND A0.PROJECTNO IN (SELECT PROJECTNO FROM F_SALES_PJT_ANDON WHERE ANDON IN ('R','G','Y')) \n");
		/* buffer.append(" AND A0.idA3I11 = (SELECT IDA2A2 FROM NUMBERCODE WHERE CODETYPE = 'SALESPJTSTATE' AND CODE = 'I') \n"); */
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_1")) { // CS회의대상제품군( 친환경 )건수
		buffer.append(" AND A0.idA3M11 = 1079543440 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_2")) { // CS회의대상제품군( PCB )건수
		buffer.append(" AND A0.idA3M11 = 1079543439 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_3")) { // CS회의대상제품군( 방수 )건수
		buffer.append(" AND A0.idA3M11 = 1079543437 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_4")) { // CS회의대상제품군( 비방수 )건수
		buffer.append(" AND A0.idA3M11 = 1079543438 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_5")) { // CS회의대상제품군( Fuse )건수
		buffer.append(" AND A0.idA3M11 = 1079543441 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_6")) { // CS회의대상제품군( W/H )건수
		buffer.append(" AND A0.idA3M11 = 1079543442 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_7")) { // CS회의대상제품군( 기타 )건수
		buffer.append(" AND A0.idA3M11 = 1079543443 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_8")) { // CS회의대상제품군( JOINT )건수
		buffer.append(" AND A0.idA3M11 = 1079543444 \n");
	    }

	    if (type.startsWith("CS_MAIN_CATEGORY_CNT_9")) { // CS회의대상제품군( I/O커넥터 )건수
		buffer.append(" AND A0.idA3M11 = 1079543445 \n");
	    }
	}

	buffer.append(" ORDER BY A1.SORT \n");

	return buffer.toString();
    }

    public List<Map<String, String>> getSalesPresentConditionDetailInfo(String type, String dept, String parent) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	List<Map<String, String>> salesPresentList = null;

	String sSql = getSalesPresentConditionDetailQuery(type, dept, parent);
	salesPresentList = oDaoManager.queryForList(sSql, new RowSetStrategy<Map<String, String>>() {
	    @Override
	    public Map<String, String> mapRow(ResultSet rs) throws SQLException {

		Map<String, String> salesPresent = new HashMap<String, String>();
		salesPresent.put("oid", StringUtil.checkNull(rs.getString("oid")));

		return salesPresent;
	    }
	});

	return salesPresentList;

    }
}