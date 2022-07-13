package e3ps.dms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.shared.log.Kogger;

/**
 * [PLM System 1차개선] 클래스명 : DocumentDao 설명 : 문서 관리 Dao 수정자 : tklee 작성일자 : 2014. 6. 27
 */
public class DocumentDao {

    /**
     * 함수명 : searchProjectDocumentList 설명 : 개발문서 검색
     * 
     * @param conditionList
     * @return List<Map<String, Object>> 검색된 개발문서 List
     * @throws Exception
     */
    public List<Map<String, Object>> searchProjectDocumentList(List<Map<String, Object>> conditionList) throws Exception {

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> projectDocumentList = null;

	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT A.OID                     AS OID          \n");
	    sb.append("       ,A.DOCUMENTNO              AS DOCUMENTNO   \n");
	    sb.append("       ,NCV.VALUE                 AS CATEGORYNAME \n");
	    sb.append("       ,A.TITLE                   AS TITLE        \n");
	    sb.append("       ,A.STATESTATE              AS STATESTATE   \n");
	    sb.append("       ,E.NAME                    AS STATE        \n");
	    sb.append("       ,A.VERSIONIDA2VERSIONINFO  AS VERSION      \n");
	    sb.append("       ,F.NAME                    AS CREATORNAME  \n");
	    sb.append("       ,A.DEPTNAME                AS DEPTNAME     \n");
	    sb.append("       ,A.CREATESTAMPA2           AS CREATEDATE   \n");
	    sb.append("       ,A.BUYERSUMMIT             AS BUYERSUMMIT  \n");
	    // added by omjae, 2013-07-13 //
	    sb.append("       ,A.HOLDEROID               AS HOLDEROID    \n");
	    sb.append("       ,A.APPDATAOID              AS APPDATAOID   \n");
	    sb.append("       ,A.EXTENSION               AS EXTENSION    \n");
	    // //////////////////////////////
	    sb.append("   FROM (                                         \n");
	    sb.append("         SELECT DOC.CLASSNAMEA2A2||':'||DOC.IDA2A2 OID    \n");
	    sb.append("               ,DOC.VERSIONIDA2VERSIONINFO            \n");
	    sb.append("               ,DOC.IDA2A2                            \n");
	    sb.append("               ,DOC.IDA3A2STATE                       \n");
	    sb.append("               ,DOC.IDA3B2ITERATIONINFO               \n");
	    sb.append("               ,DOC.IDA3MASTERREFERENCE               \n");
	    sb.append("               ,DOC.STATESTATE                        \n");
	    sb.append("               ,DOC.LATESTITERATIONINFO               \n");
	    sb.append("               ,DOC.DIVISIONCODE                      \n");
	    sb.append("               ,DOC.DOCUMENTNO                        \n");
	    sb.append("               ,DOC.TITLE                             \n");
	    sb.append("               ,DOC.DEPTNAME                          \n");
	    sb.append("               ,TO_CHAR(DOC.CREATESTAMPA2, 'YYYY-MM-DD')                   AS CREATESTAMPA2      \n");
	    sb.append("               ,DOC.ISBUYERSUMMIT                     \n");
	    sb.append("               ,FN_GET_NUMBERCODEVALUE('BUYERSUMMIT', DOC.ISBUYERSUMMIT, '" + conditionList.get(0).get("locale") + "') AS BUYERSUMMIT    \n");
	    sb.append("               ,DOC.BUYERCODE                         \n");
	    // added by omjae, 2013-07-13 첨부파일//
	    sb.append("               ,holder.CLASSNAMEKEYROLEAOBJECTREF || ':' || holder.IDA3A5  AS HOLDEROID          \n");
	    sb.append("               ,holder.CLASSNAMEKEYROLEBOBJECTREF || ':' || holder.IDA3B5  AS APPDATAOID         \n");
	    sb.append("               ,REVERSE(SUBSTR(REVERSE(app.filename), 0, INSTR(REVERSE(app.filename), '.', 1, 1)-1)) AS EXTENSION   \n");
	    // //////////////////////////////
	    sb.append("           FROM KETPROJECTDOCUMENT DOC              \n");
	    // added by omjae, 2013-07-13 첨부파일//
	    sb.append("               ,HOLDERTOCONTENT    holder           \n");
	    sb.append("               ,APPLICATIONDATA    app              \n");
	    // //////////////////////////////
	    sb.append("          WHERE LATESTITERATIONINFO = 1             \n");
	    // added by omjae, 2013-07-13 첨부파일//
	    sb.append("            AND DOC.IDA2A2 = holder.IDA3A5          \n");
	    sb.append("            AND holder.IDA3B5 = app.IDA2A2          \n");
	    sb.append("            AND app.ROLE = 'PRIMARY'                \n");
	    // //////////////////////////////
	    sb.append("          ) A,                                      \n");
	    sb.append("          KETDocumentCategoryLink B,                \n");
	    sb.append("          KETDOCUMENTCATEGORY     C,                \n");
	    sb.append("          PHASELINK               D,                \n");
	    sb.append("          PHASETEMPLATE           E,                \n");
	    sb.append("          PEOPLE                  F,                \n");
	    sb.append("          WTDocumentMaster        G,                \n");
	    sb.append("          NUMBERCODEVALUE         NCV               \n");

	    // Project No 가 있는 경우
	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil queryTable = KETParamMapUtil.getMap(condistion);
		if (!queryTable.getString("projectNo").equals("")) {
		    sb.append("         ,KETDocumentOutputLink   DOL           \n");
		    sb.append("         ,ProjectOutput           PO            \n");
		    sb.append("         ,E3PSTask                TASK          \n");
		    sb.append("         ,ProductProject          PJT           \n");
		    sb.append("         ,E3PSProjectMaster       MST           \n");
		}
		break;
	    }
	    sb.append("  WHERE A.IDA2A2              = B.IDA3B5            \n");
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
		    sb.append("    AND A.VERSIONIDA2VERSIONINFO = (SELECT TO_CHAR(MAX(TO_NUMBER(DOC.VERSIONIDA2VERSIONINFO)))   \n");
		    sb.append("                                      FROM KETPROJECTDOCUMENT DOC                           \n");
		    sb.append("                                     WHERE DOC.IDA3MASTERREFERENCE = A.IDA3MASTERREFERENCE  \n");
		    sb.append("                                       AND DOC.LATESTITERATIONINFO = 1)                     \n");
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
		    sb.append("    AND A.STATESTATE IN ('UNDERREVIEW','APPROVED')                                                               \n");
		} else {
		    // ECN에서 문서검색시 모든 상태의 문서를 조회하기 위한 파라미터 -> authorStatus : ALL (TODoEcnForm.jsp에서 사용함) 2016.03.11 by babiss

		    if (map.getStringArray("authorStatus").length > 0) {
			String authorStatus[] = map.getStringArray("authorStatus");
			if (!"ALL".equals(authorStatus[0])) {
			    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.STATESTATE", map.getStringArray("authorStatus"), false)).append("      \n");
			}

		    }
		}

		if (!map.getString("creatorName").equals("")) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("F.NAME", map.getString("creatorName"), false)).append("     \n");
		}

		/*
	         * java.sql.SQLDataException: ORA-01861: literal does not match format string 2014-05-14 Modified by Jason, Han
	         */
		if (!map.getString("predate").equals("")) {
		    String predate = map.getString("predate");
		    predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		    sb.append("    AND TO_DATE(A.CREATESTAMPA2, 'YYYY-MM-DD') >= TO_DATE('" + predate + "' || '000001','YYYYMMDDHH24MISS')    \n");
		}
		if (!map.getString("postdate").equals("")) {
		    String postdate = map.getString("postdate");
		    postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		    sb.append("    AND TO_DATE(A.CREATESTAMPA2, 'YYYY-MM-DD') < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
		}
		/*
	         * end
	         */

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

		if (!map.getString("projectNo").equals("")) {
		    sb.append("    AND A.IDA2A2    = DOL.IDA3A5      \n");
		    sb.append("    AND DOL.IDA3B5  = PO.IDA2A2(+)    \n");
		    sb.append("    AND PO.IDA3B5   = TASK.IDA2A2(+)  \n");
		    sb.append("    AND TASK.IDA3B4 = PJT.IDA2A2(+)   \n");
		    sb.append("    AND PJT.IDA3B8  = MST.IDA2A2(+)   \n");
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("MST.PJTNO", map.getString("projectNo"), false)).append("    \n");
		}
	    }
	    sb.append("ORDER BY A.CREATESTAMPA2 DESC \n");

	    projectDocumentList = oDaoManager.queryForList(sb.toString(), new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> projectDocument = new HashMap<String, Object>();
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

    /**
     * 함수명 : searchTechDocumentList 설명 : 기술문서 검색
     * 
     * @param conditionList
     * @return List<Map<String, Object>> 검색된 기술문서List
     * @throws Exception
     */
    public List<Map<String, Object>> searchTechDocumentList(List<Map<String, Object>> conditionList) throws Exception {

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> techDocumentList = null;

	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	Department department = null;
	department = (Department) DepartmentHelper.manager.getDepartment(user);
	String deptCode = department.getCode();

	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT A.OID                                 AS OID          \n");
	    sb.append("       ,G.WTDOCUMENTNUMBER                    AS DOCUMENTNO   \n");
	    sb.append("       ,A.TITLE                               AS TITLE        \n");
	    sb.append("       ,NCV.VALUE                             AS CATEGORYNAME \n");
	    sb.append("       ,A.STATESTATE                          AS STATESTATE   \n");
	    sb.append("       ,E.NAME                                AS STATE        \n");
	    sb.append("       ,A.VERSIONIDA2VERSIONINFO              AS VER          \n");
	    sb.append("       ,F.NAME                                AS CREATORNAME  \n");
	    sb.append("       ,A.DEPTNAME                            AS DEPTNAME     \n");
	    sb.append("       ,A.CREATESTAMPA2                       AS CREATEDATE   \n");
	    sb.append("       ,A.HOLDEROID                           AS HOLDEROID    \n");
	    sb.append("       ,A.APPDATAOID                          AS APPDATAOID   \n");
	    sb.append("       ,A.EXTENSION                           AS EXTENSION    \n");
	    sb.append("       ,A.ATTRIBUTE1                          AS ISDESIGN     \n");
	    sb.append("       ,A.ATTRIBUTE3                          AS DUTY     \n");
	    sb.append("   FROM (                                                     \n");
	    sb.append("         SELECT DOC.CLASSNAMEA2A2||':'||DOC.IDA2A2 OID        \n");
	    sb.append("               ,DOC.VERSIONIDA2VERSIONINFO                    \n");
	    sb.append("               ,DOC.IDA2A2                                    \n");
	    sb.append("               ,DOC.IDA3A2STATE                               \n");
	    sb.append("               ,DOC.IDA3B2ITERATIONINFO                       \n");
	    sb.append("               ,DOC.IDA3MASTERREFERENCE                       \n");
	    sb.append("               ,DOC.STATESTATE                                \n");
	    sb.append("               ,DOC.LATESTITERATIONINFO                       \n");
	    sb.append("               ,DOC.DIVISIONCODE                              \n");
	    sb.append("               ,DOC.TITLE                                     \n");
	    sb.append("               ,DOC.DEPTNAME                                  \n");
	    sb.append("               ,DOC.ATTRIBUTE1                                \n");
	    sb.append("               ,DOC.ATTRIBUTE3                                \n");
	    sb.append("               ,DOC.ATTRIBUTE2                                \n");
	    sb.append("               ,TO_CHAR(DOC.CREATESTAMPA2, 'YYYY-MM-DD')                   AS CREATESTAMPA2      \n");
	    sb.append("               ,holder.CLASSNAMEKEYROLEAOBJECTREF || ':' || holder.IDA3A5  AS HOLDEROID          \n");
	    sb.append("               ,holder.CLASSNAMEKEYROLEBOBJECTREF || ':' || holder.IDA3B5  AS APPDATAOID         \n");
	    sb.append("               ,REVERSE(SUBSTR(REVERSE(app.filename), 0, INSTR(REVERSE(app.filename), '.', 1, 1)-1)) AS EXTENSION   \n");
	    sb.append("           FROM KETTechnicalDOCUMENT DOC                      \n");
	    sb.append("               ,HOLDERTOCONTENT    holder                     \n");
	    sb.append("               ,APPLICATIONDATA    app                        \n");
	    sb.append("          WHERE LATESTITERATIONINFO = 1                       \n");
	    sb.append("            AND DOC.IDA2A2 = holder.IDA3A5                    \n");
	    sb.append("            AND holder.IDA3B5 = app.IDA2A2                    \n");
	    sb.append("            AND app.ROLE = 'PRIMARY'                          \n");
	    sb.append("        ) A,                                                  \n");
	    sb.append("        KETTechnicalCategoryLink B,                           \n");
	    sb.append("        KETDOCUMENTCATEGORY      C,                           \n");
	    sb.append("        PHASELINK                D,                           \n");
	    sb.append("        PHASETEMPLATE            E,                           \n");
	    sb.append("        PEOPLE                   F,                           \n");
	    sb.append("        WTDocumentMaster         G,                           \n");
	    sb.append("        NUMBERCODEVALUE          NCV                          \n");
	    sb.append("  WHERE 1=1                                                   \n");
	    sb.append("    AND A.IDA2A2              = B.IDA3B5                      \n");
	    sb.append("    AND B.IDA3A5              = C.IDA2A2                      \n");
	    sb.append("    AND A.IDA3A2STATE         = D.IDA3A5                      \n");
	    sb.append("    AND D.IDA3B5              = E.IDA2A2                      \n");
	    sb.append("    AND E.PHASESTATE          = A.STATESTATE                  \n");
	    sb.append("    AND A.IDA3B2ITERATIONINFO = F.IDA3B4                      \n");
	    sb.append("    AND A.IDA3MASTERREFERENCE = G.IDA2A2                      \n");
	    sb.append("    AND C.CATEGORYCODE        =  NCV.CODE           \n");
	    sb.append("    AND NCV.LANG              =  '" + conditionList.get(0).get("locale") + "'    \n");

	    if (!CommonUtil.isAdmin() && !CommonUtil.isMember("설계가이드관리") && !CommonUtil.isMember("설계가이드임원")) {
		// sb.append("    AND (( NVL(A.ATTRIBUTE1,'N') != 'Y') OR (NVL(C.ATTRIBUTE1,'N') = 'Y' AND A.ATTRIBUTE2 = '" + deptCode +
		// "'  ))    \n");
		sb.append("    AND (( NVL(A.ATTRIBUTE1,'N') != 'Y') OR (NVL(C.ATTRIBUTE1,'N') = 'Y' AND A.ATTRIBUTE2 in \n");
		sb.append("(SELECT CODE FROM DEPARTMENT                     \n");
		sb.append("  CONNECT BY PRIOR IDA3PARENTREFERENCE = IDA2A2  \n");
		sb.append("  START WITH CODE = '" + deptCode + "')  ))      \n");
	    }
	    WTUser createUser = null;

	    for (Map<String, Object> condistion : conditionList) {
		KETParamMapUtil map = KETParamMapUtil.getMap(condistion);

		if (map.getStringArray("divisionCode").length > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("DIVISIONCODE", map.getStringArray("divisionCode"), false)).append("    \n");
		}

		if (map.getString("islastversion").equals("LATEST")) {
		    sb.append("    AND A.VERSIONIDA2VERSIONINFO = (SELECT TO_CHAR(MAX(TO_NUMBER(DOC.VERSIONIDA2VERSIONINFO)))   \n");
		    sb.append("                                      FROM KETTECHNICALDOCUMENT DOC                         \n");
		    sb.append("                                     WHERE DOC.IDA3MASTERREFERENCE = A.IDA3MASTERREFERENCE  \n");
		    sb.append("                                       AND DOC.LATESTITERATIONINFO = 1)                     \n");
		}

		if (map.getString("documentNo").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("G.WTDOCUMENTNUMBER", map.getString("documentNo"), true)).append("     \n");
		}

		if ((map.getString("categoryCode")).length() > 0) {
		    ArrayList<String> categoryCodeList = KETStringUtil.getToken(map.getString("categoryCode"), ",");
		    sb.append("    AND EXISTS ( SELECT 1 FROM KETDOCUMENTCATEGORY                         \n");
		    sb.append("              WHERE CATEGORYCODE = C.CATEGORYCODE                       \n");
		    sb.append("              START WITH PARENTCATEGORYCODE IN (" + KETStringUtil.getMultiSearchCondition(categoryCodeList) + ") \n");
		    sb.append("              CONNECT BY PRIOR CATEGORYCODE = PARENTCATEGORYCODE        \n");
		    sb.append("              UNION                                                     \n");
		    sb.append("              SELECT 1 FROM KETDOCUMENTCATEGORY                         \n");
		    sb.append("              WHERE CATEGORYCODE = C.CATEGORYCODE                       \n");
		    sb.append("              AND CATEGORYCODE IN (" + KETStringUtil.getMultiSearchCondition(categoryCodeList) + ")  )           \n");
		}

		if (map.getString("documentName").length() > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.TITLE", map.getString("documentName"), true)).append("     \n");
		}

		if (map.getStringArray("authorStatus").length > 0) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("E.PHASESTATE", map.getStringArray("authorStatus"), false)).append("    \n");
		}

		if (map.getString("creator").length() > 0) {
		    String creator = map.getString("creator");
		    if (creator.indexOf("WTUser:") > 0) {
			createUser = (WTUser) CommonUtil.getObject(creator);
			sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("F.ID", createUser.getName(), false)).append("     \n");
		    } else {
			sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("F.ID", map.getString("creator"), false)).append("     \n");
		    }
		}

		if (!map.getString("deptName").equals("")) {
		    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("A.DEPTNAME", map.getString("deptName"), false)).append("     \n");
		}

		// KETS 사업부 관련 필터링
		sb.append(CommonUtil.ketsUserListWhereStr("F.IDA3B4"));

		/*
	         * java.sql.SQLDataException: ORA-01861: literal does not match format string 2014-05-14 Modified by Jason, Han
	         */
		if (!map.getString("predate").equals("")) {
		    String predate = map.getString("predate");
		    predate = predate.substring(0, 4) + predate.substring(5, 7) + predate.substring(8, 10);
		    sb.append("    AND TO_DATE(A.CREATESTAMPA2, 'YYYY-MM-DD') >= TO_DATE('" + predate + "' || '000000','YYYYMMDDHH24MISS')    \n");
		}
		if (!map.getString("postdate").equals("")) {
		    String postdate = map.getString("postdate");
		    postdate = postdate.substring(0, 4) + postdate.substring(5, 7) + postdate.substring(8, 10);
		    sb.append("    AND TO_DATE(A.CREATESTAMPA2, 'YYYY-MM-DD') < TO_DATE('" + postdate + "' || '235959','YYYYMMDDHH24MISS')    \n");
		}
		/*
	         * end
	         */
	    }

	    techDocumentList = oDaoManager.queryForList(sb.toString(), new RowSetStrategy<Map<String, Object>>() {

		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> techDocument = new HashMap<String, Object>();
		    techDocument.put("oid", (String) rs.getString("OID"));
		    techDocument.put("documentNo", (String) rs.getString("DOCUMENTNO"));
		    techDocument.put("title", (String) rs.getString("TITLE"));
		    techDocument.put("categoryName", (String) rs.getString("CATEGORYNAME"));
		    techDocument.put("stateState", (String) rs.getString("STATESTATE"));
		    techDocument.put("state", (String) rs.getString("STATE"));
		    techDocument.put("ver", (String) rs.getString("VER"));
		    techDocument.put("creatorName", (String) rs.getString("CREATORNAME"));
		    techDocument.put("deptName", (String) rs.getString("DEPTNAME"));
		    techDocument.put("createDate", (String) rs.getString("CREATEDATE"));
		    techDocument.put("holderOid", (String) rs.getString("HOLDEROID"));
		    techDocument.put("appDataOid", (String) rs.getString("APPDATAOID"));
		    techDocument.put("extension", (String) rs.getString("EXTENSION"));
		    techDocument.put("ISDESIGN", (String) rs.getString("ISDESIGN"));
		    techDocument.put("DUTY", (String) rs.getString("DUTY"));

		    return techDocument;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return techDocumentList == null ? new ArrayList<Map<String, Object>>() : techDocumentList;
    }

    /**
     * 함수명 : searchProjectDocumentList 설명 : 개발문서 검색
     * 
     * @param conditionList
     * @return List<Map<String, Object>> 문서분류체계
     * @throws Exception
     */
    public List<Map<String, Object>> searchCategoryList(KETParamMapUtil map) throws Exception {

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<Map<String, Object>> returnObjList = null;

	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT DC.CATEGORYCODE         AS CATEGORYCODE                         \n");
	    sb.append("       ,NCV.VALUE               AS CATEGORYNAME                         \n");
	    sb.append("       ,DC.PARENTCATEGORYCODE   AS PARENTCODE                           \n");
	    sb.append("       ,DECODE(DC.ISDRCHECKSHEET, '1', 'true', 'false') AS ISDRCHECK    \n");
	    sb.append("   FROM KETDOCUMENTCATEGORY DC                                          \n");
	    sb.append("       ,NUMBERCODEVALUE     NCV                                         \n");
	    sb.append("  WHERE 1=1                                                             \n");
	    sb.append("    AND DC.CATEGORYCODE        = NCV.CODE                               \n");
	    sb.append("    AND NCV.CODETYPE           = 'DOCUMENTCATEGORY'                     \n");
	    sb.append("    AND DC.DOCTYPECODE         = '" + map.getString("docTypeCode") + "' \n");
	    sb.append("    AND NCV.LANG               = '" + map.getString("locale") + "'      \n");
	    sb.append("    AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("DC.PARENTCATEGORYCODE", map.getString("parentCode"), false)).append("     \n");
	    sb.append("  ORDER BY TO_NUMBER(DC.SORTNO), NCV.VALUE                              \n");

	    returnObjList = oDaoManager.queryForList(sb.toString(), new RowSetStrategy<Map<String, Object>>() {
		@Override
		public Map<String, Object> mapRow(ResultSet rs) throws SQLException {

		    Map<String, Object> returnObj = new HashMap<String, Object>();
		    returnObj.put("categoryCode", rs.getString("CATEGORYCODE"));
		    returnObj.put("categoryName", rs.getString("CATEGORYNAME"));
		    returnObj.put("parentCode", rs.getString("PARENTCODE"));
		    returnObj.put("isDrCheck", rs.getString("ISDRCHECK"));

		    return returnObj;
		}
	    });

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return returnObjList == null ? new ArrayList<Map<String, Object>>() : returnObjList;
    }

    public int isUseDocCategory(String categoryCode) throws Exception {

	int iCnt = 0;

	StringBuffer sb = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List<String> alBindList = new ArrayList<String>();

	try {

	    sb = new StringBuffer();
	    sb.append(" SELECT * FROM ProjectOutput WHERE OUTPUTKEYTYPE = ? \n");

	    alBindList.add(categoryCode);
	    iCnt = oDaoManager.queryForCount(sb.toString(), alBindList);

	} catch (SQLException se) {
	    Kogger.error(getClass(), se);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return iCnt;
    }
}
