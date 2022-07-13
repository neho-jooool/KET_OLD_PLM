package ext.ket.project.product.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.part.WTPart;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.util.EjsConverUtil;

public class StandardKETProjectService extends StandardManager implements KETProjectService {

    private static final long serialVersionUID = 1L;

    public static StandardKETProjectService newStandardKETProjectService() throws WTException {
	StandardKETProjectService instance = new StandardKETProjectService();
	instance.initialize();
	return instance;
    }

    public String getTypeByBom(Long ida2a2) throws Exception {

	String type = "";

	int LEV = 0;
	boolean skip = false;

	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;
	PreparedStatement pstmt = null;

	// 제품의 하위 반제품에 해당하는 2Level 하위품목 중 ICT 의 값이 L이 한개라도 존재하면 사급, 전부 N이면 구매
	try {
	    StringBuffer sql = new StringBuffer();

	    sql.append(" SELECT LEV, ICT                                                                                                                                                                                                                       \n");
	    sql.append("   FROM (                                                                                                                                                                                                                         \n");
	    sql.append(" 		SELECT LEVEL AS LEV,                                                                                                                                                                                                      \n");
	    sql.append(" 	           BOM.PARTNO,                                                                                                                                                                                                        \n");
	    sql.append(" 	           BOM.PARENTITEMCODE,                                                                                                                                                                                                \n");
	    sql.append(" 	           BOM.PARENT_WTPART_OID,                                                                                                                                                                                             \n");
	    sql.append(" 	           BOM.WTMASER_OID,                                                                                                                                                                                                   \n");
	    sql.append(" 	           BOM.VERSION_WTPART_OID,                                                                                                                                                                                            \n");
	    sql.append(" 	           LAST_WTPART_OID,                                                                                                                                                                                                   \n");
	    sql.append(" 	           ICT                                                                                                                                                                                                                \n");
	    sql.append(" 	      FROM (SELECT WTPARTNUMBER AS PARTNO, '' AS PARENTITEMCODE,                                                                                                                                                              \n");
	    sql.append(" 	                   0 AS ITEMSEQ, 0 AS PARENT_WTPART_OID,0 AS WTMASER_OID, '0' AS VERSION_WTPART_OID,                                                                                                                          \n");
	    sql.append(" 	                   P.IDA2A2 AS LAST_WTPART_OID, '' AS ICT FROM WTPART P, WTPARTMASTER M                                                                                                                                       \n");
	    sql.append(" 	             WHERE P.IDA3MASTERREFERENCE = M.IDA2A2                                                                                                                                                                           \n");
	    sql.append(" 	   	           AND P.IDA2A2 = "
		    + ida2a2
		    + "                                                                                                                                                                                    \n");
	    sql.append(" 	         UNION ALL                                                                                                                                                                                                            \n");
	    sql.append(" 	            SELECT B.CHILDITEMCODE AS PARTNO ,B.PARENTITEMCODE ,B.ITEMSEQ ,B.IDA3A5  AS PARENT_WTPART_OID, B.IDA3B5  AS WTMASER_OID ,B.VERSIONITEMCODE AS VERSION_WTPART_OID                                                  \n");
	    sql.append(" 	                   ,(SELECT MAX(P.IDA2A2) FROM WTPART P  WHERE P.LATESTITERATIONINFO = 1  AND P.STATECHECKOUTINFO != 'wrk' AND P.STATESTATE = 'APPROVED' AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID, ICT       \n");
	    sql.append(" 	              FROM KETPARTUSAGELINK B ) BOM                                                                                                                                                                                   \n");
	    sql.append(" 	           START WITH BOM.PARENT_WTPART_OID  = 0                                                                                                                                                                              \n");
	    sql.append(" 	           CONNECT BY PRIOR BOM.LAST_WTPART_OID =  BOM.PARENT_WTPART_OID                                                                                                                                                      \n");
	    sql.append(" 	           ORDER SIBLINGS BY ITEMSEQ                                                                                                                                                                                          \n");
	    sql.append(" 	    ) BOM, WTPART A, WTPARTMASTER B                                                                                                                                                                                           \n");
	    sql.append("   WHERE BOM.LAST_WTPART_OID = A.IDA2A2                                                                                                                                                                                           \n");
	    sql.append(" 	AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                                                                                                                          \n");

	    conn = (WTConnection) mContext.getConnection();

	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    List<Map<String, Object>> rsList = ObjectUtil.manager.rsToList(rs);

	    for (Map<String, Object> bom : rsList) {

		String ICT = (String) bom.get("ICT");
		LEV = Integer.parseInt((String) bom.get("LEV"));

		if (skip) {
		    if (LEV == 2) {// 제품의 하위 1레벨 중 순서상 최초 반제품만 검증한다
			break;
		    }
		}

		skip = (LEV == 2);// 제품의 하위 1레벨 중 순서상 최초 반제품만 검증한다

		if (LEV == 3) {
		    if ("L".equals(ICT)) {
			type = "사급";
			break;
		    } else {
			type = "구매";
		    }
		}

	    }

	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmt != null) {
		pstmt.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return type;
    }

    @Override
    public Map<String, Object> findWareHousingList(Map<String, Object> reqMap) throws Exception {

	String pjtNo = (String) reqMap.get("pjtNo");
	ProductProject pjt = (ProductProject) ProjectHelper.getProject(pjtNo);
	List<Map<String, Object>> partList = new ArrayList<Map<String, Object>>();

	if (pjt != null) {

	    QuerySpec qs = new QuerySpec();
	    int idxpi = qs.appendClassList(ProductInfo.class, true);
	    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(pjt));
	    qs.appendWhere(cs, new int[] { idxpi });
	    QueryResult rt = PersistenceHelper.manager.find(qs);

	    int rnum = 1;

	    while (rt.hasMoreElements()) {

		Object o[] = (Object[]) rt.nextElement();
		ProductInfo pi = (ProductInfo) o[0];
		String partNo = pi.getPNum();
		String partName = pi.getPName();

		WTPart part = PartBaseHelper.service.getLatestPart(partNo);

		if (part == null) {
		    continue;
		}

		String WhPlant = PartSpecGetter.getPartSpec(part, PartSpecEnum.WhPlant);
		String whPlantName = "";
		NumberCode code = CodeHelper.manager.getNumberCode("COSTWAREHOUSING", WhPlant);
		if (code != null) {
		    whPlantName = code.getName();
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("CanDelete", "0");
		dataMap.put("CanSelect", "1");

		dataMap.put("rnum", rnum++);
		dataMap.put("pjtNoHtmlPrefix", "<div>");
		dataMap.put("pjtNo", "<a href=javascript:openProject('" + pjtNo + "');>" + pjtNo + "<a>");
		dataMap.put("pjtNoHtmlPostfix", "</div>");

		dataMap.put("partNoHtmlPrefix", "<div>");
		dataMap.put("partNo", "<a href=javascript:openViewPart('" + partNo + "');>" + partNo + "<a>");
		dataMap.put("partNoHtmlPostfix", "</div>");
		dataMap.put("partName", partName);

		dataMap.put("whPlantName", whPlantName);
		dataMap.put("type", getTypeByBom(CommonUtil.getOIDLongValue(part)));

		partList.add(dataMap);

	    }
	}
	return EjsConverUtil.convertToDto(partList);
    }

}
