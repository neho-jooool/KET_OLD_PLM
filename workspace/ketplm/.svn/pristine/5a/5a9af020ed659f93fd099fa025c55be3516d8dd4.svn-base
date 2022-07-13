package e3ps.dms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import ext.ket.shared.log.Kogger;

public class ProjectDocDao {

    public int searchProjectDocumentListCount(List<Map<String, Object>> conditionList) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	String sortCol = null;
	String query = getSearchProjectDocQueryBySortNCondition(conditionList, sortCol);
	int totalCount = oDaoManager.queryForCount(query);

	return totalCount;
    }

    public List<Map<String, Object>> searchProjectDocumentList(List<Map<String, Object>> conditionList, TgPagingControl pager) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> projectDocumentList = null;

	try {

	    // 쿼리 튜닝
	    String sortCol = pager.getSortCol();
	    // 1) 10 개의 Drawing Oid 가져오기
	    String query = getSearchProjectDocQueryBySortNCondition(conditionList, sortCol);
	    query = oDaoManager.getOrderByQuery(query, pager);
	    query = oDaoManager.getPagingQuery(query, pager);
	    // 2) 10 개의 Drawing Oid를 in 문에 넣고 값을 Select 해온다.
	    query = getSearchProjectDocQueryByDocOidJoin(conditionList, query);
	    query = oDaoManager.getOrderByQuery(query, pager);

	    projectDocumentList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> projectDocument = new HashMap<String, Object>();
		    projectDocument = new HashMap<String, Object>();
		    projectDocument.put("oid", rs.getString("OID"));
		    projectDocument.put("documentNo", rs.getString("DOCUMENTNO"));
		    projectDocument.put("categoryName", rs.getString("CATEGORYNAME"));
		    projectDocument.put("title", rs.getString("TITLE"));
		    projectDocument.put("stateState", rs.getString("STATESTATE"));
		    projectDocument.put("state", rs.getString("STATE"));
		    projectDocument.put("ver", rs.getString("VERSION"));
		    projectDocument.put("creatorName", rs.getString("CREATORNAME"));
		    projectDocument.put("deptName", rs.getString("DEPTNAME"));
		    projectDocument.put("createDate", rs.getString("CREATEDATE"));
		    projectDocument.put("buyerSummit", rs.getString("BUYERSUMMIT"));
		    // added by omjae, 2013-07-13 첨부파일//
		    projectDocument.put("holderOid", rs.getString("HOLDEROID"));
		    projectDocument.put("appDataOid", rs.getString("APPDATAOID"));
		    projectDocument.put("extension", rs.getString("EXTENSION"));

		    return projectDocument;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return projectDocumentList == null ? new ArrayList<Map<String, Object>>() : projectDocumentList;
    }

    String getSearchProjectDocQueryBySortNCondition(List<Map<String, Object>> conditionList, String sortCol) throws Exception {

	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT A.IDA2A2                  AS OID          \n");
	sb.append("       ,NCV.VALUE                 AS CATEGORYNAME \n");
	sb.append("       ,F.NAME                    AS CREATORNAME  \n");
	sb.append("       ,E.NAME                    AS STATE        \n");

	if (StringUtils.isNotEmpty(sortCol)) {

	    if ("DocumentNo".equals(sortCol)) { // ui-layout : DocumentNo > DOCUMENTNO
		sb.append("       ,A.DOCUMENTNO              AS DOCUMENTNO   \n");
		// } else if ("CategoryName".equals(sortCol)) { // ui-layout : CategoryName > CATEGORYNAME => 무조건 사용
		// sb.append("       ,NCV.VALUE                 AS CATEGORYNAME \n");
	    } else if ("Title".equals(sortCol)) { // ui-layout : Title > TITLE
		sb.append("       ,A.TITLE                   AS TITLE        \n");
	    } else if ("StateState".equals(sortCol)) { // ui-layout : StateState > STATESTATE
		sb.append("       ,A.STATESTATE              AS STATESTATE   \n");
	    } else if ("Version".equals(sortCol)) { // ui-layout : Version > VERSION
		sb.append("       ,A.VERSIONIDA2VERSIONINFO  AS VERSION      \n");
		// } else if ("Creator".equals(sortCol)) { // ui-layout : Creator > CREATORNAME => 무조건 사용
		// sb.append("       ,F.NAME                    AS CREATORNAME  \n");
	    } else if ("CreateDate".equals(sortCol)) { // ui-layout : CreateDate > CREATEDATE
		sb.append("       ,A.CREATESTAMPA2           AS CREATEDATE   \n");
	    } else if ("BuyerSummit".equals(sortCol)) { // ui-layout : BuyerSummit > BuyerSummit
		sb.append("       ,(SELECT VALUE FROM NUMBERCODEVALUE WHERE CODETYPE = 'BUYERSUMMIT' AND CODE = A.ISBUYERSUMMIT AND LANG = '" + conditionList.get(0).get("locale")
		        + "' ) AS BUYERSUMMIT \n");
	    }
	}

	sb.append("   FROM                                           \n");
	sb.append("       KETPROJECTDOCUMENT      A,                 \n");
	sb.append("       KETDocumentCategoryLink B,                 \n");
	sb.append("       KETDOCUMENTCATEGORY     C,                 \n");
	sb.append("       PHASELINK               D,                 \n");
	sb.append("       PHASETEMPLATE           E,                 \n");
	sb.append("       PEOPLE                  F,                 \n");
	sb.append("       WTDocumentMaster        G,                 \n");
	sb.append("       NUMBERCODEVALUE         NCV                \n");

	// Project No 가 있는 경우
	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil queryTable = KETParamMapUtil.getMap(condistion);
	    if (!queryTable.getString("projectNo").equals("")) {
		sb.append("       ,KETDocumentOutputLink   DOL       \n");
		sb.append("       ,ProjectOutput           PO        \n");
		sb.append("       ,E3PSTask                TASK      \n");
		sb.append("       ,ProductProject          PJT       \n");
		sb.append("       ,E3PSProjectMaster       MST       \n");
	    }
	    break;
	}

	sb.append("  WHERE A.LATESTITERATIONINFO = 1                   \n");
	sb.append("    AND A.IDA2A2              = B.IDA3B5            \n");
	sb.append("    AND B.IDA3A5              = C.IDA2A2            \n");
	sb.append("    AND A.IDA3A2STATE         = D.IDA3A5            \n");
	sb.append("    AND D.IDA3B5              = E.IDA2A2            \n");
	sb.append("    AND E.PHASESTATE          = A.STATESTATE        \n");
	sb.append("    AND A.IDA3B2ITERATIONINFO = F.IDA3B4            \n");
	sb.append("    AND A.IDA3MASTERREFERENCE = G.IDA2A2            \n");
	sb.append("    AND C.CATEGORYCODE        =  NCV.CODE           \n");
	sb.append("    AND NCV.LANG              =  '" + conditionList.get(0).get("locale") + "'    \n");

	for (Map<String, Object> condistion : conditionList) {
	    KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

	    if (!map.getString("documentNo").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.DOCUMENTNO", map.getString("documentNo"), true)).append("    \n");
	    }

	    if (map.getStringArray("divisionCode").length > 0) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.DIVISIONCODE", map.getStringArray("divisionCode"), false)).append("    \n");
	    }

	    if (map.getString("islastversion").equals("LATEST")) {
		sb.append("    AND A.VERSIONIDA2VERSIONINFO = (SELECT TO_CHAR(MAX(TO_NUMBER(DOC.VERSIONIDA2VERSIONINFO))) \n");
		sb.append("                                      FROM KETPROJECTDOCUMENT DOC                              \n");
		sb.append("                                     WHERE DOC.IDA3MASTERREFERENCE = A.IDA3MASTERREFERENCE     \n");
		sb.append("                                       AND DOC.LATESTITERATIONINFO = 1)                        \n");
	    }

	    if (map.getString("categoryCode") != null && map.getString("categoryCode").length() > 0) {
		ArrayList<String> categoryCodeList = KETStringUtil.getToken(map.getString("categoryCode"), ",");
		sb.append("    AND EXISTS ( SELECT 1 FROM KETDOCUMENTCATEGORY                                    \n");
		sb.append("                  WHERE CATEGORYCODE = C.CATEGORYCODE                                 \n");
		sb.append("                  START WITH PARENTCATEGORYCODE IN (" + KETStringUtil.getMultiSearchCondition(categoryCodeList) + ") \n");
		sb.append("                CONNECT BY PRIOR CATEGORYCODE = PARENTCATEGORYCODE                    \n");
		sb.append("                  UNION                                                               \n");
		sb.append("                 SELECT 1 FROM KETDOCUMENTCATEGORY                                    \n");
		sb.append("                  WHERE CATEGORYCODE = C.CATEGORYCODE                                 \n");
		sb.append("                    AND CATEGORYCODE IN (" + KETStringUtil.getMultiSearchCondition(categoryCodeList) + ")  )         \n");
	    }

	    if (!map.getString("documentName").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.TITLE", map.getString("documentName"), true)).append("   \n");
	    }

	    if (!map.getString("isReview").equals("") && map.getString("isReview").equals("Y")) {
		sb.append("    AND A.STATESTATE IN ('UNDERREVIEW','APPROVED')                                    \n");
	    } else {
		if (map.getStringArray("authorStatus").length > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.STATESTATE", map.getStringArray("authorStatus"), false)).append("      \n");
		}
	    }

	    if (!map.getString("creator").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("F.ID", map.getString("creator"), false)).append("     \n");
	    }

	    if (!map.getString("predate").equals("")) {
		String predate = map.getString("predate");
		predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		sb.append("    AND A.CREATESTAMPA2 >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')         \n");
	    }

	    if (!map.getString("postdate").equals("")) {
		String postdate = map.getString("postdate");
		postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		sb.append("    AND A.CREATESTAMPA2 <= TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')           \n");
	    }

	    if (!map.getString("isBuyerSummit").equals("0")) {
		sb.append("    AND A.ISBUYERSUMMIT = '" + map.getString("isBuyerSummit") + "'                                  \n");
	    }

	    if (!map.getString("buyerCodeStr").equals("")) {
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.BUYERCODE", map.getString("buyerCodeStr"), false)).append("     \n");
	    }

	    String[] quality = map.getStringArray("quality");
	    if (quality != null && quality.length > 0) {
		sb.append(" AND (                           \n");
		for (int i = 0; i < quality.length; i++) {
		    if (i > 0) {
			sb.append(" OR");
		    }
		    if (quality[i].equals("APQP")) {
			sb.append(" C.ISAPQP = '1'          \n");
		    } else if (quality[i].equals("PSO10")) {
			sb.append(" C.ISPSO10 = '1'         \n");
		    } else if (quality[i].equals("ESIR")) {
			sb.append(" C.ISESIR = '1'          \n");
		    } else if (quality[i].equals("ISIRCar")) {
			sb.append(" C.ISISIRCar = '1'       \n");
		    } else if (quality[i].equals("ISIRElec")) {
			sb.append(" C.ISISIRElec = '1'      \n");
		    } else if (quality[i].equals("ANPQP")) {
			sb.append(" C.ISANPQP = '1'         \n");
		    } else if (quality[i].equals("SYMC")) {
			sb.append(" C.ISSYMC = '1'          \n");
		    } else if (quality[i].equals("DRCheckSheet")) {
			sb.append(" C.ISDRCheckSheet = '1'  \n");
		    }
		}
		sb.append(" )                               \n");
	    }

	    if (!map.getString("projectNo").equals("") && !map.getString("projectNo").equals("projectName")) {
		sb.append("    AND A.IDA2A2    = DOL.IDA3A5      \n");
		sb.append("    AND DOL.IDA3B5  = PO.IDA2A2(+)    \n");
		sb.append("    AND PO.IDA3B5   = TASK.IDA2A2(+)  \n");
		sb.append("    AND TASK.IDA3B4 = PJT.IDA2A2(+)   \n");
		sb.append("    AND PJT.IDA3B8  = MST.IDA2A2(+)   \n");
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MST.PJTNO", map.getString("projectNo"), false)).append("    \n");
		sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MST.PJTNAME", map.getString("projectName"), false)).append("    \n");
	    }
	}

	return sb.toString();
    }

    private String getSearchProjectDocQueryByDocOidJoin(List<Map<String, Object>> conditionList, String query) {

	StringBuffer sb = new StringBuffer();

	sb.append("       SELECT                                     \n");
	// from sub query
	sb.append("        B.CATEGORYNAME     AS CATEGORYNAME        \n");
	sb.append("       ,B.CREATORNAME      AS CREATORNAME         \n");
	sb.append("       ,B.STATE            AS STATE               \n");
	//
	sb.append("       ,A.CLASSNAMEA2A2||':'||A.IDA2A2 AS OID     \n");
	sb.append("       ,A.DOCUMENTNO              AS DOCUMENTNO   \n");
	sb.append("       ,A.TITLE                   AS TITLE        \n");
	sb.append("       ,A.STATESTATE              AS STATESTATE   \n");
	sb.append("       ,A.VERSIONIDA2VERSIONINFO  AS VERSION      \n");
	sb.append("       ,A.DEPTNAME                AS DEPTNAME     \n");
	sb.append("       ,TO_CHAR(A.CREATESTAMPA2, 'YYYY-MM-DD') AS CREATEDATE   \n");
	sb.append("       ,(SELECT VALUE FROM NUMBERCODEVALUE WHERE CODETYPE = 'BUYERSUMMIT' AND CODE = A.ISBUYERSUMMIT AND LANG = '" + conditionList.get(0).get("locale") + "' ) AS BUYERSUMMIT \n");
	// Vault 관련
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3A5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n").append(
	        " WHERE A.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS HOLDEROID \n");
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3B5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n").append(
	        " WHERE A.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS APPDATAOID \n");
	sb.append(" ,(SELECT REVERSE(SUBSTR(REVERSE(APP.FILENAME), 0, INSTR(REVERSE(APP.FILENAME), '.', 1, 1)-1)) \n")
	        .append(" FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE A.IDA2A2 = HOLDER.IDA3A5 AND \n").append(" HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS EXTENSION \n");
	// sb.append("       ,A.HOLDEROID               AS HOLDEROID    \n");
	// sb.append("       ,A.APPDATAOID              AS APPDATAOID   \n");
	// sb.append("       ,A.EXTENSION               AS EXTENSION    \n");
	sb.append(" FROM KETPROJECTDOCUMENT A, \n");
	sb.append(" ( \n");
	sb.append(query);
	sb.append(" ) B \n");
	// sb.append("   ,HOLDERTOCONTENT    holder \n");
	// sb.append("   ,APPLICATIONDATA    app    \n");
	sb.append(" WHERE A.IDA2A2 = B.OID \n");
	// sb.append("   AND A.IDA2A2 = holder.IDA3A5(+) \n");
	// sb.append("   AND holder.IDA3B5 = app.IDA2A2(+) \n");
	// sb.append("   AND app.ROLE = 'PRIMARY'(+)  \n");

	return sb.toString();
    }

    public List<Map<String, Object>> searchProjectDocumentListByExcel(List<Map<String, Object>> conditionList) throws Exception {
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> projectDocumentList = null;

	try {

	    // 쿼리 튜닝
	    String query = getSearchProjectDocQueryBySortNCondition(conditionList, null);
	    query = getSearchProjectDocQueryByDocOidJoinByExcel(conditionList, query);

	    projectDocumentList = oDaoManager.queryForList(query, new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> projectDocument = new HashMap<String, Object>();
		    projectDocument = new HashMap<String, Object>();
		    projectDocument.put("oid", rs.getString("OID"));
		    projectDocument.put("documentNo", rs.getString("DOCUMENTNO"));
		    projectDocument.put("categoryName", rs.getString("CATEGORYNAME"));
		    projectDocument.put("title", rs.getString("TITLE"));
		    projectDocument.put("stateState", rs.getString("STATESTATE"));
		    projectDocument.put("state", rs.getString("STATE"));
		    projectDocument.put("ver", rs.getString("VERSION"));
		    projectDocument.put("creatorName", rs.getString("CREATORNAME"));
		    projectDocument.put("deptName", rs.getString("DEPTNAME"));
		    projectDocument.put("createDate", rs.getString("CREATEDATE"));
		    projectDocument.put("buyerSummit", rs.getString("BUYERSUMMIT"));
		    // added by omjae, 2013-07-13 첨부파일//
		    projectDocument.put("holderOid", rs.getString("HOLDEROID"));
		    projectDocument.put("appDataOid", rs.getString("APPDATAOID"));
		    projectDocument.put("extension", rs.getString("EXTENSION"));
		    // Excel 전용 추가
		    projectDocument.put("docCatePath3", rs.getString("DOCCATEPATH3"));
		    projectDocument.put("docCatePath2", rs.getString("DOCCATEPATH2"));
		    projectDocument.put("docCatePath1", rs.getString("DOCCATEPATH1"));
		    // projectDocument.put("partNo", rs.getString("PARTNO"));
		    projectDocument.put("appMastOid", (rs.getString("AppMastId") == null ? null : "e3ps.wfm.entity.KETWfmApprovalMaster:" + rs.getString("AppMastId")));

		    return projectDocument;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return projectDocumentList == null ? new ArrayList<Map<String, Object>>() : projectDocumentList;
    }

    private String getSearchProjectDocQueryByDocOidJoinByExcel(List<Map<String, Object>> conditionList, String query) {

	StringBuffer sb = new StringBuffer();

	sb.append("       SELECT                                     \n");
	// from sub query
	sb.append("        B.CATEGORYNAME     AS CATEGORYNAME        \n");
	sb.append("       ,B.CREATORNAME      AS CREATORNAME         \n");
	sb.append("       ,B.STATE            AS STATE               \n");
	//
	sb.append("       ,A.CLASSNAMEA2A2||':'||A.IDA2A2 AS OID     \n");
	sb.append("       ,A.DOCUMENTNO              AS DOCUMENTNO   \n");
	sb.append("       ,A.TITLE                   AS TITLE        \n");
	sb.append("       ,A.STATESTATE              AS STATESTATE   \n");
	sb.append("       ,A.VERSIONIDA2VERSIONINFO  AS VERSION      \n");
	sb.append("       ,A.DEPTNAME                AS DEPTNAME     \n");
	sb.append("       ,TO_CHAR(A.CREATESTAMPA2, 'YYYY-MM-DD') AS CREATEDATE   \n");
	sb.append("       ,(SELECT VALUE FROM NUMBERCODEVALUE WHERE CODETYPE = 'BUYERSUMMIT' AND CODE = A.ISBUYERSUMMIT \n");
	sb.append("                            AND LANG = '" + conditionList.get(0).get("locale") + "' ) AS BUYERSUMMIT \n");
	// Vault 관련
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3A5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n").append(
	        " WHERE A.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS HOLDEROID \n");
	sb.append(" ,(SELECT HOLDER.CLASSNAMEKEYROLEAOBJECTREF || ':' || HOLDER.IDA3B5 FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP \n").append(
	        " WHERE A.IDA2A2 = HOLDER.IDA3A5 AND HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS APPDATAOID \n");
	sb.append(" ,(SELECT REVERSE(SUBSTR(REVERSE(APP.FILENAME), 0, INSTR(REVERSE(APP.FILENAME), '.', 1, 1)-1)) \n")
	        .append(" FROM HOLDERTOCONTENT HOLDER,APPLICATIONDATA APP WHERE A.IDA2A2 = HOLDER.IDA3A5 AND \n").append(" HOLDER.IDA3B5 = APP.IDA2A2 AND APP.ROLE = 'PRIMARY' ) AS EXTENSION \n");
	// sb.append("       ,A.HOLDEROID               AS HOLDEROID    \n");
	// sb.append("       ,A.APPDATAOID              AS APPDATAOID   \n");
	// sb.append("       ,A.EXTENSION               AS EXTENSION    \n");
	// 분류체계
	// sb.append("       ,(SELECT CategoryName FROM KETDocumentCategory WHERE IDA2A2 IN (SELECT IDA3A5 FROM KETDocumentCategoryLink \n");
	// sb.append("                                                                       WHERE IDA3B5 = A.IDA2A2 )) AS DOCCATEPATH3 \n");
	// sb.append("       ,(SELECT CategoryName FROM KETDocumentCategory WHERE CategoryCode in ( SELECT ParentCategoryCode \n");
	// sb.append("                             FROM KETDocumentCategory WHERE IDA2A2 IN (SELECT IDA3A5 FROM KETDocumentCategoryLink \n");
	// sb.append("                                                      WHERE IDA3B5 = A.IDA2A2))) AS DOCCATEPATH2 \n");
	// sb.append("       ,(SELECT CategoryName FROM KETDocumentCategory WHERE CategoryCode in ( SELECT ParentCategoryCode \n");
	// sb.append("                             FROM KETDocumentCategory WHERE CategoryCode in ( SELECT ParentCategoryCode FROM \n");
	// sb.append("                             KETDocumentCategory WHERE IDA2A2 IN (SELECT IDA3A5 FROM KETDocumentCategoryLink \n");
	// sb.append("                                                      WHERE IDA3B5 = A.IDA2A2)))) AS DOCCATEPATH1 \n");
	sb.append("       , CA1.CategoryName AS DOCCATEPATH3 \n");
	sb.append("       , CA2.CategoryName AS DOCCATEPATH2 \n");
	sb.append("       , CA3.CategoryName AS DOCCATEPATH1 \n");
	// 부품 번호
	// sb.append("       ,( SELECT LISTAGG(WTPARTNUMBER, ',') WITHIN GROUP(ORDER BY WTPARTNUMBER) FROM WTPARTMASTER WHERE IDA2A2 \n");
	// sb.append("          IN ( SELECT DISTINCT IDA3MASTERREFERENCE FROM WTPART WHERE IDA2A2 IN ( SELECT IDA3B5 FROM \n");
	// sb.append("          KETDocumentPartLink WHERE IDA3A5 = A.IDA2A2 ) ) ) AS PARTNO \n");
	// sb.append("       ,(CASE WHEN STATESTATE = 'APPROVED' THEN \n");
	sb.append("       ,( \n");
	sb.append("           NVL( \n");
	sb.append("             (SELECT MAX(IDA2A2) FROM KETWfmApprovalMaster WHERE CLASSNAMEKEYA4 IN \n");
	sb.append("               ( \n");
	sb.append("               SELECT 'OR:'||CLASSNAMEKEYROLEAOBJECTREF||':'||IDA3A5 FROM KETWfmMultiReqDocLink WHERE IDA3B5 = A.IDA2A2 \n");
	sb.append("               ) \n");
	sb.append("             ) \n");
	sb.append("           , \n");
	sb.append("           (SELECT MAX(IDA2A2) FROM KETWfmApprovalMaster WHERE CLASSNAMEKEYA4 = 'OR:'||A.CLASSNAMEA2A2||':'||A.IDA2A2 \n");
	sb.append("                                    OR CLASSNAMEKEYA4 = 'VR:'||A.CLASSNAMEA2A2||':'||A.BRANCHIDITERATIONINFO ) \n");
	sb.append("            ) \n");
	// sb.append("           ELSE NULL  END \n");
	sb.append("        ) AppMastId \n");
	sb.append(" FROM KETPROJECTDOCUMENT A, \n");
	sb.append(" ( \n");
	sb.append(query);
	sb.append(" ) B \n");
	sb.append(" ,KETDocumentCategory     CA1 \n");
	sb.append(" ,KETDocumentCategoryLink DC1 \n");
	sb.append(" ,KETDocumentCategory     CA2 \n");
	sb.append(" ,KETDocumentCategory     CA3 \n");
	// sb.append("   ,HOLDERTOCONTENT    holder \n");
	// sb.append("   ,APPLICATIONDATA    app    \n");
	sb.append(" WHERE A.IDA2A2 = B.OID \n");
	// sb.append("   AND A.IDA2A2 = holder.IDA3A5(+) \n");
	// sb.append("   AND holder.IDA3B5 = app.IDA2A2(+) \n");
	// sb.append("   AND app.ROLE = 'PRIMARY'(+)  \n");
	sb.append("   AND A.IDA2A2 = DC1.IDA3B5(+) \n");
	sb.append("   AND DC1.IDA3A5 = CA1.IDA2A2(+) \n");
	sb.append("   AND CA1.ParentCategoryCode = CA2.CategoryCode(+) \n");
	sb.append("   AND CA2.ParentCategoryCode = CA3.CategoryCode(+) \n");

	return sb.toString();
    }

}
