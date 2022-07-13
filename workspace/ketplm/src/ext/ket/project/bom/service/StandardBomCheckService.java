package ext.ket.project.bom.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.method.MethodContext;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.util.StringUtil;
import ext.ket.shared.util.EjsConverUtil;

public class StandardBomCheckService extends StandardManager implements BomCheckService {
    private static final long serialVersionUID = 1L;

    public static StandardBomCheckService newStandardBomCheckService() throws WTException {
	StandardBomCheckService instance = new StandardBomCheckService();
	instance.initialize();
	return instance;
    }

    @Override
    public Map<String, Object> findPagingList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {

	    StringBuffer sql = new StringBuffer();

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    sql = getBomQuery(reqMap, false, true);

	    rs = stat.executeQuery(sql.toString());
	    int rowNum = 1;
	    while (rs.next()) {

		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("rowNum", rowNum++);
		dataMap.put("dieNo", rs.getString("DIENO"));
		dataMap.put("partNo", rs.getString("PARTNO"));
		dataMap.put("partName", rs.getString("PARTNAME"));
		dataMap.put("taskId", rs.getString("TASK_ID"));

		dataMap.put("rawQty", getNotEmptyString("R_QTY2", rs));
		dataMap.put("scrapQty", getNotEmptyString("S_QTY2", rs));

		dataMap.put("tryRawQty", getNotEmptyString("T_R_QTY", rs));
		dataMap.put("tryScrapQty", getNotEmptyString("T_S_QTY", rs));
		dataMap.put("costRawQty", getNotEmptyString("C_R_QTY", rs));
		dataMap.put("costScrapQty", getNotEmptyString("C_S_QTY", rs));

		dataMap.put("tryRawRate", getFormatString(rs.getString("TRY_R_RATE")));
		dataMap.put("tryScrapRate", getFormatString(rs.getString("TRY_S_RATE")));
		dataMap.put("costRawRate", getFormatString(rs.getString("COST_R_RATE")));
		dataMap.put("costScrapRate", getFormatString(rs.getString("COST_S_RATE")));

		// if ("0".equals(rs.getString("T_R_QTY"))) {
		// dataMap.put("tryRawRate", "");
		// } else {
		// dataMap.put("tryRawRate", getFormatString(rs.getString("TRY_R_RATE")));
		// }
		//
		// if ("0".equals(rs.getString("T_S_QTY"))) {
		// dataMap.put("tryScrapRate", "");
		// } else {
		// dataMap.put("tryScrapRate", getFormatString(rs.getString("TRY_S_RATE")));
		// }
		//
		// if ("0".equals(rs.getString("C_R_QTY"))) {
		// dataMap.put("costRawRate", "");
		// } else {
		// dataMap.put("costRawRate", getFormatString(rs.getString("COST_R_RATE")));
		// }
		//
		// if ("0".equals(rs.getString("C_S_QTY"))) {
		// dataMap.put("costScrapRate", "");
		// } else {
		// dataMap.put("costScrapRate", getFormatString(rs.getString("COST_S_RATE")));
		// }

		dataList.add(dataMap);
	    }

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

	if (dataList.size() < 1) {
	    Map<String, Object> cfg = new HashMap<String, Object>();
	    cfg.put("RootCount", 0);
	    // Body Object
	    Map<String, Object> body = new HashMap<String, Object>();
	    body.put("Body", dataList);
	    body.put("Cfg", cfg);
	    return body;
	}

	return EjsConverUtil.convertToDto(dataList);
    }

    public Object getNotEmptyString(String qty, ResultSet rs) throws Exception {
	if (StringUtils.isNotEmpty(rs.getString(qty))) {
	    return rs.getFloat(qty);
	}
	return "";
    }

    public String getFormatString(String rate) {
	if (StringUtils.isNotEmpty(rate)) {
	    return rate + "%";
	}

	return rate;

    }

    public StringBuffer getBomQuery(Map<String, Object> reqMap, boolean isCount, boolean isPaging) {

	String pjtNo = (String) reqMap.get("pjtNo");

	StringBuffer sql = new StringBuffer();

	sql.append(" WITH PJT_ITEM AS (                                                                                                                                   				\n");
	sql.append("  SELECT PM.PJTNO                                                                                                                                     					\n");
	sql.append("        ,PM.PJTNAME                                                                                                                                   					\n");
	sql.append("        ,PM.IDA2A2 AS PJTM_ID                                                                                                                         				\n");
	sql.append("        ,PJ.STATESTATE                                                                                                                                					\n");
	sql.append("        ,MII.PARTNO                                                                                                                                   					\n");
	sql.append("        ,SUBSTR(MII.PARTNO,1,INSTR(MII.PARTNO,'-') -1)||'A-'||SUBSTR(MII.PARTNO,INSTR(MII.PARTNO,'-')+1)  AS PRE_PARTNO                	\n"); // 프레스후도금번호
	sql.append("        ,SUBSTR(MII.PARTNO,1,INSTR(MII.PARTNO,'-') -1)||'D-'||SUBSTR(MII.PARTNO,INSTR(MII.PARTNO,'-')+1)  AS PARTIE_PARTNO                	\n"); // 프레스분단금형
	sql.append("       ,MII.PARTNAME                                                                                                                                  					\n");
	sql.append("       ,FN_GET_BOM_QTY('R',MII.PARTNO) AS R_QTY                                                                                                      			\n");
	// 2022.06.12 --> 분단금형의 경우 스크랩을 잘못 가져오기 때문에 다시 주석처리 한다. 아래 쿼리에서 프레스인데 스크랩중량 없는 경우 C로 시작하는 스크랩중량을 가져오도록 하는 로직으로 바꿈
	// 2022.05.02 ---> 분단금형 관련 로직 다시 살리기로 함 박상수 수석 재요청
	// 2021.07.05 ---> 무효화됨 그냥 자기 자신의 스크랩 중량을 가져옴
	// 품번이 D- 로 끝나는 분단금형일 경우 D-를 제거한 품번 (즉 상위품번)의 스크랩중량을 가져온다
	// sql.append("      ,DECODE(INSTR(MII.PARTNO, 'D-', 1, 1) , 0,  FN_GET_BOM_QTY('S',MII.PARTNO), NVL(FN_GET_BOM_QTY('S', REPLACE(MII.PARTNO,'D','')), FN_GET_BOM_QTY('S',SUBSTR(MII.PARTNO,0,INSTR(MII.PARTNO, 'D-', 1, 1) -1) ))) AS S_QTY \n");
	sql.append("        ,FN_GET_BOM_QTY('S',MII.PARTNO) AS S_QTY                                                                                                      			\n");
	sql.append("        ,MII.ITEMTYPE                                                                                                                                 					\n");
	sql.append("        ,SHRINKAGE                                                                                                                                    					\n");
	sql.append("        ,MII.DIENO                                                                                                                                    					\n");
	sql.append("        ,MII.MAKING                                                                                                                                   					\n");
	sql.append("        ,MII.IDA3B3                                                                                                                                   					\n");
	sql.append("        ,MII.IDA2A2 AS MII_ID                                                                                                                         					\n");
	sql.append("        ,PJ.EXECENDDATE44                                                                                                                    						\n");
	sql.append("       ,PJ.EXECENDDATE42   																				\n");
	sql.append("        ,PJ.EXECENDDATE31  		                                                                                                                   				\n");
	sql.append("         ,TO_CHAR(PM.CREATESTAMPA2,'YYYY-MM-DD') AS PJT_CREATE_DATE                                                                                   	\n");
	sql.append("    FROM PRODUCTPROJECT PJ                                                                                                                            	\n");
	sql.append("        ,MOLDITEMINFO  MII                                                                                                                            	\n");
	sql.append("       ,E3PSPROJECTMASTER PM                                                                                                                          	\n");
	sql.append("  WHERE 1=1                                                                                                                                           \n");
	// 대표차종
	if (StringUtil.checkString(pjtNo)) {
	    pjtNo = pjtNo.trim().replaceAll(",", "','");
	    sql.append("    AND PM.PJTNO IN ('" + pjtNo + "')");
	}

	sql.append("     AND MII.IDA3A3 = PJ.IDA2A2                                                                                                          \n");// 제품프로젝트
	                                                                                                                                                          // 아이디
	sql.append("    AND PM.IDA2A2  = PJ.IDA3B8                                                                                                           \n");
	// sql.append("     AND PJ.PJTTYPE = 2                                                                                                                 \n");
	sql.append("    AND PJ.LASTEST = 1                                                                                                             \n");
	sql.append("    AND PJ.CHECKOUTSTATE <> 'c/o'                                                                                                                     \n");
	sql.append("     AND MII.IDA3B3 =  990856667                                                                                                         \n");
	sql.append("    AND MII.SHRINKAGE = '신규'                                                                                                                          \n");
	sql.append("     AND UPPER(MII.ITEMTYPE) IN ( 'MOLD', 'PRESS')                                                                                                    \n");
	sql.append("  )                                                                                                                                                   \n");
	sql.append("  ,TRY AS (                                                                                                                                           \n");
	// sql.append("  /* Mold Try 정보조회 */                                                                                                                                 \n");
	sql.append(" SELECT C.DIENO                                                                                                                                       \n");
	sql.append("        ,C.PARTNO                                                                                                                                     \n");
	sql.append("        ,C.IDA2A2 AS TRY_ID                                                                                                                           \n");
	sql.append("        ,TO_NUMBER(C.CAVITYCOUNT ) CAVITYCOUNT                                                                                                        \n");
	sql.append("        ,C.CAVITYBIGO                                                                                                                                 \n");
	sql.append("        ,C.SHOTWEIGHT  SHOTWEIGHT                                                                                                          \n");// --
	                                                                                                                                                            // Shot중량
	sql.append("       ,TO_NUMBER(NVL(C.SPRUE,0) ) SPRUE                                                                                                    \n"); // --
	                                                                                                                                                              // 스크랩
	sql.append("       ,TO_NUMBER(NVL(C.CVWEIGHT,0) ) CVWEIGHT                                                                                           \n"); // --
	                                                                                                                                                           // 총중량
	sql.append("       ,TO_NUMBER(NVL(C.INSERTWEIGHT,0)) INSERTWEIGHT                                                                                                 \n");
	sql.append("       ,TASK_ID                                                                                                 \n");
	sql.append("    FROM (                                                                                                                                            \n");
	sql.append("          SELECT C.DIENO                                                                                                                              \n");
	sql.append("                ,MAX(C.IDA2A2) AS TRY_ID                                                                                                              \n");
	sql.append("                ,MAX('e3ps.project.E3PSTask:'||T.IDA2A2) AS TASK_ID                                                                                                              \n");
	sql.append("            FROM PJT_ITEM I                                                                                                                           \n");
	sql.append("                ,E3PSPROJECTMASTER EMR                                                                                                                      \n");
	sql.append("                ,MOLDPROJECT MPJ                                                                                                                      \n");
	sql.append("                ,E3PSTASK T                                                                                                                           \n");
	sql.append("                ,PROJECTOUTPUT A                                                                                                                      \n");
	sql.append("                ,KETTRYMOLDOUTPUTLINK B                                                                                                               \n");
	sql.append("                ,KETTRYMOLD C                                                                                                                         \n");
	sql.append("           WHERE I.DIENO = EMR.PJTNO                                                                                                               \n");
	sql.append("             AND EMR.IDA2A2 = MPJ.IDA3B8                                                                                                                      \n");
	sql.append("             AND MPJ.LASTEST = 1                                                                                                                      \n");
	sql.append("             AND MPJ.CHECKOUTSTATE <> 'C/O'                                                                                                           \n");
	sql.append("             AND MPJ.IDA2A2 = T.IDA3B4                                                                                                                \n");
	sql.append("             AND T.TASKNAME LIKE '금형Try_[양산검증%'                                                                                                      \n");
	sql.append("             AND A.IDA3B5 = T.IDA2A2                                                                                                     \n");
	sql.append("             AND A.OBJTYPE ='TRY'                                                                                                                     \n");
	sql.append("             AND A.COMPLETION = 100                                                                                                                   \n");
	// sql.append("            -- AND A.ISPRIMARY = 1                                                                                                                    \n");
	sql.append("             AND A.IDA2A2 = B.IDA3B5                                                                                                                  \n");
	sql.append("             AND C.IDA2A2 = B.IDA3A5                                                                                                                  \n");
	sql.append("           GROUP BY C.DIENO                                                                                                                           \n");
	sql.append("          ) MAX_TR                                                                                                                                    \n");
	sql.append("         ,KETTRYMOLD C                                                                                                                                \n");
	sql.append("   WHERE MAX_TR.TRY_ID = C.IDA2A2                                                                                                                     \n");
	sql.append("  UNION ALL                                                                                                                                           \n");
	// sql.append("  /* Press Try 정보조회 */                                                                                                                                \n");
	sql.append(" SELECT C.DIENO                                                                                                                                       \n");
	sql.append("        ,C.PARTNO                                                                                                                                     \n");
	sql.append("        ,C.IDA2A2 AS TRY_ID                                                                                                                           \n");
	sql.append("        ,1 AS CAVITYCOUNT                                                                                                                             \n");
	sql.append("        ,NULL AS CAVITYBIGO                                                                                                                           \n");
	sql.append("        ,W.TOTALWEIGHT AS SHOTWEIGHT                                                                                                       \n"); // --
	                                                                                                                                                             // Shot중량
	sql.append("       ,W.SCRABCARRIERWEIGHT + W.SCRABWEIGHT AS SPRUE                                                                                         \n"); // --
	                                                                                                                                                                // 스크랩
	sql.append("       ,NULL AS CVWEIGHT                                                                                                                    \n"); // --
	                                                                                                                                                              // 총중량
	sql.append("       ,NULL AS INSERTWEIGHT                                                                                                                          \n");
	sql.append("       ,TASK_ID                                                                                                 \n");
	sql.append("    FROM (                                                                                                                                            \n");
	sql.append("          SELECT C.DIENO                                                                                                                              \n");
	sql.append("                ,MAX(C.IDA2A2) AS TRY_ID                                                                                                              \n");
	sql.append("                ,MAX('e3ps.project.E3PSTask:'||T.IDA2A2) AS TASK_ID                                                                                                             \n");
	sql.append("            FROM PJT_ITEM I                                                                                                                           \n");
	sql.append("                ,E3PSPROJECTMASTER EMR                                                                                                                      \n");
	sql.append("                ,MOLDPROJECT MPJ                                                                                                                      \n");
	sql.append("                ,E3PSTASK T                                                                                                                           \n");
	sql.append("               -- ,E3PSTASK TK                                                                                                                        \n");
	sql.append("                ,PROJECTOUTPUT A                                                                                                                      \n");
	sql.append("                ,KETTRYPRESSOUTPUTLINK B                                                                                                              \n");
	sql.append("                ,KETTRYPRESS C                                                                                                                        \n");
	sql.append("           WHERE  I.DIENO = EMR.PJTNO                                                                                                               \n");
	sql.append("             AND EMR.IDA2A2 = MPJ.IDA3B8                                                                                                                     \n");
	sql.append("             AND MPJ.LASTEST = 1                                                                                                                      \n");
	sql.append("             AND MPJ.CHECKOUTSTATE <> 'C/O'                                                                                                           \n");
	sql.append("             AND MPJ.IDA2A2 = T.IDA3B4                                                                                                                \n");
	sql.append("             AND T.TASKNAME LIKE '금형Try_[양산검증%'                                                                                                      \n");
	sql.append("             AND A.IDA3B5 = T.IDA2A2                                                                                                     \n");
	sql.append("             AND A.OBJTYPE ='TRY'                                                                                                                     \n");
	sql.append("             AND A.COMPLETION = 100                                                                                                                   \n");
	// sql.append("            -- AND A.ISPRIMARY = 1                                                                                                                    \n");
	sql.append("             AND A.IDA2A2 = B.IDA3B5                                                                                                                  \n");
	sql.append("             AND C.IDA2A2 = B.IDA3A5                                                                                                                  \n");
	sql.append("           GROUP BY C.DIENO                                                                                                                           \n");
	sql.append("          ) MAX_TR                                                                                                                                    \n");
	sql.append("         ,KETTRYPRESS C                                                                                                                               \n");
	sql.append("         ,(SELECT * FROM KETTRYPARTLINK WHERE PARTNO LIKE 'R1%' ) W                                                                                   \n");
	sql.append("   WHERE MAX_TR.TRY_ID = C.IDA2A2                                                                                                                     \n");
	sql.append("     AND C.IDA2A2 = W.IDA3A5(+)                                                                                                                       \n");
	sql.append("  )                                                                                                                                                   \n");
	sql.append("  ,LAST_TMP AS (                                                                                                                                      \n");
	// sql.append("  /* 차이비교하기 위해 한번더 감쌈 */                                                                                                                            \n");
	sql.append(" SELECT P.*                                                                                                                                           \n");
	sql.append("        ,CASE WHEN P.ITEMTYPE= 'Press' AND P.R_QTY IS NULL THEN DECODE(FN_GET_BOM_QTY('R',P.PRE_PARTNO),NULL,FN_GET_BOM_QTY('R',P.PARTIE_PARTNO),FN_GET_BOM_QTY('R',P.PRE_PARTNO))                                                      \n");
	sql.append("              ELSE P.R_QTY                                                                                                                            \n");
	sql.append("         END R_QTY2                                                                                                                                   \n");
	// sql.append("        ,P.S_QTY AS S_QTY2 \n");
	sql.append("        ,CASE WHEN P.ITEMTYPE= 'Press' AND P.S_QTY IS NULL THEN DECODE(FN_GET_BOM_QTY('C',P.PRE_PARTNO),NULL,FN_GET_BOM_QTY('S',P.PRE_PARTNO))                                                       \n");
	sql.append("              ELSE P.S_QTY                                                                                                                            							\n");
	sql.append("         END S_QTY2                                                                                                                                   							\n");
	sql.append("        ,ROUND(T.SHOTWEIGHT/T.CAVITYCOUNT,3) AS T_R_QTY                                                                                               				\n");
	sql.append("        ,ROUND(T.SPRUE/T.CAVITYCOUNT,3) AS T_S_QTY                                                                                                    					\n");
	sql.append("       ,(SELECT MAX(TOTALWEIGHT)||'|'||MAX(SCRAPWEIGHT)                                                                                                         					\n");
	sql.append("           FROM COSTPART A, (SELECT A.IDA3B5,B.LASTESTVERSION FROM PJTMASTERPARTLISTLINK A, PARTLIST B WHERE A.IDA3A5 = B.IDA2A2) B           \n");
	sql.append("         WHERE A.CASEORDER = 1                                                                                                               							\n");
	sql.append("             AND A.LASTEST = 1                                                                                                                        						\n");
	// sql.append("             AND ROWNUM = 1                                                                                                                        						\n");
	// sql.append("             AND A.PARTTYPE IN ( 1149659833, 1149659837, 1149659838  )                        											\n"); //
	// HSG(일반),
	// TML(일반)
	// TML(PCB)
	// 인것
	// 중에서만
	// (양산의
	// 경우가
	// 석이면
	// 안됨)
	sql.append("             AND A.VERSION = B.LASTESTVERSION                                                                                                         					\n");
	sql.append("             AND A.IDA3B4 = B.IDA3B5                                                                                                                  						\n");
	sql.append("             AND P.PJTM_ID = A.IDA3B4                                                                                                                 						\n");
	sql.append("             AND ( A.REALPARTNO = P.PARTNO OR 'H'||A.REALPARTNO = P.PARTNO )                                                                          				\n");
	// sql.append("             AND ROWNUM = 1 -- 하나의 반제품이 여러 ASSY하위에 존재하는 경우가 있다. 이런 경우 모두 같은 것으로 보고 첫번째 것으로 판단한다                                                  		\n");
	sql.append("         ) AS C_QTY                                                                                                                                   							\n");
	sql.append("        ,T.TRY_ID                                                                                                                                     							\n");
	sql.append("        ,T.TASK_ID                                                                                                                                     							\n");
	sql.append("    FROM PJT_ITEM P                                                                                                                                   							\n");
	sql.append("        ,TRY T                                                                                                                                        							\n");
	sql.append("   WHERE P.DIENO = T.DIENO(+)                                                                                                                         						\n");
	sql.append("  )                                                                                                                                                   								\n");
	sql.append("  SELECT DIENO                                                                                                                                        							\n");
	sql.append("             ,PARTNO                                                                                                                                  							\n");
	sql.append("             ,PARTNAME                                                                                                                                							\n");
	sql.append("             ,TASK_ID                                                                                                                                							\n");
	sql.append("             ,R_QTY2                                                                                                                            \n");
	sql.append("             ,S_QTY2                                                                                                                                   							\n");
	sql.append("             ,T_R_QTY AS T_R_QTY                                                                                                                               							\n");
	sql.append("             ,T_S_QTY  AS  T_S_QTY                                                                                                                             							\n");
	sql.append("             ,REGEXP_SUBSTR(C_QTY, '[^|]+', 1, 1)  AS C_R_QTY                                                                                          					\n");
	sql.append("             ,REGEXP_SUBSTR(C_QTY, '[^|]+', 1, 2)  AS C_S_QTY                                                                                          					\n");
	sql.append("             ,NVL2(R_QTY2, ROUND((R_QTY2 - T_R_QTY)/NULLIF(R_QTY2,0)*100 ),NULL) AS TRY_R_RATE                                                                  			\n");
	sql.append("             ,NVL2(S_QTY2, ROUND((S_QTY2 - T_S_QTY)/NULLIF(S_QTY2,0)*100 ),NULL) AS TRY_S_RATE                                                                  			\n");
	sql.append("             ,NVL2(R_QTY2, ROUND((R_QTY2 - REGEXP_SUBSTR(C_QTY, '[^|]+', 1, 1))/NULLIF(R_QTY2,0)*100 ),NULL) AS COST_R_RATE                                     	\n");
	sql.append("             ,NVL2(S_QTY2, ROUND((S_QTY2 - REGEXP_SUBSTR(C_QTY, '[^|]+', 1, 2))/NULLIF(S_QTY2,0)*100 ),NULL) AS COST_S_RATE                                     	\n");
	sql.append("    FROM LAST_TMP LT																							\n");

	return sql;

    }

    @Override
    public Map<String, String> getMoldTaskOid(String pjtNo) throws Exception {

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	Map<String, String> datas = new HashMap<String, String>();

	try {

	    StringBuffer sql = new StringBuffer();

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    sql = getMoldTaskIdQuery(pjtNo);

	    rs = stat.executeQuery(sql.toString());

	    while (rs.next()) {

		datas.put("taskId", rs.getString("TASK_ID"));
	    }

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
	return datas;
    }

    public StringBuffer getMoldTaskIdQuery(String pjtNo) {

	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT MAX('e3ps.project.E3PSTask:'||MOLDTASK.IDA2A2) AS TASK_ID ");
	sql.append("   FROM MOLDPROJECT         PJT                                   ");
	sql.append("       ,MOLDITEMINFO        MOLDINFO                              ");
	sql.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                              ");
	sql.append("       ,PRODUCTPROJECT      PRODPJT                               ");
	sql.append("       ,E3PSPROJECTMASTER   PRODPJTMST                            ");
	sql.append("       ,E3PSTASK MOLDTASK                                         ");
	sql.append(" WHERE PJT.LASTEST       = 1                                      ");
	sql.append("   AND PJT.CHECKOUTSTATE <> 'c/o'                                 ");
	sql.append("   AND PJT.IDA3A10       = MOLDINFO.IDA2A2                        ");
	sql.append("   AND PJT.IDA3A8        = SCHEDULE.IDA2A2                        ");
	sql.append("   AND MOLDINFO.IDA3A3   = PRODPJT.IDA2A2                         ");
	sql.append("   AND PRODPJT.IDA3B8    = PRODPJTMST.IDA2A2                      ");
	sql.append("   AND PJT.IDA2A2 = MOLDTASK.IDA3B4                               ");
	sql.append("   AND MOLDTASK.TASKNAME LIKE '금형Try_[양산검증%'                     ");
	sql.append("   AND ( UPPER(MOLDINFO.DIENO) = '" + pjtNo + "' )                     ");
	sql.append("   AND ( PRODPJT.PJTTYPE = '2' )                                  ");
	sql.append("   AND MOLDINFO.ITEMTYPE != '구매품'                                 ");

	return sql;
    }
}
