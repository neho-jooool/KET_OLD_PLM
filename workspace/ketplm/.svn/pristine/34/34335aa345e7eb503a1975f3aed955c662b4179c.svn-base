package e3ps.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import wt.lifecycle.State;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.query.LoggableStatement;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectStateFlag;
import ext.ket.project.cost.entity.KETCostDTO;
import ext.ket.shared.log.Kogger;

public class ProjectDao {

    private final Connection conn;

    public ProjectDao(Connection conn) {
	this.conn = conn;
    }

    public List<Map<String, Object>> searchReviewProjectList(List<Map<String, String>> conditionList, TgPagingControl pager)
	    throws Exception {

	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String query = oDaoManager.getOrderByQuery(this.searchReviewProjectQuery(conditionList), pager);
	query = oDaoManager.getPagingQuery(query, pager);
	returnObjList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
	    @Override
	    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		returnObj.put("pjtOid", rs.getString("OID"));
		returnObj.put("reqOid", rs.getString("REQOID"));
		returnObj.put("reqNo", rs.getString("REQNO"));
		returnObj.put("pjtNo", rs.getString("PJTNO"));
		returnObj.put("pjtName", rs.getString("PJTNAME"));
		returnObj.put("planStartDate", rs.getString("PLANSTARTDATE"));
		returnObj.put("buyer", rs.getString("BUYER"));
		returnObj.put("salesName", rs.getString("SALESNAME"));
		returnObj.put("state", rs.getString("STATESTATE"));
		returnObj.put("pjtState", rs.getString("PJTSTATE"));
		returnObj.put("costBomTask", rs.getString("COSTBOMTASK"));
		returnObj.put("costReqTask", rs.getString("COSTREQTASK"));
		returnObj.put("costSalesTask", rs.getString("COSTSALESTASK"));
		returnObj.put("costCalcTask", rs.getString("COSTCALCTASK"));
		returnObj.put("costReportTask", rs.getString("COSTREPORTTASK"));
		returnObj.put("costMember", rs.getString("COSTMEMBER"));
		returnObj.put("reviewResult", rs.getString("REVIEWRESULT"));
		return returnObj;
	    }
	});

	return returnObjList;
    }

    public int searchReviewProjectCount(List<Map<String, String>> conditionList) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	return oDaoManager.queryForCount(this.searchReviewProjectQuery(conditionList));
    }

    @SuppressWarnings("rawtypes")
    private String searchReviewProjectQuery(List<Map<String, String>> conditionList) throws Exception {
	boolean isCost = false;
	for (Map<String, String> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
	    if (!map.getString("dType").equals("")) {
		if (map.getString("dType").equals("1")) {
		    isCost = map.getString("isCost").equals("Y");
		    break;
		}
	    }
	}

	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2                   AS OID                     \n");
	sb.append("       ,PJT.CLASSNAMEKEYI9||':'||PJT.IDA3I9                  AS REQOID                  \n");
	sb.append("       ,PJT.ATTR2                                            AS REQNO                   \n");
	sb.append("       ,E3PSPJTMST.PJTNO                                     AS PJTNO                   \n");
	sb.append("       ,E3PSPJTMST.PJTNAME                                   AS PJTNAME                 \n");
	sb.append("       ,SCHEDULE.PLANSTARTDATE                               AS PLANSTARTDATE           \n");
	sb.append("       ,(SELECT TO_CHAR(WM_CONCAT(FN_GET_NUMBERCODEVALUE_OID('SUBCONTRACTOR', IDA3A5, 'ko')))    \n");
	sb.append("           FROM PROJECTSUBCONTRACTORLINK                                                \n");
	sb.append("          WHERE IDA3B5 = PJT.IDA2A2                                                     \n");
	sb.append("         GROUP BY IDA3B5)                                    AS BUYER                   \n");
	sb.append("       ,PEOPLE.NAME                                          AS SALESNAME               \n");
	sb.append("       ,PJT.STATESTATE                                       AS STATESTATE              \n");
	sb.append("       ,PJT.PJTSTATE                                         AS PJTSTATE                \n");
	sb.append("       ,(SELECT MAX(IDA2A2) FROM E3PSTASK WHERE IDA3B4 = PJT.IDA2A2 AND TASKTYPE = 'COST' AND TASKTYPECATEGORY = 'COST001') AS COSTBOMTASK	\n");
	sb.append("       ,(SELECT MAX(IDA2A2) FROM E3PSTASK WHERE IDA3B4 = PJT.IDA2A2 AND TASKTYPE = 'COST' AND TASKTYPECATEGORY = 'COST013') AS COSTREQTASK	\n");
	sb.append("       ,(SELECT MAX(IDA2A2) FROM E3PSTASK WHERE IDA3B4 = PJT.IDA2A2 AND TASKTYPE = 'COST' AND TASKTYPECATEGORY = 'COST014') AS COSTSALESTASK	\n");
	sb.append("       ,(SELECT MAX(IDA2A2) FROM E3PSTASK WHERE IDA3B4 = PJT.IDA2A2 AND TASKTYPE = 'COST' AND TASKTYPECATEGORY = 'COST015') AS COSTCALCTASK	\n");
	sb.append("       ,(SELECT MAX(IDA2A2) FROM E3PSTASK WHERE IDA3B4 = PJT.IDA2A2 AND TASKTYPE = 'COST' AND TASKTYPECATEGORY = 'COST016') AS COSTREPORTTASK	\n");
	sb.append("       ,(SELECT B.NAME FROM PROJECTMEMBERLINK A ,PEOPLE B 			           \n");
	sb.append("          WHERE PJTROLE = 'Team_REVIEW12' AND IDA3A5 = PJT.IDA2A2			   \n");
	sb.append("            AND A.IDA3B5 = B.IDA3B4) AS COSTMEMBER						           \n");
	sb.append("       ,PJT.REVIEWRESULT                                         AS REVIEWRESULT                \n");
	sb.append("   FROM REVIEWPROJECT       PJT                                                         \n");
	sb.append("       ,E3PSPROJECTMASTER   E3PSPJTMST                                                  \n");
	sb.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                                                    \n");
	sb.append("       ,PEOPLE              PEOPLE                                                      \n");
	// if(isCost){
	// sb.append("   ,(SELECT SUBSTR(PARTNO,0,7) PJTNO FROM COSTPART GROUP BY SUBSTR(PARTNO,0,7)) COSTPRG \n");
	// }
	sb.append("  WHERE 1=1                                                                             \n");
	sb.append("    AND PJT.LASTEST       = 1                                                           \n");
	sb.append("    AND PJT.CHECKOUTSTATE <> 'c/o'                                                      \n");
	sb.append("    AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                           \n");
	sb.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                             \n");
	sb.append("    AND PJT.IDA3J9        = PEOPLE.IDA3B4(+)                                            \n");
	if (isCost) {
	    sb.append("    AND E3PSPJTMST.IDA2A2 IN (SELECT IDA3B5 FROM PJTMASTERPARTLISTLINK)                                   \n");
	}

	for (Map<String, String> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
	    // Project No
	    if (!map.getString("pjtNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNO", map.getString("pjtNo"), true))
		        .append("     \n");
	    }
	    // 사업부
	    if (!map.getString("dType").equals("")) {
		if (map.getString("dType").equals("1")) {
		    // 자동차사업부
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "1", false)).append("     \n");
		} else {
		    // 전자사업부
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "0", false)).append("     \n");
		}
	    }
	    // 개발담당자
	    if (!map.getString("setPm").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTMEMBERLINK WHERE PJTMEMBERTYPE = '0' AND IDA3B5",
		                KETParamMapUtil.OidToString(map.getString("setPm")), false)).append(" )    \n");
	    }
	    // 상태
	    if (!map.getString("pjtState").equals("")) {
		if (map.getString("pjtState").equals("UNDERREVIEW")) {
		    Hashtable h = ProjectHelper.APPROVALSTATE;
		    Enumeration e = h.keys();

		    StringBuilder strBuilder = new StringBuilder();
		    int i = 0;
		    while (e.hasMoreElements()) {
			if (i > 0)
			    strBuilder.append(", ");
			strBuilder.append((String) e.nextElement());
			i++;
		    }

		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", strBuilder.toString(), false))
			    .append("     \n");
		} else if (map.getString("pjtState").equals("delay")) {
		    sb.append("    AND PJT.STATESTATE = 'PROGRESS' \n");
		    sb.append("    AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY + "' \n");
		} else {

		    if (map.getString("pjtState").indexOf("delay") > -1) {
			sb.append("    AND (")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append(" OR (PJT.STATESTATE = 'PROGRESS' AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY
			                + "'))    \n");
		    } else {
			sb.append("    AND ")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append("     \n");
		    }
		}
	    }
	    // 제품구분
	    if (!map.getString("productType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3A9)", map.getString("productType"), false))
		        .append("     \n");
	    }
	    // 개발구분
	    if (!map.getString("developType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3D9)", map.getString("developType"), false))
		        .append("     \n");
	    }
	    // Project Name
	    if (!map.getString("pjtName").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNAME",
		                StringEscapeUtils.escapeSql(map.getString("pjtName")) + "*", true)).append("     \n");
	    }
	    // 개발담당부서
	    if (!map.getString("rdevDeptOid").equals("")) {
		// sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTVIEWDEPARTMENTLINK WHERE IDA3B5",
		// KETParamMapUtil.OidToString(map.getString("rdevDeptOid")), false)).append(" )    \n");

		// ******** PROJECTVIEWDEPARTMENTLINK는 검토프로젝트 데이터가 없으므로 주석처리후 담당부서 AND 조건 쿼리문 변경함 2013.09.11 황정태 ***********
		// String department[] = map.getString("rdevDeptOid").split(":");
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.IDA3A10",
		                KETParamMapUtil.OidToString(map.getString("rdevDeptOid")), false)).append("     \n");
	    }
	    // 개발시작일 Type
	    if (!map.getString("developDateType").equals("")) {
		// 개발시작일
		if (map.getString("developDateType").contains("DEVELOPDATESTART")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 개발완료일
		if (map.getString("developDateType").contains("DEVELOPDATEEND")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}

		// 실제시작일
		if (map.getString("developDateType").contains("STARTDATE")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 실제완료일
		if (map.getString("developDateType").contains("ENDDATE")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
	    }
	    // 조립구분
	    if (!map.getString("assembledType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3C9)", map.getString("assembledType"),
		                false)).append("     \n");
	    }
	    // 최종사용처
	    if (!map.getString("customerEvent").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PJT.IDA2A2 IN (SELECT IDA3B5 FROM PROJECTCUSTOMEREVENTLINK WHERE IDA3A5", map.getString("customerEvent"),
		                false)).append(" )    \n");
	    }
	    // 고객처
	    if (!map.getString("subcontractor").equals("")) {
		sb.append("    AND PJT.IDA2A2 IN (SELECT IDA3B5                                              \n");
		sb.append("                        FROM PROJECTSUBCONTRACTORLINK                             \n");
		sb.append("                       WHERE 1=1                                                  \n");
		sb.append("                         AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODEVALUE_OID('SUBCONTRACTOR',IDA3A5,'ko')",
		                map.getString("subcontractor"), true)).append("    \n");
		sb.append("                    )                                                             \n");
	    }
	    // Rank
	    if (!map.getString("rank").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3B9)", map.getString("rank"), false))
		        .append("     \n");
	    }
	}

	// Kogger.debug(getClass(), sb.toString());
	return sb.toString();
    }

    public List<Map<String, Object>> searchProdProjectList(List<Map<String, String>> conditionList, TgPagingControl pager) throws Exception {

	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String query = oDaoManager.getOrderByQuery(this.searchProdProjectQuery(conditionList), pager);
	query = oDaoManager.getPagingQuery(query, pager);
	returnObjList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
	    @Override
	    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		returnObj = new HashMap<String, Object>();
		returnObj.put("pjtOid", rs.getString("OID"));
		returnObj.put("pjtNo", rs.getString("PJTNO"));
		returnObj.put("pjtName", rs.getString("PJTNAME"));
		returnObj.put("buyer", rs.getString("BUYER"));
		returnObj.put("carName", rs.getString("CARNAME"));
		returnObj.put("planStartDate", rs.getString("PLANSTARTDATE"));
		returnObj.put("planEndDate", rs.getString("PLANENDDATE"));
		returnObj.put("state", rs.getString("STATESTATE"));
		returnObj.put("pjtState", rs.getString("PJTSTATE"));
		returnObj.put("eco", rs.getString("ECO"));
		returnObj.put("obtainType", rs.getString("OBTAINTYPE"));
		returnObj.put("PgmP1", rs.getString("PgmP1"));
		returnObj.put("PgmP2", rs.getString("PgmP2"));
		returnObj.put("PgmSop", rs.getString("PgmSop"));
		returnObj.put("P1", rs.getString("P1"));
		returnObj.put("P2", rs.getString("P2"));
		returnObj.put("Sop", rs.getString("SOP"));
		returnObj.put("PmUser", rs.getString("PmUser"));
		returnObj.put("PmDept", rs.getString("PmDept"));

		return returnObj;
	    }
	});

	return returnObjList;
    }

    public int searchProdProjectCount(List<Map<String, String>> conditionList) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	return oDaoManager.queryForCount(this.searchProdProjectQuery(conditionList));
    }

    @SuppressWarnings("rawtypes")
    private String searchProdProjectQuery(List<Map<String, String>> conditionList) throws Exception {
	StringBuffer sb = null;
	sb = new StringBuffer();
	sb.append(" SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2               AS OID                         \n");
	sb.append("       ,E3PSPJTMST.PJTNO                                 AS PJTNO                       \n");
	sb.append("       ,E3PSPJTMST.PJTNAME                               AS PJTNAME                     \n");
	sb.append("       ,(SELECT TO_CHAR(WM_CONCAT(FN_GET_NUMBERCODEVALUE_OID('SUBCONTRACTOR', IDA3A5, 'ko')))    \n");
	sb.append("           FROM PROJECTSUBCONTRACTORLINK                                                \n");
	sb.append("          WHERE IDA3B5 = PJT.IDA2A2                                                     \n");
	sb.append("          GROUP BY IDA3B5)                               AS BUYER                       \n");
	sb.append("       ,CAR.NAME                                         AS CARNAME                     \n");
	sb.append("       ,SCHEDULE.PLANSTARTDATE                           AS PLANSTARTDATE               \n");
	sb.append("       ,SCHEDULE.PLANENDDATE                             AS PLANENDDATE                 \n");
	sb.append("       ,PJT.STATESTATE                                   AS STATESTATE                  \n");
	sb.append("       ,PJT.PJTSTATE                                     AS PJTSTATE                    \n");
	sb.append("       ,(SELECT COUNT(*)                                                                \n");
	sb.append("           FROM KETPRODCHANGEORDER                                                      \n");
	sb.append("          WHERE PROJECTOID = 'e3ps.project.ProductProject:' || PJT.IDA2A2               \n");
	sb.append("         )                                               AS ECO                         \n");
	sb.append("      ,FN_GET_NUMBERCODENAME('OBTAINORDERTYPE',PJT.IDA3P9) AS OBTAINTYPE                \n");
	sb.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '3', '') as PgmP1				   \n");
	sb.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '4', '') as Pgmp2               		   \n");
	sb.append("      ,FN_GET_CONTACT_PLANDATE(PJT.IDA2A2, '0', 'SOP') as PgmSop			   \n");
	// sb.append("      ,TO_CHAR(TO_DATE(FN_GET_OEMPLAN_OEMENDDATE (MP.ida2a2, '3')),'YYYY-MM-DD') P1	   \n");
	// sb.append("      ,TO_CHAR(TO_DATE(FN_GET_OEMPLAN_OEMENDDATE (MP.ida2a2, '4')),'YYYY-MM-DD') P2	   \n");
	// sb.append("      ,TO_CHAR(TO_DATE(FN_GET_OEMPLAN_OEMENDDATE_SOP (MP.ida2a2)),'YYYY-MM-DD') SOP 	   \n");
	sb.append("      ,(SELECT TO_CHAR(OEMENDDATE,'YYYY-MM-DD')		\n");
	sb.append("          FROM OEMPLAN					\n");
	sb.append("         WHERE IDA3B3 = MP.ida2a2				\n");
	sb.append("           AND VIEWTYPE = '3') AS P1				\n");
	sb.append("      ,(SELECT TO_CHAR(OEMENDDATE,'YYYY-MM-DD')		\n");
	sb.append("          FROM OEMPLAN					\n");
	sb.append("         WHERE IDA3B3 = MP.ida2a2				\n");
	sb.append("           AND VIEWTYPE = '4') AS P2				\n");
	sb.append("      ,(SELECT TO_CHAR(OP.OEMENDDATE,'YYYY-MM-DD')		\n");
	sb.append("          FROM OEMPLAN        OP				\n");
	sb.append("              ,OEMPROJECTTYPE OEM				\n");
	sb.append("         WHERE OP.IDA3A3 = OEM.IDA2A2(+)			\n");
	sb.append("           AND OEM.NAME = 'SOP'				\n");
	sb.append("           AND IDA3B3 = MP.ida2a2) AS SOP			\n");
	sb.append("      ,PDATA.PM AS PmUser 								   \n");
	sb.append("      ,PDATA.PmDept 									   \n");
	sb.append("   FROM PRODUCTPROJECT      PJT                                                         \n");
	sb.append("       ,E3PSPROJECTMASTER   E3PSPJTMST                                                  \n");
	sb.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                                                    \n");
	sb.append("       ,OEMPROJECTTYPE      CAR                                                         \n");
	sb.append("       ,MODELPLAN MP									   \n");
	sb.append("       ,(SELECT WT.FULLNAME AS PM, DEPT.NAME PMDEPT, PML.IDA3A5 			   \n");
	sb.append("           FROM PROJECTMEMBERLINK PML , WTUSER WT, PEOPLE PE, DEPARTMENT DEPT 	   \n");
	sb.append("          WHERE PML.IDA3B5 = WT.IDA2A2(+) 						   \n");
	sb.append("            AND WT.IDA2A2 = PE.IDA3B4 						   \n");
	sb.append("            AND PE.IDA3C4 = DEPT.IDA2A2						   \n");
	sb.append("            AND PML.PJTMEMBERTYPE = 0) PDATA						   \n");
	sb.append("  WHERE 1=1                                                                             \n");
	sb.append("    AND PJT.LASTEST       = 1                                                           \n");
	sb.append("    AND PJT.CHECKOUTSTATE <> 'c/o'                                                      \n");
	sb.append("    AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                           \n");
	sb.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                             \n");
	sb.append("    AND PJT.IDA3D8        = CAR.IDA2A2(+)                                               \n");
	sb.append("    AND CAR.IDA2A2 = MP.IDA3B3(+) 							   \n");
	sb.append("    AND PJT.IDA2A2 = PDATA.IDA3A5(+)   						   \n");

	for (Map<String, String> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
	    // Project No
	    if (!map.getString("pjtNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNO", map.getString("pjtNo"), true))
		        .append("     \n");
	    }
	    // 사업부
	    if (!map.getString("dType").equals("")) {
		if (map.getString("dType").equals("1")) {
		    // 자동차사업부
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "2", false)).append("     \n");
		} else if (map.getString("dType").equals("2")) {
		    // 전자사업부
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "4", false)).append("     \n");
		} else if (map.getString("dType").equals("3")) {
		    // KETS
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "5", false)).append("     \n");
		}
	    }
	    // 개발담당자
	    if (!map.getString("setPm").equals("")) {
		sb.append("    AND ").append(
		        "PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTMEMBERLINK WHERE PJTMEMBERTYPE = '0' AND IDA3B5 ="
		                + CommonUtil.getOIDLongValue2Str(map.getString("setPm") + ")\n"));
	    }
	    // 제품구분
	    if (!map.getString("productType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3A9)", map.getString("productType"), false))
		        .append("     \n");
	    }
	    // 관리제품군
	    if (!map.getString("manageProductType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.idA3O9)", map.getString("manageProductType"),
		                false)).append("     \n");
	    }
	    // 개발목적1
	    if (!map.getString("developePurpose1").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.idA3M9)", map.getString("developePurpose1"),
		                false)).append("     \n");
	    }
	    // 개발목적2
	    if (!map.getString("developePurpose2").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.idA3N9)", map.getString("developePurpose2"),
		                false)).append("     \n");
	    }

	    // 개발단계
	    if (!map.getString("process").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(idA3F9)", map.getString("process"), false))
		        .append("     \n");
	    }
	    // 제품구분 Level2
	    if (!map.getString("productTypeLevel2").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PRODUCTTYPELEVEL2", map.getString("productTypeLevel2"), false))
		        .append("     \n");
	    }
	    // 시리즈
	    if (!map.getString("series").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.SERIES", map.getString("series"), false))
		        .append("     \n");
	    }
	    // 방수여부
	    if (!map.getString("sealed").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.WATERPOOF", map.getString("sealed"), false))
		        .append("     \n");
	    }
	    // Part No
	    if (!map.getString("partNo").equals("")) {
		sb.append("    AND PJT.IDA2A2 IN ( ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM",
		                map.getString("partNo"), false))
		        .append("  UNION ALL ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("SELECT IDA3A3 FROM MOLDITEMINFO WHERE PARTNO",
		                map.getString("partNo"), false)).append(" )  \n");
	    }
	    // 상태
	    if (!map.getString("pjtState").equals("")) {
		if (map.getString("pjtState").equals("UNDERREVIEW")) {
		    Hashtable h = ProjectHelper.APPROVALSTATE;
		    Enumeration e = h.keys();

		    StringBuilder strBuilder = new StringBuilder();
		    int i = 0;
		    while (e.hasMoreElements()) {
			if (i > 0)
			    strBuilder.append(", ");
			strBuilder.append((String) e.nextElement());
			i++;
		    }

		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", strBuilder.toString(), false))
			    .append("     \n");
		} else if (map.getString("pjtState").equals("delay")) {
		    sb.append("    AND PJT.STATESTATE = 'PROGRESS' \n");
		    sb.append("    AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY + "' \n");
		} else {

		    if (map.getString("pjtState").indexOf("delay") > -1) {
			sb.append("    AND (")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append(" OR (PJT.STATESTATE = 'PROGRESS' AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY
			                + "'))    \n");
		    } else {
			sb.append("    AND ")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append("     \n");
		    }
		}
	    }
	    // 개발담당부서
	    if (!map.getString("pdevDeptOid").equals("")) {
		sb.append("    AND ")
		        .append("PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTVIEWDEPARTMENTLINK WHERE IDA3B5 in ("
		                + KETParamMapUtil.OidToString(map.getString("pdevDeptOid"))).append(") )    \n");
	    }
	    // Project Name
	    if (!map.getString("pjtName").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNAME",
		                StringEscapeUtils.escapeSql(map.getString("pjtName")) + "*", true)).append("     \n");
	    }
	    // 개발구분
	    if (!map.getString("developType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3D9)", map.getString("developType"), false))
		        .append("     \n");
	    }
	    // 최종사용처
	    if (!map.getString("customerEvent").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PJT.IDA2A2 IN (SELECT IDA3B5 FROM PROJECTCUSTOMEREVENTLINK WHERE IDA3A5", map.getString("customerEvent"),
		                false)).append(" )    \n");
	    }
	    // 개발시작일 Type
	    if (!map.getString("developDateType").equals("")) {
		// 개발시작일
		if (map.getString("developDateType").contains("DEVELOPDATESTART")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 개발완료일
		if (map.getString("developDateType").contains("DEVELOPDATEEND")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 실제시작일
		if (map.getString("developDateType").contains("STARTDATE")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 실제완료일
		if (map.getString("developDateType").contains("ENDDATE")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
	    }
	    // 조립구분
	    if (!map.getString("assembledType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3C9)", map.getString("assembledType"),
		                false)).append("     \n");
	    }
	    // Rank
	    if (!map.getString("rank").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3B9)", map.getString("rank"), false))
		        .append("     \n");
	    }
	    // 고객처
	    if (!map.getString("subcontractor").equals("")) {
		sb.append("    AND PJT.IDA2A2 IN (SELECT IDA3B5                                              \n");
		sb.append("                        FROM PROJECTSUBCONTRACTORLINK                             \n");
		sb.append("                       WHERE 1=1                                                  \n");
		sb.append("                         AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODEVALUE_OID('SUBCONTRACTOR',IDA3A5,'ko')",
		                map.getString("subcontractor"), true)).append("    \n");
		sb.append("                    )                                                             \n");
	    }
	    // 대표차종
	    if (!map.getString("carTypeInfo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CAR.NAME", map.getString("carTypeInfo"), true))
		        .append("     \n");
	    }

	    // 설계구분
	    if (!map.getString("designType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3G9)", map.getString("designType"), false))
		        .append("     \n");
	    }
	    // 개발유형
	    if (!map.getString("developPatternType").equals("")) {
		sb.append("    AND REGEXP_LIKE(PJT.DEVELOPEDTYPE,'" + map.getString("developPatternType") + "')                 \n");
	    }
	    // 생산처 manufacPlace
	    if (!map.getString("manufacPlace").equals("")) {
		sb.append("    AND REGEXP_LIKE(PJT.manufacPlace,'" + map.getString("manufacPlace") + "')                 \n");
	    }
	    // 파생차종
	    if (!map.getString("oemOids").equals("")) {
		String[] oemOids = map.getString("oemOids").split(",");

		String longOids = "";
		for (String oemOid : oemOids)
		    longOids += String.valueOf(CommonUtil.getOIDLongValue(oemOid)) + ",";
		longOids = StringUtils.removeEnd(longOids, ",");

		sb.append(" AND PJT.IDA2A2 IN (SELECT IDA3B5 FROM PROJECTOEMTYPELINK WHERE IDA3A5 IN(" + longOids + "))");

		// sb.append("    AND REGEXP_LIKE(PJT.DEVELOPEDTYPE,'" + map.getString("developPatternType") + "')                 \n");
	    }
	}

	// Kogger.debug(getClass(), sb.toString());
	return sb.toString();
    }

    public List<Map<String, Object>> searchWorkProjectList(List<Map<String, String>> conditionList, TgPagingControl pager) throws Exception {

	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String query = oDaoManager.getOrderByQuery(this.searchWorkProjectQuery(conditionList), pager);
	query = oDaoManager.getPagingQuery(query, pager);
	returnObjList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
	    @Override
	    public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		returnObj = new HashMap<String, Object>();
		returnObj.put("pjtOid", rs.getString("OID"));
		returnObj.put("pjtNo", rs.getString("PJTNO"));
		returnObj.put("pjtName", rs.getString("PJTNAME"));
		returnObj.put("planStartDate", rs.getString("PLANSTARTDATE"));
		returnObj.put("planEndDate", rs.getString("PLANENDDATE"));
		returnObj.put("state", rs.getString("STATESTATE"));
		returnObj.put("pjtState", rs.getString("PJTSTATE"));
		returnObj.put("PmUser", rs.getString("PmUser"));
		returnObj.put("PmDept", rs.getString("PmDept"));

		return returnObj;
	    }
	});

	return returnObjList;
    }

    public int searchWorkProjectCount(List<Map<String, String>> conditionList) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	return oDaoManager.queryForCount(this.searchWorkProjectQuery(conditionList));
    }

    @SuppressWarnings("rawtypes")
    private String searchWorkProjectQuery(List<Map<String, String>> conditionList) throws Exception {
	StringBuffer sb = null;
	sb = new StringBuffer();
	sb.append(" SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2               AS OID                         \n");
	sb.append("       ,E3PSPJTMST.PJTNO                                 AS PJTNO                       \n");
	sb.append("       ,E3PSPJTMST.PJTNAME                               AS PJTNAME                     \n");
	sb.append("       ,SCHEDULE.PLANSTARTDATE                           AS PLANSTARTDATE               \n");
	sb.append("       ,SCHEDULE.PLANENDDATE                             AS PLANENDDATE                 \n");
	sb.append("       ,PJT.STATESTATE                                   AS STATESTATE                  \n");
	sb.append("       ,PJT.PJTSTATE                                     AS PJTSTATE                    \n");
	sb.append("      ,PDATA.PM AS PmUser 								   \n");
	sb.append("      ,PDATA.PmDept 									   \n");
	sb.append("   FROM WORKPROJECT      PJT                                                         \n");
	sb.append("       ,E3PSPROJECTMASTER   E3PSPJTMST                                                  \n");
	sb.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                                                    \n");
	sb.append("       ,(SELECT WT.FULLNAME AS PM, DEPT.NAME PMDEPT, PML.IDA3A5 			   \n");
	sb.append("           FROM PROJECTMEMBERLINK PML , WTUSER WT, PEOPLE PE, DEPARTMENT DEPT 	   \n");
	sb.append("          WHERE PML.IDA3B5 = WT.IDA2A2(+) 						   \n");
	sb.append("            AND WT.IDA2A2 = PE.IDA3B4 						   \n");
	sb.append("            AND PE.IDA3C4 = DEPT.IDA2A2						   \n");
	sb.append("            AND PML.PJTMEMBERTYPE = 0) PDATA						   \n");
	sb.append("  WHERE 1=1                                                                             \n");
	sb.append("    AND PJT.LASTEST       = 1                                                           \n");
	sb.append("    AND PJT.CHECKOUTSTATE <> 'c/o'                                                      \n");
	sb.append("    AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                           \n");
	sb.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                             \n");
	sb.append("    AND PJT.IDA2A2 = PDATA.IDA3A5(+)   						   \n");

	for (Map<String, String> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
	    // Project No
	    if (!map.getString("pjtNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNO", map.getString("pjtNo"), true))
		        .append("     \n");
	    }
	    // 개발담당자
	    if (!map.getString("setPm").equals("")) {
		sb.append("    AND ").append(
		        "PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTMEMBERLINK WHERE PJTMEMBERTYPE = '0' AND IDA3B5 ="
		                + CommonUtil.getOIDLongValue2Str(map.getString("setPm") + ")\n"));
	    }
	    // 제품구분
	    if (!map.getString("productType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3A9)", map.getString("productType"), false))
		        .append("     \n");
	    }
	    // 상태
	    if (!map.getString("pjtState").equals("")) {
		if (map.getString("pjtState").equals("UNDERREVIEW")) {
		    Hashtable h = ProjectHelper.APPROVALSTATE;
		    Enumeration e = h.keys();

		    StringBuilder strBuilder = new StringBuilder();
		    int i = 0;
		    while (e.hasMoreElements()) {
			if (i > 0)
			    strBuilder.append(", ");
			strBuilder.append((String) e.nextElement());
			i++;
		    }

		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", strBuilder.toString(), false))
			    .append("     \n");
		} else if (map.getString("pjtState").equals("delay")) {
		    sb.append("    AND PJT.STATESTATE = 'PROGRESS' \n");
		    sb.append("    AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY + "' \n");
		} else {

		    if (map.getString("pjtState").indexOf("delay") > -1) {
			sb.append("    AND (")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append(" OR (PJT.STATESTATE = 'PROGRESS' AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY
			                + "'))    \n");
		    } else {
			sb.append("    AND ")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append("     \n");
		    }
		}
	    }
	    // 개발담당부서
	    if (!map.getString("pdevDeptOid").equals("")) {

		long deptOid = CommonUtil.getOIDLongValue(map.getString("pdevDeptOid"));
		sb.append("    AND PJT.idA3A10 = " + deptOid + "\n");
	    }

	    // Project Name
	    if (!map.getString("pjtName").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNAME",
		                StringEscapeUtils.escapeSql(map.getString("pjtName")) + "*", true)).append("     \n");
	    }

	    // 개발시작일 Type
	    if (!map.getString("developDateType").equals("")) {
		// 개발시작일
		if (map.getString("developDateType").contains("DEVELOPDATESTART")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 개발완료일
		if (map.getString("developDateType").contains("DEVELOPDATEEND")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 실제시작일
		if (map.getString("developDateType").contains("STARTDATE")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 실제완료일
		if (map.getString("developDateType").contains("ENDDATE")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.EXECENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
	    }

	    // Rank
	    if (!map.getString("rank").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3B9)", map.getString("rank"), false))
		        .append("     \n");
	    }

	    // workType
	    if (!map.getString("workType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3B10)", map.getString("workType"), false))
		        .append("     \n");
	    }
	}

	// Kogger.debug(getClass(), sb.toString());
	return sb.toString();
    }

    /**
     * 프로젝트 개발원가/수익율 조회
     * 
     * @param paramMap
     * @param pager
     * @return
     * @throws Exception
     * @메소드명 : searchProdProjectList4Cost
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<KETCostDTO> searchProdProjectList4Cost(KETParamMapUtil paramMap, TgPagingControl pager) throws Exception {
	List<KETCostDTO> returnObjList = new ArrayList<KETCostDTO>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String query = oDaoManager.getOrderByQuery(this.searchProdProjectQuery4Cost(paramMap), pager);
	// query = oDaoManager.getPagingQuery(query, pager);
	returnObjList = oDaoManager.queryForList(query, new RowSetStrategy<KETCostDTO>() {
	    @Override
	    public KETCostDTO mapRow(ResultSet rs) throws SQLException {
		KETCostDTO returnObj = new KETCostDTO();
		returnObj.setProjectOid(rs.getString("OID"));
		returnObj.setProjectNo(rs.getString("PROJECTNO"));
		returnObj.setProjectName(rs.getString("PROJECTNAME"));
		returnObj.setSubContractor(rs.getString("SUBCONTRACTOR"));
		returnObj.setCarType(rs.getString("CARTYPE"));
		returnObj.setState(State.toState(rs.getString("STATE").toString()).getDisplay());
		returnObj.setRevision(rs.getString("REVISION"));
		returnObj.setProduction(rs.getString("PRODUCTION"));
		returnObj.setSalesTargetCost(rs.getString("SALESTARGETCOST"));
		returnObj.setClassification(rs.getString("CLASSIFICATION"));
		returnObj.setTotalCost(rs.getString("TOTALCOST"));
		returnObj.setProfitRate(rs.getString("PROFITRATE"));
		returnObj.setUrl(rs.getString("URL"));
		returnObj.setSuyearSum(rs.getString("SUYEARSUM"));
		returnObj.setTotalsalesSum(rs.getString("TOTALSALESSUM"));
		returnObj.setDevelopedType(rs.getString("DEVELOPEDTYPE"));
		returnObj.setDrStep(rs.getString("DR_STEP"));
		return returnObj;
	    }
	});

	return returnObjList;
    }

    /**
     * 프로젝트 개발원가/수익율 조회
     * 
     * @param paramMap
     * @return
     * @throws Exception
     * @메소드명 : searchProdProjectList4CostCount
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public int searchProdProjectList4CostCount(KETParamMapUtil paramMap) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	return oDaoManager.queryForCount(this.searchProdProjectQuery4Cost(paramMap));
    }

    /**
     * 프로젝트 개발원가/수익율 조회 쿼리
     * 
     * @param map
     * @return
     * @throws Exception
     * @메소드명 : searchProdProjectQuery4Cost
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @SuppressWarnings("rawtypes")
    private String searchProdProjectQuery4Cost(KETParamMapUtil map) throws Exception {
	StringBuffer sb = null;
	sb = new StringBuffer();
	// paging 쿼리인경우
	if (!map.getString("offSet").equals("")) {
	    sb.append("    SELECT * FROM (   								   \n");
	}
	sb.append(" SELECT ROWNUM rnum, PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2               AS OID            \n");
	sb.append("       ,E3PSPJTMST.PJTNO                                 AS PROJECTNO                   \n");
	sb.append("       ,E3PSPJTMST.PJTNAME                               AS PROJECTNAME                 \n");
	sb.append("       ,(SELECT TO_CHAR(WM_CONCAT(FN_GET_NUMBERCODEVALUE_OID('SUBCONTRACTOR', IDA3A5, 'ko')))    \n");
	sb.append("           FROM PROJECTSUBCONTRACTORLINK                                                \n");
	sb.append("          WHERE IDA3B5 = PJT.IDA2A2                                                     \n");
	sb.append("          GROUP BY IDA3B5)                               AS SUBCONTRACTOR               \n");
	sb.append("       ,CAR.NAME                                         AS CARTYPE                     \n");
	sb.append("       ,SCHEDULE.PLANSTARTDATE                           AS PLANSTARTDATE               \n");
	sb.append("       ,SCHEDULE.PLANENDDATE                             AS PLANENDDATE                 \n");
	sb.append("       ,PJT.STATESTATE                                   AS STATE	                   \n");
	sb.append("       ,PJT.PJTSTATE                                     AS PJTSTATE                    \n");
	sb.append("       ,COST.REVISION                                    AS REVISION                    \n");
	sb.append("       ,COST.PRODUCTION                                  AS PRODUCTION                  \n");
	sb.append("       ,COST.SALESTARGETCOST                             AS SALESTARGETCOST             \n");
	sb.append("       ,COST.CLASSIFICATION                              AS CLASSIFICATION              \n");
	sb.append("       ,COST.TOTALCOST                                   AS TOTALCOST                   \n");
	sb.append("       ,COST.PROFITRATE                                  AS PROFITRATE                  \n");
	sb.append("       ,COST.URL                                         AS URL                         \n");
	sb.append("       ,COST.SUYEARSUM                                   AS SUYEARSUM                   \n");
	sb.append("       ,COST.TOTALSALESSUM                               AS TOTALSALESSUM               \n");
	sb.append("       ,(SELECT LISTAGG(NAME, ', ') WITHIN GROUP (ORDER BY SORTING) NAME                                             \n");
	sb.append("	      FROM NUMBERCODE                                                                                           \n");
	sb.append("          WHERE CODE IN (SELECT REGEXP_SUBSTR(DEVELOPEDTYPE, '[^|]+', 1, LEVEL) AS VAL FROM DUAL                     \n");
	sb.append("                         CONNECT BY REGEXP_SUBSTR(DEVELOPEDTYPE, '[^|]+', 1, LEVEL) IS NOT NULL )) AS DEVELOPEDTYPE  \n");
	sb.append("       ,COST.DR_STEP                                     AS DR_STEP                     \n");
	sb.append("   FROM PRODUCTPROJECT      PJT                                                         \n");
	sb.append("       ,E3PSPROJECTMASTER   E3PSPJTMST                                                  \n");
	sb.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                                                    \n");
	sb.append("       ,OEMPROJECTTYPE      CAR                                                         \n");
	sb.append("	  ,(SELECT A.REV_NO AS REVISION																      \n ");
	sb.append("	      ,TO_CHAR (C.F_DAY, 'YYYY-MM-DD') AS REVIEWCOMPLETEDATE												      \n ");
	sb.append("	      ,A.PJT_NO AS PJT_NO												    	 			      \n ");
	sb.append("	      ,A.DR_STEP REVIEWSTEP																      \n ");
	sb.append("	      ,PART_NAME PARTNAME																      \n ");
	sb.append("	      ,(SELECT SUM(PRO_USAGE*KET_COST) FROM COST_REPORT WHERE CRP_GROUP = B.PK_CRP) AS SALESTARGETCOST                                                        \n ");
	sb.append("	      ,B.PRO_1 AS PRODUCTION																      \n ");
	sb.append("	      ,DECODE (NVL (B.SELECT_COST, '1'),  '1', B.CASE_1_NOTE,  '2', CASE_2_NOTE,  '3', CASE_3_NOTE) AS CLASSIFICATION					             \n ");
	sb.append("	      ,NVL(REPLACE (DECODE (NVL (B.SELECT_COST, '1'),  '1', B.ACTUAL_COST_SUM_1,  '2', B.ACTUAL_COST_SUM_2,  '3', B.ACTUAL_COST_SUM_3), ',', ''),0) AS TOTALCOST     \n ");
	sb.append("	      ,NVL(REPLACE (DECODE (NVL (B.SELECT_COST, '1'),  '1', B.EARN_RATE_SUM_1,  '2', B.EARN_RATE_SUM_2,  '3', B.EARN_RATE_SUM_3), ',', ''),0) AS PROFITRATE	     \n ");
	sb.append("	      ,NOTE_3 AS MAINISSUE																             \n ");
	sb.append("	      ,W_NAME AS CREATOR																      \n ");
	sb.append("	      ,DECODE (STEP_NO,  '2', '작성중',  '6', '승인완료',  '결재중') AS STATE										      \n ");
	sb.append("	      ,CASE																		      \n ");
	sb.append("		   WHEN INPUT_GB = '1'																      \n ");
	sb.append("		    AND NVL (DIVISION, '1') = '1' THEN														      \n ");
	sb.append("		       '/plm/jsp/cost/costreport/cost_report_1.jsp?report_pk=' || CRP_GROUP || '&' || 'table_row=' || TABLE_ROW					      \n ");
	sb.append("		   WHEN INPUT_GB = '1'																      \n ");
	sb.append("		    AND NVL (DIVISION, '1') = '2' THEN														      \n ");
	sb.append("		       '/plm/jsp/cost/costreport/cost_report_add_temp.jsp?report_pk=' || CRP_GROUP || '&' || 'table_row=' || TABLE_ROW                                \n ");
	sb.append("		   ELSE																		      \n ");
	sb.append("		       '/plm/jsp/cost/costreport/cost_report_1.jsp?report_pk=' || CRP_GROUP || '&' || 'table_row=' || TABLE_ROW					      \n ");
	sb.append("	       END																		      \n ");
	sb.append("		   AS URL																	      \n ");
	sb.append("          ,To_char ( NVL(B.SU_YEAR_1,0)+NVL(B.SU_YEAR_2,0)+NVL(B.SU_YEAR_3,0)+NVL(B.SU_YEAR_4,0)+NVL(B.SU_YEAR_5,0)+NVL(B.SU_YEAR_6,0)+NVL(B.SU_YEAR_7,0)+NVL(B.SU_YEAR_8,0) , 'Fm999,999,999,990' ) AS SUYEARSUM                       \n ");
	sb.append("          ,To_char ( NVL(TOTAL_SALES_1,0)+NVL(TOTAL_SALES_2,0)+NVL(TOTAL_SALES_3,0)+NVL(TOTAL_SALES_4,0)+NVL(TOTAL_SALES_5,0)+NVL(TOTAL_SALES_6,0)+NVL(TOTAL_SALES_7,0)+NVL(TOTAL_SALES_8,0) , 'Fm999,999,999,990' ) AS TOTALSALESSUM   \n ");
	sb.append("          ,A.DR_STEP      															                      \n ");
	sb.append("	  FROM COST_PRODUCTINFO A																      \n ");
	sb.append("	      ,COST_REPORT B																	      \n ");
	sb.append("	      ,WFINFO C																		      \n ");
	sb.append("	      ,COST_REQUEST D																	      \n ");
	sb.append("	 WHERE A.PK_PID = B.FK_PID																      \n ");
	sb.append("	   AND B.FK_WID = C.PK_WID																      \n ");
	sb.append("	   AND A.PK_PID = D.FK_PID(+)																      \n ");
	sb.append("	   AND A.REPORT_PK = CRP_GROUP(+)															      \n ");
	sb.append("	   AND STEP_NO = '6'															                      \n ");
	sb.append("	   AND A.GROUP_NO = 'T001') COST															      \n ");
	sb.append("  WHERE 1=1                                                                             \n");
	sb.append("    AND PJT.LASTEST       = 1                                                           \n");
	sb.append("    AND PJT.CHECKOUTSTATE <> 'c/o'                                                      \n");
	sb.append("    AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                           \n");
	sb.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                             \n");
	sb.append("    AND PJT.IDA3D8        = CAR.IDA2A2(+)                                               \n");
	sb.append("    AND E3PSPJTMST.PJTNO  = COST.PJT_NO                                                 \n");

	// Project No
	if (!map.getString("pjtNo").equals("")) {
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNO", map.getString("pjtNo"), true))
		    .append("     \n");
	}
	// 사업부
	if (!map.getString("dType").equals("")) {
	    if (map.getString("dType").equals("1")) {
		// 자동차사업부
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "2", false)).append("     \n");
	    } else if (map.getString("dType").equals("2")) {
		// 전자사업부
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "4", false)).append("     \n");
	    } else if (map.getString("dType").equals("3")) {
		// KETS
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PJTTYPE", "5", false)).append("     \n");
	    }
	}
	// 개발담당자
	if (!map.getString("setPm").equals("")) {
	    sb.append("    AND ").append(
		    "PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTMEMBERLINK WHERE PJTMEMBERTYPE = '0' AND IDA3B5 ="
		            + CommonUtil.getOIDLongValue2Str(map.getString("setPm") + ")\n"));
	}
	// 제품구분
	if (!map.getString("productType").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3A9)", map.getString("productType"), false))
		    .append("     \n");
	}
	// 제품구분 Level2
	if (!map.getString("productTypeLevel2").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.PRODUCTTYPELEVEL2", map.getString("productTypeLevel2"), false))
		    .append("     \n");
	}
	// 시리즈
	if (!map.getString("series").equals("")) {
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.SERIES", map.getString("series"), false))
		    .append("     \n");
	}
	// 방수여부
	if (!map.getString("sealed").equals("")) {
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.WATERPOOF", map.getString("sealed"), false))
		    .append("     \n");
	}
	// Part No
	if (!map.getString("partNo").equals("")) {
	    sb.append("    AND PJT.IDA2A2 IN ( ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("SELECT IDA3A3 FROM PRODUCTINFO WHERE PNUM", map.getString("partNo"),
		            false))
		    .append("  UNION ALL ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("SELECT IDA3A3 FROM MOLDITEMINFO WHERE PARTNO", map.getString("partNo"),
		            false)).append(" )  \n");
	}
	// 상태
	if (!map.getString("pjtState").equals("")) {
	    if (map.getString("pjtState").equals("UNDERREVIEW")) {
		Hashtable h = ProjectHelper.APPROVALSTATE;
		Enumeration e = h.keys();

		StringBuilder strBuilder = new StringBuilder();
		int i = 0;
		while (e.hasMoreElements()) {
		    if (i > 0)
			strBuilder.append(", ");
		    strBuilder.append((String) e.nextElement());
		    i++;
		}

		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", strBuilder.toString(), false))
		        .append("     \n");
	    } else if (map.getString("pjtState").equals("delay")) {
		sb.append("    AND PJT.STATESTATE = 'PROGRESS' \n");
		sb.append("    AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY + "' \n");
	    } else {

		if (map.getString("pjtState").indexOf("delay") > -1) {
		    sb.append("    AND (")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			    .append(" OR (PJT.STATESTATE = 'PROGRESS' AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY
			            + "'))    \n");
		} else {
		    sb.append("    AND ")
			    .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			    .append("     \n");
		}
	    }
	}
	// 개발담당부서
	if (!map.getString("devUserDeptOid").equals("")) {
	    sb.append("    AND ")
		    .append("PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTVIEWDEPARTMENTLINK WHERE IDA3B5 IN ("
		            + KETParamMapUtil.OidToString(map.getString("devUserDeptOid"))).append(" ))    \n");
	}
	// Project Name
	if (!map.getString("pjtName").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("E3PSPJTMST.PJTNAME",
		            StringEscapeUtils.escapeSql(map.getString("pjtName")) + "*", true)).append("     \n");
	}
	// 개발구분
	if (!map.getString("developType").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3D9)", map.getString("developType"), false))
		    .append("     \n");
	}
	// 최종사용처
	if (!map.getString("customerEvent").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch(
		            "PJT.IDA2A2 IN (SELECT IDA3B5 FROM PROJECTCUSTOMEREVENTLINK WHERE IDA3A5", map.getString("customerEvent"),
		            false)).append(" )    \n");
	}
	// 개발시작일 Type
	if (!map.getString("developDateType").equals("")) {
	    // 개발시작일
	    if (map.getString("developDateType").contains("DEVELOPDATESTART")) {
		if (!map.getString("planStartStartDate").equals("")) {
		    String predate = map.getString("planStartStartDate");
		    predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		    sb.append("    AND SCHEDULE.PLANSTARTDATE >= TO_DATE('" + predate
			    + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		}
		if (!map.getString("planStartEndDate").equals("")) {
		    String postdate = map.getString("planStartEndDate");
		    postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		    sb.append("    AND SCHEDULE.PLANSTARTDATE < TO_DATE('" + postdate
			    + "' || '235959','YYYYMMDDHH24MISS')                \n");
		}
	    }
	    // 개발완료일
	    if (map.getString("developDateType").contains("DEVELOPDATEEND")) {
		if (!map.getString("planStartStartDate").equals("")) {
		    String predate = map.getString("planStartStartDate");
		    predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		    sb.append("    AND SCHEDULE.PLANENDDATE >= TO_DATE('" + predate
			    + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		}
		if (!map.getString("planStartEndDate").equals("")) {
		    String postdate = map.getString("planStartEndDate");
		    postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		    sb.append("    AND SCHEDULE.PLANENDDATE < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')                \n");
		}
	    }
	}
	// 조립구분
	if (!map.getString("assembledType").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3C9)", map.getString("assembledType"), false))
		    .append("     \n");
	}
	// Rank
	if (!map.getString("rank").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3B9)", map.getString("rank"), false))
		    .append("     \n");
	}
	// 고객처
	if (!map.getString("subcontractor").equals("")) {
	    sb.append("    AND PJT.IDA2A2 IN (SELECT IDA3B5                                              \n");
	    sb.append("                        FROM PROJECTSUBCONTRACTORLINK                             \n");
	    sb.append("                       WHERE 1=1                                                  \n");
	    sb.append("                         AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODEVALUE_OID('SUBCONTRACTOR',IDA3A5,'ko')",
		            map.getString("subcontractor"), true)).append("    \n");
	    sb.append("                    )                                                             \n");
	}
	// 대표차종
	if (!map.getString("carTypeInfo").equals("")) {
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("CAR.NAME", map.getString("carTypeInfo"), true))
		    .append("     \n");
	}

	// 설계구분
	if (!map.getString("designType").equals("")) {
	    sb.append("    AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.iDA3G9)", map.getString("designType"), false))
		    .append("     \n");
	}

	// 개발유형
	if (!map.getString("developPatternType").equals("")) {
	    sb.append("    AND REGEXP_LIKE(PJT.DEVELOPEDTYPE,'" + KETStringUtil.join(map.getStringArray("developPatternType"), "|")
		    + "')               \n");
	}

	// 생산처
	if (!map.getString("manufacPlace").equals("")) {
	    sb.append("    AND REGEXP_LIKE(PJT.manufacPlace,'" + KETStringUtil.join(map.getStringArray("manufacPlace"), "|")
		    + "')               \n");
	}

	// 최신
	if (!map.getString("revision").equals("")) {
	    sb.append("    AND ")
		    .append(" (COST.REVISION,COST.PJT_NO) IN (SELECT MAX (REV_NO), PJT_NO FROM COST_PRODUCTINFO WHERE pjt_no is not null GROUP BY PJT_NO)  \n");
	}
	// 작성자
	if (!map.getString("creator").equals("")) {
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("COST.CREATOR", map.getString("creator"), true))
		    .append("     \n");
	}
	// paging 쿼리인경우
	if (!map.getString("offSet").equals("")) {
	    sb.append(" ) WHERE rnum >= ").append(map.getInt("offSet")).append(" AND rnum < ").append(map.getInt("limit"));
	}
	// Kogger.debug(getClass(), sb.toString());
	return sb.toString();
    }

    public List<Map<String, Object>> searchMoldProjectList(List<Map<String, String>> conditionList, TgPagingControl pager) throws Exception {
	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();
	try {
	    DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	    String query = oDaoManager.getOrderByQuery(searchMoldProjectQuery(conditionList), pager);
	    query = oDaoManager.getPagingQuery(query, pager);
	    returnObjList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {
		    Map<String, Object> returnObj = new HashMap<String, Object>();
		    returnObj = new HashMap<String, Object>();
		    returnObj.put("pjtOid", rs.getString("OID"));
		    returnObj.put("dieNo", rs.getString("DIENO"));
		    returnObj.put("partNo", rs.getString("PARTNO"));
		    returnObj.put("partName", rs.getString("PARTNAME"));
		    returnObj.put("planStartDate", rs.getString("PLANSTARTDATE"));
		    returnObj.put("planEndDate", rs.getString("PLANENDDATE"));
		    returnObj.put("state", rs.getString("STATESTATE"));
		    returnObj.put("pjtState", rs.getString("PJTSTATE"));
		    returnObj.put("making", rs.getString("MAKING"));
		    returnObj.put("eco", rs.getString("ECO"));
		    returnObj.put("outSourcing", rs.getString("OUTSOURCING"));
		    return returnObj;
		}
	    });
	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	}
	return returnObjList;
    }

    public int searchMoldProjectCount(List<Map<String, String>> conditionList) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	return oDaoManager.queryForCount(this.searchMoldProjectQuery(conditionList));
    }

    @SuppressWarnings("rawtypes")
    private String searchMoldProjectQuery(List<Map<String, String>> conditionList) {
	StringBuffer sb = new StringBuffer();
	sb.append(" SELECT PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2                   AS OID                     \n");
	sb.append("       ,MOLDINFO.DIENO                                       AS DIENO                   \n");
	sb.append("       ,MOLDINFO.PARTNO                                      AS PARTNO                  \n");
	sb.append("       ,MOLDINFO.PARTNAME                                    AS PARTNAME                \n");
	sb.append("       ,SCHEDULE.PLANSTARTDATE                               AS PLANSTARTDATE           \n");
	sb.append("       ,SCHEDULE.PLANENDDATE                                 AS PLANENDDATE             \n");
	sb.append("       ,PJT.STATESTATE                                       AS STATESTATE              \n");
	sb.append("       ,PJT.PJTSTATE                                         AS PJTSTATE                \n");
	sb.append("       ,MOLDINFO.MAKING                                      AS MAKING                  \n");
	sb.append("       ,(SELECT COUNT(PROJECTOID)                                                       \n");
	sb.append("           FROM KETMOLDCHANGEORDER                                                      \n");
	sb.append("          WHERE PROJECTOID = PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2                         \n");
	sb.append("         )                                                   AS ECO                     \n");
	sb.append("       ,PJT.outSourcing                                      AS OUTSOURCING             \n");
	sb.append("   FROM MOLDPROJECT         PJT                                                         \n");
	sb.append("       ,MOLDITEMINFO        MOLDINFO                                                    \n");
	sb.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                                                    \n");
	sb.append("       ,PRODUCTPROJECT      PRODPJT                                                     \n");
	sb.append("       ,E3PSPROJECTMASTER   PRODPJTMST                                                  \n");
	sb.append("  WHERE 1=1                                                                             \n");
	sb.append("    AND PJT.LASTEST       = 1                                                           \n");
	sb.append("    AND PJT.CHECKOUTSTATE <> 'c/o'                                                      \n");
	// sb.append("    AND PJT.PJTTYPE       = '3'                                                         \n");
	sb.append("    AND PJT.IDA3A10       = MOLDINFO.IDA2A2                                             \n");
	sb.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                             \n");
	sb.append("    AND MOLDINFO.IDA3A3   = PRODPJT.IDA2A2                                              \n");
	sb.append("    AND PRODPJT.IDA3B8    = PRODPJTMST.IDA2A2                                           \n");

	for (Map<String, String> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);
	    // Die No
	    if (!map.getString("dieNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MOLDINFO.DIENO", map.getString("dieNo"), true))
		        .append("     \n");
	    }
	    // Project No
	    if (!map.getString("productpjtNo").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("PRODPJTMST.PJTNO", map.getString("productpjtNo"), true))
		        .append("     \n");
	    }
	    // Part No
	    if (!map.getString("partNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MOLDINFO.PARTNO", map.getString("partNo"), false))
		        .append("     \n");
	    }
	    // 개발구분
	    if (!map.getString("developType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PRODPJT.IDA3D9)", map.getString("developType"),
		                false)).append("     \n");
	    }
	    // Part Name
	    if (!map.getString("partName").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("MOLDINFO.PARTNAME",
		                StringEscapeUtils.escapeSql(map.getString("partName")), true)).append("     \n");
	    }
	    // 대표차종
	    if (!map.getString("carTypeInfo").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PRODPJT.IDA3D8 IN (SELECT IDA2A2 FROM OEMPROJECTTYPE WHERE upper(NAME)", map.getString("carTypeInfo")
		                        .toUpperCase(), false)).append(" )    \n");
	    }
	    // 금형구분
	    if (!map.getString("itemType").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MOLDINFO.ITEMTYPE", map.getString("itemType"), false))
		        .append("     \n");
	    }
	    // 금형분류
	    if (!map.getString("moldProductType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(MOLDINFO.IDA3B3)",
		                map.getString("moldProductType"), false)).append("     \n");
	    }
	    // 금형유형
	    if (!map.getString("moldType").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(MOLDINFO.IDA3C3)", map.getString("moldType"),
		                false)).append("     \n");
	    }
	    // Rank
	    if (!map.getString("rank").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch("FN_GET_NUMBERCODE(PJT.IDA3B9)", map.getString("rank"), false))
		        .append("     \n");
	    }
	    // 상태
	    if (!map.getString("pjtState").equals("")) {
		if (map.getString("pjtState").equals("UNDERREVIEW")) {
		    Hashtable h = ProjectHelper.APPROVALSTATE;
		    Enumeration e = h.keys();

		    StringBuilder strBuilder = new StringBuilder();
		    int i = 0;
		    while (e.hasMoreElements()) {
			if (i > 0)
			    strBuilder.append(", ");
			strBuilder.append((String) e.nextElement());
			i++;
		    }

		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", strBuilder.toString(), false))
			    .append("     \n");
		} else if (map.getString("pjtState").equals("delay")) {
		    sb.append("    AND PJT.STATESTATE = 'PROGRESS' \n");
		    sb.append("    AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY + "' \n");
		} else {

		    if (map.getString("pjtState").indexOf("delay") > -1) {
			sb.append("    AND (")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append(" OR (PJT.STATESTATE = 'PROGRESS' AND PJT.PJTSTATE   = '" + ProjectStateFlag.PROJECT_STATE_DELAY
			                + "'))    \n");
		    } else {
			sb.append("    AND ")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.STATESTATE", map.getString("pjtState"), false))
			        .append("     \n");
		    }
		}
	    }
	    // 금형PM
	    if (!map.getString("setMoldPm").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTMEMBERLINK WHERE PJTMEMBERTYPE = '0' AND IDA3B5",
		                KETParamMapUtil.OidToString(map.getString("setMoldPm")), false)).append(" )    \n");
	    }
	    // 제품구분
	    if (!map.getString("making").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MOLDINFO.MAKING", map.getString("making"), false))
		        .append("     \n");
	    }
	    // 제작처
	    if (!map.getString("outsourcing").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PJT.OUTSOURCING", map.getString("outsourcing"), true))
		        .append("     \n");
	    }
	    // 개발담당부서
	    if (!map.getString("devDeptOid").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PRODPJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTVIEWDEPARTMENTLINK WHERE IDA3B5",
		                KETParamMapUtil.OidToString(map.getString("devDeptOid")), false)).append(" )    \n");
	    }
	    // 개발시작일 Type
	    if (!map.getString("developDateType").equals("")) {
		// 개발시작일
		if (map.getString("developDateType").contains("DEVELOPDATESTART")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANSTARTDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
		// 개발완료일
		if (map.getString("developDateType").contains("DEVELOPDATEEND")) {
		    if (!map.getString("planStartStartDate").equals("")) {
			String predate = map.getString("planStartStartDate");
			predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE >= TO_DATE('" + predate
			        + "' || '000000','YYYYMMDDHH24MISS')                 \n");
		    }
		    if (!map.getString("planStartEndDate").equals("")) {
			String postdate = map.getString("planStartEndDate");
			postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
			sb.append("    AND SCHEDULE.PLANENDDATE < TO_DATE('" + postdate
			        + "' || '235959','YYYYMMDDHH24MISS')                \n");
		    }
		}
	    }
	    // 개발담당자
	    if (!map.getString("setPm").equals("")) {
		sb.append("    AND ")
		        .append(KETQueryUtil.getSqlQueryForMultiSearch(
		                "PRODPJT.IDA2A2 IN (SELECT IDA3A5 FROM PROJECTMEMBERLINK WHERE PJTMEMBERTYPE = '0' AND IDA3B5",
		                KETParamMapUtil.OidToString(map.getString("setPm")), false)).append(" )    \n");
	    }

	    // 사업부
	    if (!map.getString("dType").equals("")) {
		if (map.getString("dType").equals("1")) {
		    // 자동차사업부
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PRODPJT.PJTTYPE", "2", false)).append("     \n");
		} else if (map.getString("dType").equals("2")) {
		    // 전자사업부
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PRODPJT.PJTTYPE", "4", false)).append("     \n");
		} else if (map.getString("dType").equals("3")) {
		    // KETS
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("PRODPJT.PJTTYPE", "5", false)).append("     \n");
		}
	    }
	    // 구매품 여부
	    if (!map.getString("isPurchase").equals("")) {
		if (map.getString("isPurchase").equals("Y")) {
		    sb.append("    AND MOLDINFO.ITEMTYPE = '구매품' \n");
		}
	    } else {
		sb.append("    AND MOLDINFO.ITEMTYPE != '구매품' \n");
	    }

	    try {
		if (CommonUtil.isKETSUser()) {
		    sb.append("    AND PJT.PJTTYPE = 6 ");
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	}

	// Kogger.debug(getClass(), sb.toString());
	return sb.toString();
    }

    public List<Map<String, Object>> searchPeople(KETParamMapUtil map) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> returnObj = new HashMap<String, Object>();
	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT P.CLASSNAMEA2A2 || ':' || P.IDA2A2   AS PEOPLEOID                        \n");
	    sb.append("       ,P.ID                                 AS USERID                           \n");
	    sb.append("       ,P.NAME                               AS USERNAME                         \n");
	    sb.append("       ,D.NAME                               AS DEPARTMENTNAME                   \n");
	    sb.append("       ,P.DUTY                               AS DUTY                             \n");
	    sb.append("       ,P.DUTYCODE                           AS DUTYCODE                         \n");
	    sb.append("       ,P.EMAIL                              AS EMAIL                            \n");
	    sb.append("       ,P.CLASSNAMEKEYB4 || ':' || P.IDA3B4  AS WTUSEROID                        \n");
	    sb.append("       ,D.CLASSNAMEA2A2 || ':' || D.IDA2A2   AS DEPTOID                          \n");
	    sb.append("       ,(SELECT COUNT(DISTINCT(PJT.IDA2A2))                                      \n");
	    sb.append("           FROM REVIEWPROJECT      PJT                                           \n");
	    sb.append("               ,PROJECTMEMBERLINK  MEM                                           \n");
	    sb.append("          WHERE PJT.STATESTATE    = 'PROGRESS'                                   \n");
	    sb.append("            AND PJT.LASTEST       = 1                                            \n");
	    sb.append("            AND PJT.CHECKOUTSTATE <> 'c/o'                                       \n");
	    sb.append("            AND PJT.IDA2A2        = MEM.IDA3A5                                   \n");
	    sb.append("            AND MEM.IDA3B5        = P.IDA3B4                                     \n");
	    sb.append("       ) AS REVIEWCOUNT                                                          \n");
	    sb.append("       ,(SELECT COUNT(DISTINCT(PJT.IDA2A2))                                      \n");
	    sb.append("           FROM PRODUCTPROJECT     PJT                                           \n");
	    sb.append("               ,PROJECTMEMBERLINK  MEM                                           \n");
	    sb.append("          WHERE PJT.STATESTATE    = 'PROGRESS'                                   \n");
	    sb.append("            AND PJT.LASTEST       = 1                                            \n");
	    sb.append("            AND PJT.CHECKOUTSTATE <> 'c/o'                                       \n");
	    sb.append("            AND PJT.IDA2A2        = MEM.IDA3A5                                   \n");
	    sb.append("            AND MEM.IDA3B5        = P.IDA3B4                                     \n");
	    sb.append("            AND FN_GET_NUMBERCODEVALUE_OID('PROCESS', IDA3F9, 'ko') = 'Proto'    \n");
	    sb.append("       ) AS PROTOCOUNT                                                           \n");
	    sb.append("       ,(SELECT COUNT(DISTINCT(PJT.IDA2A2))                                      \n");
	    sb.append("           FROM PRODUCTPROJECT     PJT                                           \n");
	    sb.append("               ,PROJECTMEMBERLINK  MEM                                           \n");
	    sb.append("          WHERE PJT.STATESTATE    = 'PROGRESS'                                   \n");
	    sb.append("            AND PJT.LASTEST       = 1                                            \n");
	    sb.append("            AND PJT.CHECKOUTSTATE <> 'c/o'                                       \n");
	    sb.append("            AND PJT.IDA2A2        = MEM.IDA3A5                                   \n");
	    sb.append("            AND MEM.IDA3B5        = P.IDA3B4                                     \n");
	    sb.append("            AND FN_GET_NUMBERCODEVALUE_OID('PROCESS', IDA3F9, 'ko') = 'Pilot'    \n");
	    sb.append("       ) AS PILOTCOUNT                                                           \n");
	    sb.append("       ,(SELECT COUNT(DISTINCT(PJT.IDA2A2))                                      \n");
	    sb.append("           FROM MOLDPROJECT      PJT                                             \n");
	    sb.append("               ,PROJECTMEMBERLINK  MEM                                           \n");
	    sb.append("          WHERE PJT.STATESTATE    = 'PROGRESS'                                   \n");
	    sb.append("            AND PJT.LASTEST       = 1                                            \n");
	    sb.append("            AND PJT.CHECKOUTSTATE <> 'c/o'                                       \n");
	    sb.append("            AND PJT.IDA2A2        = MEM.IDA3A5                                   \n");
	    sb.append("            AND MEM.IDA3B5        = P.IDA3B4                                     \n");
	    sb.append("       ) AS MOLDCOUNT                                                            \n");
	    sb.append("   FROM PEOPLE     P                                                             \n");
	    sb.append("       ,DEPARTMENT D                                                             \n");
	    sb.append("  WHERE 1=1                                                                      \n");
	    sb.append("    AND P.IDA3C4 = D.IDA2A2(+)                                                   \n");

	    if (!map.getString("keyvalue").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("P.NAME", map.getString("keyvalue"), false))
		        .append("     \n");
	    }
	    if (!map.getString("deptName").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("D.NAME", map.getString("deptName"), false))
		        .append("     \n");
	    }
	    if (!map.getString("deptOid").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("D.IDA2A2", map.getString("deptOid"), false))
		        .append("     \n");
	    }
	    if (!map.getString("disable").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("P.ISDISABLE", map.getString("disable"), false))
		        .append("     \n");
	    }

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		returnObj = new HashMap<String, Object>();
		returnObj.put("peopleOid", rs.getString("PEOPLEOID"));
		returnObj.put("userId", rs.getString("USERID"));
		returnObj.put("userName", rs.getString("USERNAME"));
		returnObj.put("deptName", rs.getString("DEPARTMENTNAME"));
		returnObj.put("duty", rs.getString("DUTY"));
		returnObj.put("dutyCode", rs.getString("DUTYCODE"));
		returnObj.put("email", rs.getString("EMAIL"));
		returnObj.put("wtuserOid", rs.getString("WTUSEROID"));
		returnObj.put("deptOid", rs.getString("DEPTOID"));
		returnObj.put("reviewCount", rs.getString("REVIEWCOUNT"));
		returnObj.put("protoCount", rs.getString("PROTOCOUNT"));
		returnObj.put("pilotCount", rs.getString("PILOTCOUNT"));
		returnObj.put("moldCount", rs.getString("MOLDCOUNT"));
		returnObjList.add(returnObj);
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
	return returnObjList;
    }

    public List<Map<String, Object>> searchAtftInfo(HashMap<String, String> map) throws Exception {

	LoggableStatement pstmt = null;
	StringBuffer sb = null;
	ResultSet rs = null;

	Map<String, Object> returnObj = new HashMap<String, Object>();
	List<Map<String, Object>> returnObjList = new ArrayList<Map<String, Object>>();

	try {
	    sb = new StringBuffer();
	    sb.append(" SELECT A.PJTNO,                                                                                        \n");
	    sb.append("        A.PJTOID,                                                                                       \n");
	    sb.append("        A.ATFT1LEVEL,                                                                                   \n");
	    sb.append("        A.ATFT1LEVEL_NAME,                                                                              \n");
	    sb.append("        A.CLASSIFICATION,                                                                               \n");
	    sb.append("        A.DESISION AS P1_DESISION,                                                                      \n");
	    sb.append("        B.DESISION AS P2_DESISION,                                                                      \n");
	    sb.append("        A.BASIS AS P1_BASIS,                                                                            \n");
	    sb.append("        B.BASIS AS P2_BASIS,                                                                            \n");
	    sb.append("        A.CLASSKEY AS P1_CLASSKEY,                                                                      \n");
	    sb.append("        B.CLASSKEY AS P2_CLASSKEY,                                                                      \n");
	    sb.append("        A.OID AS P1_OID,                                                                                \n");
	    sb.append("        B.OID AS P2_OID,                                                                                \n");
	    sb.append("        A.STATESTATE AS P1_STATE,                                                                       \n");
	    sb.append("        B.STATESTATE AS P2_STATE,                                                                       \n");
	    sb.append("        A.VERSIONIDA2VERSIONINFO AS REV,                                                                 \n");
	    sb.append("        TO_CHAR(DECODE(B.LASTUPDATEDATE,'',A.LASTUPDATEDATE,B.LASTUPDATEDATE),'YYYY-MM-DD') AS LASTDATE,	\n");
	    sb.append("        (SELECT PEO.NAME||' - '||DEPT.NAME FROM PEOPLE PEO, DEPARTMENT DEPT WHERE PEO.IDA3B4 = DECODE(B.USEROID,'',A.USEROID,B.USEROID) AND PEO.IDA3C4 = DEPT.IDA2A2) AS USERINFO \n");
	    sb.append("   FROM                                                                                                 \n");
	    sb.append(" (SELECT PJTNO,                                                                                         \n");
	    sb.append("         ATFT_LINK.DESISION AS DESISION,                                                                \n");
	    sb.append("         CODE.CODE AS ATFT1LEVEL,                                                                       \n");
	    sb.append("         CODE.NAME AS ATFT1LEVEL_NAME,                                                                  \n");
	    sb.append("         CLASSIFICATION,                                                                                \n");
	    sb.append("         SHEETDIVISION,                                                                                 \n");
	    sb.append("         PJT.IDA2A2 AS PJTOID,                                                                          \n");
	    sb.append("         ATFT_LINK.BASIS,                                                                               \n");
	    sb.append("         'ext.ket.project.atft.entity.KETATFTSheetLink:'||ATFT_LINK.IDA2A2 AS CLASSKEY,                 \n");
	    sb.append("         CODE.SORTING||ATFT_TEMPALTE.SORTNO AS SORTNO,                                                  \n");
	    sb.append("         'ext.ket.project.atft.entity.KETATFTMainSheet:'||MAIN_SHEET.IDA2A2 AS OID,                     \n");
	    sb.append("         MAIN_SHEET.STATESTATE,                                                                         \n");
	    sb.append("         MAIN_SHEET.VERSIONIDA2VERSIONINFO,                                                              \n");
	    sb.append("         SHEET_LINK.MODIFYSTAMPA2 AS LASTUPDATEDATE,						       \n");
	    sb.append("         MAIN_SHEET.IDA3D2ITERATIONINFO AS USEROID 						       \n");
	    sb.append("    FROM PRODUCTPROJECT PJT,                                                                            \n");
	    sb.append("         E3PSPROJECTMASTER E3PSPJTMST,                                                                  \n");
	    sb.append("         EXTENDSCHEDULEDATA SCHEDULE,                                                                   \n");
	    sb.append("         KETPROJECTATFTSHEETLINK SHEET_LINK,                                                            \n");
	    sb.append("         (SELECT P.*                                                                                    \n");
	    sb.append("            FROM WTDOCUMENTMASTER M,                                                                    \n");
	    sb.append("                 KETATFTMAINSHEET P,                                                                    \n");
	    sb.append("                (SELECT J.IDA2A2, MAX (BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO                     \n");
	    sb.append("                   FROM KETATFTMAINSHEET I, WTDOCUMENTMASTER J                                          \n");
	    sb.append("                  WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                                                \n");
	    sb.append("                    AND I.LATESTITERATIONINFO = 1                                                       \n");
	    sb.append("                    AND I.STATECHECKOUTINFO != 'wrk'                                                    \n");
	    sb.append("                    GROUP BY J.IDA2A2) MAXVERPART                                                       \n");
	    sb.append("           WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                                                       \n");
	    sb.append("             AND P.LATESTITERATIONINFO = 1                                                              \n");
	    sb.append("             AND P.STATECHECKOUTINFO NOT IN ('wrk')                                                     \n");
	    sb.append("             AND M.IDA2A2 = MAXVERPART.IDA2A2                                                           \n");
	    sb.append("             AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO) MAIN_SHEET,                \n");
	    sb.append("          KETATFTSHEETLINK ATFT_LINK,                                                                   \n");
	    sb.append("          KETATFTSHEETTEMPLATE ATFT_TEMPALTE,                                                           \n");
	    sb.append("          NUMBERCODE CODE                                                                               \n");
	    sb.append("  WHERE 1 = 1                                                                                           \n");
	    sb.append("    --AND PJT.LASTEST = 1                                                                                 \n");
	    sb.append("    --AND PJT.CHECKOUTSTATE <> 'c/o'                                                                      \n");
	    sb.append("    AND PJT.IDA3B8 = E3PSPJTMST.IDA2A2                                                                  \n");
	    sb.append("    AND PJT.IDA3A8 = SCHEDULE.IDA2A2                                                                    \n");
	    sb.append("    AND PJT.IDA2A2 = SHEET_LINK.IDA3B5                                                                  \n");
	    sb.append("    AND SHEET_LINK.IDA3A5 = MAIN_SHEET.IDA2A2                                                           \n");
	    sb.append("    AND MAIN_SHEET.IDA2A2 = ATFT_LINK.IDA3A5                                                            \n");
	    sb.append("    AND ATFT_TEMPALTE.IDA2A2 = ATFT_LINK.IDA3B5                                                         \n");
	    sb.append("    AND ATFT_TEMPALTE.IDA3A3 = CODE.IDA2A2                                                              \n");
	    sb.append("    AND PJTNO = '" + map.get("pjtNo") + "'                                                            \n");
	    sb.append("    AND SHEETDIVISION = 'P1'                                                                            \n");
	    sb.append("  ORDER BY CODE.SORTING, ATFT_TEMPALTE.SORTNO) A,                                                       \n");
	    sb.append(" (SELECT PJTNO,                                                                                         \n");
	    sb.append("         ATFT_LINK.DESISION AS DESISION,                                                                \n");
	    sb.append("         CODE.CODE AS ATFT1LEVEL,                                                                       \n");
	    sb.append("         CODE.NAME AS ATFT1LEVEL_NAME,                                                                  \n");
	    sb.append("         CLASSIFICATION,                                                                                \n");
	    sb.append("         SHEETDIVISION,                                                                                 \n");
	    sb.append("         PJT.IDA2A2 AS PJTOID,                                                                          \n");
	    sb.append("         ATFT_LINK.BASIS,                                                                               \n");
	    sb.append("         'ext.ket.project.atft.entity.KETATFTSheetLink:'||ATFT_LINK.IDA2A2 AS CLASSKEY,                 \n");
	    sb.append("         CODE.SORTING||ATFT_TEMPALTE.SORTNO AS SORTNO,                                                  \n");
	    sb.append("         'ext.ket.project.atft.entity.KETATFTMainSheet:'||MAIN_SHEET.IDA2A2 AS OID,                     \n");
	    sb.append("         MAIN_SHEET.STATESTATE,                                                                         \n");
	    sb.append("         MAIN_SHEET.VERSIONIDA2VERSIONINFO,                                                              \n");
	    sb.append("         SHEET_LINK.MODIFYSTAMPA2 AS LASTUPDATEDATE,						       \n");
	    sb.append("         MAIN_SHEET.IDA3D2ITERATIONINFO AS USEROID 						       \n");
	    sb.append("    FROM PRODUCTPROJECT PJT,                                                                            \n");
	    sb.append("         E3PSPROJECTMASTER E3PSPJTMST,                                                                  \n");
	    sb.append("         EXTENDSCHEDULEDATA SCHEDULE,                                                                   \n");
	    sb.append("         KETPROJECTATFTSHEETLINK SHEET_LINK,                                                            \n");
	    sb.append("        (SELECT P.*                                                                                     \n");
	    sb.append("           FROM WTDOCUMENTMASTER M,                                                                     \n");
	    sb.append("                KETATFTMAINSHEET P,                                                                     \n");
	    sb.append("                (SELECT J.IDA2A2, MAX (BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO                     \n");
	    sb.append("                   FROM KETATFTMAINSHEET I, WTDOCUMENTMASTER J                                          \n");
	    sb.append("                  WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                                                \n");
	    sb.append("                    AND I.LATESTITERATIONINFO = 1                                                       \n");
	    sb.append("                    AND I.STATECHECKOUTINFO != 'wrk'                                                    \n");
	    sb.append("               GROUP BY J.IDA2A2) MAXVERPART                                                            \n");
	    sb.append("          WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                                                        \n");
	    sb.append("            AND P.LATESTITERATIONINFO = 1                                                               \n");
	    sb.append("            AND P.STATECHECKOUTINFO NOT IN ('wrk')                                                      \n");
	    sb.append("            AND M.IDA2A2 = MAXVERPART.IDA2A2                                                            \n");
	    sb.append("            AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO) MAIN_SHEET,                 \n");
	    sb.append("         KETATFTSHEETLINK ATFT_LINK,                                                                    \n");
	    sb.append("         KETATFTSHEETTEMPLATE ATFT_TEMPALTE,                                                            \n");
	    sb.append("         NUMBERCODE CODE                                                                                \n");
	    sb.append("  WHERE 1 = 1                                                                                           \n");
	    sb.append("    --AND PJT.LASTEST = 1                                                                               \n");
	    sb.append("    --AND PJT.CHECKOUTSTATE <> 'c/o'                                                                    \n");
	    sb.append("    AND PJT.IDA3B8 = E3PSPJTMST.IDA2A2                                                                  \n");
	    sb.append("    AND PJT.IDA3A8 = SCHEDULE.IDA2A2                                                                    \n");
	    sb.append("    AND PJT.IDA2A2 = SHEET_LINK.IDA3B5                                                                  \n");
	    sb.append("    AND SHEET_LINK.IDA3A5 = MAIN_SHEET.IDA2A2                                                           \n");
	    sb.append("    AND MAIN_SHEET.IDA2A2 = ATFT_LINK.IDA3A5                                                            \n");
	    sb.append("    AND ATFT_TEMPALTE.IDA2A2 = ATFT_LINK.IDA3B5                                                         \n");
	    sb.append("    AND ATFT_TEMPALTE.IDA3A3 = CODE.IDA2A2                                                              \n");
	    sb.append("    AND PJTNO = '" + map.get("pjtNo") + "'                                                                  \n");
	    sb.append("    AND SHEETDIVISION = 'P2') B                                                                         \n");
	    sb.append("  WHERE A.PJTNO = B.PJTNO                                                                               \n");
	    sb.append("    AND A.ATFT1LEVEL = B.ATFT1LEVEL                                                                     \n");
	    sb.append("    AND A.CLASSIFICATION = B.CLASSIFICATION                                                             \n");

	    pstmt = new LoggableStatement(conn, sb.toString());
	    rs = pstmt.executeQuery();

	    while (rs.next()) {
		returnObj = new HashMap<String, Object>();
		returnObj.put("PJTNO", rs.getString("PJTNO"));
		returnObj.put("PJTOID", rs.getString("PJTOID"));
		returnObj.put("ATFT1LEVEL", rs.getString("ATFT1LEVEL"));
		returnObj.put("ATFT1LEVEL_NAME", rs.getString("ATFT1LEVEL_NAME"));
		returnObj.put("CLASSIFICATION", rs.getString("CLASSIFICATION").replaceAll(System.getProperty("line.separator"), ""));
		returnObj.put("P1_DESISION", rs.getString("P1_DESISION"));
		returnObj.put("P2_DESISION", rs.getString("P2_DESISION"));
		returnObj.put("P1_BASIS",
		        StringUtils.defaultIfEmpty(rs.getString("P1_BASIS"), "").replaceAll(System.getProperty("line.separator"), "<br>"));
		returnObj.put("P2_BASIS",
		        StringUtils.defaultIfEmpty(rs.getString("P2_BASIS"), "").replaceAll(System.getProperty("line.separator"), "<br>"));
		returnObj.put("P1_CLASSKEY", rs.getString("P1_CLASSKEY"));
		returnObj.put("P2_CLASSKEY", rs.getString("P2_CLASSKEY"));
		returnObj.put("P1_OID", rs.getString("P1_OID"));
		returnObj.put("P2_OID", rs.getString("P2_OID"));
		returnObj.put("P1_STATE", rs.getString("P1_STATE"));
		returnObj.put("P2_STATE", rs.getString("P2_STATE"));
		returnObj.put("REV", rs.getString("REV"));
		returnObj.put("LASTDATE", rs.getString("LASTDATE"));
		returnObj.put("USERINFO", rs.getString("USERINFO"));

		returnObjList.add(returnObj);
	    }
	} catch (SQLException se) {
	    se.printStackTrace();
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    e.printStackTrace();
	    Kogger.error(getClass(), e);
	} finally {
	    sb.delete(0, sb.length());
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(pstmt);
	}
	return returnObjList;
    }
}
