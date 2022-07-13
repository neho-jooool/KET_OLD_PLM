package ext.ket.cost.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.WTObject;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.ibm.icu.math.BigDecimal;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.query.SearchUtil;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.AuthHandler;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.NumberCodeUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskMemberLink;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.TaskUserHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.cost.code.service.CostCodeHelper;
import ext.ket.cost.entity.CSInfo;
import ext.ket.cost.entity.CSInfoItem;
import ext.ket.cost.entity.CSInfoItemDTO;
import ext.ket.cost.entity.CostAnalysis;
import ext.ket.cost.entity.CostAttribute;
import ext.ket.cost.entity.CostCurrencyInfo;
import ext.ket.cost.entity.CostFormula;
import ext.ket.cost.entity.CostInvestInfo;
import ext.ket.cost.entity.CostMaterialInfo;
import ext.ket.cost.entity.CostMaterialLink;
import ext.ket.cost.entity.CostPart;
import ext.ket.cost.entity.CostPartType;
import ext.ket.cost.entity.CostReduceLink;
import ext.ket.cost.entity.CostReport;
import ext.ket.cost.entity.PartList;
import ext.ket.cost.entity.PjtMasterPartListLink;
import ext.ket.cost.entity.ProductMaster;
import ext.ket.cost.service.CostHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.project.program.service.ProgramHelper;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.util.ComparatorUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 3. 6. 오전 9:18:32
 * @Pakage ext.ket.cost.util
 * @class CostUtil
 *********************************************************/
public class CostUtil implements RemoteAccess {

    private static final Logger LOGGER = Logger.getLogger(CostUtil.class);

    public static final CostUtil manager = new CostUtil();

    public static final String MATERIAL_COST = "MATERIAL_COST"; // 재료비
    public static final String LABOR_COST = "LABOR_COST"; // 노무비
    public static final String EXPENSE = "EXPENSE"; // 경비
    public static final String MAINTENENCE_COST = "MAINTENENCE_COST"; // 관리비
    public static final String PRODUCTION = "PRODUCTION"; // 생산량
    public static final String FORMULATYPE = "FORMULATYPE"; // 수식구분
    public static final String PRODUCTTYPE = "PRODUCTTYPE"; // 제품구분

    public static final String NMETALCOST = "NMETALCOST"; // 비철단가
    public static final String PLATINGCOST = "PLATINGCOST"; // 도금단가
    public static final String CUTTINGCOST = "CUTTINGCOST"; // 절단비
    public static final String PROFITCOST = "PROFITCOST"; // 수지단가
    public static final String EXRATECOST = "EXRATECOST"; // 환율금액
    public static final String SCRAPRECYCLECOST = "SCRAPRECYCLECOST"; // 비철 스크랩재생비
    public static final String SCRAPSALESCOST = "SCRAPSALESCOST"; // 비철스크랩판매비

    public static final String FORMULASTATE_INWORK = "INWORK";
    public static final String FORMULASTATE_COMPLETED = "COMPLETED";

    public List<Map<String, Object>> costPartInfoExtract(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	String pjtGb = (String) reqMap.get("pjtGb");

	String partNos = (String) reqMap.get("partNos");
	String projectOids = (String) reqMap.get("projectOids");
	String productProjectOidPartNos = (String) reqMap.get("productProjectOidPartNos");

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT PARTNO,IDA2A2 FROM COSTPART 																									  \n ");
	    sql.append("  WHERE IDA3B4 IN (" + projectOids
		    + ") AND IDA3PARENTREFERENCE = 0 AND CASEORDER = 1 AND LASTEST = 1                                                \n");
	    sql.append("    AND (PARTNO,VERSION) IN ( SELECT PARTNO,MAX(VERSION) AS MAXVERSION FROM COSTPART                                                                   \n");

	    if ("착수".equals(pjtGb)) {
		sql.append(" WHERE IDA3B4||REALPARTNO IN (" + productProjectOidPartNos + ")");
	    } else {
		sql.append(" WHERE IDA3B4 IN (" + projectOids
		        + ") AND IDA3PARENTREFERENCE = 0 AND CASEORDER = 1 AND LASTEST = 1                   \n");
	    }

	    // if ("착수".equals(pjtGb)) {
	    // sql.append("                                 AND (REALPARTNO IN (" + partNos + ") OR SUBSTR(REALPARTNO,2) IN (" + partNos
	    // + ") ) \n");
	    // }

	    if ("검토".equals(pjtGb)) {
		sql.append("                                 AND PARTNO IN (" + partNos
		        + ")                                                                           \n");
	    }

	    sql.append("                            GROUP BY PARTNO )                                                                                                          \n");
	    if ("착수".equals(pjtGb)) {
		// sql.append("                                 AND (REALPARTNO IN (" + partNos + ") OR SUBSTR(REALPARTNO,2) IN (" +
		// partNos+ ") ) \n");
		sql.append(" AND IDA3B4||REALPARTNO IN (" + productProjectOidPartNos + ")");
	    }

	    System.out.println("sql = " + sql.toString());
	    rs = stat.executeQuery(sql.toString());

	    while (rs.next()) {

		String PARTNO = rs.getString("PARTNO");
		String IDA2A2 = rs.getString("IDA2A2");

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("PARTNO", PARTNO);
		map.put("IDA2A2", IDA2A2);

		list.add(map);
	    }

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

    public List<Map<String, Object>> getCostInfoList(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	String ProductCostPartOids = "";
	String RiviewCostPartOids = "";

	String partNos = StringUtils.defaultIfEmpty((String) reqMap.get("partNos"), "''");
	String productProjectOids = StringUtils.defaultIfEmpty((String) reqMap.get("productProjectOids"), "''");
	String reviewProjectOids = StringUtils.defaultIfEmpty((String) reqMap.get("reviewProjectOids"), "''");
	String productProjectOidPartNos = StringUtils.defaultIfEmpty((String) reqMap.get("productProjectOidPartNos"), "''");

	if (StringUtils.isEmpty(partNos)) {
	    return list;
	}

	Map<String, Object> extractMap = new HashMap<String, Object>();

	extractMap.put("partNos", partNos);
	extractMap.put("pjtGb", "착수");
	extractMap.put("projectOids", productProjectOids);
	extractMap.put("productProjectOidPartNos", productProjectOidPartNos);

	List<Map<String, Object>> extractList = this.costPartInfoExtract(extractMap);
	partNos = "";

	for (Map<String, Object> map : extractList) {
	    partNos += "'" + (String) map.get("PARTNO") + "',";
	    ProductCostPartOids += "'" + (String) map.get("IDA2A2") + "',";
	}

	partNos = StringUtils.defaultIfEmpty(StringUtils.removeEnd(partNos, ","), "''");
	ProductCostPartOids = StringUtils.defaultIfEmpty(StringUtils.removeEnd(ProductCostPartOids, ","), "''");

	extractMap.put("partNos", partNos);
	extractMap.put("pjtGb", "검토");
	extractMap.put("projectOids", reviewProjectOids);

	extractList = this.costPartInfoExtract(extractMap);

	for (Map<String, Object> map : extractList) {
	    RiviewCostPartOids += "'" + (String) map.get("IDA2A2") + "',";
	}

	RiviewCostPartOids = StringUtils.defaultIfEmpty(StringUtils.removeEnd(RiviewCostPartOids, ","), "''");
	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    // sql.append(" SELECT OID,                                                                                                                                                                                                                 \n ");
	    // sql.append("        TASKOID,                                                                                                                                                                                                             \n ");
	    // sql.append("        NVL(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 7),PARTNAME) AS PARTNAME,                                                                                                                                                              \n ");
	    // sql.append("        ROUND(TOTALCOST,1)  AS TOTALCOST,                                                                                                                                                                                                         \n ");
	    // sql.append("        ROUND(SALESTARGET,1) AS SALESTARGET,                                                                                                                                                                                                         \n ");
	    // sql.append("        ROUND(PROFITRATE,1) AS PROFITRATE,                                                                                                                                                                                                           \n ");
	    // sql.append("        SALESTARGETGB,                                                                                                                                                                                                             \n ");
	    // sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 1) AS OID2,                                                                                                                                                                  \n ");
	    // sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 2),1) AS SALESTARGET2,                                                                                                                                                          \n ");
	    // sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 3),1) AS TOTALCOST2,                                                                                                                                                            \n ");
	    // sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 4),1) AS PROFITRATE2,                                                                                                                                                           \n ");
	    // sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 5) AS REALPARTNO,                                                                                                                                                            \n ");
	    // sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 6) AS TASKOID2,                                                                                                                                                              \n ");
	    // sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 3) - TOTALCOST,1) AS TOTALCOST_CHANGE,                                                                                                                                          \n ");
	    // sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 2) - SALESTARGET,1) AS SALESTARGET_CHANGE,                                                                                                                                      \n ");
	    // sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 4) - PROFITRATE,1) AS PROFITRATE_CHANGE,                                                                                                                                         \n ");
	    // sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 9) AS SALESTARGETGB2,                                                                                                                                                              \n ");
	    // sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 8) AS PJTNO                                                                                                                                                              \n ");
	    // sql.append("   FROM (                                                                                                                                                                                                                    \n ");
	    // sql.append(" SELECT A0.CLASSNAMEA2A2||':'||A0.IDA2A2 AS OID,                                                                                                                                                                             \n ");
	    // sql.append("        A0.SALESTARGETCOSTEXPR AS SALESTARGET,                                                                                                                                                                               \n ");
	    // sql.append("        A0.PRODUCTCOSTTOTAL AS TOTALCOST,                                                                                                                                                                                    \n ");
	    // sql.append("        A0.PROFITRATEEXPR*100 AS PROFITRATE,                                                                                                                                                                                 \n ");
	    // sql.append("        A0.REALPARTNO,                                                                                                                                                                                                       \n ");
	    // sql.append("        A0.PARTNO,                                                                                                                                                                                                           \n ");
	    // sql.append("        A0.PARTNAME,                                                                                                                                                                                                           \n ");
	    // sql.append("        A0.SALESTARGETGB,                                                                                                                                                                                                           \n ");
	    // sql.append("        A4.CLASSNAMEKEYB8||':'||A4.IDA3B8 AS TASKOID,                                                                                                                                                                        \n ");
	    // sql.append("        (                                                                                                                                                                                                                    \n ");
	    // sql.append("        SELECT P_A0.CLASSNAMEA2A2||':'||P_A0.IDA2A2||'|'||P_A0.SALESTARGETCOSTEXPR||'|'||P_A0.PRODUCTCOSTTOTAL||'|'||P_A0.PROFITRATEEXPR*100||'|'||REALPARTNO||'|'||P_A4.CLASSNAMEKEYB8||':'||P_A4.IDA3B8||'|'||A0.PARTNAME||'|'||P_A2.PJTNO||'|'||P_A0.SALESTARGETGB  \n ");
	    // sql.append("          FROM COSTPART P_A0, PRODUCTMASTER P_A1, E3PSPROJECTMASTER P_A2, COSTANALYSIS P_A3, COSTREPORT P_A4                                                                                                                 \n ");
	    // sql.append("         WHERE P_A0.IDA3A4=P_A1.IDA2A2 AND CASEORDER=1 AND P_A0.IDA3B4=P_A2.IDA2A2                                                                                                                                           \n ");
	    // sql.append("           AND P_A2.IDA2A2=P_A4.IDA3A8 AND P_A0.DRSTEP=P_A4.STEP                                                                                                                                                             \n ");
	    // sql.append("           AND P_A4.STATESTATE='APPROVED'                                                                                                                                                                                    \n ");
	    // sql.append("           AND P_A0.VERSION=P_A4.VERSION                                                                                                                                                                                     \n ");
	    // sql.append("           AND P_A0.IDA2A2=P_A3.IDA3A4                                                                                                                                                                                       \n ");
	    // sql.append("           AND P_A3.YEAR=1                                                                                                                                                                                                   \n ");
	    // sql.append("           AND P_A0.IDA2A2 IN ("+ProductCostPartOids+")                                                                                                                                                                    \n ");
	    // sql.append("           AND P_A0.PARTNO = A0.PARTNO                                                                                                                                                                                       \n ");
	    // sql.append("        ) AS PRODUCT_INFO                                                                                                                                                                                                    \n ");
	    // sql.append("   FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2, COSTANALYSIS A3, COSTREPORT A4                                                                                                                                  \n ");
	    // sql.append("  WHERE A0.IDA3A4=A1.IDA2A2 AND CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2                                                                                                                                                          \n ");
	    // sql.append("    AND A2.IDA2A2=A4.IDA3A8 AND A0.DRSTEP=A4.STEP                                                                                                                                                                            \n ");
	    // sql.append("    AND A4.STATESTATE='APPROVED'                                                                                                                                                                                             \n ");
	    // sql.append("    AND A0.VERSION=A4.VERSION                                                                                                                                                                                                \n ");
	    // sql.append("    AND A0.IDA2A2=A3.IDA3A4 																								 \n ");
	    // sql.append("    AND A3.YEAR=1 																										 \n ");
	    // sql.append("    AND A0.IDA2A2 IN ("+RiviewCostPartOids+") 																						 \n ");
	    // sql.append("  ORDER BY A0.PARTNO,A0.DRSTEP, A0.VERSION, A0.SORTLOCATION) 																				 \n ");

	    sql.append(" SELECT OID AS OID2,																																																																	\n");
	    sql.append("        TASKOID AS TASKOID2,                                                                                                                                                                                                                                                    \n");
	    sql.append("        PARTNAME,                                                                                                                                                                                                                                                               \n");
	    sql.append("        ROUND(TOTALCOST,1)  AS TOTALCOST2,                                                                                                                                                                                                                                      \n");
	    sql.append("        ROUND(SALESTARGET,1) AS SALESTARGET2,                                                                                                                                                                                                                                   \n");
	    sql.append("        ROUND(PROFITRATE,1) AS PROFITRATE2,                                                                                                                                                                                                                                     \n");
	    sql.append("        SALESTARGETGB AS SALESTARGETGB2,                                                                                                                                                                                                                                        \n");
	    sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 1) AS OID,                                                                                                                                                                                                                     \n");
	    sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 2),1) AS SALESTARGET,                                                                                                                                                                                                     \n");
	    sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 3),1) AS TOTALCOST,                                                                                                                                                                                                       \n");
	    sql.append("        ROUND(REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 4),1) AS PROFITRATE,                                                                                                                                                                                                      \n");
	    sql.append("        REALPARTNO,                                                                                                                                                                                                                                                             \n");
	    sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 5) AS TASKOID,                                                                                                                                                                                                                  \n");
	    sql.append("        ROUND(TOTALCOST - REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 3),1) AS TOTALCOST_CHANGE,                                                                                                                                                                                    \n");
	    sql.append("        ROUND(SALESTARGET - REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 2),1) AS SALESTARGET_CHANGE,                                                                                                                                                                                \n");
	    sql.append("        ROUND(PROFITRATE - REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 4),1) AS PROFITRATE_CHANGE,                                                                                                                                                                                  \n");
	    sql.append("        REGEXP_SUBSTR(PRODUCT_INFO, '[^|]+', 1, 9) AS SALESTARGETGB,                                                                                                                                                                                                            \n");
	    sql.append("        PJTNO                                                                                                                                                                                                                                                                   \n");
	    sql.append("   FROM (SELECT A0.CLASSNAMEA2A2||':'||A0.IDA2A2 AS OID,                                                                                                                                                                                                                        \n");
	    sql.append("                A0.SALESTARGETCOSTEXPR AS SALESTARGET,                                                                                                                                                                                                                          \n");
	    sql.append("                A0.PRODUCTCOSTTOTAL AS TOTALCOST,                                                                                                                                                                                                                               \n");
	    sql.append("                A0.PROFITRATEEXPR*100 AS PROFITRATE,                                                                                                                                                                                                                            \n");
	    sql.append("                A0.REALPARTNO,                                                                                                                                                                                                                                                  \n");
	    sql.append("                A0.PARTNO,                                                                                                                                                                                                                                                      \n");
	    sql.append("                A0.PARTNAME,                                                                                                                                                                                                                                                    \n");
	    sql.append("                A0.SALESTARGETGB,                                                                                                                                                                                                                                               \n");
	    sql.append("                A4.CLASSNAMEKEYB8||':'||A4.IDA3B8 AS TASKOID,                                                                                                                                                                                                                   \n");
	    sql.append("                A2.PJTNO,                                                                                                                                                                                                                                                       \n");
	    sql.append("               (                                                                                                                                                                                                                                                                \n");
	    sql.append("                SELECT P_A0.CLASSNAMEA2A2||':'||P_A0.IDA2A2||'|'||P_A0.SALESTARGETCOSTEXPR||'|'||P_A0.PRODUCTCOSTTOTAL||'|'||P_A0.PROFITRATEEXPR*100||'|'||REALPARTNO||'|'||P_A4.CLASSNAMEKEYB8||':'||P_A4.IDA3B8||'|'||A0.PARTNAME||'|'||P_A2.PJTNO||'|'||P_A0.SALESTARGETGB   \n");
	    sql.append("                  FROM COSTPART P_A0, PRODUCTMASTER P_A1, E3PSPROJECTMASTER P_A2, COSTANALYSIS P_A3, COSTREPORT P_A4                                                                                                                                                            \n");
	    sql.append("                 WHERE P_A0.IDA3A4=P_A1.IDA2A2 AND CASEORDER=1 AND P_A0.IDA3B4=P_A2.IDA2A2                                                                                                                                                                                      \n");
	    sql.append("                   AND P_A2.IDA2A2=P_A4.IDA3A8 AND P_A0.DRSTEP=P_A4.STEP                                                                                                                                                                                                        \n");
	    sql.append("                   AND P_A4.STATESTATE='APPROVED'                                                                                                                                                                                                                               \n");
	    sql.append("                   AND P_A0.VERSION=P_A4.VERSION                                                                                                                                                                                                                                \n");
	    sql.append("                   AND P_A0.IDA2A2=P_A3.IDA3A4                                                                                                                                                                                                                                  \n");
	    sql.append("                   AND P_A3.YEAR=1                                                                                                                                                                                                                                              \n");
	    sql.append("                   AND P_A0.IDA2A2 IN ("
		    + RiviewCostPartOids
		    + ")                                                                                                                                                                                                 \n");
	    sql.append("                   AND P_A0.PARTNO = A0.PARTNO                                                                                                                                                                                                                                  \n");
	    sql.append("                ) AS PRODUCT_INFO                                                                                                                                                                                                                                               \n");
	    sql.append("           FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2, COSTANALYSIS A3, COSTREPORT A4                                                                                                                                                                             \n");
	    sql.append("          WHERE A0.IDA3A4=A1.IDA2A2 AND CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2                                                                                                                                                                                                     \n");
	    sql.append("            AND A2.IDA2A2=A4.IDA3A8 AND A0.DRSTEP=A4.STEP                                                                                                                                                                                                                       \n");
	    sql.append("            AND A4.STATESTATE='APPROVED'                                                                                                                                                                                                                                        \n");
	    sql.append("            AND A0.VERSION=A4.VERSION                                                                                                                                                                                                                                           \n");
	    sql.append("            AND A0.IDA2A2=A3.IDA3A4                                                                                                                                                                                                                                             \n");
	    sql.append("            AND A3.YEAR=1                                                                                                                                                                                                                                                       \n");
	    sql.append("            AND A0.IDA2A2 IN ("
		    + ProductCostPartOids
		    + ")                                                                                                                                                                               \n");
	    sql.append("    ORDER BY A0.PARTNO,A0.DRSTEP, A0.VERSION, A0.SORTLOCATION)																				\n");
	    System.out.println("sql = " + sql.toString());
	    rs = stat.executeQuery(sql.toString());

	    while (rs.next()) {

		String OID = rs.getString("OID");
		String TASKOID = rs.getString("TASKOID");
		String REALPARTNO = rs.getString("REALPARTNO");
		String PJTNO = rs.getString("PJTNO");
		String PARTNAME = rs.getString("PARTNAME");
		String SALESTARGET = rs.getString("SALESTARGET");
		String TOTALCOST = rs.getString("TOTALCOST");
		String PROFITRATE = String.format("%.1f", rs.getDouble("PROFITRATE"));

		String OID2 = rs.getString("OID2");
		String TASKOID2 = rs.getString("TASKOID2");
		String SALESTARGET2 = rs.getString("SALESTARGET2");
		String TOTALCOST2 = rs.getString("TOTALCOST2");
		String PROFITRATE2 = String.format("%.1f", rs.getDouble("PROFITRATE2"));

		String TOTALCOST_CHANGE = rs.getString("TOTALCOST_CHANGE");
		String SALESTARGET_CHANGE = rs.getString("SALESTARGET_CHANGE");
		String PROFITRATE_CHANGE = String.format("%.1f", rs.getDouble("PROFITRATE_CHANGE"));
		String SALESTARGETGB = rs.getString("SALESTARGETGB");
		String SALESTARGETGB2 = rs.getString("SALESTARGETGB2");

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("OID", OID);
		map.put("TASKOID", TASKOID);
		map.put("REALPARTNO", REALPARTNO);
		map.put("PJTNO", PJTNO);
		map.put("PARTNAME", PARTNAME);

		map.put("SALESTARGET", SALESTARGET);
		map.put("TOTALCOST", TOTALCOST);
		map.put("PROFITRATE", PROFITRATE);

		map.put("OID2", OID2);
		map.put("TASKOID2", TASKOID2);
		map.put("SALESTARGET2", SALESTARGET2);
		map.put("TOTALCOST2", TOTALCOST2);
		map.put("PROFITRATE2", PROFITRATE2);

		map.put("TOTALCOST_CHANGE", TOTALCOST_CHANGE);
		map.put("SALESTARGET_CHANGE", SALESTARGET_CHANGE);
		map.put("PROFITRATE_CHANGE", PROFITRATE_CHANGE);
		map.put("EXIST", "YES");
		map.put("SALESTARGETGB", SALESTARGETGB);
		map.put("SALESTARGETGB2", SALESTARGETGB2);

		list.add(map);
	    }

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

	List<Map<String, Object>> partList = (List<Map<String, Object>>) reqMap.get("partList");
	List<Map<String, Object>> notExistPartList = new ArrayList<Map<String, Object>>();

	if (list.size() > 0) {// 프로젝트의 제품 중 원가산출이 발생하지 않은 건을 list에 추가한다. EXIST = NO
	    for (Map<String, Object> partMap : partList) {
		String partNo = (String) partMap.get("partNo");
		String partName = (String) partMap.get("partName");
		String pjtNo = (String) partMap.get("pjtNo");
		boolean isExist = false;

		for (Map<String, Object> map : list) {
		    String costPartNo = (String) map.get("REALPARTNO");
		    if (StringUtils.isEmpty(costPartNo)) {
			continue;
		    }
		    if (partNo.equals(costPartNo)) {
			isExist = true;
			break;
		    } else if (partNo.equals(costPartNo.substring(1))) {
			isExist = true;
			break;
		    }
		}

		if (!isExist) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("REALPARTNO", partNo);
		    map.put("PARTNAME", partName);
		    map.put("PJTNO", pjtNo);
		    map.put("EXIST", "NO");
		    notExistPartList.add(map);
		}
	    }

	    for (Map<String, Object> notExistPart : notExistPartList) {
		list.add(notExistPart);
	    }
	}

	Collections.sort(list, new Comparator<Object>() {

	    @Override
	    public int compare(Object obj1, Object obj2) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map1 = (HashMap<String, Object>) obj1;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map2 = (HashMap<String, Object>) obj2;
		String x = (String) (map1.get("PJTNO") + "" + map1.get("REALPARTNO"));
		String y = (String) (map2.get("PJTNO") + "" + map2.get("REALPARTNO"));

		return x.compareTo(y);
	    }
	});

	return list;
    }

    /**
     * <pre>
     * @description 차트 정보 로드
     * @author dhkim
     * @date 2019. 4. 16. 오전 11:13:45
     * @method loadChartData
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> loadChartData(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	String chartID = (String) reqMap.get("chartID");

	if ("chart2".equals(chartID)) {
	    resMap = getDRStepCostChartData(reqMap);
	} else if ("chart1".equals(chartID)) {
	    resMap = getCostTypeChartData(reqMap);
	} else if ("chart".equals(chartID)) {
	    resMap = getCostDriverChartData(reqMap);
	}

	resMap.put("chartID", chartID);

	return resMap;
    }

    /**
     * <pre>
     * @description 원가동인별 정보
     * @author dhkim
     * @date 2019. 4. 16. 오후 4:21:29
     * @method getCostDriverChartData
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    private Map<String, Object> getCostDriverChartData(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	String oid = (String) reqMap.get("oid");
	String productNo = (String) reqMap.get("productNo");
	String flags = (String) reqMap.get("flags");
	String drStepRev = (String) reqMap.get("drStepRev");
	String drStep = drStepRev.split("\\.")[0];
	int rev = Integer.parseInt(drStepRev.split("\\.")[1]);

	boolean isSetTotal = "SETTOTAL".equals(productNo);
	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	String pjtNo = "'" + project.getPjtNo() + "'";

	if (project instanceof ProductProject && project.getDevRequest() != null
	        && StringUtil.checkString(project.getDevRequest().getProjectOID())) {
	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    for (String RpjtNo : pjtNos) {
		E3PSProject rp = ProjectHelper.getProject(RpjtNo);
		// ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
		pjtNo += ",'" + rp.getPjtNo() + "'";
	    }

	}

	if (isSetTotal) {

	    productNo = "'";
	    List<Map<String, String>> productList = CostUtil.manager.getCostProductList(pjtNo);

	    for (Map<String, String> product : productList) {
		productNo += product.get("partNo") + "','";
	    }

	    productNo = StringUtils.removeEnd(productNo, ",'");

	} else {
	    productNo = "'" + productNo + "'";
	}

	try {

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    if (isSetTotal) {
		sql.append("	SELECT                                                                 \n");
		sql.append("	SUM(X0.재료비) AS 재료비,                                                  \n");
		sql.append("	SUM(X0.노무비) AS 노무비,                                                  \n");
		sql.append("	SUM(X0.경비) AS 경비,                                                     \n");
		sql.append("	SUM(X0.관리비) AS 관리비,                                                  \n");
		sql.append("	SUM(X0.감가비) AS 감가비                                                        			   \n");
		sql.append("	FROM (                                                                 \n");
	    }

	    sql.append("	SELECT                                                                     \n");
	    sql.append("	NVL((SELECT SUM(B0.MATERIALCOSTEXPR * B0.US)                               \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                          \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                                \n");

	    if (!"총원가".equals(flags)) {
		sql.append("	AND B1.COSTRATIOCODE = '" + flags + "'                                 \n");
	    }

	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                           \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE),0) AS 재료비,           \n");
	    sql.append("	NVL((SELECT SUM(B0.LABORCOSTEXPR * B0.US)                                  \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                          \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                                \n");

	    if (!"총원가".equals(flags)) {
		sql.append("	AND B1.COSTRATIOCODE = '" + flags + "'                                 \n");
	    }

	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                           \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE),0) AS 노무비,           \n");
	    sql.append("	NVL((SELECT SUM(B0.EXPENSEEXPR * B0.US)                                    \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                          \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                                \n");

	    if (!"총원가".equals(flags)) {
		sql.append("	AND B1.COSTRATIOCODE = '" + flags + "'                                 \n");
	    }

	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                           \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE),0) AS 경비,             \n");
	    sql.append("	NVL((SELECT SUM(B0.MANAGECOSTEXPR * B0.US)                                 \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                          \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                                \n");

	    if (!"총원가".equals(flags)) {
		sql.append("	AND B1.COSTRATIOCODE = '" + flags + "'                                 \n");
	    }

	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                           \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE),0) AS 관리비,           \n");
	    sql.append("	NVL((SELECT SUM(B0.REDUCECOSTEXPR * B0.US)                                 \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                          \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                                \n");

	    if (!"총원가".equals(flags)) {
		sql.append("	AND B1.COSTRATIOCODE = '" + flags + "'                                 \n");
	    }

	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                           \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE),0) AS 감가비                  \n");
	    sql.append("	FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2, COSTREPORT A4    \n");
	    sql.append("	WHERE A0.IDA3A4=A1.IDA2A2 AND CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2          \n");
	    sql.append("	AND A2.IDA2A2=A4.IDA3A8 AND A4.VERSION=" + rev + "						   \n");

	    if ("DR0".equals(drStep)) {
		sql.append("	AND A0.DRSTEP='DR0'						   						   	   \n");
	    } else {
		sql.append("	AND A0.DRSTEP!='DR0'						   						   \n");
	    }

	    sql.append("	AND A4.STATESTATE='APPROVED'                                               \n");
	    sql.append("	AND A0.VERSION=A4.VERSION                                                  \n");
	    sql.append("	AND A0.PARTNO IN (" + productNo + ")                                       \n");
	    sql.append("	AND A2.PJTNO IN (" + pjtNo + ")                           			 \n");
	    sql.append("	ORDER BY A0.DRSTEP, A0.VERSION, A0.SORTLOCATION                            \n");

	    if (isSetTotal) {
		sql.append("	) X0                                                                   \n");
	    }

	    rs = stat.executeQuery(sql.toString());

	    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	    if (rs.next()) {

		double REDUCECOST = rs.getDouble("감가비");
		double MANAGECOST = rs.getDouble("관리비");
		double EXPENSE = rs.getDouble("경비");
		double LABORCOST = rs.getDouble("노무비");
		double MATERIALCOST = rs.getDouble("재료비");

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("label", "[감가비] " + String.format("%,.1f", REDUCECOST) + "원");
		dataMap.put("value", String.format("%.1f", REDUCECOST));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart1'),'감가비')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[관리비] " + String.format("%,.1f", MANAGECOST) + "원");
		dataMap.put("value", String.format("%.1f", MANAGECOST));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart1'),'관리비')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[경비] " + String.format("%,.1f", EXPENSE) + "원");
		dataMap.put("value", String.format("%.1f", EXPENSE));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart1'),'경비')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[노무비] " + String.format("%,.1f", LABORCOST) + "원");
		dataMap.put("value", String.format("%.1f", LABORCOST));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart1'),'노무비')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[재료비] " + String.format("%,.1f", MATERIALCOST) + "원");
		dataMap.put("value", String.format("%.1f", MATERIALCOST));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart1'),'재료비')");
		data.add(dataMap);

	    }

	    resMap.put("data", data);

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

	return resMap;
    }

    /**
     * <pre>
     * @description 유형별 차트 정보
     * @author dhkim
     * @date 2019. 4. 16. 오후 4:23:06
     * @method getCostTypeChartData
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    private Map<String, Object> getCostTypeChartData(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	String oid = (String) reqMap.get("oid");
	String productNo = (String) reqMap.get("productNo");
	String flags = (String) reqMap.get("flags");
	String drStepRev = (String) reqMap.get("drStepRev");
	String drStep = drStepRev.split("\\.")[0];
	int rev = Integer.parseInt(drStepRev.split("\\.")[1]);

	boolean isSetTotal = "SETTOTAL".equals(productNo);
	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	String pjtNo = "'" + project.getPjtNo() + "'";

	if (project instanceof ProductProject && project.getDevRequest() != null
	        && StringUtil.checkString(project.getDevRequest().getProjectOID())) {

	    // ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
	    // E3PSProject rp = ProjectHelper.getProject(project.getReviewPjtNo());
	    // pjtNo += ",'" + rp.getPjtNo() + "'";

	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    for (String RpjtNo : pjtNos) {
		E3PSProject rp = ProjectHelper.getProject(RpjtNo);
		// ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
		pjtNo += ",'" + rp.getPjtNo() + "'";
	    }

	}

	if (isSetTotal) {

	    productNo = "'";
	    List<Map<String, String>> productList = CostUtil.manager.getCostProductList(pjtNo);

	    for (Map<String, String> product : productList) {
		productNo += product.get("partNo") + "','";
	    }

	    productNo = StringUtils.removeEnd(productNo, ",'");

	} else {
	    productNo = "'" + productNo + "'";
	}

	try {

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    String attrName = "";

	    switch (flags) {
	    case "재료비":
		attrName = "B0.MATERIALCOSTEXPR*B0.US";
		break;
	    case "노무비":
		attrName = "B0.LABORCOSTEXPR*B0.US";
		break;
	    case "경비":
		attrName = "B0.EXPENSEEXPR*B0.US";
		break;
	    case "관리비":
		attrName = "B0.MANAGECOSTEXPR*B0.US";
		break;
	    case "감가비":
		attrName = "B0.REDUCECOSTEXPR*B0.US";
		break;
	    default:
		attrName = "B0.TOTALCOSTEXPR*B0.US";
		break;
	    }

	    if (isSetTotal) {
		sql.append("	SELECT                                                                   \n");
		sql.append("	SUM(X0.양산품) AS 양산품,                                                    \n");
		sql.append("	SUM(X0.지정품) AS 지정품,                                                    \n");
		sql.append("	SUM(X0.WH품) AS WH품,                                                     \n");
		sql.append("	SUM(X0.구매품) AS 구매품,                                                    \n");
		sql.append("	SUM(X0.조립) AS 조립,                                                       \n");
		sql.append("	SUM(X0.Press) AS Press,                                                  \n");
		sql.append("	SUM(X0.Mold) AS Mold                                                     \n");
		sql.append("	FROM (                                                                   \n");
	    }

	    sql.append("	SELECT                                                                   \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='양산품'                                             \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS 양산품,                                   \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='지정품'                                             \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS 지정품,                                   \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='W/H품'                                             \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS WH품,                                    \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='구매품'                                             \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS 구매품,                                   \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='조립'                                              \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS 조립,                                    \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='Press'                                            \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS Press,                                  \n");
	    sql.append("	NVL((SELECT SUM(" + attrName + ")                                        \n");
	    sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                        \n");
	    sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2                                              \n");
	    sql.append("	AND B1.COSTRATIOCODE ='Mold'                                             \n");
	    sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                         \n");
	    sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE                      \n");
	    sql.append("	GROUP BY B1.COSTRATIOCODE),0) AS Mold                                    \n");
	    sql.append("	FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2, COSTREPORT A4  \n");
	    sql.append("	WHERE A0.IDA3A4=A1.IDA2A2 AND CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2        \n");
	    sql.append("	AND A2.IDA2A2=A4.IDA3A8 AND A4.VERSION=" + rev + "						 \n");

	    if ("DR0".equals(drStep)) {
		sql.append("	AND A0.DRSTEP='DR0'						   						   	 \n");
	    } else {
		sql.append("	AND A0.DRSTEP!='DR0'						   						 \n");
	    }

	    sql.append("	AND A4.STATESTATE='APPROVED'                                             \n");
	    sql.append("	AND A0.VERSION=A4.VERSION                                                \n");
	    sql.append("	AND A0.PARTNO IN (" + productNo + ")                           			 \n");
	    sql.append("	AND A2.PJTNO IN (" + pjtNo + ")                           			 \n");
	    sql.append("	ORDER BY A0.DRSTEP, A0.VERSION, A0.SORTLOCATION                          \n");

	    if (isSetTotal) {
		sql.append("	) X0                                                                 \n");
	    }

	    rs = stat.executeQuery(sql.toString());

	    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	    if (rs.next()) {

		double VALUE1 = rs.getDouble("양산품");
		double VALUE2 = rs.getDouble("지정품");
		double VALUE3 = rs.getDouble("WH품");
		double VALUE4 = rs.getDouble("구매품");
		double VALUE5 = rs.getDouble("조립");
		double VALUE6 = rs.getDouble("Press");
		double VALUE7 = rs.getDouble("Mold");

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("label", "[양산품] " + String.format("%,.1f", VALUE1) + "원");
		dataMap.put("value", String.format("%.1f", VALUE1));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'양산품')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[지정품] " + String.format("%,.1f", VALUE2) + "원");
		dataMap.put("value", String.format("%.1f", VALUE2));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'지정품')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[W/H품] " + String.format("%,.1f", VALUE3) + "원");
		dataMap.put("value", String.format("%.1f", VALUE3));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'W/H품')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[구매품] " + String.format("%,.1f", VALUE4) + "원");
		dataMap.put("value", String.format("%.1f", VALUE4));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'구매품')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[조립] " + String.format("%,.1f", VALUE5) + "원");
		dataMap.put("value", String.format("%.1f", VALUE5));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'조립')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[Press] " + String.format("%,.1f", VALUE6) + "원");
		dataMap.put("value", String.format("%.1f", VALUE6));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'Press')");
		data.add(dataMap);

		dataMap = new HashMap<String, String>();
		dataMap.put("label", "[Mold] " + String.format("%,.1f", VALUE7) + "원");
		dataMap.put("value", String.format("%.1f", VALUE7));
		dataMap.put("link", "javascript:onChartDataClick(FusionCharts('chart'),'Mold')");
		data.add(dataMap);

	    }

	    resMap.put("data", data);

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

	return resMap;
    }

    /**
     * <pre>
     * @description DR단계별 원가 차트 정보
     * @author dhkim
     * @date 2019. 4. 16. 오전 11:13:47
     * @method getDRStepCostChartData
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    private Map<String, Object> getDRStepCostChartData(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	String oid = (String) reqMap.get("oid");
	String productNo = (String) reqMap.get("productNo");
	boolean isSetTotal = "SETTOTAL".equals(productNo);
	E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	String pjtNo = "'" + project.getPjtNo() + "'";

	if (project instanceof ProductProject && project.getDevRequest() != null
	        && StringUtil.checkString(project.getDevRequest().getProjectOID())) {

	    // ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
	    // E3PSProject rp = ProjectHelper.getProject(project.getReviewPjtNo());
	    // pjtNo += ",'" + rp.getPjtNo() + "'";

	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    for (String RpjtNo : pjtNos) {
		E3PSProject rp = ProjectHelper.getProject(RpjtNo);
		// ReviewProject rp = (ReviewProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
		pjtNo += ",'" + rp.getPjtNo() + "'";
	    }

	}

	if (isSetTotal) {

	    productNo = "'";
	    List<Map<String, String>> productList = CostUtil.manager.getCostProductList(pjtNo);

	    for (Map<String, String> product : productList) {
		productNo += product.get("partNo") + "','";
	    }

	    productNo = StringUtils.removeEnd(productNo, ",'");

	} else {
	    productNo = "'" + productNo + "'";
	}

	try {

	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    if (isSetTotal) {
		sql.append("	SELECT X0.DRSTEP, X0.REV, 			                                                   \n");
		sql.append("	SUM(X0.KETTOTALCOST  ) AS KETTOTALCOST  ,                                  			   \n");
		sql.append("	SUM(X0.SUBTOTALCOST  ) AS SUBTOTALCOST  ,                                 		       \n");
		sql.append("	SUM(X0.TOTALCOST     ) AS TOTALCOST,                                  				   \n");
		sql.append("	SUM(X0.SALESCOST     ) AS SALESCOST                                  				   \n");
		sql.append("	FROM (                                                                                 \n");
	    }

	    sql.append("	SELECT 																		               \n");
	    sql.append("	A0.CLASSNAMEA2A2||':'||A0.IDA2A2 AS OID,                                                   \n");
	    sql.append("	A0.DRSTEP,                                                                                 \n");
	    sql.append("	A0.VERSION AS REV,									                               		   \n");
	    sql.append("	A0.PRODUCTCOSTTOTAL-A0.SUBCOSTEXCEPTTOTAL AS KETTOTALCOST,                                 \n");
	    sql.append("	A0.SUBCOSTEXCEPTTOTAL AS SUBTOTALCOST,                                                     \n");
	    sql.append("	A0.PRODUCTCOSTTOTAL AS TOTALCOST,                                                           \n");
	    sql.append("	A0.SALESTARGETCOSTEXPR AS SALESCOST                                                           \n");
	    sql.append("	FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2, COSTANALYSIS A3, COSTREPORT A4   \n");
	    sql.append("	WHERE A0.IDA3A4=A1.IDA2A2 AND CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2                          \n");
	    sql.append("	AND A2.IDA2A2=A4.IDA3A8 AND A0.DRSTEP=A4.STEP                                              \n");
	    sql.append("	AND A4.STATESTATE='APPROVED'                                                               \n");
	    sql.append("	AND A0.VERSION=A4.VERSION                                                                  \n");
	    sql.append("	AND A0.IDA2A2=A3.IDA3A4 AND A3.YEAR=1                                                      \n");
	    sql.append("	AND A0.PARTNO IN (" + productNo + ")                                                       \n");
	    sql.append("	AND A2.PJTNO IN (" + pjtNo + ")                                                       \n");
	    sql.append("	ORDER BY DRSTEP, REV, A0.SORTLOCATION                                                      \n");

	    if (isSetTotal) {
		sql.append("	) X0 GROUP BY X0.DRSTEP, X0.REV                                                        \n");
		sql.append("	ORDER BY X0.DRSTEP, X0.REV                                                             \n");
	    }

	    rs = stat.executeQuery(sql.toString());

	    List<Map<String, String>> categories = new ArrayList<Map<String, String>>();
	    Map<String, String> category = new HashMap<String, String>();
	    String categoryStr = "";

	    List<Map<String, String>> dataset = new ArrayList<Map<String, String>>();
	    Map<String, String> subCost = new HashMap<String, String>();
	    Map<String, String> ketCost = new HashMap<String, String>();
	    Map<String, String> cost = new HashMap<String, String>();
	    Map<String, String> sales = new HashMap<String, String>();

	    sales.put("seriesname", "판매목표가");
	    subCost.put("seriesname", "지정품");
	    ketCost.put("seriesname", "KET품");
	    cost.put("seriesname", "총원가");

	    String salesData = "";
	    String subData = "";
	    String ketData = "";
	    String data = "";

	    Map<String, String> revdata = new HashMap<String, String>();

	    boolean isDR0Check = true;
	    int tempDRStep = 0;
	    String tempREV = "0";
	    String tempKETTOTALCOST = "0";
	    String tempSUBTOTALCOST = "0";
	    String tempTOTALCOST = "0";
	    String tempSALESCOST = "0";

	    while (rs.next()) {

		String DRSTEP = rs.getString("DRSTEP");
		String REV = rs.getString("REV");
		String KETTOTALCOST = String.format("%.1f", rs.getDouble("KETTOTALCOST"));
		String SUBTOTALCOST = String.format("%.1f", rs.getDouble("SUBTOTALCOST"));
		String TOTALCOST = String.format("%.1f", rs.getDouble("TOTALCOST"));
		String SALESCOST = String.format("%.1f", rs.getDouble("SALESCOST"));

		if (isDR0Check) {
		    if (!"DR0".equals(DRSTEP)) {
			categoryStr += "DR0|";
			subData += "0|";
			ketData += "0|";
			data += "0|";
			revdata.put("DR0", "DR0.0");
		    }
		    isDR0Check = false;
		}

		int step = Integer.parseInt(DRSTEP.substring(2));
		if (!"DR0".equals(DRSTEP)) {

		    boolean isDR1Temp = false;
		    for (tempDRStep++; step - 1 >= tempDRStep; tempDRStep++) {

			categoryStr += "DR" + tempDRStep + "|";
			subData += tempKETTOTALCOST + "|";
			ketData += tempSUBTOTALCOST + "|";
			data += tempTOTALCOST + "|";
			salesData += tempSALESCOST + "|";

			if (1 == tempDRStep || isDR1Temp) {
			    revdata.put("DR" + tempDRStep, "DR0." + tempREV);
			    isDR1Temp = true;
			} else {
			    revdata.put("DR" + tempDRStep, "DR" + tempDRStep + "." + tempREV);
			}
		    }
		}

		tempDRStep = step;
		tempREV = REV;
		tempKETTOTALCOST = SUBTOTALCOST;
		tempSUBTOTALCOST = KETTOTALCOST;
		tempTOTALCOST = TOTALCOST;
		tempSALESCOST = SALESCOST;

		categoryStr += DRSTEP + "." + REV + "|";
		subData += SUBTOTALCOST + "|";
		ketData += KETTOTALCOST + "|";
		data += TOTALCOST + "|";
		salesData += SALESCOST + "|";
		revdata.put(DRSTEP + "." + REV, DRSTEP + "." + REV);
		resMap.put("lastDRStepRev", DRSTEP + "." + REV);
	    }

	    categoryStr = StringUtils.removeEnd(categoryStr, "|");

	    subData = StringUtils.removeEnd(subData, "|");
	    ketData = StringUtils.removeEnd(ketData, "|");
	    data = StringUtils.removeEnd(data, "|");
	    salesData = StringUtils.removeEnd(salesData, "|");
	    ;

	    sales.put("data", salesData);
	    subCost.put("data", subData);
	    ketCost.put("data", ketData);
	    cost.put("data", data);

	    dataset.add(sales);
	    dataset.add(subCost);
	    dataset.add(ketCost);
	    dataset.add(cost);

	    category.put("category", categoryStr);
	    categories.add(category);

	    resMap.put("dataset", dataset);
	    resMap.put("categories", categories);
	    resMap.put("revdata", revdata);

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

	return resMap;
    }

    /**
     * <pre>
     * @description 개발원가이력
     * @author dhkim
     * @date 2019. 4. 15. 오후 5:22:40
     * @method getCostHistoryData
     * @param reqMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public List<Map<String, Object>> getCostHistoryData(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	boolean isSetTotal = Boolean.parseBoolean((String) reqMap.get("isSetTotal"));
	String productNo = (String) reqMap.get("productNo");
	E3PSProject project = (E3PSProject) CommonUtil.getObject((String) reqMap.get("oid"));

	String projectOid = "";

	if (project instanceof ProductProject) {

	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    for (String pjtNo : pjtNos) {
		E3PSProject rp = ProjectHelper.getProject(pjtNo);
		projectOid += "'" + CommonUtil.getOIDLongValue2Str(rp.getMaster()) + "'" + ",";
	    }
	    projectOid = projectOid + "'" + CommonUtil.getOIDLongValue2Str(project.getMaster()) + "'";

	} else if (project instanceof ReviewProject) {

	    projectOid = "'" + CommonUtil.getOIDLongValue2Str(project.getMaster()) + "'";

	}

	if (!isSetTotal) {
	    productNo = "'" + productNo + "'";
	}

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    if (isSetTotal) {
		sql.append("	SELECT X0.TASKOID, X0.CALCTYPE, X0.PUBSTD, X0.DRSTEP, X0.REV, X0.CALCDATE, 				\n");
		sql.append("	SUM(X0.KETSALESTARGET) AS KETSALESTARGET,                                  				\n");
		sql.append("	SUM(X0.KETTOTALCOST  ) AS KETTOTALCOST  ,                                  				\n");
		sql.append("	AVG(X0.KETPROFITRATE ) AS KETPROFITRATE ,                                  				\n");
		sql.append("	SUM(X0.SUBSALESTARGET) AS SUBSALESTARGET,                                  				\n");
		sql.append("	SUM(X0.SUBTOTALCOST  ) AS SUBTOTALCOST  ,                                 				\n");
		sql.append("	AVG(X0.SUBPROFITRATE ) AS SUBPROFITRATE ,                                  				\n");
		sql.append("	SUM(X0.SALESTARGET   ) AS SALESTARGET   ,                                  				\n");
		sql.append("	SUM(X0.TOTALCOST     ) AS TOTALCOST     ,                                  				\n");
		sql.append("	(1-(SUM(X0.TOTALCOST)/NULLIF(SUM(X0.SALESTARGET),0)))*100 AS PROFITRATE,    		                \n");
		sql.append("	SUM(X0.MOLDINVEST    ) AS MOLDINVEST    ,                                  				\n");
		sql.append("	SUM(X0.PRESSINVEST   ) AS PRESSINVEST   ,                                  				\n");
		sql.append("	SUM(X0.EQUIPINVEST   ) AS EQUIPINVEST   ,                                  				\n");
		sql.append("	SUM(X0.PURMOLDINVEST ) AS PURMOLDINVEST ,                                  				\n");
		sql.append("	SUM(X0.ETCINVEST     ) AS ETCINVEST     ,                                  				\n");
		sql.append("	SUM(X0.TOTALINVEST   ) AS TOTALINVEST                                      				\n");
		sql.append("	FROM (                                                                     				\n");
	    }

	    sql.append("	SELECT X1.*,																				\n");
	    sql.append("	NVL(X1.MOLDINVEST,0)+NVL(X1.PRESSINVEST,0)+NVL(X1.EQUIPINVEST,0)+							\n");
	    sql.append("	NVL(X1.PURMOLDINVEST,0)+NVL(X1.ETCINVEST,0) AS TOTALINVEST FROM(							\n");
	    sql.append("	SELECT                                                                                      \n");

	    if (!isSetTotal) {
		sql.append("	A0.CLASSNAMEA2A2||':'||A0.IDA2A2 AS OID,                                                \n");
	    }

	    sql.append("	A4.CLASSNAMEKEYB8||':'||A4.IDA3B8 AS TASKOID,                                               \n");
	    sql.append("	(CASE WHEN A2.PJTTYPENAME = '제품' THEN '착수'                                                 \n");
	    sql.append("	ELSE A2.PJTTYPENAME END) AS CALCTYPE,                                                       \n");
	    sql.append("	SUBSTR(A4.RELEASESTEP,0,2) AS PUBSTD,                                                       \n");
	    sql.append("	A0.DRSTEP, A0.VERSION AS REV,                                                               \n");
	    sql.append("	TO_CHAR(A4.UPDATESTAMPA2, 'yyyy-MM-dd') AS CALCDATE,                                        \n");
	    sql.append("	A0.SALESTARGETCOSTEXPR-A3.APPOINTSALES AS KETSALESTARGET,                                   \n");
	    sql.append("	A0.PRODUCTCOSTTOTAL-A0.SUBCOSTEXCEPTTOTAL AS KETTOTALCOST,                                  \n");
	    sql.append("	NVL((1-(DECODE((A0.PRODUCTCOSTTOTAL-A0.SUBCOSTEXCEPTTOTAL),0,0,NULL,0,               		\n");
	    sql.append("	(A0.PRODUCTCOSTTOTAL-A0.SUBCOSTEXCEPTTOTAL)/                                         		\n");
	    sql.append("	(NULLIF(A0.SALESTARGETCOSTEXPR-A3.APPOINTSALES,0)    ))))*100,0) AS KETPROFITRATE,  		\n");
	    sql.append("	A3.APPOINTSALES AS SUBSALESTARGET,                                                          \n");
	    sql.append("	A0.SUBCOSTEXCEPTTOTAL AS SUBTOTALCOST,                                                      \n");
	    sql.append("	round((1-(DECODE(A0.SUBCOSTEXCEPTTOTAL,0,0,NULL,0,A0.SUBCOSTEXCEPTTOTAL/NULLIF(A3.APPOINTSALES,'0'))))*100,3) AS SUBPROFITRATE,                       \n");
	    sql.append("	A0.SALESTARGETCOSTEXPR AS SALESTARGET,                                                      \n");
	    sql.append("	A0.PRODUCTCOSTTOTAL AS TOTALCOST,                                                           \n");
	    sql.append("	A0.PROFITRATEEXPR*100 AS PROFITRATE,                                                        \n");
	    sql.append("	A0.MOLDINVESTPRICETOTAL AS MOLDINVEST,                                                        \n");
	    sql.append("	A0.PRESSINVESTPRICETOTAL AS PRESSINVEST,                                                        \n");
	    sql.append("	A0.EQUIPINVESTPRICETOTAL AS EQUIPINVEST,                                                        \n");
	    sql.append("	A0.PURCHASEINVESTPRICETOTAL AS PURMOLDINVEST,                                                        \n");
	    sql.append("	A0.ETCINVESTPRICETOTAL AS ETCINVEST                                                        \n");
	    // sql.append("	(SELECT SUM((SELECT SUM(C1.INVESTCOST*C1.INVESTUNIT)                                             \n");
	    // sql.append("	FROM COSTREDUCELINK C0, COSTINVESTINFO C1                                                        \n");
	    // sql.append("	WHERE C0.IDA3A5=C1.IDA2A2 AND C0.IDA3B5=B0.IDA2A2                                                \n");
	    // sql.append("	AND C1.INVESTTYPE='mold' AND C1.COSTTYPE='N'))                                                   \n");
	    // sql.append("	FROM COSTPART B0, COSTPARTTYPE B1, NUMBERCODE B2                                                 \n");
	    // sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2 AND B1.CODE='MOLD'                                                   \n");
	    // sql.append("	AND B0.MOLDMFTDIVISION = B2.CODE(+) AND B2.CODETYPE='MOLDDIVISION' AND B2.NAME NOT LIKE '%(공용)%'    \n");
	    // sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                                                 \n");
	    // sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE) AS MOLDINVEST,                              \n");
	    // sql.append("	(SELECT SUM((SELECT SUM(C1.INVESTCOST*C1.INVESTUNIT)                                             \n");
	    // sql.append("	FROM COSTREDUCELINK C0, COSTINVESTINFO C1                                                        \n");
	    // sql.append("	WHERE C0.IDA3A5=C1.IDA2A2 AND C0.IDA3B5=B0.IDA2A2                                                \n");
	    // sql.append("	AND C1.INVESTTYPE='mold' AND C1.COSTTYPE='N'))                                                   \n");
	    // sql.append("	FROM COSTPART B0, COSTPARTTYPE B1, NUMBERCODE B2                                                 \n");
	    // sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2 AND B1.CODE='PRESS'                                                  \n");
	    // sql.append("	AND B0.MOLDMFTDIVISION = B2.CODE(+) AND B2.CODETYPE='MOLDDIVISION' AND B2.NAME NOT LIKE '%(공용)%'    \n");
	    // sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                                                 \n");
	    // sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE) AS PRESSINVEST,                             \n");
	    // sql.append("	(SELECT SUM((SELECT SUM(C1.INVESTCOST*C1.INVESTUNIT)                                             \n");
	    // sql.append("	FROM COSTREDUCELINK C0, COSTINVESTINFO C1                                                        \n");
	    // sql.append("	WHERE C0.IDA3A5=C1.IDA2A2 AND C0.IDA3B5=B0.IDA2A2                                                \n");
	    // sql.append("	AND C1.INVESTTYPE='equip' AND C1.COSTTYPE='N'))                                                  \n");
	    // sql.append("	FROM COSTPART B0, NUMBERCODE B2                                                                  \n");
	    // sql.append("	WHERE B0.FACMFTDIVISION = B2.CODE(+) AND B2.CODETYPE='FACDIVISION' AND B2.NAME NOT LIKE '%(공용)%'    \n");
	    // sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                                                 \n");
	    // sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE) AS EQUIPINVEST,                             \n");
	    // sql.append("	(SELECT SUM((SELECT SUM(C1.INVESTCOST*C1.INVESTUNIT)                                        \n");
	    // sql.append("	FROM COSTREDUCELINK C0, COSTINVESTINFO C1                                                   \n");
	    // sql.append("	WHERE C0.IDA3A5=C1.IDA2A2 AND C0.IDA3B5=B0.IDA2A2                                           \n");
	    // sql.append("	AND C1.INVESTTYPE='mold' AND C1.COSTTYPE='N'))                                              \n");
	    // sql.append("	FROM COSTPART B0, COSTPARTTYPE B1                                                           \n");
	    // sql.append("	WHERE B0.PARTTYPE=B1.IDA2A2 AND B1.CODE='구매'                                               \n");
	    // sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                                            \n");
	    // sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE) AS PURMOLDINVEST,                      \n");
	    // sql.append("	(SELECT SUM((SELECT SUM(C1.INVESTCOST*C1.INVESTUNIT)                                        \n");
	    // sql.append("	FROM COSTREDUCELINK C0, COSTINVESTINFO C1                                                   \n");
	    // sql.append("	WHERE C0.IDA3A5=C1.IDA2A2 AND C0.IDA3B5=B0.IDA2A2                                           \n");
	    // sql.append("	AND C1.INVESTTYPE='etc' AND C1.COSTTYPE='N'))                                               \n");
	    // sql.append("	FROM COSTPART B0                                                                            \n");
	    // sql.append("	START WITH B0.IDA2A2 = A0.IDA2A2                                                            \n");
	    // sql.append("	CONNECT BY PRIOR B0.IDA2A2 = B0.IDA3PARENTREFERENCE) AS ETCINVEST                           \n");
	    sql.append("	FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2, COSTANALYSIS A3, COSTREPORT A4    \n");
	    sql.append("	WHERE A0.IDA3A4=A1.IDA2A2 AND CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2                           \n");
	    sql.append("	AND A2.IDA2A2=A4.IDA3A8 AND A0.DRSTEP=A4.STEP                                               \n");
	    sql.append("	AND A4.STATESTATE='APPROVED'                                                                \n");
	    sql.append("	AND A0.VERSION=A4.VERSION                                                                   \n");
	    sql.append("	AND A0.IDA2A2=A3.IDA3A4 AND A3.YEAR=1                                                       \n");
	    sql.append("	AND A0.PARTNO IN (" + productNo + ")                                           		        \n");
	    sql.append("	AND A0.IDA3B4 IN (" + projectOid + ")                                           		        \n");
	    sql.append("	ORDER BY A0.DRSTEP, A0.VERSION, A0.SORTLOCATION) X1                                         \n");

	    if (isSetTotal) {
		sql.append("	) X0 GROUP BY X0.TASKOID, X0.CALCTYPE, X0.PUBSTD, X0.DRSTEP, X0.REV, X0.CALCDATE		\n");
		sql.append("	ORDER BY X0.DRSTEP, X0.REV																\n");
	    }

	    System.out.println(sql);
	    rs = stat.executeQuery(sql.toString());

	    int RNUM = 0;
	    while (rs.next()) {

		RNUM++;
		String OID = "";

		if (!isSetTotal) {
		    OID = rs.getString("OID");
		}

		String TASKOID = rs.getString("TASKOID");
		String CALCTYPE = rs.getString("CALCTYPE");
		String PUBSTD = rs.getString("PUBSTD");
		String DRSTEP = rs.getString("DRSTEP");
		String REV = rs.getString("REV");
		String CALCDATE = rs.getString("CALCDATE");
		String KETSALESTARGET = String.format("%.1f", rs.getDouble("KETSALESTARGET"));
		String KETTOTALCOST = String.format("%.1f", rs.getDouble("KETTOTALCOST"));
		String KETPROFITRATE = String.format("%.1f", rs.getDouble("KETPROFITRATE"));
		String SUBSALESTARGET = rs.getString("SUBSALESTARGET");
		String SUBTOTALCOST = rs.getString("SUBTOTALCOST");
		String SUBPROFITRATE = String.format("%.1f", rs.getDouble("SUBPROFITRATE"));
		String SALESTARGET = rs.getString("SALESTARGET");
		String TOTALCOST = rs.getString("TOTALCOST");
		String PROFITRATE = String.format("%.1f", rs.getDouble("PROFITRATE"));
		String MOLDINVEST = String.format("%.1f", rs.getDouble("MOLDINVEST"));
		String PRESSINVEST = String.format("%.1f", rs.getDouble("PRESSINVEST"));
		String EQUIPINVEST = String.format("%.1f", rs.getDouble("EQUIPINVEST"));
		String PURMOLDINVEST = String.format("%.1f", rs.getDouble("PURMOLDINVEST"));
		String ETCINVEST = String.format("%.1f", rs.getDouble("ETCINVEST"));
		String TOTALINVEST = String.format("%.1f", rs.getDouble("TOTALINVEST"));

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("RNUM", RNUM);
		map.put("OID", OID);
		map.put("TASKOID", TASKOID);
		map.put("CALCTYPE", CALCTYPE);
		map.put("PUBSTD", PUBSTD);
		map.put("DRSTEP", DRSTEP);
		map.put("REV", "Rev" + REV);
		map.put("CALCDATE", CALCDATE);
		map.put("KETSALESTARGET", KETSALESTARGET);
		map.put("KETTOTALCOST", KETTOTALCOST);
		map.put("KETPROFITRATE", KETPROFITRATE);
		map.put("SUBSALESTARGET", SUBSALESTARGET);
		map.put("SUBTOTALCOST", SUBTOTALCOST);
		map.put("SUBPROFITRATE", SUBPROFITRATE);
		map.put("SALESTARGET", SALESTARGET);
		map.put("TOTALCOST", TOTALCOST);
		map.put("PROFITRATE", PROFITRATE);
		map.put("MOLDINVEST", MOLDINVEST);
		map.put("PRESSINVEST", PRESSINVEST);
		map.put("EQUIPINVEST", EQUIPINVEST);
		map.put("PURMOLDINVEST", PURMOLDINVEST);
		map.put("ETCINVEST", ETCINVEST);
		map.put("TOTALINVEST", TOTALINVEST);

		list.add(map);
	    }

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

    /**
     * <pre>
     * @description 프로젝트 원가 제품 목록
     * @author dhkim
     * @date 2019. 4. 16. 오전 11:14:31
     * @method getCostProductList
     * @param pjtNo
     * @return List<Map<String,String>>
     * @throws Exception
     * </pre>
     */
    public List<Map<String, String>> getCostProductList(String pjtNo) throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();

	    sql.append("	SELECT DISTINCT A0.PARTNO, A0.PARTNAME, VERSION                                                                \n");
	    sql.append("	FROM COSTPART A0, PRODUCTMASTER A1, E3PSPROJECTMASTER A2                                                       \n");
	    sql.append("	WHERE A0.IDA3A4=A1.IDA2A2 AND A0.CASEORDER=1 AND A0.IDA3B4=A2.IDA2A2 AND A2.PJTNO IN (" + pjtNo + ")		   \n");
	    sql.append("	AND A0.VERSION=(SELECT MAX(VERSION) FROM COSTPART WHERE CASEORDER=1 AND IDA3A4=A1.IDA2A2 AND IDA3B4=A2.IDA2A2) \n");
	    sql.append("	ORDER BY A0.VERSION DESC, A0.PARTNO                                                                            \n");

	    rs = stat.executeQuery(sql.toString());
	    List<String> check = new ArrayList<String>();

	    while (rs.next()) {
		String PARTNO = rs.getString("PARTNO");
		String PARTNAME = rs.getString("PARTNAME");

		if (!check.contains(PARTNO)) {
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("partNo", PARTNO);
		    map.put("partName", PARTNAME);
		    list.add(map);
		    check.add(PARTNO);
		}
	    }

	} catch (Exception e) {
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

    /**
     * <pre>
     * @description 원가수식 최종버전
     * @author dhkim
     * @date 2018. 10. 17. 오전 11:00:38
     * @method getLastestFormulaVersion
     * @return int
     * @throws Exception
     * </pre>
     */
    public int getLastestFormulaVersion() throws Exception {
	return getLastestFormulaVersion(false);
    }

    /**
     * <pre>
     * @description 원가수식 최종버전
     * @author dhkim
     * @date 2018. 8. 22. 오후 3:28:46
     * @method getLastestFormulaVersion
     * @return int
     * @throws Exception
     * </pre>
     */
    public int getLastestFormulaVersion(boolean isCompleted) throws Exception {

	int lastest = -1;
	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT MAX(FORMULAVERSION) AS LASTESTVERSION FROM COSTFORMULA ");
	    if (isCompleted)
		sql.append("WHERE STATUS='" + CostUtil.FORMULASTATE_COMPLETED + "'");

	    rs = stat.executeQuery(sql.toString());

	    if (rs.next()) {
		lastest = rs.getInt("LASTESTVERSION");
	    }
	} catch (Exception e) {
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

	return lastest;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 8. 22. 오후 3:28:40
     * @method replaceFormula
     * @param targetMap
     * @param formulaVersion 
     * @throws Exception
     * </pre>
     */
    public void replaceFormula(Map<String, String> targetMap, int formulaVersion) throws Exception {

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    Set<String> st = targetMap.keySet();
	    Iterator<String> it = st.iterator();

	    while (it.hasNext()) {
		String oldOid = it.next();
		String newOid = targetMap.get(oldOid);

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE COSTFORMULA SET FORMULAKEYS= REPLACE(FORMULAKEYS,'");
		sql.append(oldOid);
		sql.append("','");
		sql.append(newOid);
		sql.append("') WHERE FORMULAKEYS LIKE '%");
		sql.append(oldOid);
		sql.append("%' AND FORMULAVERSION=" + formulaVersion);

		stat.executeUpdate(sql.toString());
	    }

	} catch (Exception e) {
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
    }

    public WTPart updateGetPartNo(String partNo, CostPart cp) throws Exception {
	WTPart part = null;
	if (StringUtils.isNotEmpty(partNo)) {
	    part = PartBaseHelper.service.getLatestPart(partNo);
	}
	if (part == null && StringUtils.isNotEmpty(partNo)) {
	    partNo = partNo.replaceAll("\\p{Space}", ""); // 공백제거

	    if (StringUtils.isNotEmpty(partNo)) {
		part = PartBaseHelper.service.getLatestPart(partNo);
	    }

	    if (part != null && cp != null) {
		cp.setRealPartNo(partNo);
		PersistenceHelper.manager.save(cp);
	    }

	}
	return part;
    }

    public String getCheckRealPartNoMsg(List<CostPart> cpList) throws Exception {
	String realPartCheckmsg = "";
	String materialPartCheckmsg = "";

	for (CostPart cp : cpList) {

	    String realPartNo = cp.getRealPartNo();
	    String partNo = cp.getPartNo();

	    WTPart part = updateGetPartNo(realPartNo, cp);

	    if (part == null) {
		realPartCheckmsg += partNo + ",";
	    }

	    QueryResult qr = PersistenceHelper.manager.navigate(cp, CostMaterialLink.MATERIAL_ROLE, CostMaterialLink.class, true);

	    while (qr.hasMoreElements()) {
		CostMaterialInfo info = (CostMaterialInfo) qr.nextElement();

		part = updateGetPartNo(info.getPartNo(), null);

		if (part == null || (part != null && !info.getPartNo().startsWith("R2"))) {
		    materialPartCheckmsg += partNo + " 의 수지 : " + StringUtil.checkNull(info.getPartNo()) + "\r\n";
		}

	    }

	    /**
	     * 원재료 사양이 있다하더라도 비철 원재료번호는 없을 수 있다. 그래서 비철원재료 번호가 있다면 체크로직을 적용하는 것. 또한 부품 유형별 acl에서 필수값을 설정하고 있으므로 해당 설정에 의존하여 아래의 체크로직을 적용한다
	     */
	    if (StringUtils.isNotEmpty(cp.getMetalPartNo())) {
		String metalPartNo = cp.getMetalPartNo();

		part = updateGetPartNo(metalPartNo, null);

		if (part == null || (part != null && !metalPartNo.startsWith("R1"))) {
		    materialPartCheckmsg += partNo + " 의 비철 : " + metalPartNo + "\r\n";
		}
	    }

	}

	String CRLF = StringUtils.isNotEmpty(realPartCheckmsg) ? "\r\n\r\n" : "";

	realPartCheckmsg = StringUtils.removeEnd(realPartCheckmsg, ",") + CRLF;
	return realPartCheckmsg + materialPartCheckmsg;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 5. 17. 오후 4:49:27
     * @method checkRealPartNo
     * @param reqMap
     * @return boolean
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public boolean checkRealPartNo(Map<String, Object> reqMap, Map<String, Object> resMap, boolean isError) throws Exception {

	List<CostPart> cpList = (List<CostPart>) reqMap.get("CPLIST");

	if (cpList == null) {
	    cpList = CostTreeUtil.manager.getCostPartList(reqMap);
	}

	String msg = getCheckRealPartNoMsg(cpList);

	if (StringUtils.isNotEmpty(msg)) {
	    msg = StringUtils.removeEnd(msg, ",");
	    resMap.put("checkResult", false);
	    resMap.put("message", "실제 품번연동 확인 또는\r\n원자재번호를 체크하세요.\r\n\r\n" + msg);
	    isError = true;
	}

	return isError;

    }

    /**
     * <pre>
     * @description 원가동인별 변동 분석
     * @author dhkim
     * @date 2018. 5. 4. 오전 11:51:09
     * @method getAnalysisList
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getAnalysisList(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();
	List<Map<String, Object>> body = new ArrayList<Map<String, Object>>();
	Map<String, Object> bodyMap = new HashMap<String, Object>();
	List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

	String leftPartOid = String.valueOf(reqMap.get("leftPartOid"));
	String rightPartOid = String.valueOf(reqMap.get("rightPartOid"));
	String leftProductOid = String.valueOf(reqMap.get("leftProductOid"));
	String rightProductOid = String.valueOf(reqMap.get("rightProductOid"));
	boolean isTotal = Boolean.parseBoolean(String.valueOf(reqMap.get("isTotal")));
	boolean isUpdateSync = Boolean.parseBoolean(String.valueOf(reqMap.get("isUpdateSync")));

	CostPart leftPart = null;
	CostPart rightPart = null;

	if (StringUtil.checkString(leftPartOid)) {
	    leftPart = (CostPart) CommonUtil.getObject(leftPartOid);
	}

	boolean isERP = "ERP".equals(rightProductOid);

	CostPart lProduct = (CostPart) CommonUtil.getObject(leftProductOid);
	CostPart rProduct = null;

	List<CostPart> leftPartList = new ArrayList<CostPart>();
	List<CostPart> rightPartList = new ArrayList<CostPart>();

	int formulaVersion = 0;

	if (isUpdateSync) {

	    formulaVersion = CostUtil.manager.getLastestFormulaVersion(true);
	    lProduct.setFormulaVersion(formulaVersion);
	    leftPartList = CostUtil.manager.syncCostPart(lProduct, false);

	    if (leftPartList.size() > 0) {
		lProduct = leftPartList.get(0);
	    }

	} else {
	    leftPartList = CostTreeUtil.manager.getCostPartList(lProduct);
	}

	if (leftPart != null) {
	    for (CostPart part : leftPartList) {
		if (part.getPartNo().equals(leftPart.getPartNo())) {
		    leftPart = part;
		    break;
		}
	    }
	}

	if (isERP) {

	    List<CostPart> partList = new ArrayList<CostPart>();
	    for (CostPart part : leftPartList) {

		if (!StringUtil.checkString(part.getRealPartNo())) {
		    resMap.put("errorMsg", "실제 품번연동이 누락된 부품이 존재합니다.");
		    return resMap;
		}

		Map<String, Object> copyMap = CostUtil.manager.converObjectToMap(part);
		CostPart copyPart = (CostPart) CommonUtil.getObject(CommonUtil.getOIDString(part));
		CostUtil.manager.convertMapToObject(copyMap, copyPart);
		copyPart.setFormulaVersion(formulaVersion);
		partList.add(copyPart);
	    }

	    reqMap.put("partNo", lProduct.getRealPartNo());
	    reqMap.put("partList", partList);

	    String startMonth = (String) reqMap.get("startMonth");
	    String endMonth = (String) reqMap.get("endMonth");
	    startMonth = StringUtils.remove(startMonth, "-");
	    endMonth = StringUtils.remove(endMonth, "-");

	    reqMap.put("startMonth", startMonth);
	    reqMap.put("endMonth", endMonth);

	    rightPartList = CostCodeHelper.service.erpBom2CostPart(reqMap);
	    if (rightPartList.size() > 0) {
		rProduct = rightPartList.get(0);
	    }
	    System.out.println(" ############# " + rProduct.getTotalCostExpr());

	    for (CostPart part : rightPartList) {
		if (rightPartOid.equals(part.getRealPartNo())) {
		    rightPart = part;
		    break;
		}
	    }

	} else {

	    rProduct = (CostPart) CommonUtil.getObject(rightProductOid);
	    rightPart = (CostPart) CommonUtil.getObject(rightPartOid);

	    if (isUpdateSync) {
		rProduct.setFormulaVersion(formulaVersion);
		rightPartList = CostUtil.manager.syncCostPart(rProduct, false);

		if (rightPartList.size() > 0) {
		    rProduct = rightPartList.get(0);
		}

		if (rightPart != null) {
		    for (CostPart part : rightPartList) {
			if (part.getPartNo().equals(rightPart.getPartNo())) {
			    rightPart = part;
			    break;
			}
		    }
		}

	    } else {
		rightPartList = CostTreeUtil.manager.getCostPartList(rProduct);
	    }
	}

	if (isTotal) {
	    List<CostPart[]> compareParts = getComparePartList(leftPartList, rightPartList, isERP);

	    String costChangePriceTotal = "0";
	    String profitChangeTotal = "0";

	    for (CostPart[] parts : compareParts) {

		CostPart lPart = parts[0];
		CostPart rPart = parts[1];

		Map<String, Object> leftPartMap = new HashMap<String, Object>();
		Map<String, Object> rightPartMap = new HashMap<String, Object>();

		boolean isHasChild = false;
		if (lPart != null) {
		    leftPartMap = CostUtil.manager.converObjectToMap(lPart);
		    for (CostPart part : leftPartList) {
			CostPart parent = (CostPart) part.getParent();
			String partNo = StringUtil.checkNull(lPart.getPartNo());
			String realPartNo = StringUtil.checkNull(lPart.getRealPartNo());

			if ((parent != null && partNo.equals(parent.getPartNo()))
			        || (parent != null && realPartNo.equals(parent.getRealPartNo()))) {
			    isHasChild = true;
			    break;
			}
		    }
		}

		if (rPart != null) {
		    rightPartMap = CostUtil.manager.converObjectToMap(rPart);

		    if (!isHasChild) {
			for (CostPart part : rightPartList) {
			    CostPart parent = (CostPart) part.getParent();
			    String partNo = StringUtil.checkNull(rPart.getPartNo());
			    String realPartNo = StringUtil.checkNull(rPart.getRealPartNo());

			    if ((parent != null && partNo.equals(parent.getPartNo()))
				    || (parent != null && realPartNo.equals(parent.getRealPartNo()))) {
				isHasChild = true;
				break;
			    }
			}
		    }
		}

		leftPartMap.put("partObject", lPart);
		rightPartMap.put("partObject", rPart);
		leftPartMap.put("product", lProduct);
		rightPartMap.put("product", rProduct);
		leftPartMap.put("isTotal", isTotal);
		leftPartMap.put("isHasChild", isHasChild);

		items.addAll(drPartAnalysis(leftPartMap, rightPartMap));

		String tempCPT = (String) leftPartMap.get("costChangePriceTotal");
		String tempPCT = (String) leftPartMap.get("profitChangeTotal");

		costChangePriceTotal = (String) CostCalculateUtil.manager.eval(costChangePriceTotal + "+" + tempCPT);
		profitChangeTotal = (String) CostCalculateUtil.manager.eval(profitChangeTotal + "+" + tempPCT);
	    }

	    if (items.size() > 0) {

		Map<String, Object> rowData = new HashMap<String, Object>();
		rowData.put("Spanned", 1);
		rowData.put("realPartNoSpan", "7");
		rowData.put("realPartNo", "합계");
		rowData.put("realPartNoColor", "#BDF");
		rowData.put("costChangePriceColor", "#FFA");
		rowData.put("profitChangeColor", "#FFA");
		rowData.put("bestWorstColor", "#FFA");

		if (Float.parseFloat(costChangePriceTotal) > 0) {
		    rowData.put("costChangePriceHtmlPrefix", "<span class='red'>▲");
		    rowData.put("costChangePriceHtmlPostfix", "</span>");
		} else if (Float.parseFloat(costChangePriceTotal) < 0) {
		    rowData.put("costChangePriceHtmlPrefix", "<span class='blue'>▼");
		    rowData.put("costChangePriceHtmlPostfix", "</span>");
		}
		rowData.put("costChangePrice", costChangePriceTotal);

		if (Float.parseFloat(profitChangeTotal) > 0) {
		    rowData.put("profitChangeHtmlPrefix", "<span class='blue'>▲");
		    rowData.put("profitChangeHtmlPostfix", "</span>");
		} else if (Float.parseFloat(profitChangeTotal) < 0) {
		    rowData.put("profitChangeHtmlPrefix", "<span class='red'>▼");
		    rowData.put("profitChangeHtmlPostfix", "</span>");
		}

		rowData.put("profitChange", profitChangeTotal);

		items.add(rowData);
	    }

	} else {

	    Map<String, Object> leftPartMap = new HashMap<String, Object>();
	    Map<String, Object> rightPartMap = new HashMap<String, Object>();

	    boolean isHasChild = false;
	    if (leftPart != null) {
		leftPartMap = CostUtil.manager.converObjectToMap(leftPart);
		for (CostPart part : leftPartList) {
		    CostPart parent = (CostPart) part.getParent();
		    String partNo = StringUtil.checkNull(leftPart.getPartNo());
		    String realPartNo = StringUtil.checkNull(leftPart.getRealPartNo());

		    if ((parent != null && partNo.equals(parent.getPartNo()))
			    || (parent != null && realPartNo.equals(parent.getRealPartNo()))) {
			isHasChild = true;
			break;
		    }
		}
	    }

	    if (rightPart != null) {
		rightPartMap = CostUtil.manager.converObjectToMap(rightPart);

		if (!isHasChild) {
		    for (CostPart part : rightPartList) {
			CostPart parent = (CostPart) part.getParent();
			String partNo = StringUtil.checkNull(rightPart.getPartNo());
			String realPartNo = StringUtil.checkNull(rightPart.getRealPartNo());

			if ((parent != null && partNo.equals(parent.getPartNo()))
			        || (parent != null && realPartNo.equals(parent.getRealPartNo()))) {
			    isHasChild = true;
			    break;
			}
		    }
		}
	    }

	    leftPartMap.put("partObject", leftPart);
	    rightPartMap.put("partObject", rightPart);
	    leftPartMap.put("product", lProduct);
	    rightPartMap.put("product", rProduct);
	    leftPartMap.put("isTotal", isTotal);
	    leftPartMap.put("isHasChild", isHasChild);

	    items.addAll(drPartAnalysis(leftPartMap, rightPartMap));
	}

	bodyMap.put("Items", items);
	body.add(bodyMap);
	resMap.put("Body", body);
	return resMap;
    }

    /**
     * <pre>
     * @description 원가 동인별 변동 분석 부품정보
     * @author dhkim
     * @date 2018. 5. 9. 오전 11:10:04
     * @method drPartAnalysis
     * @param leftPartMap
     * @param rightPartMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    private List<Map<String, Object>> drPartAnalysis(Map<String, Object> leftPartMap, Map<String, Object> rightPartMap) throws Exception {

	CostPart leftPart = (CostPart) leftPartMap.get("partObject");
	CostPart rightPart = (CostPart) rightPartMap.get("partObject");
	CostPart leftProduct = (CostPart) leftPartMap.get("product");
	CostPart rightProduct = (CostPart) rightPartMap.get("product");

	boolean isHasChild = (boolean) leftPartMap.get("isHasChild");
	boolean isTotal = (boolean) leftPartMap.get("isTotal");

	String ltotalCost = leftProduct.getProductCostTotal();
	String rtotalCost = rightProduct.getProductCostTotal();

	String totalCostChange = (String) CostCalculateUtil.manager.eval(rtotalCost + "-" + ltotalCost);

	String lprofitRate = leftProduct.getProfitRateExpr();
	String rprofitRate = rightProduct.getProfitRateExpr();

	String profitRateChange = (String) CostCalculateUtil.manager.eval(rprofitRate + "-" + lprofitRate);

	String ltotalCostExpr = StringUtil.checkReplaceStr((String) leftPartMap.get("totalCostExpr"), "0");
	String rtotalCostExpr = StringUtil.checkReplaceStr((String) rightPartMap.get("totalCostExpr"), "0");

	List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

	List<Map<String, Object>> attrList = TrackingAttrExcelUtil.getTrackingAttrList();

	String realPartNo = (String) leftPartMap.get("realPartNo");
	String partName = (String) leftPartMap.get("partName");

	if (!StringUtil.checkString(realPartNo)) {
	    realPartNo = (String) leftPartMap.get("partNo");
	}

	if (leftPart == null) {
	    realPartNo = (String) rightPartMap.get("realPartNo");
	    partName = (String) rightPartMap.get("partName");

	    if (!StringUtil.checkString(realPartNo)) {
		realPartNo = (String) rightPartMap.get("partNo");
	    }
	}

	String costChangePriceTotal = "0";
	String profitChangeTotal = "0";

	boolean isMaterialOption = false;
	boolean isManageOption = false;
	boolean leftNullValueSet = (leftPart == null);

	List<String> cAttrList = new ArrayList<String>();

	for (Map<String, Object> attrData : attrList) {
	    String attrKey = (String) attrData.get("attrKey");
	    cAttrList.add(attrKey);
	}

	// 엑셀에 정의된 컬럼이 아닌 attribute를 선별해서 대입하는 로직 (수식제외)
	List<CostAttribute> caList = CostUtil.manager.getCostAttributeList();

	for (CostAttribute attr : caList) {
	    if (!cAttrList.contains(attr.getCode()) && attr.getAttrType().indexOf(CostUtil.FORMULATYPE) < 0) {
		String rightValue = StringUtil.checkReplaceStr((String) rightPartMap.get(attr.getCode()), "0");
		leftPartMap.put(attr.getCode(), rightValue);
	    }
	}

	System.out.println("##################### " + realPartNo + " BEFORE ANALYSIS START #############################################");
	System.out.println(realPartNo + " ### leftPartMap #### costTotalExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("materialCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### laborCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("laborCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### expenseExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("expenseExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### manageCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("manageCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### reduceCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("reduceCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### totalCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("totalCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println("##################### " + realPartNo + " BEFORE ANALYSIS START #############################################");

	if (rightPart != null) {
	    leftPartMap.put("partType", rightPartMap.get("partType"));
	    leftPartMap.put("mftFactory1", rightPartMap.get("mftFactory1"));
	    leftPartMap.put("mftFactory2", rightPartMap.get("mftFactory2"));
	}

	Map<String, Object> tempLeftPartMap = ObjectUtil.manager.converObjectToMap(leftPart);

	for (Map<String, Object> attrData : attrList) {

	    String displayName = (String) attrData.get("displayName");
	    String attrKey = (String) attrData.get("attrKey");
	    boolean isTarget = (boolean) attrData.get("isTarget");

	    String leftValue = StringUtil.checkReplaceStr((String) leftPartMap.get(attrKey), "0");
	    String rightValue = StringUtil.checkReplaceStr((String) rightPartMap.get(attrKey), "0");

	    try {

		leftValue = new BigDecimal(leftValue).setScale(10, BigDecimal.ROUND_HALF_UP).toString();
		rightValue = new BigDecimal(rightValue).setScale(10, BigDecimal.ROUND_HALF_UP).toString();

	    } catch (NumberFormatException e) {
	    }

	    if (!leftValue.equals(rightValue) || (!ltotalCostExpr.equals(rtotalCostExpr) && leftValue.equals(rightValue))) {
		if (!isHasChild && (attrKey.equals("subCostAllTotal") || attrKey.equals("subCostExceptTotal"))) {
		    continue;
		}

		boolean isOptions = false;

		if (attrKey.equals("packUnitPriceOption") || attrKey.equals("apUnitPriceOption")) {

		    if (isMaterialOption)
			continue;

		    displayName = "산출옵션(재료비)";

		    if (attrKey.equals("packUnitPriceOption")) {
			leftPartMap.put("apUnitPriceOption", rightPartMap.get("apUnitPriceOption"));
		    } else {
			leftPartMap.put("packUnitPriceOption", rightPartMap.get("packUnitPriceOption"));
		    }

		    isOptions = true;
		    isMaterialOption = true;
		} else if (attrKey.equals("coManageRateOption") || attrKey.equals("subCostExceptTotalOption")) {

		    if (isManageOption)
			continue;

		    displayName = "산출옵션(관리비)";

		    if (attrKey.equals("coManageRateOption")) {
			leftPartMap.put("subCostExceptTotalOption", rightPartMap.get("subCostExceptTotalOption"));
		    } else {
			leftPartMap.put("coManageRateOption", rightPartMap.get("coManageRateOption"));
		    }

		    isOptions = true;
		    isManageOption = true;
		}

		if (isTarget || isOptions) {

		    Map<String, Object> rowData = new HashMap<String, Object>();
		    rowData.put("realPartNo", realPartNo);
		    rowData.put("partName", partName);
		    rowData.put("attrName", displayName);

		    LOGGER.info(attrKey + "###############################" + rightValue + "########" + leftValue);

		    // 산출기준 [ 재료비, 노무비, 경비, 관리비 ]
		    if (attrKey.equals("calcStdMaterial") || attrKey.equals("calcStdLabor") || attrKey.equals("calcStdExpense")
			    || attrKey.equals("calcStdManage")) {

			NumberCode lCode = CodeHelper.manager.getNumberCode("CALCULATIONSTD", leftValue);
			NumberCode rCode = CodeHelper.manager.getNumberCode("CALCULATIONSTD", rightValue);

			String lCodeName = "";
			String rCodeName = "";

			if (lCode != null) {
			    lCodeName = lCode.getName();
			}
			if (rCode != null) {
			    rCodeName = rCode.getName();
			}

			if (StringUtil.checkString(lCodeName)) {
			    rowData.put("leftValueHtmlPostfix", lCodeName);
			}
			if (StringUtil.checkString(rCodeName)) {
			    rowData.put("rightValueHtmlPostfix", rCodeName);
			}

			rowData.put("leftValue", "");
			rowData.put("rightValue", "");

			// 화폐단위 [ 외주단가 ]
		    } else if (attrKey.equals("outMonetaryUnit")) {

			NumberCode lCode = CodeHelper.manager.getNumberCode("MONETARYUNIT", leftValue);
			NumberCode rCode = CodeHelper.manager.getNumberCode("MONETARYUNIT", leftValue);

			String lCodeName = "";
			String rCodeName = "";

			if (lCode != null) {
			    lCodeName = lCode.getName();
			}
			if (rCode != null) {
			    rCodeName = rCode.getName();
			}

			if (StringUtil.checkString(lCodeName)) {
			    rowData.put("leftValueHtmlPostfix", lCodeName);
			}
			if (StringUtil.checkString(rCodeName)) {
			    rowData.put("rightValueHtmlPostfix", rCodeName);
			}

			rowData.put("leftValue", "");
			rowData.put("rightValue", "");

		    } else {

			rowData.put("leftValue", leftValue);
			rowData.put("rightValue", rightValue);

			String change = (String) CostCalculateUtil.manager.eval(rightValue + "-" + leftValue);
			if (Float.parseFloat(change) > 0) {
			    rowData.put("changeHtmlPrefix", "<span class='red'>▲");
			    rowData.put("changeHtmlPostfix", "</span>");
			} else if (Float.parseFloat(change) < 0) {
			    rowData.put("changeHtmlPrefix", "<span class='blue'>▼");
			    rowData.put("changeHtmlPostfix", "</span>");
			}
			rowData.put("change", change);

		    }

		    // ############################## 총원가 (변동전) #####################################################
		    String totalCostExpr = StringUtil.checkReplaceStr((String) leftPartMap.get("totalCostExpr"), "0");
		    String us = StringUtil.checkReplaceStr((String) leftPartMap.get("us"), "0");
		    totalCostExpr = (String) CostCalculateUtil.manager.eval(totalCostExpr + "*" + us);
		    // ############################## 대입 후 연산 #####################################################
		    System.out.println(realPartNo + " ######### " + attrKey + "#### 변동전 총원가 ########## " + totalCostExpr);

		    leftPartMap.put(attrKey, rightValue);

		    if (attrKey.equals("pUnitCost")) {
			Iterator<String> it = leftPartMap.keySet().iterator();

			while (it.hasNext()) {
			    String key = it.next();
			    Object leftval = leftPartMap.get(key);
			    Object tempval = tempLeftPartMap.get(key);

			    if (leftval != null && !leftval.equals(tempval)) {
				System.out.println("############# 변경 된 key: " + key + " leftvalue : " + String.valueOf(leftval)
				        + "원본 value : " + String.valueOf(tempval) + " #################");
			    }
			}
		    }

		    // System.out.println("##############CALCULATE PART BEFORE###" + attrKey + "#####" + rightValue + "##### " +
		    // totalCostExpr);
		    if (leftPart != null) {
			CostCalculateUtil.manager.calculateTotalCost(leftPartMap);
		    }

		    // ############################## 총원가 (변동후) #####################################################
		    String changeTotalCostExpr = StringUtil.checkReplaceStr((String) leftPartMap.get("totalCost"), "0");

		    if (leftNullValueSet) {
			changeTotalCostExpr = StringUtil.checkReplaceStr((String) rightPartMap.get("totalCostExpr"), "0");
			us = StringUtil.checkReplaceStr((String) rightPartMap.get("us"), "0");
			changeTotalCostExpr = (String) CostCalculateUtil.manager.eval(changeTotalCostExpr + "*" + us);
			leftNullValueSet = false;
		    }
		    System.out.println(realPartNo + " ######### " + attrKey + "#### 변동후 총원가 ########## " + changeTotalCostExpr);

		    // System.out.println("##############CALCULATE PART AFTER############# " + changeTotalCostExpr);
		    // ############################## 원가 변동액 #####################################################
		    String costChangePrice = (String) CostCalculateUtil.manager.eval(changeTotalCostExpr + "-" + totalCostExpr);

		    if (leftValue.equals(rightValue) && Float.parseFloat(costChangePrice) == 0) {
			continue;
		    }

		    costChangePriceTotal = (String) CostCalculateUtil.manager.eval(costChangePriceTotal + "+" + costChangePrice);
		    // System.out.println("###############TOTAL CALCULATE################ " + costChangePriceTotal);
		    // ############################## 수익변동율 #####################################################
		    String profitChange = (String) CostCalculateUtil.manager.eval(profitRateChange + "*(" + costChangePrice + "/"
			    + totalCostChange + ")", BigDecimal.ROUND_HALF_UP);
		    // System.out.println("##############CALCULATE PART DIFF############# " + costChangePrice);
		    profitChangeTotal = (String) CostCalculateUtil.manager.eval(profitChangeTotal + "+" + profitChange);

		    if (Float.parseFloat(costChangePrice) > 0) {
			rowData.put("costChangePriceHtmlPrefix", "<span class='red'>▲");
			rowData.put("costChangePriceHtmlPostfix", "</span>");
		    } else if (Float.parseFloat(costChangePrice) < 0) {
			rowData.put("costChangePriceHtmlPrefix", "<span class='blue'>▼");
			rowData.put("costChangePriceHtmlPostfix", "</span>");
		    }
		    rowData.put("costChangePrice", costChangePrice);

		    if (Float.parseFloat(profitChange) > 0) {
			rowData.put("profitChangeHtmlPrefix", "<span class='blue'>▲");
			rowData.put("profitChangeHtmlPostfix", "</span>");
		    } else if (Float.parseFloat(profitChange) < 0) {
			rowData.put("profitChangeHtmlPrefix", "<span class='red'>▼");
			rowData.put("profitChangeHtmlPostfix", "</span>");
		    }
		    rowData.put("profitChange", profitChange);

		    rowData.put("Spanned", 1);

		    items.add(rowData);

		} else {
		    leftPartMap.put(attrKey, rightValue);
		}
	    }
	}

	if (items.size() > 0) {
	    Map<String, Object> rowData = items.get(0);
	    rowData.put("realPartNoRowSpan", items.size());
	    rowData.put("partNameRowSpan", items.size());

	    if (!isTotal) {

		rowData = new HashMap<String, Object>();
		rowData.put("Spanned", 1);
		rowData.put("realPartNoSpan", "7");
		rowData.put("realPartNo", "합계");
		rowData.put("realPartNoColor", "#BDF");
		rowData.put("costChangePriceColor", "#FFA");
		rowData.put("profitChangeColor", "#FFA");
		rowData.put("bestWorstColor", "#FFA");

		if (Float.parseFloat(costChangePriceTotal) > 0) {
		    rowData.put("costChangePriceHtmlPrefix", "<span class='red'>▲");
		    rowData.put("costChangePriceHtmlPostfix", "</span>");
		} else if (Float.parseFloat(costChangePriceTotal) < 0) {
		    rowData.put("costChangePriceHtmlPrefix", "<span class='blue'>▼");
		    rowData.put("costChangePriceHtmlPostfix", "</span>");
		}
		rowData.put("costChangePrice", costChangePriceTotal);

		if (Float.parseFloat(profitChangeTotal) > 0) {
		    rowData.put("profitChangeHtmlPrefix", "<span class='blue'>▲");
		    rowData.put("profitChangeHtmlPostfix", "</span>");
		} else if (Float.parseFloat(profitChangeTotal) < 0) {
		    rowData.put("profitChangeHtmlPrefix", "<span class='red'>▼");
		    rowData.put("profitChangeHtmlPostfix", "</span>");
		}

		rowData.put("profitChange", profitChangeTotal);

		items.add(rowData);
	    } else {
		leftPartMap.put("costChangePriceTotal", costChangePriceTotal);
		leftPartMap.put("profitChangeTotal", profitChangeTotal);
	    }
	}

	System.out.println("##################### " + realPartNo + " AFTER ANALYSIS END #############################################");
	System.out.println(realPartNo + " ### leftPartMap #### costTotalExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("materialCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### laborCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("laborCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### expenseExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("expenseExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### manageCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("manageCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### reduceCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("reduceCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println(realPartNo + " ### leftPartMap #### totalCostExpr ########## "
	        + CostCalculateUtil.manager.eval(leftPartMap.get("totalCostExpr") + "*" + leftPartMap.get("us")));
	System.out.println("##################### " + realPartNo + "  AFTER ANALYSIS END #############################################");

	System.out.println("##################### " + realPartNo + " RECALCULATE RIGHT PART #############################################");
	CostCalculateUtil.manager.calculateTotalCost(rightPartMap);
	System.out.println(realPartNo + " ### rightPartMap #### costTotalExpr ########## "
	        + CostCalculateUtil.manager.eval(rightPartMap.get("materialCostExpr") + "*" + rightPartMap.get("us")));
	System.out.println(realPartNo + " ### rightPartMap #### laborCostExpr ########## "
	        + CostCalculateUtil.manager.eval(rightPartMap.get("laborCostExpr") + "*" + rightPartMap.get("us")));
	System.out.println(realPartNo + " ### rightPartMap #### expenseExpr ########## "
	        + CostCalculateUtil.manager.eval(rightPartMap.get("expenseExpr") + "*" + rightPartMap.get("us")));
	System.out.println(realPartNo + " ### rightPartMap #### manageCostExpr ########## "
	        + CostCalculateUtil.manager.eval(rightPartMap.get("manageCostExpr") + "*" + rightPartMap.get("us")));
	System.out.println(realPartNo + " ### rightPartMap #### reduceCostExpr ########## "
	        + CostCalculateUtil.manager.eval(rightPartMap.get("reduceCostExpr") + "*" + rightPartMap.get("us")));
	System.out.println(realPartNo + " ### rightPartMap #### totalCostExpr ########## "
	        + CostCalculateUtil.manager.eval(rightPartMap.get("totalCostExpr") + "*" + rightPartMap.get("us")));
	System.out.println("##################### " + realPartNo + " RECALCULATE RIGHT PART #############################################");

	return items;
    }

    /**
     * <pre>
     * @description BOM 구조 변동 정보
     * @author dhkim
     * @date 2019. 5. 22. 오후 4:39:03
     * @method getComparePartList
     * @param leftPartList
     * @param rightPartList
     * @return List<CostPart[]>
     * @throws Exception
     * </pre>
     */
    public List<CostPart[]> getComparePartList(List<CostPart> leftPartList, List<CostPart> rightPartList, boolean isErp) throws Exception {

	// ############################### PART COMPARE ADDING ####################################
	List<String> lpartNoList = new ArrayList<String>();
	List<String> rpartNoList = new ArrayList<String>();
	List<String> apartNoList = new ArrayList<String>();

	if (isErp) {
	    for (CostPart part : leftPartList) {
		String partNo = part.getRealPartNo();
		lpartNoList.add(partNo);
	    }

	    for (CostPart part : rightPartList) {
		String partNo = part.getRealPartNo();
		rpartNoList.add(partNo);
	    }
	} else {
	    for (CostPart part : leftPartList) {
		String partNo = part.getPartNo();
		lpartNoList.add(partNo);
	    }

	    for (CostPart part : rightPartList) {
		String partNo = part.getPartNo();
		rpartNoList.add(partNo);
	    }
	}

	boolean isAdding = true;
	int count = 0;

	while (isAdding) {

	    String one = "";
	    String two = "";

	    if (count < lpartNoList.size()) {
		one = lpartNoList.get(count);
	    }
	    if (count < rpartNoList.size()) {
		two = rpartNoList.get(count);
	    }

	    if (!apartNoList.contains(one) && one.length() > 0) {
		if (!rpartNoList.contains(one)) {
		    apartNoList.add(one);
		}
	    }
	    if (!apartNoList.contains(two) && two.length() > 0) {
		apartNoList.add(two);
	    }

	    isAdding = count <= lpartNoList.size() || count <= rpartNoList.size();
	    count++;
	}

	Map<String, CostPart[]> tempMap = new HashMap<String, CostPart[]>();
	List<CostPart[]> result = new ArrayList<CostPart[]>();

	for (String partNo : apartNoList) {
	    CostPart[] parts = { null, null };
	    tempMap.put(partNo, parts);
	}
	// ############################### PART COMPARE ADDING ####################################

	// ############################### SETTING LEFT, RIGHT PART ####################################
	for (CostPart part : leftPartList) {
	    String partNo = part.getPartNo();
	    if (isErp) {
		partNo = part.getRealPartNo();
	    }
	    CostPart[] parts = tempMap.get(partNo);
	    parts[0] = part;
	}

	for (CostPart part : rightPartList) {
	    String partNo = part.getPartNo();
	    if (isErp) {
		partNo = part.getRealPartNo();
	    }
	    CostPart[] parts = tempMap.get(partNo);
	    parts[1] = part;
	}

	for (String partNo : apartNoList) {
	    CostPart[] parts = tempMap.get(partNo);
	    result.add(parts);
	}

	// ############################### SETTING LEFT, RIGHT PART ####################################

	return result;
    }

    /**
     * <pre>
     * @description DR 단계별 원가변동 분석 정보
     * @author dhkim
     * @date 2018. 4. 5. 오전 10:10:18
     * @method getCompareDRInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getCompareDRInfo(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	String leftPartOid = (String) reqMap.get("leftPartOid");
	String rightPartOid = (String) reqMap.get("rightPartOid");
	boolean isUpdateSync = Boolean.parseBoolean(String.valueOf(reqMap.get("isUpdateSync")));

	CostPart leftPart = (CostPart) CommonUtil.getObject(leftPartOid);
	CostPart rightPart = null;

	List<CostPart> leftPartList = new ArrayList<CostPart>();
	List<CostPart> rightPartList = new ArrayList<CostPart>();

	int formulaVersion = 0;

	if (isUpdateSync) {
	    formulaVersion = CostUtil.manager.getLastestFormulaVersion(true);
	    leftPart.setFormulaVersion(formulaVersion);
	    leftPartList = CostUtil.manager.syncCostPart(leftPart, false);

	    if (leftPartList.size() > 0) {
		leftPart = leftPartList.get(0);
	    }
	} else {
	    leftPartList = CostTreeUtil.manager.getCostPartList(leftPart);
	}

	boolean isERP = "ERP".equals(rightPartOid);

	if (isERP) {

	    List<CostPart> partList = new ArrayList<CostPart>();

	    for (CostPart part : leftPartList) {

		if (!StringUtil.checkString(part.getRealPartNo())) {
		    resMap.put("errorMsg", "실제 품번연동이 누락된 부품이 존재합니다.");
		    return resMap;
		}

		Map<String, Object> copyMap = CostUtil.manager.converObjectToMap(part);
		CostPart copyPart = (CostPart) CommonUtil.getObject(CommonUtil.getOIDString(part));
		CostUtil.manager.convertMapToObject(copyMap, copyPart);
		copyPart.setFormulaVersion(formulaVersion);
		partList.add(copyPart);
	    }

	    reqMap.put("partNo", leftPart.getRealPartNo());
	    reqMap.put("partList", partList);
	    String startMonth = (String) reqMap.get("startMonth");
	    String endMonth = (String) reqMap.get("endMonth");
	    startMonth = StringUtils.remove(startMonth, "-");
	    endMonth = StringUtils.remove(endMonth, "-");

	    reqMap.put("startMonth", startMonth);
	    reqMap.put("endMonth", endMonth);

	    rightPartList = CostCodeHelper.service.erpBom2CostPart(reqMap);

	    if (rightPartList.size() > 0) {
		rightPart = rightPartList.get(0);
	    } else {
		resMap.put("errorMsg", leftPart.getRealPartNo() + "에 대한 양산원가 정보가 존재하지 않습니다.");
		return resMap;
	    }

	} else {
	    rightPart = (CostPart) CommonUtil.getObject(rightPartOid);

	    if (isUpdateSync) {
		rightPart.setFormulaVersion(formulaVersion);
		rightPartList = CostUtil.manager.syncCostPart(rightPart, false);

		if (rightPartList.size() > 0) {
		    rightPart = rightPartList.get(0);
		}

	    } else {
		rightPartList = CostTreeUtil.manager.getCostPartList(rightPart);
	    }
	}

	List<CostPart[]> compareParts = getComparePartList(leftPartList, rightPartList, isERP);

	List<Map<String, Object>> drAnalysisList = new ArrayList<Map<String, Object>>();

	String ltmaterialCostExpr = "0"; // 재료비
	String ltlaborCostExpr = "0"; // 노무비
	String ltexpenseExpr = "0"; // 경비
	String ltmanageCostExpr = "0"; // 관리비
	String ltreduceCostExpr = "0"; // 감가비

	String rtmaterialCostExpr = "0"; // 재료비
	String rtlaborCostExpr = "0"; // 노무비
	String rtexpenseExpr = "0"; // 경비
	String rtmanageCostExpr = "0"; // 관리비
	String rtreduceCostExpr = "0"; // 감가비

	String lttotalCostExpr = leftPart.getProductCostTotal(); // 총원가 합계
	String rttotalCostExpr = rightPart.getProductCostTotal(); // 총원가 합계
	String lprofitRateExpr = leftPart.getProfitRateExpr(); // 수익율
	String rprofitRateExpr = rightPart.getProfitRateExpr(); // 수익율
	String lsalesTargetCostExpr = leftPart.getSalesTargetCostExpr(); // 판매목표가
	String rsalesTargetCostExpr = rightPart.getSalesTargetCostExpr(); // 판매목표가

	// 수익율(백분율)
	lprofitRateExpr = (String) CostCalculateUtil.manager.eval(lprofitRateExpr + "*100");
	rprofitRateExpr = (String) CostCalculateUtil.manager.eval(rprofitRateExpr + "*100");

	// 판가변동액
	String saleCostChangePrice = (String) CostCalculateUtil.manager.eval(rsalesTargetCostExpr + "-" + lsalesTargetCostExpr);

	// 수익변동율
	String profitChangeRate = (String) CostCalculateUtil.manager.eval(rprofitRateExpr + "-" + lprofitRateExpr);

	for (CostPart[] parts : compareParts) {

	    Map<String, Object> data = new HashMap<String, Object>();

	    CostPart lPart = parts[0];
	    CostPart rPart = parts[1];
	    boolean lpartExist = lPart != null;
	    boolean rpartExist = rPart != null;
	    data.put("lpartExist", lpartExist);
	    data.put("rpartExist", rpartExist);

	    String lmaterialCostExpr = "0"; // 재료비
	    String llaborCostExpr = "0"; // 노무비
	    String lexpenseExpr = "0"; // 경비
	    String lmanageCostExpr = "0"; // 관리비
	    String lreduceCostExpr = "0"; // 감가비
	    String ltotalCostExpr = "0"; // 합계
	    String us = "0";

	    String rmaterialCostExpr = "0"; // 재료비
	    String rlaborCostExpr = "0"; // 노무비
	    String rexpenseExpr = "0"; // 경비
	    String rmanageCostExpr = "0"; // 관리비
	    String rreduceCostExpr = "0"; // 감가비
	    String rtotalCostExpr = "0"; // 합계

	    if (lpartExist) {

		lmaterialCostExpr = lPart.getMaterialCostExpr();
		llaborCostExpr = lPart.getLaborCostExpr();
		lexpenseExpr = lPart.getExpenseExpr();
		lmanageCostExpr = lPart.getManageCostExpr();
		lreduceCostExpr = lPart.getReduceCostExpr();
		ltotalCostExpr = lPart.getTotalCostExpr();
		us = StringUtil.checkReplaceStr(lPart.getUs(), "0");

		lmaterialCostExpr = (String) CostCalculateUtil.manager.eval(lmaterialCostExpr + "*" + us);
		llaborCostExpr = (String) CostCalculateUtil.manager.eval(llaborCostExpr + "*" + us);
		lexpenseExpr = (String) CostCalculateUtil.manager.eval(lexpenseExpr + "*" + us);
		lmanageCostExpr = (String) CostCalculateUtil.manager.eval(lmanageCostExpr + "*" + us);
		lreduceCostExpr = (String) CostCalculateUtil.manager.eval(lreduceCostExpr + "*" + us);
		ltotalCostExpr = (String) CostCalculateUtil.manager.eval(ltotalCostExpr + "*" + us);

		ltmaterialCostExpr = (String) CostCalculateUtil.manager.eval(ltmaterialCostExpr + "+" + lmaterialCostExpr);
		ltlaborCostExpr = (String) CostCalculateUtil.manager.eval(ltlaborCostExpr + "+" + llaborCostExpr);
		ltexpenseExpr = (String) CostCalculateUtil.manager.eval(ltexpenseExpr + "+" + lexpenseExpr);
		ltmanageCostExpr = (String) CostCalculateUtil.manager.eval(ltmanageCostExpr + "+" + lmanageCostExpr);
		ltreduceCostExpr = (String) CostCalculateUtil.manager.eval(ltreduceCostExpr + "+" + lreduceCostExpr);

		String realPartNo = lPart.getRealPartNo();
		if (!StringUtil.checkString(realPartNo))
		    realPartNo = lPart.getPartNo();

		data.put("lpartNo", realPartNo);
		data.put("lpartName", lPart.getPartName());
		data.put("leftPartOid", CommonUtil.getOIDString(lPart));
	    }
	    data.put("lmaterialCostExpr", lmaterialCostExpr);
	    data.put("llaborCostExpr", llaborCostExpr);
	    data.put("lexpenseExpr", lexpenseExpr);
	    data.put("lmanageCostExpr", lmanageCostExpr);
	    data.put("lreduceCostExpr", lreduceCostExpr);
	    data.put("ltotalCostExpr", ltotalCostExpr);
	    data.put("lsalesTargetCostExpr", lsalesTargetCostExpr);
	    data.put("lprofitRateExpr", lprofitRateExpr);

	    if (rpartExist) {
		rmaterialCostExpr = rPart.getMaterialCostExpr(); // 재료비
		rlaborCostExpr = rPart.getLaborCostExpr(); // 노무비
		rexpenseExpr = rPart.getExpenseExpr(); // 경비
		rmanageCostExpr = rPart.getManageCostExpr(); // 관리비
		rreduceCostExpr = rPart.getReduceCostExpr(); // 감가비
		rtotalCostExpr = rPart.getTotalCostExpr(); // 합계
		us = StringUtil.checkReplaceStr(rPart.getUs(), "0");

		rmaterialCostExpr = (String) CostCalculateUtil.manager.eval(rmaterialCostExpr + "*" + us);
		rlaborCostExpr = (String) CostCalculateUtil.manager.eval(rlaborCostExpr + "*" + us);
		rexpenseExpr = (String) CostCalculateUtil.manager.eval(rexpenseExpr + "*" + us);
		rmanageCostExpr = (String) CostCalculateUtil.manager.eval(rmanageCostExpr + "*" + us);
		rreduceCostExpr = (String) CostCalculateUtil.manager.eval(rreduceCostExpr + "*" + us);
		rtotalCostExpr = (String) CostCalculateUtil.manager.eval(rtotalCostExpr + "*" + us);

		rtmaterialCostExpr = (String) CostCalculateUtil.manager.eval(rtmaterialCostExpr + "+" + rmaterialCostExpr);
		rtlaborCostExpr = (String) CostCalculateUtil.manager.eval(rtlaborCostExpr + "+" + rlaborCostExpr);
		rtexpenseExpr = (String) CostCalculateUtil.manager.eval(rtexpenseExpr + "+" + rexpenseExpr);
		rtmanageCostExpr = (String) CostCalculateUtil.manager.eval(rtmanageCostExpr + "+" + rmanageCostExpr);
		rtreduceCostExpr = (String) CostCalculateUtil.manager.eval(rtreduceCostExpr + "+" + rreduceCostExpr);

		String realPartNo = rPart.getRealPartNo();
		if (!StringUtil.checkString(realPartNo))
		    realPartNo = rPart.getPartNo();

		data.put("rpartNo", realPartNo);
		data.put("rpartName", rPart.getPartName());

		if (isERP) {
		    data.put("rightPartOid", rPart.getRealPartNo());
		} else {
		    data.put("rightPartOid", CommonUtil.getOIDString(rPart));
		}
	    }

	    data.put("rmaterialCostExpr", rmaterialCostExpr);
	    data.put("rlaborCostExpr", rlaborCostExpr);
	    data.put("rexpenseExpr", rexpenseExpr);
	    data.put("rmanageCostExpr", rmanageCostExpr);
	    data.put("rreduceCostExpr", rreduceCostExpr);
	    data.put("rtotalCostExpr", rtotalCostExpr);
	    data.put("rsalesTargetCostExpr", rsalesTargetCostExpr);
	    data.put("rprofitRateExpr", rprofitRateExpr);

	    // 원가변동액
	    String costChangePrice = (String) CostCalculateUtil.manager.eval(rtotalCostExpr + "-" + ltotalCostExpr);
	    data.put("costChangePrice", costChangePrice);
	    data.put("saleCostChangePrice", saleCostChangePrice);
	    data.put("profitChangeRate", profitChangeRate);

	    drAnalysisList.add(data);
	}

	// drAnalysisList = Lists.reverse(drAnalysisList);

	Map<String, Object> data = new HashMap<String, Object>();
	data.put("lpartExist", true);
	data.put("lpartNo", "합계");
	data.put("lformulaVersion", leftPart.getFormulaVersion());
	data.put("lmaterialCostExpr", ltmaterialCostExpr);
	data.put("llaborCostExpr", ltlaborCostExpr);
	data.put("lexpenseExpr", ltexpenseExpr);
	data.put("lmanageCostExpr", ltmanageCostExpr);
	data.put("lreduceCostExpr", ltreduceCostExpr);
	data.put("ltotalCostExpr", lttotalCostExpr);

	data.put("rpartExist", true);
	data.put("rpartNo", "합계");
	data.put("rformulaVersion", rightPart.getFormulaVersion());
	data.put("rmaterialCostExpr", rtmaterialCostExpr);
	data.put("rlaborCostExpr", rtlaborCostExpr);
	data.put("rexpenseExpr", rtexpenseExpr);
	data.put("rmanageCostExpr", rtmanageCostExpr);
	data.put("rreduceCostExpr", rtreduceCostExpr);
	data.put("rtotalCostExpr", rttotalCostExpr);
	data.put("leftPartOid", leftPartOid);
	data.put("rightPartOid", rightPartOid);

	// 원가변동액
	String costChangePrice = (String) CostCalculateUtil.manager.eval(rttotalCostExpr + "-" + lttotalCostExpr);
	data.put("costChangePrice", costChangePrice);

	drAnalysisList.add(data);

	resMap.put("drAnalysisList", drAnalysisList);

	if (!isERP) {
	    List<Map<String, Object>> eqAnalysisList = CostCodeHelper.service.getCompareCostBasicInfo(leftPart, rightPart);
	    resMap.put("eqAnalysisList", eqAnalysisList);
	}

	return resMap;
    }

    /**
     * <pre>
     * @description 버전별 제품 조회
     * @author dhkim
     * @date 2018. 4. 3. 오후 6:24:42
     * @method getVersionPart
     * @param master
     * @param version
     * @return CostPart
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CostPart getVersionPart(ProductMaster master, String version) throws Exception {

	CostPart part = null;
	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostPart.class, true);

	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.VERSION, SearchCondition.EQUAL, Integer.parseInt(version)),
	        new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(master)), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    part = (CostPart) o[0];
	}

	return part;
    }

    /**
     * <pre>
     * @description DR STEP별 제품 조회
     * @author dhkim
     * @date 2018. 4. 23. 오전 11:38:45
     * @method getDrStepPart
     * @param master
     * @param drStep
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<CostPart> getDrStepPart(ProductMaster master, String drStep) throws Exception {

	List<CostPart> drPartList = new ArrayList<CostPart>();

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostPart.class, true);

	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.DR_STEP, SearchCondition.EQUAL, drStep), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(master)), new int[] { idx });

	SearchUtil.setOrderBy(qs, CostPart.class, 0, CostPart.VERSION, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostPart part = (CostPart) o[0];
	    drPartList.add(part);
	}

	return drPartList;
    }

    /**
     * <pre>
     * @description 제품의 모든 버전&케이스 조회
     * @author dhkim
     * @date 2018. 4. 23. 오후 1:58:50
     * @method getAllVersionLastestPart
     * @param master
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<CostPart> getAllVersionLastestPart(ProductMaster master) throws Exception {

	List<CostPart> drPartList = new ArrayList<CostPart>();

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostPart.class, true);

	// if (qs.getConditionCount() > 0) qs.appendAnd();
	// qs.appendWhere(new SearchCondition(CostPart.class, CostPart.LASTEST, SearchCondition.IS_TRUE, true), new int[] { idx });

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(master)), new int[] { idx });

	SearchUtil.setOrderBy(qs, CostPart.class, 0, CostPart.VERSION, false);
	SearchUtil.setOrderBy(qs, CostPart.class, 0, CostPart.SUB_VERSION, false);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostPart part = (CostPart) o[0];
	    drPartList.add(part);
	}

	return drPartList;
    }

    public List<CostPart> syncCostPart(CostPart rootPart) throws Exception {
	DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(rootPart);
	return syncCostPart(root, true);
    }

    public List<CostPart> syncCostPart(CostPart rootPart, boolean isSync) throws Exception {
	DefaultMutableTreeNode root = CostTreeUtil.manager.getCostTree(rootPart);
	return syncCostPart(root, isSync);
    }

    /**
     * <pre>
     * @description 기준정보 동기화 및 원가 산출 처리
     * @author dhkim
     * @date 2018. 3. 13. 오후 5:12:10
     * @method syncCostPart
     * @param rootPart
     * @return List<CostPart>
     * @throws Exception
     * </pre>
     */
    public List<CostPart> syncCostPart(DefaultMutableTreeNode root, boolean isSync) throws Exception {

	CostPart rootPart = (CostPart) root.getUserObject();
	int formulaVersion = rootPart.getFormulaVersion();
	List<CostPart> spList = new ArrayList<CostPart>();

	String costAllTotal = "0";
	String costExceptTotal = "0";
	String partTotalCost = "0";
	String freeRawMaterialCostAllTotal = "0";
	String costDefectTotal = "0";
	String partType = rootPart.getPartType();

	for (int i = 0; i < root.getChildCount(); i++) {

	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
	    Map<String, String> resMap = syncCostPartChild(child, spList, isSync, formulaVersion);

	    CostPart part = (CostPart) child.getUserObject();
	    part.setFormulaVersion(formulaVersion);

	    partType = part.getPartType();
	    String costAllTotalTemp = "0";
	    String totalCost = "0";
	    String freeRawMaterialCostTotal = "0";
	    String freeRawMaterialCostTemp = "0";

	    String partTypeCode = "";

	    boolean isDefectPart = StringUtils.contains(part.getCalcOptionManage(), "CALCSTD021");

	    if (StringUtil.checkString(partType)) {
		CostPartType cpType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partType);
		partTypeCode = cpType.getCode();
	    }

	    if (child.getChildCount() > 0) {

		costAllTotalTemp = resMap.get("costAllTotal");
		String costAllExceptTemp = resMap.get("costExceptTotal");
		String costAllDefectTemp = resMap.get("costDefectTotal");
		part.setSubCostAllTotal(costAllTotalTemp);
		part.setSubCostExceptTotal(costAllExceptTemp);
		part.setSubCostDefectTotal(costAllDefectTemp);
		costExceptTotal = (String) CostCalculateUtil.manager.eval(costExceptTotal + "+" + costAllExceptTemp);

		freeRawMaterialCostTemp = resMap.get("freeRawMaterialCostAllTotal");
		part.setFreeRawMaterialCostTotal(freeRawMaterialCostTemp);

		if (isSync) {
		    part = CostCodeHelper.service.syncCost(part);
		}

		Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
		CostCalculateUtil.manager.calculateTotalCost(partMap);
		CostUtil.manager.convertMapToObject(partMap, part);

		totalCost = (String) partMap.get("totalCost");

		if (isDefectPart) {// 하위 부품 존재시 나의 부품합계 + 나의 총원가 를 누적한다. 목적은 최상위 부품의 subCostDefectTotal 불량률 제외 부품합계를 세팅해주기 위해서
		    costDefectTotal = (String) CostCalculateUtil.manager.eval(costDefectTotal + "+" + part.getSubCostAllTotal() + "+"
			    + totalCost);
		}

		costAllTotalTemp = (String) CostCalculateUtil.manager.eval(costAllTotalTemp + "+" + totalCost);

		freeRawMaterialCostTotal = (String) partMap.get("freeRawMaterialCostTotal");

		// freeRawMaterialCostTemp = (String) CostCalculateUtil.manager.eval(freeRawMaterialCostTemp + "+" +
		// freeRawMaterialCostTotal);

		if ("무상".equals((String) partMap.get("mftFactory2Name"))) {// 무상일 경우 calculateTotalCostf를 타면서 (총원가 계산하면서) 초기화됐을 것이므로 원복시켜준다
			                                                   // 그런데 무상은 하위부품이 달리지 않으므로 이런 경우는 발생하지 않을 것이다
		    part.setFreeRawMaterialCostTotal(freeRawMaterialCostTemp);
		}

		if ("지정품".equals(partTypeCode)) {
		    costExceptTotal = (String) CostCalculateUtil.manager.eval(costExceptTotal + "+" + totalCost);
		}

		partTotalCost = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + resMap.get("partTotalCost"));
		partTotalCost = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + totalCost);

	    } else {

		if (isSync) {
		    part = CostCodeHelper.service.syncCost(part);
		}
		Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
		CostCalculateUtil.manager.calculateTotalCost(partMap);
		CostUtil.manager.convertMapToObject(partMap, part);

		totalCost = (String) partMap.get("totalCost");

		part.setSubCostAllTotal("0");
		part.setFreeRawMaterialCostTotal("0");
		costAllTotalTemp = totalCost;

		freeRawMaterialCostTemp = (String) partMap.get("freeRawMaterialCostTotal");

		if ("지정품".equals(partTypeCode)) {
		    part.setSubCostExceptTotal(totalCost);
		    costExceptTotal = (String) CostCalculateUtil.manager.eval(costExceptTotal + "+" + totalCost);
		}
		if (isDefectPart) {// 하위 부품이 없을 때 불량율제외 옵션이 적용된 부품의 총원가를 누적한다. 목적은 최상위 부품의 subCostDefectTotal 불량률 제외 부품합계를 세팅해주기 위해서
		    costDefectTotal = (String) CostCalculateUtil.manager.eval(costDefectTotal + "+" + totalCost);
		}

		partTotalCost = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + totalCost);
	    }

	    spList.add(part);

	    costAllTotal = (String) CostCalculateUtil.manager.eval(costAllTotal + "+" + costAllTotalTemp);
	    freeRawMaterialCostAllTotal = (String) CostCalculateUtil.manager.eval(freeRawMaterialCostAllTotal + "+"
		    + freeRawMaterialCostTemp);
	}

	if (isSync) {
	    rootPart = CostCodeHelper.service.syncCost(rootPart);
	}

	rootPart.setSubCostAllTotal(costAllTotal);
	rootPart.setSubCostExceptTotal(costExceptTotal);
	rootPart.setSubCostDefectTotal(costDefectTotal);
	rootPart.setFreeRawMaterialCostTotal(freeRawMaterialCostAllTotal);
	rootPart = CostCalculateUtil.manager.calculateTotalCost(rootPart);

	String us = StringUtil.checkReplaceStr(rootPart.getUs(), "0");
	String totalCost = (String) CostCalculateUtil.manager.eval(rootPart.getTotalCostExpr() + "*" + us);
	String productCostTotal = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + totalCost);
	rootPart.setPartTotalCost(partTotalCost); // 부품 전체 합계
	rootPart.setProductCostTotal(productCostTotal); // 제품 총원가 합계
	String salesTargetCostExpr = rootPart.getSalesTargetCostExpr(); // 판매목표가
	String profitRateExpr = "1-(" + productCostTotal + "/" + salesTargetCostExpr + ")"; // 수익율
	profitRateExpr = (String) CostCalculateUtil.manager.eval(profitRateExpr);
	rootPart.setProfitRateExpr(profitRateExpr);

	spList.add(rootPart);

	CostCodeHelper.service.NinvestPartiotionUpdate(rootPart);// 0레벨 파트 신규투자비 분할 update

	Collections.sort(spList, new Comparator<CostPart>() {
	    @Override
	    public int compare(CostPart o1, CostPart o2) {
		if (o1 == null || o2 == null) {
		    return 0;
		}
		return o1.getSortLocation().compareTo(o2.getSortLocation());
	    }
	});

	for (CostPart savePart : spList) {
	    LOGGER.info("syncCostPart : " + savePart.getPartNo() + " ########## " + savePart.getSubCostAllTotal() + " ############# "
		    + savePart.getSubCostExceptTotal());

	    System.out.println("syncCostPart : " + savePart.getRealPartNo() + " ########## 재료비 : "
		    + CostCalculateUtil.manager.eval(savePart.getMaterialCostExpr() + "*" + savePart.getUs()));
	    System.out.println("syncCostPart : " + savePart.getRealPartNo() + " ########## 노무비 : "
		    + CostCalculateUtil.manager.eval(savePart.getLaborCostExpr() + "*" + savePart.getUs()));
	    System.out.println("syncCostPart : " + savePart.getRealPartNo() + " ########## 경비 : "
		    + CostCalculateUtil.manager.eval(savePart.getExpenseExpr() + "*" + savePart.getUs()));
	    System.out.println("syncCostPart : " + savePart.getRealPartNo() + " ########## 관리비 : "
		    + CostCalculateUtil.manager.eval(savePart.getManageCostExpr() + "*" + savePart.getUs()));
	    System.out.println("syncCostPart : " + savePart.getRealPartNo() + " ########## 감가비 : "
		    + CostCalculateUtil.manager.eval(savePart.getReduceCostExpr() + "*" + savePart.getUs()));
	    System.out.println("syncCostPart : " + savePart.getRealPartNo() + " ########## 총원가 : "
		    + CostCalculateUtil.manager.eval(savePart.getTotalCostExpr() + "*" + savePart.getUs()));
	}

	// System.out.println("ROOTPART ############### " + rootPart.getProductCostTotal() + " #### " + rootPart.getProfitRateExpr());

	return spList;
    }

    /**
     * <pre>
     * @description 기준정보 동기화 및 원가 산출 (CHILD)
     * @author dhkim
     * @date 2018. 3. 13. 오후 3:51:05
     * @method syncCostPartChild
     * @param parent
     * @param spList
     * @return Map<String,String>
     * @throws Exception
     * </pre>
     */
    private Map<String, String> syncCostPartChild(DefaultMutableTreeNode parent, List<CostPart> spList, boolean isSync, int formulaVersion)
	    throws Exception {

	Map<String, String> resMap = new HashMap<String, String>();

	String costAllTotal = "0";
	String costExceptTotal = "0";
	String partTotalCost = "0";
	String freeRawMaterialCostAllTotal = "0";// 무상 재료비 total
	String costDefectTotal = "0"; // 불량율 제외 합계

	for (int i = 0; i < parent.getChildCount(); i++) {

	    DefaultMutableTreeNode child = (DefaultMutableTreeNode) parent.getChildAt(i);

	    resMap = syncCostPartChild(child, spList, isSync, formulaVersion);

	    CostPart part = (CostPart) child.getUserObject();
	    part.setFormulaVersion(formulaVersion);

	    String partType = part.getPartType();
	    String costAllTotalTemp = "0";
	    String totalCost = "0";

	    String freeRawMaterialCostTotal = "0";
	    String freeRawMaterialCostTotalTemp = "0";

	    String partTypeCode = "";

	    boolean isDefectPart = StringUtils.contains(part.getCalcOptionManage(), "CALCSTD021");

	    if (StringUtil.checkString(partType)) {
		CostPartType cpType = (CostPartType) CommonUtil.getObject(CostPartType.class.getName() + ":" + partType);
		partTypeCode = cpType.getCode();
	    }

	    if (child.getChildCount() > 0) {

		costAllTotalTemp = resMap.get("costAllTotal");
		String costAllExceptTemp = resMap.get("costExceptTotal");
		String costAllDefectTemp = resMap.get("costDefectTotal");
		freeRawMaterialCostTotalTemp = resMap.get("freeRawMaterialCostAllTotal");// 하위 무상 재료비 합계
		part.setSubCostAllTotal(costAllTotalTemp);
		part.setSubCostExceptTotal(costAllExceptTemp);
		part.setFreeRawMaterialCostTotal(freeRawMaterialCostTotalTemp);
		part.setSubCostDefectTotal(costAllDefectTemp);

		costExceptTotal = (String) CostCalculateUtil.manager.eval(costExceptTotal + "+" + costAllExceptTemp);

		if (isSync) {
		    part = CostCodeHelper.service.syncCost(part);
		}

		Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
		CostCalculateUtil.manager.calculateTotalCost(partMap);
		CostUtil.manager.convertMapToObject(partMap, part);

		totalCost = (String) partMap.get("totalCost");

		costAllTotalTemp = (String) CostCalculateUtil.manager.eval(costAllTotalTemp + "+" + totalCost);

		if (isDefectPart) {// 하위 부품 존재시 나의 부품합계 + 나의 총원가 를 누적한다. 목적은 최상위 부품의 subCostDefectTotal 불량률 제외 부품합계를 세팅해주기 위해서
		    costDefectTotal = (String) CostCalculateUtil.manager.eval(costDefectTotal + "+" + part.getSubCostAllTotal() + "+"
			    + totalCost);
		}

		freeRawMaterialCostTotal = (String) partMap.get("freeRawMaterialCostTotal");
		// freeRawMaterialCostTotalTemp = (String) CostCalculateUtil.manager.eval(freeRawMaterialCostTotalTemp + "+" +
		// freeRawMaterialCostTotal);

		if ("무상".equals((String) partMap.get("mftFactory2Name"))) {// 무상일 경우 calculateTotalCostf를 타면서 (총원가 계산하면서) 초기화됐을 것이므로 원복시켜준다
			                                                   // 그런데 무상은 하위부품이 달리지 않으므로 이런 경우는 발생하지 않을 것이다
		    part.setFreeRawMaterialCostTotal(freeRawMaterialCostTotalTemp);
		}

		if ("지정품".equals(partTypeCode)) {
		    costExceptTotal = (String) CostCalculateUtil.manager.eval(costExceptTotal + "+" + totalCost);
		}

		partTotalCost = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + resMap.get("partTotalCost"));
		partTotalCost = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + totalCost);

	    } else {

		if (isSync) {
		    part = CostCodeHelper.service.syncCost(part);
		}

		Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
		CostCalculateUtil.manager.calculateTotalCost(partMap);
		CostUtil.manager.convertMapToObject(partMap, part);

		totalCost = (String) partMap.get("totalCost");

		part.setSubCostAllTotal("0");
		part.setFreeRawMaterialCostTotal("0");
		costAllTotalTemp = (String) totalCost;

		freeRawMaterialCostTotalTemp = (String) partMap.get("freeRawMaterialCostTotal");

		if ("지정품".equals(partTypeCode)) {
		    part.setSubCostExceptTotal(totalCost);
		    costExceptTotal = (String) CostCalculateUtil.manager.eval(costExceptTotal + "+" + totalCost);
		}

		if (isDefectPart) {
		    costDefectTotal = (String) CostCalculateUtil.manager.eval(costDefectTotal + "+" + totalCost);// 하위 부품이 없을 때 불량율제외 옵션이
			                                                                                         // 적용된 부품의 총원가를 누적한다. 목적은 모
			                                                                                         // 부품의 subCostDefectTotal
			                                                                                         // 불량률 제외 부품합계를 세팅해주기 위해서
		}

		partTotalCost = (String) CostCalculateUtil.manager.eval(partTotalCost + "+" + totalCost);
	    }

	    spList.add(part);

	    String expr = costAllTotal + "+" + costAllTotalTemp;
	    costAllTotal = (String) CostCalculateUtil.manager.eval(expr);

	    expr = freeRawMaterialCostAllTotal + "+" + freeRawMaterialCostTotalTemp;
	    freeRawMaterialCostAllTotal = (String) CostCalculateUtil.manager.eval(expr);
	}

	resMap.put("costAllTotal", costAllTotal);
	resMap.put("costExceptTotal", costExceptTotal);
	resMap.put("partTotalCost", partTotalCost);
	resMap.put("costDefectTotal", costDefectTotal);
	resMap.put("freeRawMaterialCostAllTotal", freeRawMaterialCostAllTotal);

	return resMap;
    }

    /**
     * <pre>
     * @description 보고서 조회 
     * @author dhkim
     * @date 2018. 3. 19. 오전 9:55:20
     * @method getCostReport
     * @param projectOid
     * @param version
     * @return CostReport
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CostReport getCostReport(String projectOid, String version) throws Exception {

	CostReport cr = null;

	Persistable pbo = CommonUtil.getObject(projectOid);
	String pjtNo = "";

	if (pbo instanceof E3PSProjectMaster) {
	    E3PSProjectMaster pjt = (E3PSProjectMaster) CommonUtil.getObject(projectOid);
	    pjtNo = pjt.getPjtNo();
	} else if (pbo instanceof E3PSProject) {
	    E3PSProject pjt = (E3PSProject) CommonUtil.getObject(projectOid);
	    pjtNo = pjt.getPjtNo();
	}

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostReport.class, true);

	qs.appendWhere(new SearchCondition(CostReport.class, CostReport.PJT_NO, SearchCondition.EQUAL, pjtNo), new int[] { idx });

	/*
	 * qs.appendWhere( new SearchCondition(CostReport.class, CostReport.PROJECT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
	 * SearchCondition.EQUAL, CommonUtil .getOIDLongValue(projectOid)), new int[] { idx });
	 */

	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostReport.class, CostReport.VERSION, SearchCondition.EQUAL, version), new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	if (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    cr = (CostReport) o[0];
	}

	return cr;
    }

    public String getLastestCostReportByPjtNo(String pjtNo) throws Exception {

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	String oid = "";

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT CLASSNAMEA2A2||':'||IDA2A2 AS OID FROM COSTREPORT WHERE PJTNO = '" + pjtNo
		    + "' AND VERSION = (SELECT MAX(VERSION) FROM COSTREPORT");
	    sql.append(" WHERE PJTNO='" + pjtNo + "')");

	    rs = stat.executeQuery(sql.toString());

	    if (rs.next()) {
		oid = rs.getString("OID");
	    }

	} catch (Exception e) {
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

	return oid;
    }

    public CostReport getPreviousCostReport(E3PSProject project) throws Exception {

	String oid = "";

	if (project instanceof ProductProject) {

	    // E3PSProject reviewPjt = (E3PSProject) CommonUtil.getObject(project.getDevRequest().getProjectOID());
	    String pjtNos[] = project.getReviewPjtNo().split(",");
	    E3PSProject reviewPjt = ProjectHelper.getProject(pjtNos[0]);
	    oid = getLastestCostReportByPjtNo(project.getPjtNo());

	    if (StringUtils.isEmpty(oid)) {
		oid = getLastestCostReportByPjtNo(reviewPjt.getPjtNo());
	    }

	} else if (project instanceof ReviewProject) {
	    oid = getLastestCostReportByPjtNo(project.getPjtNo());
	}

	CostReport report = (CostReport) CommonUtil.getObject(oid);

	return report;
    }

    /**
     * <pre>
     * @description 승인된 보고서 목록
     * @author dhkim
     * @date 2019. 4. 24. 오후 4:53:09
     * @method getCostReportList
     * @param projectOid
     * @param version
     * @return CostReport
     * @throws Exception
     * </pre>
     */
    public List<CostReport> getCostReportList(String pjtNo) throws Exception {

	List<CostReport> list = new ArrayList<CostReport>();

	CostReport cr = null;

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostReport.class, true);

	qs.appendWhere(new SearchCondition(CostReport.class, CostReport.PJT_NO, SearchCondition.EQUAL, pjtNo), new int[] { idx });
	if (qs.getConditionCount() > 0)
	    qs.appendAnd();
	qs.appendWhere(new SearchCondition(CostReport.class, CostReport.LIFE_CYCLE_STATE, SearchCondition.EQUAL, "APPROVED"),
	        new int[] { idx });

	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    cr = (CostReport) o[0];
	    list.add(cr);
	}

	return list;
    }

    public int getLastestReportVersion(String pjtNo, boolean isApproved) throws Exception {

	QuerySpec query = new QuerySpec();
	query.setAdvancedQueryEnabled(true);

	int idx = query.appendClassList(CostReport.class, false);
	ClassAttribute ver = new ClassAttribute(CostReport.class, "version");

	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, SQLFunction.newSQLFunction(SQLFunction.TO_NUMBER, ver));

	query.appendWhere(new SearchCondition(CostReport.class, CostReport.PJT_NO, SearchCondition.EQUAL, pjtNo), new int[] { idx });

	if (query.getConditionCount() > 0 && isApproved) {
	    query.appendAnd();
	    query.appendWhere(new SearchCondition(CostReport.class, CostReport.LIFE_CYCLE_STATE, SearchCondition.EQUAL, "APPROVED"),
		    new int[] { idx });
	}
	query.appendSelect(max, false);

	QueryResult qr = PersistenceServerHelper.manager.query(query);
	String maxVer = "-1";
	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    if (obj[0] != null) {
		maxVer = String.valueOf(obj[0]);
	    }
	}
	if (StringUtils.isEmpty(maxVer)) {
	    maxVer = "-1";
	}

	return Integer.valueOf(maxVer);

    }

    /**
     * <pre>
     * @description 제품 환율/물량/수지&비철원재료/범용감가 정보
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:40
     * @method getCostPartInfo
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    public Map<String, Object> getCostPartInfo(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	LOGGER.info("PART ID ###############" + reqMap.get("oid"));
	CostPart rootPart = (CostPart) CommonUtil.getObject((String) reqMap.get("oid"));
	List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);

	// #################환율정보#############################################
	List<Map<String, String>> exRateInfo = new ArrayList<Map<String, String>>();
	QueryResult qr = CostCodeHelper.service.getCostCurrencyQuery(rootPart);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    CostCurrencyInfo currencyInfo = (CostCurrencyInfo) o[0];

	    Map<String, String> map = new HashMap<String, String>();

	    map.put("id", CommonUtil.getOIDString(currencyInfo));
	    map.put("name", StringUtil.checkNull(currencyInfo.getName()));
	    map.put("value", StringUtil.checkNull(currencyInfo.getValue2()));

	    exRateInfo.add(map);
	}
	resMap.put("exRateInfo", exRateInfo);

	// #################물량정보#############################################
	Map<String, String> quantityInfo = new HashMap<String, String>();
	quantityInfo.put("quantityTotal", StringUtil.checkNull(rootPart.getQuantityTotal()));
	quantityInfo.put("quantityAvg", StringUtil.checkNull(rootPart.getQuantityAvg()));
	quantityInfo.put("quantityMax", StringUtil.checkNull(rootPart.getQuantityMax()));
	quantityInfo.put("sopYear", StringUtil.checkNull(rootPart.getSopYear()));

	if (StringUtil.checkString(rootPart.getPayPlace())) {
	    NumberCode code = (NumberCode) CommonUtil.getObject(rootPart.getPayPlace());
	    quantityInfo.put("payPlace", code.getName());
	}
	quantityInfo.put("deliverName", StringUtil.checkNull(rootPart.getDeliverName()));

	resMap.put("quantityInfo", quantityInfo);

	List<Map<String, Object>> partInfoList = new ArrayList<Map<String, Object>>();

	for (CostPart part : cpList) {

	    Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);
	    List<Map<String, Object>> profitInfo = new ArrayList<Map<String, Object>>();
	    List<Map<String, Object>> investInfo = new ArrayList<Map<String, Object>>();

	    partMap.put("oid", CommonUtil.getOIDString(part));

	    // ################### 수지원재료 #############################################
	    qr = PersistenceHelper.manager.navigate(part, CostMaterialLink.MATERIAL_ROLE, CostMaterialLink.class, true);

	    while (qr.hasMoreElements()) {
		CostMaterialInfo info = (CostMaterialInfo) qr.nextElement();
		Map<String, Object> infoMap = CostUtil.manager.converObjectToMap(info);
		infoMap.put("scrapCost", part.getSujiScrapCost());
		infoMap.put("scrapRecycle", part.getSujiScrapRecycle());
		profitInfo.add(infoMap);
	    }

	    // ################### 비철원재료 #############################################
	    NumberCode code = NumberCodeHelper.manager.getNumberCode("RAWMATERIAL", part.getRMatNMetalName());
	    if (code != null)
		partMap.put("nMetalName", code.getName());

	    code = NumberCodeHelper.manager.getNumberCode("PLATING", part.getPrePlatingSpec());
	    if (code != null)
		partMap.put("platingName", code.getName());

	    // #################### 범용감가 ##############################################
	    qr = PersistenceHelper.manager.navigate(part, CostReduceLink.REDUCE_ROLE, CostReduceLink.class, true);

	    while (qr.hasMoreElements()) {
		CostInvestInfo info = (CostInvestInfo) qr.nextElement();

		if ("REDU003".equals(info.getReduceCode())) {
		    Map<String, Object> infoMap = CostUtil.manager.converObjectToMap(info);

		    infoMap.put("oid", CommonUtil.getOIDString(info));
		    String investReduceCost = info.getInvestReduceCost();
		    String workUseHour = info.getWorkUseHour();
		    String workUseDay = info.getWorkUseDay();
		    String workUseYear = info.getWorkUseYear();

		    // String exp = "parseInt((((" + investReduceCost + "*1.05)/(" + workUseHour + "*" + workUseDay + "*" + workUseYear+
		    // "))+9)/10)*10";
		    // String exp = "parseInt((((" + investReduceCost + ")/(" + workUseHour + "*" + workUseDay + "*" + workUseYear+
		    // "))+9)/10)*10";
		    String machineReduceCost = CostCalculateUtil.manager.getMachineReduceCost(investReduceCost, workUseHour, workUseDay,
			    workUseYear);

		    infoMap.put("machineReduceCost", machineReduceCost);

		    investInfo.add(infoMap);
		}
	    }

	    partMap.put("profitInfo", profitInfo);
	    partMap.put("investInfo", investInfo);

	    partInfoList.add(partMap);

	}

	resMap.put("partInfoList", partInfoList);

	return resMap;
    }

    /**
     * <pre>
     * @description 감가비 정보 (금형/설비/기타)  
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:32
     * @method costReductionGridData
     * @param reqMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public List<Map<String, Object>> costReductionGridData(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	List<NumberCode> costReduceOption = NumberCodeHelper.manager.getNumberCodeList("COSTREDUCEOPTION");
	String investType = (String) reqMap.get("REDUCTIONTYPE");

	List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);

	for (CostPart part : cpList) {

	    String oid = (String) CommonUtil.getOIDString(part);
	    reqMap.remove("pOid");
	    reqMap.put("oid", oid);
	    QueryResult qr = getCostInvestInfoQuery(reqMap);

	    while (qr.hasMoreElements()) {

		Object[] o = (Object[]) qr.nextElement();
		CostInvestInfo ciInfo = (CostInvestInfo) o[0];

		Map<String, Object> data = converObjectToMap(ciInfo);

		data.put("cpOid", oid);
		data.put("id", CommonUtil.getOIDString(ciInfo));
		data.put("partName", part.getPartName());

		String costType = (String) data.get("costType");
		String reduceCode = (String) data.get("reduceCode");
		if ("N".equals(costType)) {
		    data.put("costTypeDisplay", "신규");
		} else if ("M".equals(costType)) {
		    data.put("costTypeDisplay", "양산");
		}
		data.put("PANEL", "Copy");

		if ("REDU004".equals(reduceCode)) { // 지급
		    data.put("reduceCodeCanEdit", "0");
		}

		String reduceCodeKeys = "";
		String reduceCodeNames = "";

		for (NumberCode code : costReduceOption) {

		    if ("REDU004".equals(code.getCode()) && !"REDU004".equals(reduceCode)) {
			continue;
		    }

		    if ("mold".equals(investType) && "REDU003".equals(code.getCode())) {
			continue;
		    }
		    if ("etc".equals(investType) && "REDU002".equals(code.getCode())) {
			continue;
		    }

		    reduceCodeKeys += "|" + code.getCode();
		    reduceCodeNames += "|" + code.getName();
		}

		data.put("reduceCodeEnum", reduceCodeNames);
		data.put("reduceCodeEnumKeys", reduceCodeKeys);

		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

		reqMap.put("pOid", data.get("id"));
		QueryResult cqr = getCostInvestInfoQuery(reqMap);

		while (cqr.hasMoreElements()) {

		    Object[] co = (Object[]) cqr.nextElement();
		    CostInvestInfo child = (CostInvestInfo) co[0];

		    reduceCode = child.getReduceCode();

		    if ("REDU004".equals(reduceCode)) { // 지급
			data.put("reduceCodeCanEdit", "0");
		    }

		    Map<String, Object> childData = converObjectToMap(child);
		    childData.put("PANEL", "Delete");
		    childData.put("id", CommonUtil.getOIDString(child));

		    childData.put("reduceCodeEnum", reduceCodeNames);
		    childData.put("reduceCodeEnumKeys", reduceCodeKeys);
		    childData.put("cpOid", oid);
		    childList.add(childData);
		}

		data.put("Items", childList);

		list.add(data);
	    }
	}
	return list;
    }

    /**
     * <pre>
     * @description 투자비 정보
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:32
     * @method costInvestGridData
     * @param reqMap
     * @return List<Map<String,Object>>
     * @throws Exception
     * </pre>
     */
    public List<Map<String, Object>> costInvestGridData(Map<String, Object> reqMap) throws Exception {

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);

	List<Map<String, Object>> moldList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> pressList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> equipList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> purchaseList = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> etcList = new ArrayList<Map<String, Object>>();

	for (CostPart part : cpList) {

	    String oid = (String) CommonUtil.getOIDString(part);

	    reqMap.remove("pOid");
	    reqMap.put("oid", oid);
	    reqMap.put("costType", "N");
	    QueryResult qr = getCostInvestInfoQuery(reqMap);

	    while (qr.hasMoreElements()) {

		Object[] o = (Object[]) qr.nextElement();
		CostInvestInfo ciInfo = (CostInvestInfo) o[0];

		Map<String, Object> data = converObjectToMap(ciInfo);

		data.put("id", CommonUtil.getOIDString(ciInfo));

		data.put("partName", part.getPartName());

		String typeOid = CostPartType.class.getName() + ":" + part.getPartType();

		CostPartType cpType = (CostPartType) CommonUtil.getObject(typeOid);

		String investType = ciInfo.getInvestType();
		String investCost = ciInfo.getInvestCost();
		String investUnit = ciInfo.getInvestUnit();
		// String reduceCode = ciInfo.getReduceCode();

		String exp = investCost + "*" + investUnit;
		String investPrice = (String) CostCalculateUtil.manager.eval(exp);
		data.put("investPrice", investPrice);
		data.put("investTypeDisplayClass", "boldCell");

		NumberCode code = null;

		switch (investType) {
		case "mold":

		    code = NumberCodeHelper.manager.getNumberCode("MOLDDIVISION", part.getMoldMftDivision());

		    if (code != null && code.getName().indexOf("(공용)") >= 0) {
			continue;
		    }

		    data.put("investUnitFormat", "0,###.## 벌");

		    if ("MOLD".equals(cpType.getCode())) {

			data.put("investTypeDisplay", "MOLD");
			data.put("investTypeDisplayColor", "#FFFFCC");

			if (moldList.isEmpty()) {
			    data.put("Spanned", 1);
			}

			moldList.add(data);

		    } else if ("PRESS".equals(cpType.getCode())) {

			data.put("investTypeDisplay", "PRESS");
			data.put("investTypeDisplayColor", "#EBF1DE");
			pressList.add(data);
		    } else if ("구매".equals(cpType.getCode())) {
			data.put("investTypeDisplay", "구매금형비");
			data.put("investTypeDisplayColor", "#B2CCFF");
			purchaseList.add(data);
		    }
		    break;

		case "equip":

		    code = NumberCodeHelper.manager.getNumberCode("FACDIVISION", part.getFacMftDivision());

		    if (code != null && code.getName().indexOf("(공용)") >= 0) {
			continue;
		    }

		    data.put("investUnitFormat", "0,###.## Line");
		    data.put("investTypeDisplay", "조립설비");
		    data.put("investTypeDisplayColor", "#E4DFEC");
		    equipList.add(data);
		    break;

		case "etc":
		    String itemName = StringUtil.checkNull((String) data.get("itemName"));

		    if (StringUtil.checkString(itemName)) {
			itemName = " / " + itemName;
		    }

		    data.put("partName", part.getPartName() + itemName);
		    data.put("investUnitFormat", "0,###.## Set");
		    data.put("investTypeDisplay", "기타 투자비");
		    data.put("investTypeDisplayColor", "#DAEEF3");
		    etcList.add(data);
		    break;

		default:
		    break;
		}
	    }
	}

	if (moldList.size() > 0) {
	    moldList.get(0).put("investTypeDisplayRowSpan", moldList.size() + 1);
	}

	if (pressList.size() > 0) {
	    pressList.get(0).put("investTypeDisplayRowSpan", pressList.size() + 1);
	}

	if (equipList.size() > 0) {
	    equipList.get(0).put("investTypeDisplayRowSpan", equipList.size() + 1);
	}

	if (purchaseList.size() > 0) {
	    purchaseList.get(0).put("investTypeDisplayRowSpan", purchaseList.size() + 1);
	}

	if (etcList.size() > 0) {
	    etcList.get(0).put("investTypeDisplayRowSpan", etcList.size() + 1);
	}

	String investUnitTotal = "0";
	String investPriceTotal = "0";
	String ipTotalTotal = "0";

	for (Map<String, Object> data : moldList) {
	    String investUnit = (String) data.get("investUnit");
	    String investPrice = (String) data.get("investPrice");

	    String exp = investUnit + "+" + investUnitTotal;
	    ;
	    investUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

	    exp = investPrice + "+" + investPriceTotal;
	    investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);
	}
	Map<String, Object> totalData = new HashMap<String, Object>();
	totalData.put("investTypeDisplay", "MOLD");
	totalData.put("partName", "소계");
	totalData.put("investUnit", investUnitTotal);
	totalData.put("investPrice", investPriceTotal);
	totalData.put("Color", "#FFFFCC");
	totalData.put("investCostFormat", "");
	totalData.put("investUnitFormat", "0,###.## 벌");
	totalData.put("investCostType", "Text");
	totalData.put("Class", "boldCell");
	moldList.add(totalData);

	String expt = ipTotalTotal + "+" + investPriceTotal;
	ipTotalTotal = (String) CostCalculateUtil.manager.eval(expt);

	investUnitTotal = "0";
	investPriceTotal = "0";

	for (Map<String, Object> data : pressList) {
	    String investUnit = (String) data.get("investUnit");
	    String investPrice = (String) data.get("investPrice");

	    String exp = investUnit + "+" + investUnitTotal;
	    ;
	    investUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

	    exp = investPrice + "+" + investPriceTotal;
	    investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);
	}
	totalData = new HashMap<String, Object>();
	totalData.put("investTypeDisplay", "PRESS");
	totalData.put("partName", "소계");
	totalData.put("investUnit", investUnitTotal);
	totalData.put("investPrice", investPriceTotal);
	totalData.put("investCostFormat", "");
	totalData.put("investCostType", "Text");
	totalData.put("investUnitFormat", "0,###.## 벌");
	totalData.put("Color", "#EBF1DE");
	totalData.put("Class", "boldCell");
	pressList.add(totalData);

	expt = ipTotalTotal + "+" + investPriceTotal;
	ipTotalTotal = (String) CostCalculateUtil.manager.eval(expt);

	investUnitTotal = "0";
	investPriceTotal = "0";

	for (Map<String, Object> data : equipList) {
	    String investUnit = (String) data.get("investUnit");
	    String investPrice = (String) data.get("investPrice");

	    String exp = investUnit + "+" + investUnitTotal;
	    ;
	    investUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

	    exp = investPrice + "+" + investPriceTotal;
	    investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);
	}

	totalData = new HashMap<String, Object>();
	totalData.put("investTypeDisplay", "조립설비");
	totalData.put("partName", "소계");
	totalData.put("investUnit", investUnitTotal);
	totalData.put("investPrice", investPriceTotal);
	totalData.put("investCostFormat", "");
	totalData.put("investCostType", "Text");
	totalData.put("investUnitFormat", "0,###.## Line");
	totalData.put("Color", "#E4DFEC");
	totalData.put("Class", "boldCell");
	equipList.add(totalData);

	expt = ipTotalTotal + "+" + investPriceTotal;
	ipTotalTotal = (String) CostCalculateUtil.manager.eval(expt);

	investUnitTotal = "0";
	investPriceTotal = "0";

	for (Map<String, Object> data : purchaseList) {
	    String investUnit = (String) data.get("investUnit");
	    String investPrice = (String) data.get("investPrice");

	    String exp = investUnit + "+" + investUnitTotal;
	    ;
	    investUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

	    exp = investPrice + "+" + investPriceTotal;
	    investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);
	}
	totalData = new HashMap<String, Object>();
	totalData.put("investTypeDisplay", "구매금형비");
	totalData.put("partName", "소계");
	totalData.put("investUnit", investUnitTotal);
	totalData.put("investPrice", investPriceTotal);
	totalData.put("Color", "#B2CCFF");
	totalData.put("investCostFormat", "");
	totalData.put("investUnitFormat", "0,###.## 벌");
	totalData.put("investCostType", "Text");
	totalData.put("Class", "boldCell");
	purchaseList.add(totalData);

	expt = ipTotalTotal + "+" + investPriceTotal;
	ipTotalTotal = (String) CostCalculateUtil.manager.eval(expt);

	investUnitTotal = "0";
	investPriceTotal = "0";

	for (Map<String, Object> data : etcList) {
	    String investUnit = (String) data.get("investUnit");
	    String investPrice = (String) data.get("investPrice");

	    String exp = investUnit + "+" + investUnitTotal;
	    investUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

	    exp = investPrice + "+" + investPriceTotal;
	    investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);
	}
	totalData = new HashMap<String, Object>();
	totalData.put("investTypeDisplay", "기타 투자비");
	totalData.put("partName", "소계");
	totalData.put("investUnit", investUnitTotal);
	totalData.put("investPrice", investPriceTotal);
	totalData.put("investCostFormat", "");
	totalData.put("investCostType", "Text");
	totalData.put("investUnitFormat", "0,###.## Set");
	totalData.put("Color", "#DAEEF3");
	totalData.put("Class", "boldCell");
	etcList.add(totalData);

	expt = ipTotalTotal + "+" + investPriceTotal;
	ipTotalTotal = (String) CostCalculateUtil.manager.eval(expt);

	totalData = new HashMap<String, Object>();
	totalData.put("investTypeDisplay", "투자비 합계");
	totalData.put("investTypeDisplaySpan", 2);
	totalData.put("Spanned", 1);
	totalData.put("investCostFormat", "");
	totalData.put("investUnitFormat", "");
	totalData.put("investCostType", "Text");
	totalData.put("investUnitType", "Text");
	totalData.put("investPrice", ipTotalTotal);
	totalData.put("Color", "#FFFF00");
	totalData.put("Class", "boldCell");
	etcList.add(totalData);

	list.addAll(moldList);
	list.addAll(pressList);
	list.addAll(equipList);
	list.addAll(purchaseList);
	list.addAll(etcList);

	return list;
    }

    /**
     * <pre>
     * @description 보고서 정보
     * @author dhkim
     * @date 2018. 3. 19. 오후 2:33:22
     * @method getCostInvestTotalData
     * @param reqMap
     * @return Map<String,Object>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getReportData(Map<String, Object> reqMap) throws Exception {

	Map<String, Object> data = new HashMap<String, Object>();
	List<CostPart> lpList = new ArrayList<CostPart>();
	Map<ProductMaster, List<CostPart>> productMap = new HashMap<ProductMaster, List<CostPart>>();
	List<Map<String, Object>> partMapList = new ArrayList<Map<String, Object>>();

	String caseOrderStr = StringUtil.checkReplaceStr((String) reqMap.get("caseOrder"), "1");
	int caseOrder = Integer.parseInt(caseOrderStr);
	reqMap.remove("caseOrder");
	reqMap.put("noSortOption", "YES");

	reqMap.put("caseOrder", "1");
	List<CostPart> cpList = CostTreeUtil.manager.getCostPartList(reqMap);
	reqMap.put("caseOrder", "2");
	cpList.addAll(CostTreeUtil.manager.getCostPartList(reqMap));
	reqMap.put("caseOrder", "3");
	cpList.addAll(CostTreeUtil.manager.getCostPartList(reqMap));

	KETMessageService messageService = KETMessageService.getMessageService();

	Map<String, List<CostPart>> childMap = new HashMap<String, List<CostPart>>();

	// 개발 배경 및 투자비 현황
	String productNInvestTotal = "0";

	for (CostPart part : cpList) {

	    if (part.getCaseOrder() == null) {
		continue;
	    }

	    if (part.getCaseOrder() == 2) {
		data.put("case2Exist", true);
	    }
	    if (part.getCaseOrder() == 3) {
		data.put("case3Exist", true);
	    }

	    if (caseOrder == part.getCaseOrder()) {
		Map<String, Object> partMap = CostUtil.manager.converObjectToMap(part);

		String oid = CommonUtil.getOIDString(part);
		String mftFactory1 = (String) partMap.get("mftFactory1");
		String mftFactory2 = (String) partMap.get("mftFactory2");
		partMap.put("oid", oid);
		partMap.put("pOid", "0");

		if (StringUtil.checkString(mftFactory2)) {
		    NumberCode code1 = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + mftFactory1);
		    NumberCode code2 = (NumberCode) CommonUtil.getObject(NumberCode.class.getName() + ":" + mftFactory2);
		    String mftFactoryDisplay = code1.getName() + " / " + code2.getName();
		    partMap.put("mftFactory2Display", mftFactoryDisplay);
		}

		if (part.getParent() != null) {
		    String pOid = CommonUtil.getOIDString(part.getParent());
		    partMap.put("pOid", pOid);
		}

		partMapList.add(partMap);

	    }

	    Map<String, String> caseTotal = (Map<String, String>) data.get(String.valueOf("CASE" + part.getCaseOrder()));

	    if (caseTotal == null) {
		caseTotal = new HashMap<String, String>();
		caseTotal.put("moldinvestPriceTotal", "0");
		caseTotal.put("pressinvestPriceTotal", "0");
		caseTotal.put("equipinvestPriceTotal", "0");
		caseTotal.put("purchaseinvestPriceTotal", "0");
		caseTotal.put("etcinvestPriceTotal", "0");
		caseTotal.put("investPriceTotal", "0");
	    }

	    if (part.getParent() == null) {

		ProductMaster master = part.getMaster();

		List<CostPart> caseList = productMap.get(master);

		if (caseList == null) {
		    caseList = new ArrayList<CostPart>();
		}
		caseList.add(part);
		productMap.put(master, caseList);

		if (caseOrder == part.getCaseOrder()) {
		    lpList.add(part);
		}
	    } else {
		String pOid = CommonUtil.getOIDString(part.getParent());
		List<CostPart> childList = childMap.get(pOid);

		if (childList == null) {
		    childList = new ArrayList<CostPart>();
		}

		childList.add(part);
		childMap.put(pOid, childList);
	    }

	    String oid = (String) CommonUtil.getOIDString(part);
	    reqMap.remove("pOid");
	    reqMap.put("oid", oid);
	    reqMap.put("costType", "N");
	    QueryResult qr = getCostInvestInfoQuery(reqMap);

	    while (qr.hasMoreElements()) {

		Object[] o = (Object[]) qr.nextElement();
		CostInvestInfo ciInfo = (CostInvestInfo) o[0];

		String typeOid = CostPartType.class.getName() + ":" + part.getPartType();

		CostPartType cpType = (CostPartType) CommonUtil.getObject(typeOid);

		String investType = ciInfo.getInvestType();
		String investCost = ciInfo.getInvestCost();
		String investUnit = ciInfo.getInvestUnit();
		String exp = investCost + "*" + investUnit;
		String investPrice = (String) CostCalculateUtil.manager.eval(exp);

		if ("mold".equals(investType)) {
		    NumberCode code = NumberCodeHelper.manager.getNumberCode("MOLDDIVISION", part.getMoldMftDivision());

		    if (code != null && code.getName().indexOf("(공용)") >= 0) {
			continue;
		    }
		}

		if ("equip".equals(investType)) {
		    NumberCode code = NumberCodeHelper.manager.getNumberCode("FACDIVISION", part.getFacMftDivision());

		    if (code != null && code.getName().indexOf("(공용)") >= 0) {
			continue;
		    }
		}

		if ("mold".equals(investType) && "PRESS".equals(cpType.getCode())) {
		    investType = "press";
		}

		if ("mold".equals(investType) && "MOLD".equals(cpType.getCode())) {
		    investType = "mold";
		}

		if ("mold".equals(investType) && "구매".equals(cpType.getCode())) {
		    investType = "purchase";
		}

		if (part.isLastest()) {

		    String reduceCode = ciInfo.getReduceCode();
		    String reduceOption = "";
		    if (ciInfo != null && StringUtils.isNotEmpty(reduceCode)) {
			NumberCode code = CodeHelper.manager.getNumberCode("COSTREDUCEOPTION", reduceCode);
			if (code != null) {
			    reduceOption = code.getName();
			}
		    }

		    String reduceOptions = (String) data.get(investType + "reduceOptions");

		    if (!StringUtil.checkString(reduceOptions)) {
			reduceOptions = reduceOption;
		    } else if (reduceOptions.indexOf(reduceOption) < 0) {
			reduceOptions += "," + reduceOption;
		    }

		    data.put(investType + "reduceOptions", reduceOptions);

		    String typeInvestUnitTotal = (String) data.get(investType + "investUnitTotal");
		    String investUnitTotal = (String) data.get("investUnitTotal");

		    exp = investUnit + "+" + typeInvestUnitTotal;
		    typeInvestUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

		    data.put(investType + "investUnitTotal", typeInvestUnitTotal);

		    exp = investUnit + "+" + investUnitTotal;
		    investUnitTotal = (String) CostCalculateUtil.manager.eval(exp);

		    data.put("investUnitTotal", investUnitTotal);

		}

		String typeInvestPriceTotal = caseTotal.get(investType + "investPriceTotal");
		String investPriceTotal = caseTotal.get("investPriceTotal");

		exp = investPrice + "+" + typeInvestPriceTotal;
		typeInvestPriceTotal = (String) CostCalculateUtil.manager.eval(exp);

		caseTotal.put(investType + "investPriceTotal", typeInvestPriceTotal);

		exp = investPrice + "+" + investPriceTotal;
		investPriceTotal = (String) CostCalculateUtil.manager.eval(exp);

		caseTotal.put("investPriceTotal", investPriceTotal);

	    }

	    data.put(String.valueOf("CASE" + part.getCaseOrder()), caseTotal);
	}

	Map<String, String> case1Total = (Map<String, String>) data.get("CASE1");
	productNInvestTotal = case1Total.get("investPriceTotal");

	// Collections.sort(partMapList, new Comparator<Object>() {
	//
	// @Override
	// public int compare(Object obj1, Object obj2) {
	//
	// @SuppressWarnings("unchecked")
	// HashMap<String, Object> map1 = (HashMap<String, Object>) obj1;
	// @SuppressWarnings("unchecked")
	// HashMap<String, Object> map2 = (HashMap<String, Object>) obj2;
	//
	// String sort1 = (String)map1.get("sortLocation");
	// String sort2 = (String)map2.get("sortLocation");
	//
	// int x = Integer.parseInt(sort1);
	// int y = Integer.parseInt(sort2);
	//
	// if (x > y)
	// return 1;
	// else if (x < y)
	// return -1;
	// else
	// return 0;
	// }
	// });

	Collections.sort(lpList, new Comparator<CostPart>() {
	    @Override
	    public int compare(CostPart o1, CostPart o2) {
		if (o1 == null || o2 == null) {
		    return 0;
		}
		return o1.getSortLocation().compareTo(o2.getSortLocation());
	    }
	});

	data.put("cpList", partMapList);

	List<Map<String, Object>> caDataList = new ArrayList<Map<String, Object>>();

	Map<Integer, String> totalSalesMap = new HashMap<Integer, String>();
	Map<Integer, String> profitCostMap = new HashMap<Integer, String>();
	Map<Integer, String> profitRateMap = new HashMap<Integer, String>();
	Map<Integer, String> cashInFlowMap = new HashMap<Integer, String>();
	int yearSize = 0;

	// ################################## 예상 손익 및 원재료 가격기준 ########################################
	for (CostPart lp : lpList) {

	    List<CostAnalysis> caList = CostUtil.manager.getCostAnalysis(lp);

	    Map<String, Object> caData = new HashMap<String, Object>();

	    List<String> salesQtyList = new ArrayList<String>();

	    if (yearSize < caList.size())
		yearSize = caList.size();

	    String totalQty = "0";

	    String cr = "0";
	    String applyYear = "0";

	    for (CostAnalysis ca : caList) {
		int year = ca.getYear();

		String qty = ca.getQuantity();
		String totalSales = ca.getTotalSales();
		String profitCost = ca.getProfitCost();
		String cashInFlow = ca.getCashInFlow();

		cr = ca.getCr();
		applyYear = ca.getApplyYear();

		String exp = totalQty + "+" + qty;
		totalQty = (String) CostCalculateUtil.manager.eval(exp);
		salesQtyList.add(qty);

		exp = totalSales + "+" + totalSalesMap.get(year);
		totalSales = (String) CostCalculateUtil.manager.eval(exp);
		totalSalesMap.put(year, totalSales);

		exp = profitCost + "+" + profitCostMap.get(year);
		profitCost = (String) CostCalculateUtil.manager.eval(exp);
		profitCostMap.put(year, profitCost);

		exp = cashInFlow + "+" + cashInFlowMap.get(year);
		cashInFlow = (String) CostCalculateUtil.manager.eval(exp);
		cashInFlowMap.put(year, cashInFlow);
	    }

	    caData.put("cr", StringUtil.checkReplaceStr(cr, "0"));
	    caData.put("applyYear", Integer.parseInt(StringUtil.checkReplaceStr(applyYear, "0")));
	    caData.put("salesQtyList", salesQtyList);
	    caData.put("totalQty", StringUtil.checkReplaceStr(totalQty, "0"));

	    List<CostPart> childList = childMap.get(CommonUtil.getOIDString(lp));

	    if (childList != null) {
		for (CostPart child : childList) {

		    String metalLmeCost = StringUtil.checkNullZero(child.getMetalLmeStd());

		    if (StringUtil.checkString(metalLmeCost) && !"0".equals(metalLmeCost)) {
			caData.put("metalLmeCost", metalLmeCost);
			break;
		    }
		}
	    }

	    caDataList.add(caData);
	}

	String profitCostTotal = "0";
	String totalSaleTotal = "0";
	String correctPeriod = null;
	boolean isCorrectCalc = false;

	for (int i = 1; i <= yearSize; i++) {

	    String totalSales = totalSalesMap.get(i);
	    String profitCost = profitCostMap.get(i);
	    String cashInFlow = cashInFlowMap.get(i);

	    String exp = "+" + profitCost + "/" + totalSales;
	    String profitRate = (String) CostCalculateUtil.manager.eval(exp);
	    profitRateMap.put(i, profitRate);

	    exp = profitCost + "+" + profitCostTotal;
	    profitCostTotal = (String) CostCalculateUtil.manager.eval(exp);

	    exp = totalSales + "+" + totalSaleTotal;
	    totalSaleTotal = (String) CostCalculateUtil.manager.eval(exp);

	    if (!isCorrectCalc) {

		exp = productNInvestTotal + "-" + cashInFlow;
		String balance = (String) CostCalculateUtil.manager.eval(exp);

		exp = balance + "<0";
		boolean isCorrect = (boolean) CostCalculateUtil.engine.eval(exp);

		if (isCorrect) {
		    exp = "(" + i + "-1)+" + productNInvestTotal + "/" + cashInFlow;
		    correctPeriod = (String) CostCalculateUtil.manager.eval(exp);
		    isCorrectCalc = true;
		} else {
		    productNInvestTotal = balance;
		}
	    }
	}

	if (!isCorrectCalc) {
	    data.put("correctPeriod", "회수불가");
	} else {
	    data.put("correctPeriod", correctPeriod);
	}

	String exp = "+" + profitCostTotal + "/" + totalSaleTotal;
	String profitRateTotal = (String) CostCalculateUtil.manager.eval(exp);

	data.put("profitRateTotal", profitRateTotal);
	data.put("profitCostTotal", profitCostTotal);
	data.put("totalSaleTotal", totalSaleTotal);
	data.put("caProfitRate", profitRateMap);
	data.put("caTotalSales", totalSalesMap);
	data.put("caProfitCost", profitCostMap);

	data.put("caYearSize", yearSize);
	data.put("caDataList", caDataList);

	// ################################## 예상 손익 및 원재료 가격기준 END ########################################

	// ################################## 원가 계산 결과 ########################################
	List<Map<String, Object>> calcData = new ArrayList<Map<String, Object>>();
	Set<ProductMaster> st = productMap.keySet();
	Iterator<ProductMaster> it = st.iterator();
	// boolean isCase2 = false;
	// boolean isCase3 = false;

	int calcResultRow = 0;
	while (it.hasNext()) {

	    ProductMaster master = it.next();

	    List<CostPart> caseList = productMap.get(master);

	    Map<String, Object> caseMap = new HashMap<String, Object>();

	    // 지정품 여부
	    boolean isAppoint = false;

	    for (CostPart part : caseList) {
		LOGGER.info(part.getPartNo() + "########### " + part.getCaseOrder());

		CostAnalysis ca = CostUtil.manager.getCostAnalysis(part, "1");
		int cOrder = part.getCaseOrder();

		// 최종안
		if (1 == cOrder) {
		    String reportUS = StringUtil.checkReplaceStr(part.getReportUS(), "0");
		    caseMap.put("partOid", CommonUtil.getOIDString(part));
		    caseMap.put("partName", part.getPartName());
		    caseMap.put("reportUS", reportUS);
		}

		String salesTargetCostExpr = StringUtil.checkReplaceStr(part.getSalesTargetCostExpr(), "0"); // 판매목표가
		String productCostTotal = StringUtil.checkReplaceStr(part.getProductCostTotal(), "0"); // 총원가 합계
		String subCostExceptTotal = StringUtil.checkReplaceStr(part.getSubCostExceptTotal(), "0"); // 총원가(지정품합계)

		String caseNote = part.getCaseNote(); // 비고
		String appointSales = "0"; // 지정품판가

		if (ca != null) {
		    appointSales = ca.getAppointSales();
		}

		exp = productCostTotal + "-" + subCostExceptTotal;
		String ketCostAllTotal = (String) CostCalculateUtil.manager.eval(exp); // 총원가 (KET품)
		exp = salesTargetCostExpr + "-" + appointSales;
		String ketSalesTargetCost = (String) CostCalculateUtil.manager.eval(exp); // 판매목표가 (KET품)

		if (!isAppoint) {
		    BigDecimal check = new BigDecimal(subCostExceptTotal);
		    isAppoint = check.compareTo(BigDecimal.ZERO) > 0;
		}

		// ################################### 지정품 #########################################
		String appProfitRate = "1-(" + subCostExceptTotal + "/" + appointSales + ")";
		appProfitRate = (String) CostCalculateUtil.manager.eval(appProfitRate); // 수익율 (지정품)

		caseMap.put("appProfitRate" + cOrder, appProfitRate);
		caseMap.put("subCostExceptTotal" + cOrder, subCostExceptTotal);
		caseMap.put("appointSales" + cOrder, appointSales);

		// ################################### KET 품 #########################################
		String ketProfitRate = "1-(" + ketCostAllTotal + "/" + ketSalesTargetCost + ")";
		ketProfitRate = (String) CostCalculateUtil.manager.eval(ketProfitRate); // 수익율 (KET품)

		caseMap.put("ketProfitRate" + cOrder, ketProfitRate);
		caseMap.put("ketCostAllTotal" + cOrder, ketCostAllTotal);
		caseMap.put("ketSalesTargetCost" + cOrder, ketSalesTargetCost);

		// ################################### 전체합계 #########################################
		String profitRateExpr = "1-(" + productCostTotal + "/" + salesTargetCostExpr + ")";
		profitRateExpr = (String) CostCalculateUtil.manager.eval(profitRateExpr);

		caseMap.put("salesTargetCostExpr" + cOrder, salesTargetCostExpr);
		caseMap.put("productCostTotal" + cOrder, productCostTotal);
		caseMap.put("profitRateExpr" + cOrder, profitRateExpr);

		caseMap.put("caseNote" + cOrder, caseNote);

		caseMap.put("sortLocation", part.getSortLocation());
	    }

	    calcResultRow++;

	    if (isAppoint) {
		calcResultRow++;
		calcResultRow++;
	    }

	    caseMap.put("isAppoint", isAppoint);

	    calcData.add(caseMap);
	}

	Collections.sort(calcData, new Comparator<Object>() {

	    @Override
	    public int compare(Object obj1, Object obj2) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map1 = (HashMap<String, Object>) obj1;
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map2 = (HashMap<String, Object>) obj2;
		int x = (int) (map1.get("sortLocation"));
		int y = (int) (map2.get("sortLocation"));

		if (x > y)
		    return 1;
		else if (x < y)
		    return -1;
		else
		    return 0;
	    }
	});

	data.put("calcResultRow", calcResultRow);
	data.put("calcData", calcData);

	String taskOid = (String) reqMap.get("taskOid");
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	E3PSProject project = (E3PSProject) task.getProject();
	E3PSProjectData projectData = new E3PSProjectData(project);

	// ############################## 담당자 ########################################################
	String salesName = projectData.salesName; // 영업
	// String pmName = projectData.pjtPmName; // PM
	String costName = projectData.costMemberName; // 원가담당

	// PeopleData pm = new PeopleData(projectData.pjtPm);
	PeopleData sales = new PeopleData(projectData.sales);
	PeopleData cost = new PeopleData(projectData.costMember);
	// String pmDeptName = pm.departmentName;
	String salesDeptName = sales.departmentName;
	String costDeptName = cost.departmentName;

	String devDeptName = "";
	String devUserName = "";

	QueryResult qs = null;

	if (project instanceof ReviewProject) {
	    qs = ProjectUserHelper.manager.getProjectRoleMember(project, "Team_REVIEW01");
	}

	if (project instanceof ProductProject) {
	    qs = ProjectUserHelper.manager.getProjectRoleMember(project, "Team_PRODUCT01");
	}

	if (qs != null && qs.size() > 0) {
	    ProjectMemberLink memberLink = (ProjectMemberLink) qs.nextElement();
	    PeopleData roleUser = new PeopleData(memberLink.getMember());
	    devUserName = roleUser.name;
	    devDeptName = roleUser.departmentName;
	} else {
	    devDeptName = "지정안됨";
	}

	data.put("salesName", salesName);
	data.put("devUserName", devUserName);
	data.put("costName", costName);
	data.put("devDeptName", devDeptName);
	data.put("salesDeptName", salesDeptName);
	data.put("costDeptName", costDeptName);
	// ############################## 담당자 END ########################################################

	// ############################## 고객사명 ########################################################
	String customer = "";
	if (projectData.customereventVec != null && projectData.customereventVec.size() > 0) {
	    for (int i = 0; i < projectData.customereventVec.size(); i++) {
		NumberCode nc = (NumberCode) projectData.customereventVec.get(i);
		String masterName = NumberCodeUtil.getNumberCodeValue("CUSTOMEREVENT", nc.getCode(), messageService.getLocale().toString());
		if (i != 0) {
		    customer += ",";
		}
		customer += masterName;
	    }
	}
	data.put("customer", customer);
	// ############################## 고객사명 END ######################################################

	// ################################ 제품명, 적용차종, 적용부위, SOP ########################################################
	String repModel = "";
	String applyPart = "";
	String sop = "";

	KETDevelopmentRequest DR = project.getDevRequest();

	if (DR != null) {
	    applyPart = DR.getAttribute3();
	}

	// 검토 프로젝트
	if (project instanceof ReviewProject) {

	    if (DR != null) {
		DR = this.getDvRequst(DR);
		applyPart = DR.getAttribute3();
		repModel = DR.getAttribute2();
		sop = DR.getProductSaleFirstYear();
	    }

	    // 개발 프로젝트
	} else {
	    List<HashMap<String, String>> list = ProgramHelper.service.findContactEventByproject(projectData.e3psPjtOID);// 프로그램의 접점고객 일정 찾기

	    for (HashMap<String, String> hash : list) {
		if (StringUtils.isNotEmpty(hash.get("sop_contact_date"))) {
		    sop = hash.get("sop_contact_date");
		}
	    }
	    repModel = projectData.representModel;
	}

	Map<String, String> wfmInfo = this.getWfmApproveInfo(reqMap);// 결재 comment 정보

	data.put("comment1", wfmInfo.get("comment1"));
	data.put("comment2", wfmInfo.get("comment2"));
	data.put("pjtName", projectData.pjtName);
	data.put("repModel", repModel);
	data.put("applyPart", applyPart);
	data.put("sop", sop);
	// ################################ 제품명, 적용차종, 적용부위, SOP END #######################################################

	return data;
    }

    /**
     * <pre>
     * @description 투자비 정보 조회
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:25ZZZ
     * @method getCostInvestInfoQuery
     * @param reqMap
     * @return QueryResult
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public QueryResult getCostInvestInfoQuery(Map<String, Object> reqMap) throws Exception {

	String oid = (String) reqMap.get("oid");
	String REDUCTIONTYPE = (String) reqMap.get("REDUCTIONTYPE");
	String costType = (String) reqMap.get("costType");
	String pOid = (String) reqMap.get("pOid");

	QuerySpec qs = new QuerySpec();
	int idx = qs.addClassList(CostInvestInfo.class, true);

	if (StringUtil.checkString(REDUCTIONTYPE)) {
	    qs.appendWhere(new SearchCondition(CostInvestInfo.class, CostInvestInfo.INVEST_TYPE, SearchCondition.EQUAL, REDUCTIONTYPE),
		    new int[] { idx });
	}

	if (StringUtil.checkString(costType)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostInvestInfo.class, CostInvestInfo.COST_TYPE, SearchCondition.EQUAL, costType),
		    new int[] { idx });
	}

	if (StringUtil.checkString(pOid)) {
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostInvestInfo.class, CostInvestInfo.PARENT_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(pOid)), new int[] { idx });
	} else {

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostInvestInfo.class, CostInvestInfo.PARENT_REFERENCE + "."
		    + WTAttributeNameIfc.REF_CLASSNAME, SearchCondition.IS_NULL, true), new int[] { idx });

	    int idx2 = qs.appendClassList(CostReduceLink.class, false);
	    qs.setAdvancedQueryEnabled(true);

	    ClassAttribute ca0 = new ClassAttribute(CostInvestInfo.class, WTAttributeNameIfc.ID_NAME);
	    ClassAttribute ca1 = new ClassAttribute(CostReduceLink.class, CostReduceLink.ROLE_AOBJECT_REF + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID);
	    SearchCondition sc0 = new SearchCondition(ca0, SearchCondition.EQUAL, ca1);
	    sc0.setFromIndicies(new int[] { idx, idx2 }, 0);
	    sc0.setOuterJoin(0);

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(sc0, new int[] { idx, idx2 });

	    if (StringUtil.checkString(oid)) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		qs.appendWhere(new SearchCondition(CostReduceLink.class, CostReduceLink.ROLE_BOBJECT_REF + "."
		        + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(oid)), new int[] { idx2 });
	    }
	}

	SearchUtil.setOrderBy(qs, CostInvestInfo.class, idx, CostInvestInfo.ITEM_NAME, false);
	SearchUtil.setOrderBy(qs, CostInvestInfo.class, idx, CostInvestInfo.COST_TYPE, true);

	QueryResult qr = PersistenceHelper.manager.find(qs);

	return qr;
    }

    /**
     * <pre>
     * @description 수식 부품구분, 생산처 뷴류 처리
     * @author dhkim
     * @date 2018. 1. 26. 오후 1:37:20
     * @method getPartTypeMftFatcory
     * @param formula
     * @return List<Map<String,Object>>
     * </pre>
     */
    public List<Map<String, Object>> getPartTypeMftFatcory(CostFormula formula) {

	String partType = StringUtil.checkNull(formula.getPartType());
	String mftFactory1 = StringUtil.checkNull(formula.getMftFactory1());
	String mftFactory2 = "";
	if (formula.getMftFactory2() != null) {
	    mftFactory2 = formula.getMftFactory2().toString();
	}
	List<Map<String, Object>> partTypeList = new ArrayList<Map<String, Object>>();

	if (StringUtil.checkString(partType)) {

	    String[] partTypeOids = partType.split("\\|", -1);
	    String[] mftFactory1Oids = mftFactory1.split("\\|", -1);
	    String[] mftFactory2Oids = mftFactory2.split("\\|", -1);

	    for (int i = 0; i < partTypeOids.length; i++) {

		String oid = partTypeOids[i];

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("partType", oid);

		List<String> list = new ArrayList<String>();
		String mftFactory = mftFactory1Oids[i];
		String[] oids = mftFactory.split(",", -1);

		for (String mftOid : oids) {
		    if (StringUtil.checkString(mftOid))
			list.add(mftOid);
		}

		map.put("mftFactory1", list);

		list = new ArrayList<String>();
		mftFactory = mftFactory2Oids[i];
		oids = mftFactory.split(",", -1);

		for (String mftOid : oids) {
		    if (StringUtil.checkString(mftOid))
			list.add(mftOid);
		}

		map.put("mftFactory2", list);

		partTypeList.add(map);
	    }
	}

	return partTypeList;
    }

    /**
     * <pre>
     * @description 
     * @author dhkim
     * @date 2018. 2. 26. 오후 3:52:28
     * @method getPartList
     * @param master
     * @return PartList
     * @throws Exception
     * </pre>
     */
    public PartList getPartList(E3PSProjectMaster master) throws Exception {
	return getPartList(master, false);
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 2. 12. 오후 12:08:01
     * @method getPartList
     * @return PartList
     * @throws Exception
     * </pre>
     */
    public PartList getPartList(E3PSProjectMaster master, boolean isGenerate) throws Exception {

	PartList partList = null;

	QuerySpec qs = new QuerySpec();
	qs.addClassList(PartList.class, true);
	qs.addClassList(PjtMasterPartListLink.class, false);

	QueryResult qr = PersistenceHelper.manager.navigate(master, PjtMasterPartListLink.PART_LIST_ROLE, qs, true);

	if (qr.hasMoreElements()) {
	    partList = (PartList) qr.nextElement();
	} else if (isGenerate) {
	    partList = PartList.newPartList();
	    partList = (PartList) PersistenceHelper.manager.save(partList);

	    PjtMasterPartListLink link = PjtMasterPartListLink.newPjtMasterPartListLink(partList, master);
	    PersistenceHelper.manager.save(link);
	}

	return partList;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 11. 오후 3:56:02
     * @method getCostAttributeList
     * @return List<CostAttribute>
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<CostAttribute> getCostAttributeList() throws WTException {

	List<CostAttribute> list = new ArrayList<CostAttribute>();

	QuerySpec qs = new QuerySpec(CostAttribute.class);
	QueryResult qr = PersistenceHelper.manager.find(qs);

	while (qr.hasMoreElements()) {
	    CostAttribute costAttr = (CostAttribute) qr.nextElement();
	    list.add(costAttr);
	}
	return list;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @param reqMap 
     * @date 2018. 1. 19. 오후 2:35:39
     * @method getCostAttrMapList
     * @return List<Map<String,String>>
     * @throws WTException
     * </pre>
     */
    public List<Map<String, String>> getCostAttrMapList(Map<String, String> reqMap) throws WTException {

	List<CostAttribute> attrList = getCostAttributeList();

	List<Map<String, String>> result = new ArrayList<Map<String, String>>();

	String useType = reqMap.get("useType");

	for (CostAttribute attr : attrList) {

	    Map<String, String> map = new HashMap<String, String>();

	    String attrType = attr.getAttrType();

	    if ("CALCULATOR".equals(useType)) {
		if (attrType.indexOf(CostUtil.FORMULATYPE) >= 0) {
		    continue;
		}
	    } else {
		if (attrType.indexOf(CostUtil.FORMULATYPE) < 0) {
		    continue;
		}
	    }

	    map.put("attrType", attr.getAttrType());
	    map.put("id", CommonUtil.getOIDString(attr));
	    map.put("code", attr.getCode());
	    map.put("name", attr.getName());

	    result.add(map);
	}

	return result;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 11. 오후 3:55:56
     * @method getCostAttributeList
     * @param attrType
     * @return List<CostAttribute>
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<CostAttribute> getCostAttributeList(String attrType) throws WTException {

	List<CostAttribute> list = new ArrayList<CostAttribute>();

	QuerySpec qs = new QuerySpec(CostAttribute.class);
	QueryResult qr = PersistenceHelper.manager.find(qs);

	qs.appendWhere(new SearchCondition(CostAttribute.class, CostAttribute.ATTR_TYPE, SearchCondition.EQUAL, attrType), new int[] { 0 });

	while (qr.hasMoreElements()) {
	    CostAttribute costAttr = (CostAttribute) qr.nextElement();
	    list.add(costAttr);
	}
	return list;

    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2017. 12. 11. 오후 3:55:47
     * @method getCostAttribute
     * @param code
     * @return CostAttribute
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CostAttribute getCostAttribute(String code) throws WTException {

	CostAttribute costAttr = null;

	if (StringUtils.isNotEmpty(code)) {
	    QuerySpec qs = new QuerySpec(CostAttribute.class);

	    qs.appendWhere(new SearchCondition(CostAttribute.class, CostAttribute.CODE, SearchCondition.EQUAL, code), new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostAttribute.class, CostAttribute.ATTR_TYPE, SearchCondition.LIKE, "%FORMULATYPE%"),
		    new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    if (rs.hasMoreElements()) {
		costAttr = (CostAttribute) rs.nextElement();
	    }
	}

	return costAttr;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 10. 25. 오전 10:06:18
     * @method getCostAttributeForName
     * @param name
     * @return CostAttribute
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CostAttribute getCostAttributeForName(String name) throws WTException {

	CostAttribute costAttr = null;

	if (StringUtils.isNotEmpty(name)) {
	    QuerySpec qs = new QuerySpec(CostAttribute.class);

	    qs.appendWhere(new SearchCondition(CostAttribute.class, CostAttribute.NAME, SearchCondition.EQUAL, name), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    if (rs.hasMoreElements()) {
		costAttr = (CostAttribute) rs.nextElement();
	    }
	}

	return costAttr;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:12
     * @method getCostFormula
     * @param attr
     * @param version
     * @return CostFormula
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CostFormula getCostFormula(CostAttribute attr, int version) throws WTException {

	CostFormula costFormula = null;

	if (attr != null) {
	    QuerySpec qs = new QuerySpec(CostFormula.class);

	    qs.appendWhere(
		    new SearchCondition(CostFormula.class, CostFormula.MAPPING_CODE, SearchCondition.EQUAL, CommonUtil.getOIDString(attr)),
		    new int[] { 0 });
	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostFormula.class, CostFormula.FORMULA_VERSION, SearchCondition.EQUAL, version),
		    new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    if (rs.hasMoreElements()) {
		costFormula = (CostFormula) rs.nextElement();
	    }
	}

	return costFormula;
    }

    /**
     * <pre>
     * @description 버전별 전체 수식 가져오기
     * @author dhkim
     * @date 2018. 10. 17. 오후 1:51:34
     * @method getCostFormulaList
     * @param version
     * @return List<CostFormula>
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<CostFormula> getCostFormulaList(int version) throws WTException {

	List<CostFormula> cfList = new ArrayList<CostFormula>();

	QuerySpec qs = new QuerySpec(CostFormula.class);
	qs.appendWhere(new SearchCondition(CostFormula.class, CostFormula.FORMULA_VERSION, SearchCondition.EQUAL, version), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(qs);

	while (rs.hasMoreElements()) {
	    CostFormula cf = (CostFormula) rs.nextElement();
	    cfList.add(cf);
	}

	return cfList;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:07
     * @method getCostAnalysis
     * @param part
     * @param year
     * @return CostAnalysis
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CostAnalysis getCostAnalysis(CostPart part, String year) throws WTException {

	CostAnalysis ca = null;
	if (part != null) {
	    QuerySpec qs = new QuerySpec(CostAnalysis.class);

	    qs.appendWhere(new SearchCondition(CostAnalysis.class, CostAnalysis.COST_PART_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(part)), new int[] { 0 });

	    if (qs.getConditionCount() > 0)
		qs.appendAnd();
	    qs.appendWhere(new SearchCondition(CostAnalysis.class, CostAnalysis.YEAR, SearchCondition.EQUAL, Integer.parseInt(year)),
		    new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    if (rs.hasMoreElements()) {
		ca = (CostAnalysis) rs.nextElement();
	    }
	}

	return ca;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 19. 오후 3:12:20
     * @method getCostAnalysis
     * @param part
     * @return List<CostAnalysis>
     * @throws Exception
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public List<CostAnalysis> getCostAnalysis(CostPart part) throws Exception {

	List<CostAnalysis> caList = new ArrayList<CostAnalysis>();
	if (part != null) {
	    QuerySpec qs = new QuerySpec(CostAnalysis.class);

	    qs.appendWhere(new SearchCondition(CostAnalysis.class, CostAnalysis.COST_PART_REFERENCE + "."
		    + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, CommonUtil.getOIDLongValue(part)), new int[] { 0 });

	    SearchUtil.setOrderBy(qs, CostAnalysis.class, 0, CostAnalysis.YEAR, false);

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    while (rs.hasMoreElements()) {
		CostAnalysis ca = (CostAnalysis) rs.nextElement();
		caList.add(ca);
	    }
	}

	return caList;
    }

    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 3. 9. 오전 11:26:02
     * @method getLatestCSInfo
     * @param infoType
     * @return CSInfo
     * @throws WTException
     * </pre>
     */
    @SuppressWarnings("deprecation")
    public CSInfo getLatestCSInfo(String infoType) throws WTException {

	CSInfo csInfo = null;

	try {

	    QuerySpec qs = new QuerySpec(CSInfo.class);
	    qs.appendWhere(new SearchCondition(CSInfo.class, CSInfo.INFO_TYPE, SearchCondition.EQUAL, infoType), new int[] { 0 });
	    SearchUtil.setOrderBy(qs, CSInfo.class, 0, CSInfo.VERSION, true);

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    if (rs.hasMoreElements()) {
		csInfo = (CSInfo) rs.nextElement();
	    }

	} catch (WTPropertyVetoException e) {
	    e.printStackTrace();
	}

	return csInfo;
    }

    @SuppressWarnings("deprecation")
    public List<CSInfo> getAllCSInfo(String infoType) throws WTException {

	List<CSInfo> list = new ArrayList<CSInfo>();

	try {

	    QuerySpec qs = new QuerySpec(CSInfo.class);
	    qs.appendWhere(new SearchCondition(CSInfo.class, CSInfo.INFO_TYPE, SearchCondition.EQUAL, infoType), new int[] { 0 });
	    SearchUtil.setOrderBy(qs, CSInfo.class, 0, CSInfo.VERSION, true);

	    QueryResult rs = PersistenceHelper.manager.find(qs);

	    while (rs.hasMoreElements()) {
		CSInfo csInfo = (CSInfo) rs.nextElement();
		list.add(csInfo);
	    }
	} catch (WTPropertyVetoException e) {
	    e.printStackTrace();
	}

	return list;
    }

    public List<CSInfoItemDTO> getCSInfoItemList(CSInfo csInfo) throws WTException {
	return getCSInfoItemList(csInfo, null);
    }

    @SuppressWarnings("deprecation")
    public List<CSInfoItemDTO> getCSInfoItemList(CSInfo csInfo, String itemType) throws WTException {

	List<CSInfoItemDTO> list = new ArrayList<CSInfoItemDTO>();

	try {
	    if (csInfo != null) {
		QuerySpec qs = new QuerySpec(CSInfoItem.class);

		qs.appendWhere(new SearchCondition(CSInfoItem.class, CSInfoItem.CSINFO_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		        SearchCondition.EQUAL, CommonUtil.getOIDLongValue(csInfo)), new int[] { 0 });

		if (itemType != null) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(new SearchCondition(CSInfoItem.class, CSInfoItem.ITEM_TYPE, SearchCondition.EQUAL, itemType),
			    new int[] { 0 });
		}

		SearchUtil.setOrderBy(qs, CSInfoItem.class, 0, CSInfoItem.SORT, false);

		QueryResult rs = PersistenceHelper.manager.find(qs);

		while (rs.hasMoreElements()) {
		    CSInfoItem csInfoItem = (CSInfoItem) rs.nextElement();
		    CSInfoItemDTO data = new CSInfoItemDTO(csInfoItem);
		    list.add(data);
		}
	    }
	} catch (WTPropertyVetoException e) {
	    e.printStackTrace();
	}

	return list;
    }

    /**
     * <pre>
     * @description OBJECT TO MAP CONVERT
     * @author dhkim
     * @date 2017. 11. 17. 오후 1:49:59
     * @method converObjectToMap
     * @param obj
     * @return Map<String,Object>
     * </pre>
     */
    public Map<String, Object> converObjectToMap(Object obj) throws Exception {

	Map<String, Object> result = new HashMap<String, Object>();
	try {
	    Method[] methods = obj.getClass().getMethods();

	    for (Method method : methods) {
		if (method.getName().indexOf("get") == 0 || method.getName().indexOf("is") == 0) {
		    String key = method.getName().substring(3);

		    if (method.getName().indexOf("is") == 0) {
			key = method.getName().substring(2);
		    }

		    key = key.substring(0, 1).toLowerCase() + key.substring(1);
		    Object value = method.invoke(obj);

		    if (value != null) {
			result.put(key, String.valueOf(value));
		    }
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * <pre>
     * @description MAP TO OBJECT CONVERT
     * @author dhkim
     * @date 2017. 11. 17. 오후 1:49:37
     * @method convertMapToObject
     * @param map
     * @param objClass
     * @return Object
     * </pre>
     */
    public Object convertMapToObject(Map<String, Object> map, Object object) throws Exception {

	map.remove("conceptualClassname");
	map.remove("class");
	map.remove("persistInfo");
	map.remove("masterReference");
	map.remove("parentReference");
	map.remove("classInfo");
	map.remove("master");

	String keyAttribute = null;
	String setMethodString = "set";
	String methodString = null;
	Iterator<String> itr = map.keySet().iterator();
	while (itr.hasNext()) {
	    keyAttribute = itr.next();
	    methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);

	    try {
		Method[] methods = object.getClass().getMethods();

		for (int i = 0; i <= methods.length - 1; i++) {

		    if (methodString.equals(methods[i].getName())) {

			Object data = map.get(keyAttribute);
			methods[i].invoke(object, data);
		    }
		}
	    } catch (SecurityException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// LOGGER.info("######## CONVERTMAPTOOBJECT NON SETTING ########" + methodString);
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}
	return object;
    }

    // 개발검토프로젝트에서 최신 개발검토의뢰서 찾아오기
    @SuppressWarnings("deprecation")
    public KETDevelopmentRequest getDvRequst(KETDevelopmentRequest DR) throws Exception {
	String requestNo = DR.getNumber();
	QuerySpec qs = new QuerySpec(KETDevelopmentRequest.class);
	qs.appendWhere(
	        new SearchCondition(KETDevelopmentRequest.class, KETDevelopmentRequest.NUMBER, SearchCondition.EQUAL, requestNo
	                .toUpperCase()), new int[] { 0 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(KETDevelopmentRequest.class, WTAttributeNameIfc.LATEST_ITERATION, SearchCondition.IS_TRUE),
	        new int[] { 0 });
	QueryResult rs = PersistenceHelper.manager.find(qs);
	KETDevelopmentRequest dvr = null;
	while (rs.hasMoreElements()) {
	    dvr = (KETDevelopmentRequest) rs.nextElement();
	}

	return dvr;
    }

    // 결재이력 정보
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, String> getWfmApproveInfo(Map<String, Object> reqMap) throws WTException {
	String reportOid = (String) reqMap.get("reportOid");
	WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(reportOid));
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);

	Map<String, String> resMap = new HashMap<String, String>();

	Vector vec = null;

	if (master != null) {

	    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    Collections.sort(vec, ComparatorUtil.APPROVALLINESORT);

	    if (vec != null) {

		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    String activityName = StringUtil.checkNull(history.getActivityName());

		    if (activityName.equals("요청") || activityName.equals("재작업")) {
			if (StringUtils.isNotEmpty(history.getComments())) {
			    String comment = history.getComments().replaceAll(System.getProperty("line.separator"), "<br>");
			    resMap.put("comment1", "<br>" + comment);
			}

		    }

		    if (activityName.equals("검토") && history.isLast()) {
			if (StringUtils.isNotEmpty(history.getComments())) {
			    String comment = history.getComments().replaceAll(System.getProperty("line.separator"), "<br>");
			    resMap.put("comment2", "<br>" + comment);
			}

		    }
		}

	    }
	}

	return resMap;
    }

    public String projectSalesAuth(E3PSProject project) throws Exception {
	WTUser salesUser = project.getBusiness();
	WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	String AUTH = "ERROR";
	String salesType = "Team_REVIEW11";
	if (project instanceof ProductProject) {
	    salesType = "Team_PRODUCT09";
	}
	if (salesUser == null || (salesUser != null && !sessionUser.equals(salesUser))) {
	    QueryResult memberQr = ProjectUserHelper.manager.getProjectRoleMember(project, sessionUser, salesType);

	    while (memberQr.hasMoreElements()) {
		ProjectMemberLink link = (ProjectMemberLink) memberQr.nextElement();
		salesUser = link.getMember();
	    }
	}

	if (salesUser != null) {

	    if (sessionUser.equals(salesUser)) {
		AUTH = "VIEW";
		return AUTH;
	    } else {
		AUTH = getDepartmentAuth(salesUser, sessionUser);

		if ("VIEW".equals(AUTH)) {
		    return "VIEW";
		}
	    }
	}
	return AUTH;
    }

    public void setCalcAuth(Map<String, Object> reqMap) throws Exception {

	String dataType = (String) reqMap.get("DATATYPE");

	if ("COSTPART".equals(dataType)) {
	    reqMap.put("CALCAUTH", false);

	    E3PSTask task = (E3PSTask) CommonUtil.getObject((String) reqMap.get("taskOid"));

	    WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	    boolean isCostManager = CommonUtil.isMember("원가관리", sessionUser);
	    boolean isCostMember = CommonUtil.isMember("원가조회", sessionUser);

	    if (isCostManager || isCostMember) {
		reqMap.put("CALCAUTH", true);
	    } else {
		WTUser taskUser = null;

		QueryResult masterList = TaskUserHelper.manager.getMaster(task);

		if (masterList != null) {

		    while (masterList.hasMoreElements()) {
			Object o[] = (Object[]) masterList.nextElement();
			TaskMemberLink link = (TaskMemberLink) o[0];
			PeopleData data = new PeopleData(link.getMember().getMember());
			taskUser = data.wtuser;
		    }
		}

		if (taskUser != null && sessionUser.getName().equals(taskUser.getName())) {
		    reqMap.put("CALCAUTH", true);
		}
	    }
	}
    }

    public String getCostAuthInfo(Map<String, Object> reqMap) throws Exception {

	E3PSProject project = null;
	String EDITMODE = (String) reqMap.get("EDITMODE");

	String AUTH = "ERROR";

	WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	boolean isCostManager = CommonUtil.isMember("원가관리", sessionUser);
	boolean isCostMember = CommonUtil.isMember("원가조회", sessionUser);

	String authProjectCheckOid = (String) reqMap.get("authProjectCheckOid");

	if ("COSTHISTORY".equals(EDITMODE)) {
	    String oid = (String) reqMap.get("oid");
	    project = (E3PSProject) CommonUtil.getObject(oid);
	    project = ProjectHelper.getProject(project.getPjtNo());// 최신프로젝트 가져오기

	    if ((isCostManager || isCostMember) || isPm(project, sessionUser, authProjectCheckOid)) {// 원가관리 or 원가조회 or pm
		AUTH = "VIEW";
		return AUTH;
	    } else {
		return projectSalesAuth(project);
	    }
	}

	String taskOid = (String) reqMap.get("taskOid");

	String reportOid = (String) reqMap.get("reportOid");

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String taskCode = task.getTaskTypeCategory();
	project = (E3PSProject) task.getProject();

	project = ProjectHelper.getProject(project.getPjtNo());// 최신프로젝트 가져오기

	int taskState = task.getTaskState();

	if (CommonUtil.isAdmin() && isCostManager) {
	    if ("TREEEDIT".equals(EDITMODE)) {
		return EDITMODE;
	    } else {
		AUTH = "EDIT";
	    }

	    return AUTH;
	}

	WTUser taskUser = null;
	WTUser memberUser = null;
	QueryResult masterList = TaskUserHelper.manager.getMaster(task);

	if (masterList != null) {

	    while (masterList.hasMoreElements()) {
		Object o[] = (Object[]) masterList.nextElement();
		TaskMemberLink link = (TaskMemberLink) o[0];
		PeopleData data = new PeopleData(link.getMember().getMember());
		taskUser = data.wtuser;
	    }
	}

	CostReport report = null;
	boolean isReportApproved = false;

	if ("COST016".equals(taskCode)) {
	    report = (CostReport) CommonUtil.getObject(reportOid);
	    if (report == null) {
		E3PSProjectMaster master = (E3PSProjectMaster) project.getMaster();

		String projectOid = CommonUtil.getOIDString(master);
		String version = task.getCostVersion();
		report = CostUtil.manager.getCostReport(projectOid, version);
	    }
	    if (report != null) {
		String state = report.getState().toString();
		isReportApproved = state.equals("APPROVED");
	    }
	}

	if (sessionUser.equals(taskUser)) {// task담당자
	    if (taskState != 5) {// task가 완료가 아니면

		if (("COST015".equals(taskCode) || "COST016".equals(taskCode))) {
		    reqMap.put("DATATYPE", "COSTPART");
		    List<Persistable> list = CostHelper.service.getCostRootList(reqMap);

		    for (Persistable obj : list) {
			if (0 == ((CostPart) obj).getSubVersion()) {
			    return "VIEW";
			}
		    }
		}

		if ("TREEEDIT".equals(EDITMODE)) {
		    return EDITMODE;
		} else {
		    AUTH = "EDIT";
		}

	    } else {
		AUTH = "VIEW";
		if ("COST016".equals(taskCode)) {
		    AUTH = "VIEW_EXCEL";
		}
	    }
	    return AUTH;
	}

	QueryResult memberlist = TaskUserHelper.manager.getMember(task);

	if (memberlist != null) {
	    while (memberlist.hasMoreElements()) {
		Object o[] = (Object[]) memberlist.nextElement();
		TaskMemberLink link = (TaskMemberLink) o[0];
		PeopleData data = new PeopleData(link.getMember().getMember());
		memberUser = data.wtuser;
	    }
	}

	if (sessionUser.equals(memberUser)) {// 해당 task의 참여자
	    AUTH = "VIEW";
	    return AUTH;
	}

	if ((taskState == 5 && "COST013".equals(taskCode)) || (isReportApproved && "COST016".equals(taskCode))) {// 원가요청서 or 보고서 일 경우 프로젝트의
		                                                                                                 // 영업담당자 , 팀장, 원가관리 / 원가조회
		                                                                                                 // 열람 허용

	    E3PSProject pjt = (E3PSProject) CommonUtil.getObject(authProjectCheckOid);

	    if (project instanceof ReviewProject && pjt != null && pjt instanceof ProductProject) {// 검토프로젝트와 연계된 개발프로젝트 리스트를 찾는다. 개발프로젝트
		                                                                                   // 영업에게도 열람권한부여 위함

		if ("VIEW".equals(projectSalesAuth(pjt))) {
		    return "VIEW_EXCEL";
		}
	    }

	    if ("VIEW".equals(projectSalesAuth(project)) || isCostManager || isCostMember) {
		return "VIEW_EXCEL";
	    }
	}

	if (isCostManager || isCostMember) {// 원가관리 or 원가조회
	    AUTH = "VIEW";
	    return AUTH;
	}

	AUTH = getDepartmentAuth(taskUser, sessionUser);

	if ("VIEW".equals(AUTH)) {
	    return AUTH;
	}

	// WTUser pmUser = ProjectUserHelper.manager.getPM(project);
	//
	// if (pmUser != null && sessionUser.equals(pmUser)) {// 접속자가 pm이면 view
	//
	// if ("COST016".equals(taskCode) && !isReportApproved) {
	//
	// } else {
	// AUTH = "VIEW";
	// return AUTH;
	// }
	//
	// }
	//
	// if (isReportApproved && "COST016".equals(taskCode)) {
	// project = (E3PSProject) CommonUtil.getObject((String) reqMap.get("projectAuth"));
	// if (project != null) {
	// pmUser = ProjectUserHelper.manager.getPM(project);
	// if (pmUser != null && sessionUser.equals(pmUser)) {
	// AUTH = "VIEW";
	// return AUTH;
	// }
	// }
	// }

	if ("COST016".equals(taskCode) && report != null && isReportApproved) {// 원가보고서 열람 권한 지정

	    if (isPm(project, sessionUser, authProjectCheckOid)) {// pm 이면 ok
		AUTH = "VIEW";
		return AUTH;
	    }

	    if (WorkflowSearchHelper.manager.userInApproval(report, sessionUser.getName())) {// 결재라인에 포함되어 있으면 ok
		AUTH = "VIEW";
		return AUTH;
	    }
	}

	return AUTH;
    }

    public boolean isPm(E3PSProject pjt, WTUser sessionUser, String authProjectCheckOid) throws Exception {

	WTUser pmUser = ProjectUserHelper.manager.getPM(pjt);

	if (pmUser != null && sessionUser.equals(pmUser)) {
	    return true;
	}

	E3PSProject project = (E3PSProject) CommonUtil.getObject(authProjectCheckOid);

	if (pjt instanceof ReviewProject && project != null && project instanceof ProductProject) {// 검토프로젝트와 연계된 개발프로젝트 리스트를 찾는다. 개발프로젝트
		                                                                                   // pm에게도 열람권한부여 위함

	    pmUser = ProjectUserHelper.manager.getPM(project);

	    if (pmUser != null && sessionUser.equals(pmUser)) {
		return true;
	    }
	}

	return false;
    }

    public String getDepartmentAuth(WTUser taskUser, WTUser sessionUser) throws Exception {

	String AUTH = "ERROR";

	PeopleData peoData = new PeopleData(taskUser);

	Department taskDept = peoData.department;// task 담당자의 부서정보

	WTUser chiefUser = DepartmentHelper.manager.getDepartChief(taskDept);

	if (chiefUser != null && sessionUser.equals(chiefUser)) {// 접속자가 task담당자의 팀장인지
	    AUTH = "VIEW";
	    return AUTH;
	}

	ArrayList<Department> OfficerList = new ArrayList<Department>();

	DepartmentHelper.manager.getParentsList(taskDept, OfficerList);

	for (Department parent : OfficerList) {

	    chiefUser = DepartmentHelper.manager.getDepartChief(parent);

	    if (chiefUser != null && sessionUser.equals(chiefUser)) {// 접속자가 task담당자의 상위부서장인지
		AUTH = "VIEW";
		return AUTH;
	    }
	}
	return AUTH;
    }

    public void taskVersionSync(E3PSTask task) throws Exception {

	// 개발검토BOM task 가 아니고 미완료 task 인 경우 최신 taskversion 으로 업데이트 한다

	if (!"COST001".equals(task.getTaskTypeCategory()) && task.getTaskState() != 5) {

	    E3PSProject project = (E3PSProject) task.getProject();

	    E3PSProjectMaster pjtMaster = (E3PSProjectMaster) project.getMaster();

	    PartList partList = CostUtil.manager.getPartList(pjtMaster);

	    int lastestVersion = 0;

	    if (partList != null) {

		lastestVersion = partList.getLastestVersion();

		if (Integer.parseInt(task.getCostVersion()) != lastestVersion) {
		    task.setCostVersion(Integer.toString((lastestVersion)));
		    task = (E3PSTask) PersistenceHelper.manager.save(task);
		}
	    }
	}
    }

    // 테스트용 싱크
    public static void load(String sFilePath) throws Exception {
	Transaction trx = new Transaction();
	try {

	    if (!RemoteMethodServer.ServerFlag) {
		Class c[] = new Class[] { String.class };
		Object o[] = new Object[] { sFilePath };

		RemoteMethodServer.getDefault().invoke("load", CostUtil.class.getName(), null, c, o);
		return;
	    }
	    trx.start();

	    QuerySpec qs = new QuerySpec();
	    qs.addClassList(CostPart.class, true);
	    qs.appendWhere(new SearchCondition(CostPart.class, CostPart.MASTER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID,
		    SearchCondition.NOT_EQUAL, Long.parseLong("0")), new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    List<CostPart> partList = new ArrayList<CostPart>();

	    while (qr.hasMoreElements()) {
		Object[] o = (Object[]) qr.nextElement();
		CostPart rootPart = (CostPart) o[0];
		partList.addAll(CostUtil.manager.syncCostPart(rootPart));
	    }

	    for (CostPart part : partList) {
		PersistenceHelper.manager.save(part);
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
    }

    public static void costReqListDataBuilder(Map<String, Object> reqMap, List<Map<String, Object>> caseList) throws Exception {

	String partListOid = (String) reqMap.get("partListOid");

	if (StringUtils.isNotEmpty(partListOid)) {
	    reqMap.put("lastest", "1");
	    reqMap.put("caseOrder", "1");
	    reqMap.put("DATATYPE", "COSTPART");
	    reqMap.put("orderByPartNo", "OK");
	    reqMap.put("orderByVersionDesc", "OK");

	    List<Persistable> rootList = CostHelper.service.getCostRootList(reqMap);

	    for (Persistable obj : rootList) {
		Map<String, Object> dataMap = CostUtil.manager.converObjectToMap(obj);
		String id = CommonUtil.getOIDString(obj);
		dataMap.put("id", id);
		CostPart part = (CostPart) obj;

		CostReport report = CostUtil.manager.getCostReport(CommonUtil.getOIDString(part.getProject()),
		        String.valueOf(part.getVersion()));

		if (report == null) {
		    dataMap.put("taskOid", "");
		    dataMap.put("reportState", "");
		} else {
		    dataMap.put("taskOid", CommonUtil.getOIDString(report.getTask()));
		    dataMap.put("reportState", report.getState().toString());
		}

		caseList.add(dataMap);
	    }

	    Map<String, Object> ParentMap = new HashMap<String, Object>();
	    String partNo = "";
	    for (Map<String, Object> part : caseList) {
		if (!partNo.equals((String) part.get("partNo"))) {
		    partNo = (String) part.get("partNo");
		    ParentMap.put(partNo, part.get("version"));
		}
	    }

	    String parentId = "";
	    for (Map<String, Object> partMap : caseList) {

		if (ParentMap.get(partMap.get("partNo")) != null) {
		    String version = (String) partMap.get("version");
		    String maxVersion = (String) ParentMap.get(partMap.get("partNo"));
		    if (version.equals(maxVersion)) {
			partMap.put("parentId", "0");
			partMap.put("level", "1");
			parentId = (String) partMap.get("id");
		    } else {
			partMap.put("parentId", parentId);
			partMap.put("level", "2");
		    }
		}

	    }
	}

    }

    public static void main(String args[]) {

	try {
	    RemoteMethodServer server = RemoteMethodServer.getDefault();

	    server.setAuthenticator(AuthHandler.getMethodAuthenticator("wcadmin"));

	    LOGGER.setLevel(Level.INFO);

	    String sFilePath = "";
	    LOGGER.info("############CostUtil SYNCCOST START####################");
	    if (args != null && args.length > 0 && args[0].trim().length() > 0) {
		sFilePath = args[0];
	    }
	    load(sFilePath);
	    LOGGER.info("############CostUtil SYNCCOST COMPLETE####################");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
